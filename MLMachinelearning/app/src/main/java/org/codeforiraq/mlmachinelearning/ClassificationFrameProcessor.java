package org.codeforiraq.mlmachinelearning;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;

import androidx.annotation.NonNull;
import com.otaliastudios.cameraview.Frame;
import com.otaliastudios.cameraview.FrameProcessor;

import org.codeforiraq.mlmachinelearning.configs.ModelConfig;
import org.tensorflow.lite.Interpreter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class ClassificationFrameProcessor implements FrameProcessor {

    private static final int MAX_CLASSIFICATION_RESULTS = 3;
    private static final float CLASSIFICATION_THRESHOLD = 0.2f;

    private final Interpreter interpreter;
    private final List<String> labels;
    private final ClassificationListener classificationListener;
    private final ModelConfig modelConfig;

    public ClassificationFrameProcessor(Context context,
                                        ClassificationListener listener,
                                        ModelConfig modelConfig) throws IOException {
        ByteBuffer model = AssetsUtils.loadFile(context, modelConfig.getModelFilename());
        this.interpreter = new Interpreter(model);
        this.labels = AssetsUtils.loadLines(context, modelConfig.getLabelsFilename());
        this.classificationListener = listener;
        this.modelConfig = modelConfig;
    }

    @Override
    public void process(@NonNull Frame frame) {
        Bitmap bitmap = ImageUtils.frameToBitmap(frame);
        Bitmap toClassify = ThumbnailUtils.extractThumbnail(
                bitmap, modelConfig.getInputWidth(), modelConfig.getInputHeight()
        );
        bitmap.recycle();

        ByteBuffer byteBufferToClassify = bitmapToModelsMatchingByteBuffer(toClassify);
        if (modelConfig.isQuantized()) {
            runInferenceOnQuantizedModel(byteBufferToClassify);
        } else {
            runInferenceOnFloatModel(byteBufferToClassify);
        }
    }

    private void runInferenceOnQuantizedModel(ByteBuffer byteBufferToClassify) {
        byte[][] result = new byte[1][labels.size()];
        interpreter.run(byteBufferToClassify, result);
        float[][] resultFloats = new float[1][labels.size()];
        byte[] bytes = result[0];
        for (int i = 0; i < bytes.length; i++) {
            float resultF = (bytes[i] & 0xff) / 255.f;
            resultFloats[0][i] = resultF;

        }
        classificationListener.onClassifiedFrame(getSortedResult(resultFloats));
    }

    private void runInferenceOnFloatModel(ByteBuffer byteBufferToClassify) {
        float[][] result = new float[1][labels.size()];
        interpreter.run(byteBufferToClassify, result);
        classificationListener.onClassifiedFrame(getSortedResult(result));
    }

    private ByteBuffer bitmapToModelsMatchingByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(modelConfig.getInputSize());
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues = new int[modelConfig.getInputWidth() * modelConfig.getInputHeight()];
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int pixel = 0;
        for (int i = 0; i < modelConfig.getInputWidth(); ++i) {
            for (int j = 0; j < modelConfig.getInputHeight(); ++j) {
                int pixelVal = intValues[pixel++];
                if (modelConfig.isQuantized()) {
                    for (byte channelVal : pixelToChannelValuesQuant(pixelVal)) {
                        byteBuffer.put(channelVal);
                    }
                } else {
                    for (float channelVal : pixelToChannelValues(pixelVal)) {
                        byteBuffer.putFloat(channelVal);
                    }
                }
            }
        }
        return byteBuffer;
    }

    private float[] pixelToChannelValues(int pixel) {
        if (modelConfig.getChannelsCount() == 1) {
            float[] singleChannelVal = new float[1];
            float rChannel = (pixel >> 16) & 0xFF;
            float gChannel = (pixel >> 8) & 0xFF;
            float bChannel = (pixel) & 0xFF;
            singleChannelVal[0] = (rChannel + gChannel + bChannel) / 3 / modelConfig.getStd();
            return singleChannelVal;
        } else if (modelConfig.getChannelsCount() == 3) {
            float[] rgbVals = new float[3];
            rgbVals[0] = ((((pixel >> 16) & 0xFF) - modelConfig.getMean()) / modelConfig.getStd());
            rgbVals[1] = ((((pixel >> 8) & 0xFF) - modelConfig.getMean()) / modelConfig.getStd());
            rgbVals[2] = ((((pixel) & 0xFF) - modelConfig.getMean()) / modelConfig.getStd());
            return rgbVals;
        } else {
            throw new RuntimeException("Only 1 or 3 channels supported at the moment.");
        }
    }

    private byte[] pixelToChannelValuesQuant(int pixel) {
        byte[] rgbVals = new byte[3];
        rgbVals[0] = (byte) ((pixel >> 16) & 0xFF);
        rgbVals[1] = (byte) ((pixel >> 8) & 0xFF);
        rgbVals[2] = (byte) ((pixel) & 0xFF);
        return rgbVals;
    }

    private List<ClassificationResult> getSortedResult(float[][] resultsArray) {
        PriorityQueue<ClassificationResult> sortedResults = new PriorityQueue<>(
                MAX_CLASSIFICATION_RESULTS,
                (lhs, rhs) -> Float.compare(rhs.confidence, lhs.confidence)
        );


        for (int i = 0; i < labels.size(); ++i) {
            float confidence = resultsArray[0][i];
            if (confidence > CLASSIFICATION_THRESHOLD) {
                sortedResults.add(new ClassificationResult(labels.get(i), confidence));
            }
        }

        return new ArrayList<>(sortedResults);
    }

    public interface ClassificationListener {
        void onClassifiedFrame(List<ClassificationResult> classificationResults);
    }
}
