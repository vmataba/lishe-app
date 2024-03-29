package com.example.emmanuel.lisheapp.tools;

import android.content.Context;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpDateGenerator;
import org.apache.http.util.EntityUtils;

public class Api {

    public static final String VALIDATION_KEY = "IfDBVthtGXKxZh6ktW43TNJ6hWNUEOS4";
    public static final String BASE_URL = "http://lisheapp.smprotz.com/index.php?r=";
    public static final String FAILED_PROCESS_MESSAGE = "Connection Failure";

    public String post(int timeoutConnection, int timeoutSocket, String url, String xml) {

        String finalResponse = "Connection Failure";
        try {

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpPost httppost = new HttpPost(url);
            StringEntity entity;
            entity = new StringEntity(xml, "UTF-8");
            httppost.setEntity(entity);
            httppost.addHeader("Accept", "text/json");
            httppost.addHeader("Content-Type", "text/json");
            HttpResponse response = httpclient.execute(httppost);
            finalResponse = EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            finalResponse = e.toString();
        }
        return finalResponse;

    }


    public String get(int timeoutConnection, int timeoutSocket, String url,String xml) {

        String finalResponse = "Connection Failure";
        try {
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Accept", "text/xml");
            httpGet.addHeader("Content-Type", "text/xml");
            HttpResponse response = httpclient.execute(httpGet);
            finalResponse = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            //finalResponse =e.getMessage();
            //finalResponse = "exp";
            finalResponse = e.toString();
        }
        return finalResponse;

    }


    public String postUrl(int timeoutConnection, int timeoutSocket, String url, String xml) {
        String finalResponse = null;
        try {

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpPost httppost = new HttpPost(url);
            StringEntity entity;
            entity = new StringEntity(xml, "UTF-8");
            httppost.setEntity(entity);
            httppost.addHeader("Accept", "text/xml");
            httppost.addHeader("Content-Type", "text/xml");
            HttpResponse response = httpclient.execute(httppost);
            finalResponse = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            //return e.getMessage();
             //return FAILED_PROCESS_MESSAGE;
            finalResponse = FAILED_PROCESS_MESSAGE;

        }
        return finalResponse;
    }

}
