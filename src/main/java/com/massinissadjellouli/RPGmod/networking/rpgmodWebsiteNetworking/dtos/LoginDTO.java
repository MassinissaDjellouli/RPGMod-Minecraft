package com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.dtos;

import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.PlayerInfoCapability.PlayerLoginInfo;

public class LoginDTO {
    private String username;

    public LoginDTO(String username) {
        this.username = username;
    }
    public static LoginDTO fromLoginInfo(PlayerLoginInfo loginInfo) {
        return new LoginDTO(loginInfo.getUsername());
    }

    public String getUsername() {
        return username;
    }


}
