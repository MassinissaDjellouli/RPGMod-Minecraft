package com.massinissadjellouli.RPGmod.worldEvents;

import com.massinissadjellouli.RPGmod.block.ModBlocks;
import com.massinissadjellouli.RPGmod.client.ClientLastMessageReceived;
import com.massinissadjellouli.RPGmod.client.ClientLastTitleReceived;
import com.massinissadjellouli.RPGmod.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.commands.GiveCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.level.LevelEvent;

import java.util.List;
import java.util.Random;

public class GoldRushWorldEvent extends WorldEvent {
    private boolean rewardOffered;
    private int blocksMined;
    private int neededBlocks;
    private int rewardAmount;
    private String nextReward;
    public GoldRushWorldEvent() {
        super("Gold Rush");
        showTitle = true;
    }

    @Override
    protected void launch() {
        ClientLastTitleReceived.set("Un event gold rush commence!",
                "À chaque bloc miné, vous avez une chance d'obtenir un bonus!",
                ChatFormatting.GOLD);
        System.out.println("Gold Rush World Event launched");
    }

    @Override
    protected void tick() {
        if(rewardOffered){
            nextReward();
        }
    }

    private void nextReward() {
        nextReward = "";
        rewardOffered = false;
        blocksMined = 0;
        rewardAmount = new Random().nextInt(5) + 1;
        neededBlocks = new Random().nextInt(140) + 30;
    }

    public void minedBlock(Player player, ServerLevel level) {
        blocksMined++;
        if(blocksMined >= neededBlocks){
            offerReward(player, level);
        }
    }

    private void offerReward(Player player, ServerLevel level) {
        rewardOffered = true;
        player.giveExperiencePoints(4000);
        GoldRushReward randomReward = getRandomReward();
        ItemStack itemStack = new ItemStack(randomReward.getReward(), rewardAmount);
        if(!player.getInventory().add(itemStack)){
            EntityType.ITEM.spawn(level, itemStack, null, player.blockPosition(), MobSpawnType.COMMAND, false, false);
        }
        ClientLastMessageReceived.setImportant(randomReward.getMessage());
    }

    private GoldRushReward getRandomReward() {
        int random = new Random().nextInt(26);
        int rarity = switch (random){
            case 9,10,11,12,13 -> 1;
            case 14,15,16 -> 2;
            case 17,18,19 -> 3;
            case 20,21,22 -> 4;
            case 23,24 -> 5;
            case 25 -> 6;
            default -> 0;
        };
        return getRewards().stream().filter(reward -> reward.getRarity() == rarity).findAny().get();
    }

    @Override
    protected int[] getEventRange() {
        return new int[]{5000, 6000};
    }

    @Override
    protected void saveData(CompoundTag nbt) {}
    @Override
    protected void loadData(CompoundTag nbt) {}

    @Override
    protected RPGModWorldEventType getEventType() {
        return RPGModWorldEventType.GOLD_RUSH;
    }

    private List<GoldRushReward> getRewards(){
        return List.of(
                new GoldRushReward(Items.GOLD_BLOCK, "Vous avez gagné un bloc d'or!"),
                new GoldRushReward(Items.GOLD_INGOT, "Vous avez gagné un lingot d'or!"),
                new GoldRushReward(Items.GOLD_NUGGET, "Vous avez gagné une pépite d'or!"),
                new GoldRushReward(Items.GOLDEN_APPLE, "Vous avez gagné une pomme d'or!"),
                new GoldRushReward(Items.ENCHANTED_GOLDEN_APPLE, "Vous avez gagné une pomme d'or enchantée!",4),
                new GoldRushReward(Items.EMERALD, "Vous avez gagné un émeraude!"),
                new GoldRushReward(Items.DIAMOND, "Vous avez gagné un diamant!"),
                new GoldRushReward(Items.NETHERITE_INGOT, "Vous avez gagné un lingot de netherite!",1),
                new GoldRushReward(Items.NETHERITE_SCRAP, "Vous avez gagné un morceau de netherite!",1),
                new GoldRushReward(Items.NETHERITE_BLOCK, "Vous avez gagné un bloc de netherite!",2),
                new GoldRushReward(Items.NETHER_STAR, "Vous avez gagné une étoile du nether!",1),
                new GoldRushReward(Items.EXPERIENCE_BOTTLE, "Vous avez gagné une bouteille d'xp!"),
                new GoldRushReward(Items.DIAMOND_BLOCK, "Vous avez gagné un bloc de diamant!"),
                new GoldRushReward(Items.EMERALD_BLOCK, "Vous avez gagné un bloc d'émeraude!",1),
                new GoldRushReward(ModItems.COMPRESSED_COAL.get(), "Vous avez gagné un charbon compressé!",1),
                new GoldRushReward(ModItems.COMPRESSED_DIAMOND.get(), "Vous avez gagné un diamant compressé!",3),
                new GoldRushReward(ModItems.COMPRESSED_EMERALD.get(), "Vous avez gagné un émeraude compressé!",4),
                new GoldRushReward(ModItems.TITANIUM_INGOT.get(), "Vous avez gagné un lingot de titane!",3),
                new GoldRushReward(ModItems.REINFORCED_TITANIUM_INGOT.get(), "Vous avez gagné un lingot de titane renforcé!",4),
                new GoldRushReward(ModBlocks.TITANIUM_BLOCK.get().asItem(), "Vous avez gagné un bloc de titane!",4),
                new GoldRushReward(ModBlocks.REINFORCED_TITANIUM_BLOCK.get().asItem(), "Vous avez gagné un bloc de titane renforcé!",5),
                new GoldRushReward(ModItems.THORS_HAMMER.get(), "Vous avez gagné le marteau de thor!",5),
                new GoldRushReward(ModItems.COMPRESSED_NETHERITE.get(), "Vous avez gagné un netherite compressé!",4),
                new GoldRushReward(ModItems.GOD_SWORD.get(), "Vous avez gagné l'épée de dieu!", 6),
                new GoldRushReward(ModItems.HELL_SWORD.get(), "Vous avez gagné l'épée de l'enfer!", 4),
                new GoldRushReward(ModItems.FIRE_CRYSTAL.get(), "Vous avez gagné un cristal de feu!", 4),
                new GoldRushReward(ModItems.ICE_CRYSTAL.get(), "Vous avez gagné un cristal de glace!", 4),
                new GoldRushReward(ModItems.POISON_CRYSTAL.get(), "Vous avez gagné un cristal de poison!", 4)
        );
    }
}
