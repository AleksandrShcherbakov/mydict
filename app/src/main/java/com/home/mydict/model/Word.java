package com.home.mydict.model;

import android.annotation.SuppressLint;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Word {
    private String enWord;
    private String transcription;
    private String translate;

    private static final int count=10;

    public static List<Word> words= new ArrayList<>();

    public Word(String enWord, String transcription, String translate) {
        this.enWord = enWord;
        this.transcription = transcription;
        this.translate = translate;
    }

    public String getEnWord() {
        return enWord;
    }

    public void setEnWord(String enWord) {
        this.enWord = enWord;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    @Override
    public String toString() {
        return "Word{" +
                "enWord='" + enWord + '\'' +
                ", transcription='" + transcription + '\'' +
                ", translate='" + translate + '\'' +
                '}';
    }

    public static String getStringForTextView(){
        if (!words.isEmpty()){
            StringBuilder stringBuilder = new StringBuilder();
            int localCount=0;
            for (int i=words.size()-1; i>=0; i--){
                stringBuilder.append(words.get(i).getDictStr());
                stringBuilder.append("\n");
                localCount++;
                if (localCount==count){
                    break;
                }
            }
            return stringBuilder.toString();
        }
        return "";
    }

    public static String getStringForFile() {
        if (!words.isEmpty()){
            StringBuilder stringBuilder = new StringBuilder();
            for (int i=0; i<words.size(); i++){
                stringBuilder.append(words.get(i).getDictStr());
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }
        return "";
    }

    public static void addWordToWords(Word word) {
        if (!Word.words.contains(word)) {
            Word.words.add(word);
        }
    }

    private String getDictStr(){
        return this.enWord+" "+this.transcription+" "+this.translate;
    }

    @SuppressLint("NewApi")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(enWord, word.enWord) &&
                Objects.equals(transcription, word.transcription) &&
                Objects.equals(translate, word.translate);
    }

    @SuppressLint("NewApi")
    @Override
    public int hashCode() {
        return Objects.hash(enWord, transcription, translate);
    }
}
