package com.home.mydict.service;

import org.jsoup.nodes.Document;

public interface TranslateService {
    String getTranscription(Document document);
    String getTranslate(Document document);
}
