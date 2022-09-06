package com.massinissadjellouli.RPGmod.events;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.client.ClientGamemodeData;
import com.massinissadjellouli.RPGmod.client.ThirstHudOverlay;
import com.massinissadjellouli.RPGmod.networking.ModPackets;
import com.massinissadjellouli.RPGmod.networking.packet.*;
import com.massinissadjellouli.RPGmod.tags.ModTags;
import com.massinissadjellouli.RPGmod.thirst.PlayerThirst;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = RPGMod.MODID,value = Dist.CLIENT)
public class ClientEvents {
    private static final int ATTACK_DAMAGE_INCREASE_PERCENT = 10;
    private static final int ATTACK_SPEED_INCREASE_PERCENT = 10;
    private static final String TOOLTIPS_TAB = " ";

    @SubscribeEvent
    public static void onLivingEntityUseItem(LivingEntityUseItemEvent.Finish event){
        if(event.getEntity().level.isClientSide && event.getItem().is(Items.POTION) && event.getDuration() == 0 ){
                    ModPackets.sendToServer(new DrankC2SPacket());
        }
    }
    @SubscribeEvent
    public static void onPlayerJump(LivingEvent.LivingJumpEvent event){
        LocalPlayer player = Minecraft.getInstance().player;
        if(event.getEntity().is(player) && event.getEntity().level.isClientSide){
            ModPackets.sendToServer(new JumpReduceThirstC2SPacket());

        }
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(event.side.isClient()){
            if(event.player.isSprinting()){
                PlayerThirst.setReduceByTick(PlayerThirst.getReduceByTick() + 0.4f);
            }
            ModPackets.sendToServer(new ReduceThirstByTickC2SPacket());
            ModPackets.sendToServer(new GamemodeDataSyncC2SPacket());
            ModPackets.sendToServer(new ThirstEffectC2SPacket());
        }
    }

    private static void setWeaponAttributes(ItemTooltipEvent event) {
        ItemStack weapon = event.getItemStack();
        ITagManager<Item> tagManager = ForgeRegistries.ITEMS.tags();
        for (ModTags.Items.RarityTags tagKey : ModTags.Items.RarityTags.values()){

            if(tagManager.getTag(tagKey.tagKey).contains(weapon.getItem())
                    && tagManager.getTag(Tags.Items.TOOLS_SWORDS).contains(weapon.getItem())){
                float damage = ((SwordItem) weapon.getItem()).getDamage();
                float speed = ((SwordItem) weapon.getItem()).getTier().getSpeed();
                float damageIncrease = damage * (1f + ((float)tagKey.level * ATTACK_DAMAGE_INCREASE_PERCENT)/100);
                float speedIncrease = speed * (1f + ((float)tagKey.level * ATTACK_SPEED_INCREASE_PERCENT)/100) - speed;
                final AttributeModifier ATTACK_MODIFIER =
                        new AttributeModifier( UUID.fromString("f83fdd4d-7c5f-4433-92b3-0cc5592ef67c"), "ATTACK_MODIFIER",
                                damageIncrease, AttributeModifier.Operation.ADDITION);
                final AttributeModifier SPEED_MODIFIER =
                        new AttributeModifier( UUID.fromString("bfa50c88-ca13-4224-b73f-0a500d6889e3"), "SPEED_MODIFIER",
                                speedIncrease, AttributeModifier.Operation.ADDITION);
                if(attributeNotPresent(weapon.getItem(),ATTACK_MODIFIER) && attributeNotPresent(weapon.getItem(),SPEED_MODIFIER) && damageIncrease > 0){
                    weapon.addAttributeModifier(Attributes.ATTACK_DAMAGE,ATTACK_MODIFIER,EquipmentSlot.MAINHAND);
                    weapon.addAttributeModifier(Attributes.ATTACK_SPEED,SPEED_MODIFIER,EquipmentSlot.MAINHAND);
                    weapon.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                }
                break;
            }
        }
    }

    private static DecimalFormat getDecimalFormater(){
        DecimalFormat decimalFormat = new DecimalFormat("##.#");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        return decimalFormat;
    }
    @SubscribeEvent
    public static void itemTooltip(ItemTooltipEvent event){
        DecimalFormat decimalFormat = getDecimalFormater();
        String rarity = "";
        ITagManager<Item> tagManager = ForgeRegistries.ITEMS.tags();
        Item item = event.getItemStack().getItem();
        ChatFormatting style = ChatFormatting.WHITE;
        float dmgIncrease = 0;
        String id = "";
        String nbt = "";
        String damageIncreasePercent = "";
        String bonusDmg = "";
        for (ModTags.Items.RarityTags tagKey : ModTags.Items.RarityTags.values()){
            TagKey<Item> tag = tagKey.tagKey;
            if(tagManager.getTag(tag).contains(item)){
                style = tagKey.style;
                String title = event.getToolTip().remove(0).getString();
                id = event.getToolTip().remove(0).getString();
                nbt = event.getToolTip().remove(0).getString();
                event.getToolTip().add(0,Component.literal(title).withStyle(ChatFormatting.BOLD,style));
                rarity = tagKey.name;
                dmgIncrease = tagKey.level * ATTACK_DAMAGE_INCREASE_PERCENT;
                damageIncreasePercent = dmgIncrease > 0
                        ? decimalFormat.format(dmgIncrease) + "% de dommages en plus" : "";
                break;
            }
        }
        if(tagManager.getTag(Tags.Items.TOOLS_SWORDS).contains(item)){
            float dmg = ((SwordItem) item).getDamage();
            event.getToolTip().add(Component.literal(""));
            event.getToolTip().add(Component.literal( TOOLTIPS_TAB +
                    decimalFormat.format(dmg) + " de dégâts d'attaque").withStyle(ChatFormatting.DARK_GREEN));
            bonusDmg = getBonusDmg(dmg,dmgIncrease,decimalFormat);
            if(dmgIncrease > 0){
            event.getToolTip().add(Component.literal( bonusDmg)
                    .withStyle(ChatFormatting.DARK_GREEN));
            event.getToolTip().add(Component.literal(""));
              event.getToolTip().add(Component.literal(damageIncreasePercent)
                    .withStyle(ChatFormatting.RED));
            }else {
                event.getToolTip().add(Component.literal(""));
            }
        }
        event.getToolTip().add(Component.literal(rarity).withStyle(style));
        event.getToolTip().add(Component.literal(id).withStyle(ChatFormatting.DARK_GRAY));
        event.getToolTip().add(Component.literal(nbt).withStyle(ChatFormatting.DARK_GRAY));

        setWeaponAttributes(event);

    }

    private static String getBonusDmg(float dmg, float dmgIncrease, DecimalFormat decimalFormat) {
        String bonusDmg = "";
        if(dmgIncrease > 0){
            float dmgBonus = dmg * (1+dmgIncrease/100) - dmg;
            String isPlural = (dmg * (1+dmgIncrease/100) - dmg) > 1 ? "s":"";
            bonusDmg = TOOLTIPS_TAB + "+" +
                    decimalFormat.format(dmgBonus) +
                    " dommage" + isPlural + " en plus";
        }
        return bonusDmg;
    }

    private static boolean attributeNotPresent(Item weapon,AttributeModifier attribute) {
        return !weapon.getDefaultInstance().getAttributeModifiers(EquipmentSlot.MAINHAND).containsValue(attribute);
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event){
        System.out.println(event.getAmount());

    }
    @SubscribeEvent
    public static void onGMChange(PlayerEvent.PlayerChangeGameModeEvent event){
        ClientGamemodeData.setIsSurvival(event.getCurrentGameMode().isSurvival());
    }
    @Mod.EventBusSubscriber(modid = RPGMod.MODID,value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents{
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
            event.registerAboveAll("thirst", ThirstHudOverlay.HUD_THIRST);
        }
    }

}
