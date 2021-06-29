package com.home.mydict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.home.mydict.model.Word;
import com.home.mydict.service.PromtTranslateRequest;
import com.home.mydict.service.PromtTranslateService;
import com.home.mydict.service.TranslateRequest;
import com.home.mydict.service.TranslateService;
import com.home.mydict.ssl.SSLSocket;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;
    Button button;
    TranslateService translateService = new PromtTranslateService();
    TranslateRequest translateRequest = new PromtTranslateRequest();
    String fileName="dictionary_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.textView);
        editText=findViewById(R.id.editText);
        button =findViewById(R.id.button);

        try {
            readFile();
            textView.setText(Word.getStringForTextView());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void okClick(View view) throws IOException {

        final String enWord = editText.getText().toString();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Document document = translateRequest.getHtmlPage(enWord);
                    String transcription = translateService.getTranscription(document);
                    String translate = translateService.getTranslate(document);
                    Word word = new Word(enWord, transcription, translate);
                    if (!Word.words.contains(word)) {
                        Word.words.add(word);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Word.words);

        textView.setText(Word.getStringForTextView());

        writeFile();
    }

    private void writeFile() throws IOException {
        File dir = this.getExternalFilesDir(null);
        String filePath = dir.getPath()+"/"+fileName;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean fileExists = Files.exists(Paths.get(filePath));
            if (!fileExists){
                try {
                    Files.createFile(Paths.get(filePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                FileWriter fileWriter = new FileWriter(new File(filePath));
                fileWriter.write(Word.getStringForTextView());
                fileWriter.close();
            }
        }
    }

    private void readFile() throws IOException {
        File dir = this.getExternalFilesDir(null);
        String filePath = dir.getPath()+"/"+fileName;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            boolean fileExists = Files.exists(Paths.get(filePath));
            if (!fileExists){
                try {
                    Files.createFile(Paths.get(filePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                FileReader fileReader = new FileReader(filePath);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while (bufferedReader.ready()) {
                    String string = bufferedReader.readLine();
                    String[] strings = string.split(" ");
                    Word word = new Word(strings[0], strings[1], strings[2]);
                    if (!Word.words.contains(word)) {
                        Word.words.add(word);
                    }
                }
                bufferedReader.close();
            }
        }
    }

}
