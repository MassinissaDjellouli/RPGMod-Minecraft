package com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.Requests.PostRequests;

import com.google.gson.Gson;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.HttpRequestHandler;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.Requests.BackendRequest;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.WebsiteUtils;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.util.Optional;

public class BackendPostRequest<T> extends BackendRequest<T> {
    protected T data;
    public BackendPostRequest(String endpoint, T data) {
        this.endpoint = WebsiteUtils.getEndpoint(endpoint);
        this.data = data;
    }

    @Override
    public BackendPostRequest<T> sendRequest() {
        try {
            Optional<CloseableHttpResponse> res = sendPost();
            if(res.isEmpty()){
                this.hasError = true;
                this.status = 500;
                this.done = true;
                return this;
            }
            response = res.get();
            status = response.getStatusLine().getStatusCode();
            this.hasError = status < 200 || status > 299;
            done = true;
        } catch (Throwable e) {
            e.printStackTrace();
            hasError = true;
        }
        return this;
    }
    protected Optional<CloseableHttpResponse> sendPost(){
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return HttpRequestHandler.sendPost(endpoint, json);
    }
}
