package org.codeforiraq.storeintextfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editText.getText().toString().isEmpty()){
                   String message = editText.getText().toString();
                    writeToText(message);
                }

            }
        });


        try {
            if(readData() != null){
                textView.setText(readData());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private  void writeToText(String filetext){

        try {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(openFileOutput("myfile.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(filetext);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readData() throws IOException {
        String myStoredText = "";
        InputStream inputStream = openFileInput("myfile.txt");
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String tempData = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ( (tempData = bufferedReader.readLine()) != null ){
                stringBuilder.append(tempData);
            }
            inputStream.close();
            myStoredText = stringBuilder.toString();
        }
        return  myStoredText;
    }

}
