package com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.Requests;

import org.apache.http.client.methods.CloseableHttpResponse;

import java.util.function.Consumer;

public abstract class BackendRequest<T> {
    protected boolean done;
    protected String endpoint;
    protected CloseableHttpResponse response;
    protected int status;
    protected boolean hasError;


    public abstract BackendRequest<T> sendRequest();
    public BackendRequest<T> then(Consumer<CloseableHttpResponse> callback){
        try{
            if(hasError){
                return this;
            }
            callback.accept(response);
        } catch (Exception e) {
            hasError = true;
        }
        return this;
    }
    public void err(){
        if(hasError){
            throw new RuntimeException("Error while sending request to " + endpoint + " with status code " + status);
        }
    }
    public void err(Consumer<CloseableHttpResponse> callback){
        if(hasError) {
            callback.accept(response);
        }
    }

    public void throwErr() {
        hasError = true;
    }
}
