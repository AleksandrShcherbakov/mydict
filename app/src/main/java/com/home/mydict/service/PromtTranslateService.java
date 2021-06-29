package com.home.mydict.service;

import org.jsoup.nodes.Document;

public class PromtTranslateService implements TranslateService {

    @Override
    public String getTranscription(Document document) {
        String className = "transcription";
        String transcription = document.getElementsByClass(className).first().text();
        return transcription;
    }

    @Override
    public String getTranslate(Document document) {
        String className = "result_only sayWord";
        String translate = document.getElementsByClass(className).first().text();
        return translate;
    }
}
