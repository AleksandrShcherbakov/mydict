package com.home.mydict.model;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.home.mydict.util.StringUtils;

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

    @Deprecated
    public static String getStringForTextView(TextViewType textViewType){
        if (!words.isEmpty()){
            StringBuilder stringBuilder = new StringBuilder();
            int localCount=0;
            for (int i=words.size()-1; i>=0; i--){
                switch (textViewType){
                    case DICT:
                        stringBuilder.append(words.get(i).getDictStr());
                        break;
                    case ENG:
                        stringBuilder.append(words.get(i).getEngStr());
                        break;
                    case RUS:
                        stringBuilder.append(words.get(i).getRusStr());
                        break;
                }
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

    public static SpannableString getSpannableStringForTextView(TextViewType textViewType){
        if (!words.isEmpty()){
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
            int localCount=0;
            for (int i=words.size()-1; i>=0; i--){
                switch (textViewType){
                    case DICT:
                        stringBuilder.append(words.get(i).getDictStr());
                        break;
                    case ENG:
                        stringBuilder.append(words.get(i).getSpanRus());
                        break;
                    case RUS:
                        stringBuilder.append(words.get(i).getSpanEng());
                        break;
                }
                stringBuilder.append("\n");
                localCount++;
                if (localCount==count){
                    break;
                }
            }
            return SpannableString.valueOf(stringBuilder);
        }
        return SpannableString.valueOf("");
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

    private SpannableString getSpanEng(){
        SpannableString spannableString = new SpannableString(this.getDictStr());
        spannableString.setSpan(new ForegroundColorSpan(000000),0,this.getEngLength(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    private SpannableString getSpanRus(){
        int dictStrLength = this.getDictStr().length();
        int start = dictStrLength - this.getRusLength();
        SpannableString spannableString = new SpannableString(this.getDictStr());
        spannableString.setSpan(new ForegroundColorSpan(000000),start,dictStrLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }


    private String getDictStr(){
        return this.enWord+" "+this.transcription+" "+this.translate;
    }

    private String getEngStr(){
        return this.enWord+" "+this.transcription+" "+ StringUtils.generateEmptyString(this.getRusLength());
    }

    public int getRusLength(){
        return this.translate.length();
    }

    public int getEngLength(){
        return (this.enWord+" "+this.transcription).length();
    }

    private String getRusStr(){
        return StringUtils.generateEmptyString(this.getEngLength())+" "+this.translate;
    }

    public enum TextViewType {
        DICT,
        RUS,
        ENG
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
