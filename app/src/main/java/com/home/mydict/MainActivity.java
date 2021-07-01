package com.home.mydict;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.home.mydict.model.Word;
import com.home.mydict.service.FileService;
import com.home.mydict.service.FileServiceImpl;
import com.home.mydict.service.PromtTranslateRequest;
import com.home.mydict.service.PromtTranslateService;
import com.home.mydict.service.TranslateRequest;
import com.home.mydict.service.TranslateService;

import org.jsoup.nodes.Document;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;
    Button button;
    TranslateService translateService;
    TranslateRequest translateRequest;
    FileService fileService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.textView);
        editText=findViewById(R.id.editText);
        button =findViewById(R.id.button);

        fileService = new FileServiceImpl(this);
        translateService=new PromtTranslateService();
        translateRequest=new PromtTranslateRequest(this);

        try {
            fileService.readFile();
            textView.setText(Word.getStringForTextView());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void okClick(View view) throws IOException {

        final String enWord = editText.getText().toString();
        final boolean[] isWordFound = {false};
        final boolean[] isWordExists = {false};

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Document document = translateRequest.getHtmlPage(enWord);
                    String transcription = translateService.getTranscription(document);
                    String translate = translateService.getTranslate(document);
                    if (!transcription.isEmpty() && !translate.isEmpty()) {
                        isWordFound[0] =true;
                        Word word = new Word(enWord, transcription, translate);
                        if (!Word.words.contains(word)) {
                            Word.words.add(word);
                        } else {
                            isWordExists[0]=true;
                        }
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

        if (!isWordFound[0]) {
            showToast("перевод для слова "+enWord+" не найден");
        }
        if (isWordExists[0]) {
            showToast("слово "+enWord+" уже было в поиске");
        }

        System.out.println(Word.words);

        textView.setText(Word.getStringForTextView());

        fileService.writeFile();
    }

    private void showToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,0);
        toast.show();
    }

}
