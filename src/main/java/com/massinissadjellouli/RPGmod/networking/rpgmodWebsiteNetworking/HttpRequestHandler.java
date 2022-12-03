package com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.Optional;

public class HttpRequestHandler {
    public static Optional<CloseableHttpResponse> sendPost(String targetURL, String data) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(targetURL);
        try {
            post.setEntity(new StringEntity(data));
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            return Optional.of(client.execute(post));
        }catch (Exception e){
            return Optional.empty();
        }
    }
    public static Optional<CloseableHttpResponse> sendGet(String targetURL) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(targetURL);
        try {
            get.setHeader("Accept", "application/json");
            get.setHeader("Content-type", "application/json");
            return Optional.of(client.execute(get));
        }catch (Exception e){
            return Optional.empty();
        }
    }
    public static Optional<CloseableHttpResponse> sendPost(String targetURL, String data,String token) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(targetURL);
        try {
            post.setEntity(new StringEntity(data));
            post.setHeader("Accept", "application/json");
            post.setHeader("Authorization", "Bearer " + token);
            post.setHeader("Content-type", "application/json");
            return Optional.of(client.execute(post));
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
