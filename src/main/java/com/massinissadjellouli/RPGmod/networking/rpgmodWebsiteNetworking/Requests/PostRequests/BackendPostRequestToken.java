package com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.Requests.PostRequests;

import com.google.gson.Gson;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.HttpRequestHandler;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.util.Optional;

public class BackendPostRequestToken<T> extends BackendPostRequest<T> {
    private String token;

    public BackendPostRequestToken(String endpoint, T data, String token) {
        super(endpoint, data);
        this.token = token;
    }
    @Override
    protected Optional<CloseableHttpResponse> sendPost(){
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return HttpRequestHandler.sendPost(endpoint, json, token);
    }
}
