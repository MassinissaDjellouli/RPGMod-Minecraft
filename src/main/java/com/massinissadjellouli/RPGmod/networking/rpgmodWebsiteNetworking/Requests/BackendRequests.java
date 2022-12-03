package com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.Requests;

import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.Requests.GetRequests.BackendGetRequest;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.Requests.PostRequests.BackendPostRequest;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.Requests.PostRequests.BackendPostRequestToken;

public class BackendRequests {

    public static <T> BackendRequest<T> postRequest(String endpoint, T data){
        return new BackendPostRequest<>(endpoint, data);
    }
    public static <T> BackendRequest<T> postRequest(String endpoint, T data, String token){
        return new BackendPostRequestToken<>(endpoint, data, token);
    }
    public static BackendRequest getRequest(String endpoint){
        return new BackendGetRequest(endpoint);
    }
}
