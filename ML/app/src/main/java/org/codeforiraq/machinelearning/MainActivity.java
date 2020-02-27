package org.codeforiraq.machinelearning;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.codeforiraq.machinelearning.configs.GtsrbQuantConfig;
import org.codeforiraq.machinelearning.configs.ModelConfig;
import com.otaliastudios.cameraview.CameraView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements ClassificationFrameProcessor.ClassificationListener {

    private CameraView cameraView;
    private TextView tvClassification;
    private ClassificationFrameProcessor classificationFrameProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvClassification = findViewById(R.id.tvClassification);
        cameraView = findViewById(R.id.cameraView);
        cameraView.setLifecycleOwner(this);

        initClassification();
    }

    private void initClassification() {
        try {
            ModelConfig modelConfig = new GtsrbQuantConfig();
            classificationFrameProcessor = new ClassificationFrameProcessor(this, this, modelConfig);
            cameraView.addFrameProcessor(classificationFrameProcessor);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Frame Processor initialization failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClassifiedFrame(List<ClassificationResult> classificationResults) {
        StringBuilder results = new StringBuilder("Classification:\n");
        if (classificationResults.size() == 0) {
            results.append("No results");
        } else {
            for (ClassificationResult classificationResult : classificationResults) {
                results.append(classificationResult.title)
                        .append("(")
                        .append(classificationResult.confidence * 100)
                        .append("%)\n");
            }
        }

        runOnUiThread(() -> tvClassification.setText(results));

    }
}