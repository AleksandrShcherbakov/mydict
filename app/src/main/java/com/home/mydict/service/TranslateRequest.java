package com.home.mydict.service;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface TranslateRequest {
    Document getHtmlPage(String url) throws IOException;
}
