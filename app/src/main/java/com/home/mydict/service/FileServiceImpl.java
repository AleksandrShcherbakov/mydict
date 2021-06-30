package com.home.mydict.service;

import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

import com.home.mydict.MainActivity;
import com.home.mydict.model.Word;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileServiceImpl implements FileService {

    private AppCompatActivity activity;
    private final File externalDirectory;
    private final String filename = "dictionaty_file";
    private final String filePath;

    public FileServiceImpl(AppCompatActivity activity) {
        this.activity = activity;
        this.externalDirectory = activity.getExternalFilesDir(null);
        this.filePath = externalDirectory.getPath()+"/"+filename;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            boolean isFileExists = Files.exists(Paths.get(this.filePath));
            if (!isFileExists){
                try {
                    Files.createFile(Paths.get(filePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void readFile() throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while (bufferedReader.ready()) {
            String string = bufferedReader.readLine();
            String[] strings = string.split(" ");
            if (strings.length==3) {
                Word word = new Word(strings[0], strings[1], strings[2]);
                Word.addWordToWords(word);
            }
        }
        bufferedReader.close();
    }

    @Override
    public void writeFile() throws IOException {
        FileWriter fileWriter = new FileWriter(new File(filePath));
        fileWriter.write(Word.getStringForFile());
        fileWriter.close();
    }
}
