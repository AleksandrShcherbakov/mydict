package com.home.mydict.ssl;

import android.content.res.AssetManager;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SSLSocketPromt implements SSLSocket {

    private AppCompatActivity activity;

    public SSLSocketPromt(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public SSLSocketFactory getSocketFactory() {
        try {
            KeyStore trustStore = getTrustStore();
            TrustManager[] trustManagers = trustManagerFactory(trustStore).getTrustManagers();
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustManagers, new SecureRandom());
            return sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private KeyStore getTrustStore(){
        try {
            AssetManager am = activity.getAssets();
            InputStream fis = am.open("promt.p12");
            String pass = "111111";

            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(fis,pass.toCharArray());
            return keyStore;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private TrustManagerFactory trustManagerFactory(KeyStore keyStore){
        TrustManagerFactory trustManagerFactory = null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance("X509");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            trustManagerFactory.init(keyStore);
            return trustManagerFactory;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }
}
