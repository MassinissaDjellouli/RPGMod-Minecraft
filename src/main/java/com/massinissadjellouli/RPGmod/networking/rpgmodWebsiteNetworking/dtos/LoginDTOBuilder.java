package com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.dtos;

public class LoginDTOBuilder {
    private String username;
    public LoginDTOBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public LoginDTO createLoginDTO() {
        return new LoginDTO(username);
    }
}