package com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.PlayerInfoCapability;

import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.dtos.LoginDTO;
import net.minecraft.nbt.CompoundTag;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PlayerLoginInfo {
    private String username;
    private LocalDateTime lastLogin;
    public CompoundTag serialize(CompoundTag nbt) {
        if(username == null) {
            return nbt;
        }
        nbt.putString("username", username);
        nbt.putLong("last_login_day", lastLogin.getDayOfMonth());
        nbt.putLong("last_login_month", lastLogin.getMonthValue());
        nbt.putLong("last_login_year", lastLogin.getYear());
        nbt.putLong("last_login_hour", lastLogin.getHour());
        nbt.putLong("last_login_minute", lastLogin.getMinute());
        nbt.putLong("last_login_second", lastLogin.getSecond());
        return nbt;
    }

    public PlayerLoginInfo deserializeNBT(CompoundTag loginInfo) {
        if (!loginInfo.contains("username")) {
            return new PlayerLoginInfo();
        }
        this.username = loginInfo.getString("username");
        this.lastLogin = LocalDateTime.of(
                loginInfo.getInt("last_login_year"),
                loginInfo.getInt("last_login_month"),
                loginInfo.getInt("last_login_day"),
                loginInfo.getInt("last_login_hour"),
                loginInfo.getInt("last_login_minute"),
                loginInfo.getInt("last_login_second")
        );
        return this;
    }

    public void setLoginInfo(LoginDTO dto) {
        this.username = dto.getUsername();
        this.lastLogin = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }

    public boolean lastLoginStillValid() {
        return lastLogin.plusDays(1).isAfter(LocalDateTime.now());
    }
}
