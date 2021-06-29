package com.home.mydict.service;

import com.home.mydict.ssl.SSLSocket;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class PromtTranslateRequest implements TranslateRequest {

    final String url = "https://www.translate.ru/%D0%BF%D0%B5%D1%80%D0%B5%D0%B2%D0%BE%D0%B4/%D0%B0%D0%BD%D0%B3%D0%BB%D0%B8%D0%B9%D1%81%D0%BA%D0%B8%D0%B9-%D1%80%D1%83%D1%81%D1%81%D0%BA%D0%B8%D0%B9/";

    @Override
    public Document getHtmlPage(String word) throws IOException {
        Document document = Jsoup.connect(url+word).timeout(10000).sslSocketFactory(SSLSocket.trustAllCertificates()).get();
        return document;
    }
}
