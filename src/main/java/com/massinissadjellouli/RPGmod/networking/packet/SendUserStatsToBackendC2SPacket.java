package com.massinissadjellouli.RPGmod.networking.packet;

import com.google.gson.Gson;
import com.massinissadjellouli.RPGmod.classSystem.PlayerClassProvider;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.PlayerInfoCapability.PlayerLoginInfo;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.Requests.BackendRequest;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.Requests.BackendRequests;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.dtos.LoginDTO;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.dtos.PlayerStatsDTO;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.dtos.PlayerStatsDTOBuilder;
import com.massinissadjellouli.RPGmod.skills.PlayerSkillData;
import com.massinissadjellouli.RPGmod.skills.PlayerSkillProvider;
import com.massinissadjellouli.RPGmod.skills.PlayerSkillsData.PlayerAttackSkillData;
import com.massinissadjellouli.RPGmod.skills.PlayerSkillsData.PlayerForagingSkillData;
import com.massinissadjellouli.RPGmod.skills.PlayerSkillsData.PlayerMiningSkillData;
import com.massinissadjellouli.RPGmod.skills.PlayerSkillsData.SkillData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.function.Supplier;

import static com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.PlayerInfoCapability.PlayerLoginInfoCapabilityProvider.PLAYER_LOGIN_INFO;
import static com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.PlayerInfoCapability.PlayerUIDCapabilityProvider.PLAYER_UID;
import static com.massinissadjellouli.RPGmod.skills.PlayerSkillData.PlayerSkillEnum.*;

public class SendUserStatsToBackendC2SPacket {

    public SendUserStatsToBackendC2SPacket() {}

    public SendUserStatsToBackendC2SPacket(FriendlyByteBuf buf) {}

    public void toBytes(FriendlyByteBuf buf) {}

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();
            context.enqueueWork(() -> {
                try {
                    throwOnFalse(player.getCapability(PLAYER_UID).isPresent(),"Player UID capability is not present");
                    player.getCapability(PLAYER_UID).ifPresent(uid ->
                        player.getCapability(PLAYER_LOGIN_INFO).ifPresent(loginInfo -> {
                            if(loginInfo.getUsername() == null || !loginInfo.lastLoginStillValid()){
                                throwOnFalse(loginIfNeeded(player, uid),"Login failed");
                                if(loginInfo.getUsername() == null){
                                    return;
                                }
                            }
                            BackendRequest<PlayerStatsDTO> backendRequest =
                                  BackendRequests.postRequest("uploadStats", getStats(player, uid, loginInfo));
                            backendRequest.sendRequest().then(closeableHttpResponse -> System.out.println()).err();
                        })
                    );
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            });
        return true;
    }

    private PlayerStatsDTO getStats(ServerPlayer player, String uid, PlayerLoginInfo loginInfo) {
        PlayerStatsDTOBuilder builder = new PlayerStatsDTOBuilder();
        player.getCapability(PlayerSkillProvider.PLAYER_SKILLS).ifPresent(playerSkills -> {
            player.getCapability(PlayerClassProvider.PLAYER_CLASS).ifPresent(playerClass -> {
            throwOnFalse(playerSkills.getSkill(MINING).isPresent(),"Mining skill is not present");
            PlayerMiningSkillData miningSD = (PlayerMiningSkillData) playerSkills.getSkill(MINING).get();
            throwOnFalse(playerSkills.getSkill(FORAGING).isPresent(),"Foraging skill level is not present");
            PlayerForagingSkillData foragingSD = (PlayerForagingSkillData) playerSkills.getSkill(FORAGING).get();
            throwOnFalse(playerSkills.getSkill(ATTACKING).isPresent(),"Attacking skill level is not present");
            PlayerAttackSkillData attackingSD = (PlayerAttackSkillData)  playerSkills.getSkill(ATTACKING).get();
            builder
                    .worldId(uid)
                    .username(loginInfo.getUsername())
                    .blocksForaged(foragingSD.woodCut)
                    .blocksMined(miningSD.blocksMined)
                    .mobsKilled(attackingSD.entitiesKilled)
                    .combatLevel(attackingSD.level)
                    .foragingLevel(foragingSD.level)
                    .miningLevel(miningSD.level)
                    .combatXp(attackingSD.currentXp)
                    .foragingXp(foragingSD.currentXp)
                    .miningXp(miningSD.currentXp)
                    .totalCombatXp(attackingSD.totalXp)
                    .totalForagingXp(foragingSD.totalXp)
                    .totalMiningXp(miningSD.totalXp)
                    .currentClass(playerClass.getCurrentClass());
            });
        });
        throwOnFalse(builder.isFull(),"Player stats are not complete");
        return builder.build();
    }

    private void throwOnFalse(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private boolean loginIfNeeded(ServerPlayer player, String uid) {
        try {
            Gson gson = new Gson();
            BackendRequest<?> isLinkedRequest =
                    BackendRequests.getRequest("isLinked/" + uid);
            isLinkedRequest.sendRequest()
                    .then((isLinked) -> {
                        throwOnFalse(isLinked != null, "isLinked response is null");
                        try {
                            String body = EntityUtils.toString(isLinked.getEntity());
                            LoginDTO temp = gson.fromJson(body, LoginDTO.class);
                            player.getCapability(PLAYER_LOGIN_INFO).ifPresent(loginInfo -> {
                                loginInfo.setLoginInfo(temp);
                            });
                        } catch (IOException e) {
                            isLinkedRequest.throwErr();
                        }
                    }).err((err) -> {
                        if(err.getStatusLine().getStatusCode() == 404){
                            return;
                        }
                        throw new RuntimeException("isLinked request failed");
                    });
            return true;
        }catch (Exception ignored) {
            return false;
        }
    }
}
