package com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.Requests.GetRequests;

import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.HttpRequestHandler;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.Requests.BackendRequest;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.WebsiteUtils;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.util.Optional;

public class BackendGetRequest extends BackendRequest {


    public BackendGetRequest(String endpoint) {
        this.endpoint = WebsiteUtils.getEndpoint(endpoint);
    }

    @Override
    public BackendGetRequest sendRequest() {
        try {
            Optional<CloseableHttpResponse> res = sendGet();
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
    protected Optional<CloseableHttpResponse> sendGet(){
        return HttpRequestHandler.sendGet(endpoint);
    }
}
