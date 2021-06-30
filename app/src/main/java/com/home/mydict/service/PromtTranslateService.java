package com.home.mydict.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PromtTranslateService implements TranslateService {

    @Override
    public String getTranscription(Document document) {
        String className = "transcription";
        Element transcriptionelement = document.getElementsByClass(className).first();
        if (transcriptionelement!=null) {
            return transcriptionelement.text();
        }
        return "";
    }

    @Override
    public String getTranslate(Document document) {
        String className = "result_only sayWord";
        Element translateElement = document.getElementsByClass(className).first();
        if (translateElement!=null) {
            return translateElement.text();
        }
        return "";
    }
}
