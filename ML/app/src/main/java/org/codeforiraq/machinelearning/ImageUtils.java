package org.codeforiraq.machinelearning;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;

import androidx.annotation.NonNull;

import com.otaliastudios.cameraview.Frame;

import java.io.ByteArrayOutputStream;

public class ImageUtils {

    public static Bitmap frameToBitmap(@NonNull Frame frame) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        YuvImage yuvImage = new YuvImage(
                frame.getData(),
                ImageFormat.NV21,
                frame.getSize().getWidth(),
                frame.getSize().getHeight(),
                null
        );
        Rect rectangle = new Rect(0, 0, frame.getSize().getWidth(), frame.getSize().getHeight());
        yuvImage.compressToJpeg(rectangle, 90, out);
        byte[] imageBytes = out.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        if (frame.getRotation() != 0) {
            Matrix matrix = new Matrix();
            matrix.setRotate(frame.getRotation());
            Bitmap temp = bitmap;
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            temp.recycle();
        }

        return bitmap;
    }
}
