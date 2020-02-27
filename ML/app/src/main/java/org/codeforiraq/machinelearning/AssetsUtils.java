package org.codeforiraq.machinelearning;

import android.content.Context;
import android.content.res.AssetFileDescriptor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AssetsUtils {
    public static ByteBuffer loadFile(Context context, String filename) throws IOException {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd(filename);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    public static List<String> loadLines(Context context, String filename) throws IOException {
        Scanner s = new Scanner(new InputStreamReader(context.getAssets().open(filename)));
        ArrayList<String> labels = new ArrayList<>();
        while (s.hasNextLine()) {
            labels.add(s.nextLine());
        }
        s.close();
        return labels;
    }
}
