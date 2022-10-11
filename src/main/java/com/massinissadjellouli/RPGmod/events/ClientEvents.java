package com.massinissadjellouli.RPGmod.events;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.classSystem.PlayerClassProvider;
import com.massinissadjellouli.RPGmod.client.ClientGamemodeData;
import com.massinissadjellouli.RPGmod.client.ClientLastMessageReceived;
import com.massinissadjellouli.RPGmod.client.MessagesHudOverlay;
import com.massinissadjellouli.RPGmod.client.ThirstHudOverlay;
import com.massinissadjellouli.RPGmod.damageIndicator.ActiveDamageIndicators;
import com.massinissadjellouli.RPGmod.damageIndicator.DamageIndicatorData;
import com.massinissadjellouli.RPGmod.networking.ModPackets;
import com.massinissadjellouli.RPGmod.networking.packet.*;
import com.massinissadjellouli.RPGmod.skills.PlayerSkillData;
import com.massinissadjellouli.RPGmod.skills.PlayerSkillProvider;
import com.massinissadjellouli.RPGmod.tags.ModTags;
import com.massinissadjellouli.RPGmod.tags.RarityTags;
import com.massinissadjellouli.RPGmod.thirst.PlayerThirst;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

import static com.massinissadjellouli.RPGmod.classSystem.PlayerClassType.*;
import static com.massinissadjellouli.RPGmod.skills.PlayerSkillData.PlayerSkillEnum.ATTACKING;
import static com.massinissadjellouli.RPGmod.tags.ModTags.EntityTypes.EntityTags.*;
import static com.massinissadjellouli.RPGmod.tags.RarityTags.*;
import static java.util.Collections.EMPTY_LIST;
import static net.minecraft.ChatFormatting.*;
import static net.minecraft.world.InteractionHand.MAIN_HAND;
import static net.minecraft.world.entity.EquipmentSlot.MAINHAND;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.MULTIPLY_TOTAL;
import static net.minecraft.world.entity.ai.attributes.Attributes.*;
import static net.minecraft.world.item.ItemStack.TooltipPart.MODIFIERS;
import static net.minecraftforge.common.Tags.Items.ARMORS;
import static net.minecraftforge.common.Tags.Items.TOOLS_SWORDS;
import static net.minecraftforge.registries.ForgeRegistries.ITEMS;

@Mod.EventBusSubscriber(modid = RPGMod.MODID, value = Dist.CLIENT)
public class ClientEvents {
    private static final int TOUGHNESS_INCREASE_PERCENT = 30;
    private static final int PLAYER_SPEED_INCREASE_PERCENT = 2;
    private static final int ATTACK_DAMAGE_INCREASE_PERCENT = 10;
    private static final int ATTACK_SPEED_INCREASE_PERCENT = 15;
    private static final DecimalFormat DECIMAL_FORMATER = getDecimalFormater();
    private static final String TOOLTIPS_TAB = " ";

    private static final Map<String, List<Component>> savedItemRegularTooltips = new HashMap<>();
    private static final Map<String, List<Component>> savedItemAltTooltips = new HashMap<>();

    @SubscribeEvent
    public static void onLivingEntityUseItem(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity().level.isClientSide && event.getItem().is(Items.POTION) && event.getDuration() == 0) {
            ModPackets.sendToServer(new DrankC2SPacket());
        }
    }

    @SubscribeEvent
    public static void onClose(PlayerEvent.PlayerLoggedOutEvent event) {
       ActiveDamageIndicators.flush();
    }

    @SubscribeEvent
    public static void onBlockMined(BlockEvent.BreakEvent event) {
        if (!event.getPlayer().level.isClientSide) {
            event.getPlayer().getCapability(PlayerSkillProvider.PLAYER_SKILLS).ifPresent(capability -> {
                if (blockTagHas(BlockTags.MINEABLE_WITH_PICKAXE, event.getState().getBlock())) {
                    if (itemTagHas(Tags.Items.TOOLS_PICKAXES,
                            event.getPlayer().getMainHandItem().getItem())) {
                        int xp[] = {getXpFromBreakingBlock(event.getState().getBlock())};
                        event.getPlayer().getCapability(PlayerClassProvider.PLAYER_CLASS).ifPresent(playerClass -> {
                                    if(playerClass.isCurrently(MINEUR)){
                                        playerClass.increaseXp(100);
                                        xp[0] = (xp[0] * (playerClass.getCurrentClassLevel() + 1));

                                    }
                                }
                        );
                        capability.addXP(xp[0],
                                PlayerSkillData.PlayerSkillEnum.MINING
                                , event.getPlayer());
                        capability.blockMined();
                    }
                }
                if (blockTagHas(BlockTags.MINEABLE_WITH_AXE, event.getState().getBlock())) {
                    if (itemTagHas(Tags.Items.TOOLS_AXES,
                            event.getPlayer().getMainHandItem().getItem())) {
                        int xp[] = {getXpFromBreakingBlock(event.getState().getBlock())};
                        event.getPlayer().getCapability(PlayerClassProvider.PLAYER_CLASS).ifPresent(playerClass -> {
                                    if(playerClass.isCurrently(BUCHERON)){
                                        playerClass.increaseXp(100);
                                        xp[0] = (xp[0] * (playerClass.getCurrentClassLevel() + 1));
                                    }
                                }
                        );
                        capability.addXP(xp[0],
                                PlayerSkillData.PlayerSkillEnum.FORAGING
                                , event.getPlayer());
                        capability.woodCut();
                    }
                }
            });

        }
    }

    @SubscribeEvent
    public static void onLevelUp(LevelUpEvent event) {
        switch (event.getSkill()) {
            case ATTACKING -> setPlayerAttackingBonus(event);
            case MINING -> setPlayerMiningBonus(event);
            case FORAGING -> setPlayerForagingBonus(event);
        }
    }

    private static void setPlayerMiningBonus(LevelUpEvent event) {
        Player player = event.getEntity();
        if (event.getSkillLevel() % 2 == 0) {
            int healthLevel = event.getSkillLevel() / 2;

            AttributeModifier health_modifier = new AttributeModifier(UUID.fromString("ff3fdd4d-7c5f-4433-92b3-0cc5592ef67c"), "HEALTH_MODIFIER",
                    healthLevel, ADDITION);

            if (player.getAttribute(MAX_HEALTH).hasModifier(health_modifier))
                player.getAttribute(MAX_HEALTH).removePermanentModifier(health_modifier.getId());
            if (!player.getAttribute(MAX_HEALTH).hasModifier(health_modifier))
                player.getAttribute(MAX_HEALTH).addPermanentModifier(health_modifier);
        }
        if (event.getSkillLevel() % 10 == 0) {
            int hasteLevel = event.getSkillLevel() / 10;

            if (player.hasEffect(MobEffects.DIG_SPEED)) {
                player.removeEffect(MobEffects.DIG_SPEED);
            }
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 100000, hasteLevel));
        }
    }

    private static void setPlayerForagingBonus(LevelUpEvent event) {
        Player player = event.getEntity();
        int strength = event.getSkillLevel() / 3;

        final AttributeModifier ATTACK_MODIFIER =
                new AttributeModifier(UUID.fromString("75a87111-8a82-4517-b68c-ff95ecc5be6b"), "MULTIPLY_MODIFIER",
                        strength, ADDITION);
        if (player.getAttribute(ATTACK_DAMAGE).hasModifier(ATTACK_MODIFIER))
            player.getAttribute(ATTACK_DAMAGE).removeModifier(ATTACK_MODIFIER);
        if (!player.getAttribute(ATTACK_DAMAGE).hasModifier(ATTACK_MODIFIER))
            player.getAttribute(ATTACK_DAMAGE).addPermanentModifier(ATTACK_MODIFIER);

        if (event.getSkillLevel() % 8 == 0) {
            int resistanceLevel = event.getSkillLevel() / 10;

            MobEffect effect = resistanceLevel < 4 ? MobEffects.DAMAGE_RESISTANCE : MobEffects.JUMP;
            if (player.hasEffect(effect)) {
                player.removeEffect(effect);
            }
            player.addEffect(new MobEffectInstance(effect, 100000, resistanceLevel));
        }
    }

    private static void setPlayerAttackingBonus(LevelUpEvent event) {
        Player player = event.getEntity();
        int kbRes = event.getSkillLevel() / 3;

        final AttributeModifier ATTACK_MODIFIER =
                new AttributeModifier(UUID.fromString("75a87111-8a82-4517-b68c-ff95ecc5be6b"), "MULTIPLY_MODIFIER",
                        kbRes, ADDITION);
        if (player.getAttribute(KNOCKBACK_RESISTANCE).hasModifier(ATTACK_MODIFIER))
            player.getAttribute(KNOCKBACK_RESISTANCE).removeModifier(ATTACK_MODIFIER);
        if (!player.getAttribute(KNOCKBACK_RESISTANCE).hasModifier(ATTACK_MODIFIER))
            player.getAttribute(KNOCKBACK_RESISTANCE).addPermanentModifier(ATTACK_MODIFIER);

        if (event.getSkillLevel() % 10 == 0) {
            int regenerationLvl = event.getSkillLevel() / 15;

            MobEffect effect = regenerationLvl > 2 ? MobEffects.DAMAGE_BOOST : MobEffects.REGENERATION;

            if (player.hasEffect(effect)) {
                player.removeEffect(effect);
            }
            player.addEffect(new MobEffectInstance(effect, 100000, regenerationLvl));
        }
        if (event.getSkillLevel() == 30) {
            MobEffect effect = MobEffects.FIRE_RESISTANCE;
            if (player.hasEffect(effect)) {
                player.removeEffect(effect);
            }
            player.addEffect(new MobEffectInstance(effect, 100000, 1));
        }
        if (event.getSkillLevel() == 35) {
            MobEffect effect = MobEffects.NIGHT_VISION;
            if (player.hasEffect(effect)) {
                player.removeEffect(effect);
            }
            player.addEffect(new MobEffectInstance(effect, 100000, 1));
        }
    }

    private static boolean blockTagHas(TagKey<Block> tag, Block block) {
        return ForgeRegistries.BLOCKS.tags().getTag(tag).contains(block);
    }

    private static boolean itemTagHas(TagKey<Item> tag, Item item) {
        return ITEMS.tags().getTag(tag).contains(item);
    }

    private static boolean entityTagHas(ModTags.EntityTypes.EntityTags tag, EntityType<?> entity) {
        return entity.is(tag.tagKey);
    }

    private static int getXpFromBreakingBlock(Block block) {
        if (blockTagHas(Tags.Blocks.STONE, block)
                || blockTagHas(Tags.Blocks.COBBLESTONE, block)) return 3;
        if (blockTagHas(BlockTags.STONE_BRICKS, block)) return 4;
        if (blockTagHas(BlockTags.ICE, block)) return 6;
        if (blockTagHas(Tags.Blocks.ORES_COAL, block)) return 10;
        if (blockTagHas(Tags.Blocks.ORES_IRON, block)) return 12;
        if (blockTagHas(Tags.Blocks.ORES_COPPER, block)) return 15;
        if (blockTagHas(Tags.Blocks.ORES_LAPIS, block)
                || blockTagHas(Tags.Blocks.ORES_REDSTONE, block)) return 16;
        if (blockTagHas(Tags.Blocks.ORES_GOLD, block)) return 18;
        if (blockTagHas(Tags.Blocks.ORES_DIAMOND, block)) return 25;
        if (blockTagHas(Tags.Blocks.ORES_QUARTZ, block)) return 27;
        if (blockTagHas(Tags.Blocks.ORES_EMERALD, block)) return 30;
        if (blockTagHas(Tags.Blocks.END_STONES, block)) return 40;
        if (blockTagHas(ModTags.Blocks.ORES_TITANIUM, block)) return 50;
        if (blockTagHas(Tags.Blocks.OBSIDIAN, block)) return 80;
        if (blockTagHas(ModTags.Blocks.ORES_CRYSTAL, block)) return 275;
        if (blockTagHas(Tags.Blocks.ORES_NETHERITE_SCRAP, block)) return 300;
        if (blockTagHas(BlockTags.PLANKS, block)) return 10;
        if (blockTagHas(BlockTags.LOGS, block)) return 30;
        return 3;
    }


    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {

        if (!event.getEntity().level.isClientSide && event.getSource().getEntity() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getSource().getEntity();
            ServerLevel level = player.getLevel();
            ArmorStand damageIndicator = EntityType.ARMOR_STAND.spawn(
                    level,
                    null,
                    null,
                    null,
                    event.getEntity().blockPosition(),
                    MobSpawnType.COMMAND,
                    true,
                    false
            );
            damageIndicator.setInvisible(true);
            damageIndicator.setInvulnerable(true);
            damageIndicator.setCustomNameVisible(true);
            damageIndicator.setCustomName(Component.literal(
                            DECIMAL_FORMATER.format(
                                    Math.max(event.getEntity().getHealth() - event.getAmount(), 0)) + "/" +
                                    DECIMAL_FORMATER.format(event.getEntity().getMaxHealth()))
                    .withStyle(RED));
            damageIndicator.setNoGravity(true);

            ActiveDamageIndicators.addDamageIndicator(new DamageIndicatorData(damageIndicator));
            player.getCapability(PlayerSkillProvider.PLAYER_SKILLS).ifPresent(capability -> {
                if (event.getEntity().getHealth() - event.getAmount() <= 0) {
                    final int[] xp = {getXpFromKilling(event.getEntity())};
                    player.getCapability(PlayerClassProvider.PLAYER_CLASS).ifPresent(playerClass -> {
                                if(playerClass.isCurrently(SOLDAT)){
                                    playerClass.increaseXp(200);
                                    xp[0] = xp[0] * (playerClass.getCurrentClassLevel() + 1);
                                }
                            }
                    );
                    capability.addXP(xp[0],
                            ATTACKING,
                            player);
                    capability.entityKilled();
                }
            });
        }
    }

    @SubscribeEvent
    public static void onGMChange(PlayerEvent.PlayerChangeGameModeEvent event) {
        ClientGamemodeData.setIsSurvival(event.getCurrentGameMode().isSurvival());
    }

    @SubscribeEvent
    public static void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (event.getEntity().is(player) && event.getEntity().level.isClientSide) {
            ModPackets.sendToServer(new JumpReduceThirstC2SPacket());

        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient()) {

            if (event.player.isSprinting()) {
                PlayerThirst.setReduceByTick(PlayerThirst.getReduceByTick() + 0.4f);
            }
            ClientLastMessageReceived.incrementTick();
            ModPackets.sendToServer(new ReduceThirstByTickC2SPacket());
            ModPackets.sendToServer(new GamemodeDataSyncC2SPacket());
            ModPackets.sendToServer(new ThirstEffectC2SPacket());
        }else{
            ItemStack item = event.player.getItemInHand(MAIN_HAND);
            setupCurrentItem(item);
            if(savedItemRegularTooltips.containsKey(getItemId(item))){
                setAttributes(item);
            }
        }
    }


    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.side.isServer()) {
            ActiveDamageIndicators.updateCurrentDamageIndicators();
        }
    }


    private static void setWeaponAttributes(ItemStack weapon) {
        ITagManager<Item> tagManager = ITEMS.tags();
        if(itemHasNoRarity(weapon)){
            return;
        }
        RarityTags tagKey = getTag(getItemRarity(weapon));
        if (tagManager.getTag(TOOLS_SWORDS).contains(weapon.getItem())) {
            float damage = ((SwordItem) weapon.getItem()).getDamage();
            float speed = ((SwordItem) weapon.getItem()).getTier().getSpeed();
            float damageIncrease = damage * (1f + ((float) tagKey.level * ATTACK_DAMAGE_INCREASE_PERCENT) / 100);
            float speedIncrease = speed * (1f + ((float) tagKey.level * ATTACK_SPEED_INCREASE_PERCENT) / 100) - speed;
            final AttributeModifier ATTACK_MODIFIER =
                    new AttributeModifier(UUID.fromString("f83fdd4d-7c5f-4433-92b3-0cc5592ef67c"), "ATTACK_MODIFIER",
                            damageIncrease, ADDITION);
            final AttributeModifier SPEED_MODIFIER =
                    new AttributeModifier(UUID.fromString("bfa50c88-ca13-4224-b73f-0a500d6889e3"), "SPEED_MODIFIER",
                            speedIncrease, ADDITION);
            if (attributeNotPresent(weapon, ATTACK_MODIFIER)
                    && attributeNotPresent(weapon, SPEED_MODIFIER)
                    && damageIncrease > 0) {
                weapon.addAttributeModifier(ATTACK_DAMAGE, ATTACK_MODIFIER, MAINHAND);
                weapon.addAttributeModifier(ATTACK_SPEED, SPEED_MODIFIER, MAINHAND);
                weapon.hideTooltipPart(MODIFIERS);
            }
        }
    }

    private static void setArmorAttributes(ItemStack armor) {
        ITagManager<Item> tagManager = ITEMS.tags();
        if(itemHasNoRarity(armor)){
            return;
        }
        RarityTags tagKey = getTag(getItemRarity(armor));
        if (tagManager.getTag(ARMORS).contains(armor.getItem())) {
            ArmorItem armorItem = (ArmorItem) armor.getItem();
            float toughness = armorItem.getToughness();
            float toughnessIncrease = (1 + toughness) * (1f + ((float) tagKey.level * TOUGHNESS_INCREASE_PERCENT) / 100);
            float speedIncrease = (((float) tagKey.level * PLAYER_SPEED_INCREASE_PERCENT) / 100);
            final AttributeModifier DEFENSE_MODIFIER =
                    new AttributeModifier(UUID.fromString("f83fdd4d-7c5f-4433-92b3-0cc5592ef67c"), "DEFENSE_MODIFIER",
                            armorItem.getDefense(), ADDITION);
            final AttributeModifier TOUGHNESS_MODIFIER =
                    new AttributeModifier(UUID.fromString("f83fdd4d-7c5f-4433-92b3-0cc5592ef67c"), "TOUGHNESS_MODIFIER",
                            toughnessIncrease, ADDITION);
            final AttributeModifier SPEED_MODIFIER =
                    new AttributeModifier(UUID.fromString("bfa50c88-ca13-4224-b73f-0a500d6889e3"), "SPEED_MODIFIER",
                            speedIncrease, MULTIPLY_TOTAL);
            if (attributeNotPresent(armor, TOUGHNESS_MODIFIER)
                    && attributeNotPresent(armor, SPEED_MODIFIER)
                    && attributeNotPresent(armor, DEFENSE_MODIFIER)
                    && toughnessIncrease > 0) {
                armor.addAttributeModifier(ARMOR_TOUGHNESS, TOUGHNESS_MODIFIER, armorItem.getSlot());
                armor.addAttributeModifier(MOVEMENT_SPEED, SPEED_MODIFIER, armorItem.getSlot());
                armor.addAttributeModifier(ARMOR, DEFENSE_MODIFIER, armorItem.getSlot());
            }
        }
    }

    private static DecimalFormat getDecimalFormater() {
        DecimalFormat decimalFormat = new DecimalFormat("##.#");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        return decimalFormat;
    }

    private static DecimalFormat getDecimalFormater(int amountBeforeDot) {
        StringBuilder amountAfterDot = new StringBuilder();
        amountAfterDot.append("#".repeat(Math.max(0, amountBeforeDot)));
        DecimalFormat decimalFormat = new DecimalFormat("##." + amountAfterDot);
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        return decimalFormat;
    }


    @SubscribeEvent
    public static void itemTooltip(ItemTooltipEvent event) {

        if (event.getEntity() != null && event.getEntity().level.isClientSide) {
            ItemStack item = event.getItemStack();
            setupCurrentItem(item);
            if (itemHasNoRarity(item)){
                return;
            }
            String tagString = event.getItemStack().getTag().getString("item_rarity");
            RarityTags rarityTag = getTag(tagString);
            List<Component> tooltips;
            if (!Screen.hasAltDown() || rarityTag.level == 0) {
                if(!savedItemRegularTooltips.containsKey(getItemId(item))){
                    saveItemRegularTooltip(event,rarityTag);
                }
                 tooltips = savedItemRegularTooltips.get(getItemId(item));
            }else {
                if(!savedItemAltTooltips.containsKey(getItemId(item))){
                    saveItemAltTooltip(event,rarityTag);
                }
                tooltips = savedItemAltTooltips.get(getItemId(item));
            }
            event.getToolTip().clear();
            event.getToolTip().addAll(tooltips);
        }
    }

    private static void setupCurrentItem(ItemStack item) {
        setItemRarityIfNecessary(item);
        setItemIdIfNecessary(item);
    }

    private static void saveItemAltTooltip(ItemTooltipEvent event, RarityTags rarityTag) {
        List<Component> newTooltips = new ArrayList<>();
        List<Component> currentTooltips = savedItemRegularTooltips.get(getItemId(event.getItemStack()));
        if(currentTooltips == null) {
            return;
        }
        final int size = currentTooltips.size();
        for (int i = 0; i < size; i++) {
            newTooltips.add(currentTooltips.get(i));
            if(i == size - 4 && rarityTag.level > 0){
                i++;
            }
            if(i == size - 3 && rarityTag.level > 0){
                newTooltips.addAll(getTooltipBody(event.getItemStack(),rarityTag.level));
            }
        }
        savedItemAltTooltips.put(getItemId(event.getItemStack()),newTooltips);
    }

    private static void saveItemRegularTooltip(ItemTooltipEvent event,RarityTags rarityTag) {
        List<Component> newTooltips = new ArrayList<>();
        List<Component> currentTooltips = event.getToolTip();
        final int size = currentTooltips.size();
        for (int i = 0; i < size; i++) {
            switch (i){
                case 0 -> newTooltips.add(currentTooltips.get(0).copy().withStyle(rarityTag.style));
                case 1 -> {
                    newTooltips.add(Component.literal(rarityTag.name).withStyle(rarityTag.style));
                    newTooltips.add(currentTooltips.get(i));
                }

                default -> newTooltips.add(currentTooltips.get(i));
            }
            if(i == size - 3 && rarityTag.level > 0){
                newTooltips.add(Component.literal(
                        "Appuyer sur Alt pour plus de détails").withStyle(AQUA)
                );
            }
        }
        savedItemRegularTooltips.put(getItemId(event.getItemStack()),newTooltips);
    }

    private static void setItemIdIfNecessary(ItemStack item) {
        if(itemHasNoId(item)){
            item.addTagElement("item_uuid", StringTag.valueOf(UUID.randomUUID().toString()));
        }
    }

    private static boolean itemHasNoId(ItemStack item) {
        return item.getTag() == null
                || getItemId(item).isBlank();
    }

    private static String getItemId(ItemStack item) {
        return item.getTag().getString("item_uuid");
    }

    private static void setItemRarityIfNecessary(ItemStack item) {
        if(itemHasNoRarity(item)){
            for (RarityTags tagKey : RarityTags.values()) {
                ITagManager<Item> tagManager = ITEMS.tags();
                TagKey<Item> tag = tagKey.tagKey;
                if (tagManager.getTag(tag).contains(item.getItem())) {
                    item.addTagElement("item_rarity", StringTag.valueOf(tagKey.name));
                    break;
                }else{
                    item.addTagElement("item_rarity", StringTag.valueOf("none"));
                }
            }
        }
    }


    private static void setAttributes(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (item instanceof SwordItem) {
            setWeaponAttributes(itemStack);
        } else if (item instanceof ArmorItem) {
            setArmorAttributes(itemStack);
        }
    }

    private static List<Component> getTooltipBody(ItemStack itemStack, int level) {
        List<Component> tooltipBody = new ArrayList<>();
        Item item = itemStack.getItem();
        if (item instanceof SwordItem) {
            tooltipBody.addAll(getSwordTooltips(itemStack,level));
        } else if (item instanceof ArmorItem) {
            tooltipBody.addAll(getArmorTooltips(itemStack, level, EMPTY_LIST, EMPTY_LIST));
        }
        return tooltipBody;
    }

    private static List<Component> getArmorTooltips(ItemStack itemStack,int level
            , List<String> additionnalAttr, List<String> bonuses) {
        List<Component> armorTooltips = new ArrayList<>();
        ITagManager<Item> tagManager = ITEMS.tags();
        ArmorItem item = (ArmorItem) itemStack.getItem();
        if (tagManager.getTag(ARMORS).contains(item)) {
            float speedIncrease = (((float) level * PLAYER_SPEED_INCREASE_PERCENT) / 100);
            if (speedIncrease > 0) {

                armorTooltips.add(Component.literal("+" +
                        DECIMAL_FORMATER.format(speedIncrease * 100) +
                        " % de vitesse"
                ).withStyle(BLUE));
            }
            if (additionnalAttr.size() > 0) {
                for (String attr : additionnalAttr) {
                    armorTooltips.add(
                            Component.literal(attr).withStyle(BLUE)
                    );
                }
            }
            if (TOUGHNESS_INCREASE_PERCENT * level > 0) {
                armorTooltips.add(
                        Component.literal(
                                TOUGHNESS_INCREASE_PERCENT * level
                                        + "% de robustesse en plus"
                        ).withStyle(RED)
                );
            }
            if (speedIncrease > 0) {
                armorTooltips.add(
                        Component.literal(
                                DECIMAL_FORMATER.format(speedIncrease * 100)
                                        + "% de vitesse en plus"
                        ).withStyle(RED)
                );
            }
            if (bonuses.size() > 0) {
                for (String bonus : bonuses) {
                    armorTooltips.add(
                            Component.literal(bonus).withStyle(RED)
                    );
                }
            }
        }
        return armorTooltips;
    }


    private static List<Component> getSwordTooltips(ItemStack itemStack,int level) {
        ITagManager<Item> tagManager = ITEMS.tags();
        SwordItem item = (SwordItem) itemStack.getItem();
        List<Component> swordTooltips = new ArrayList<>();
        if (tagManager.getTag(TOOLS_SWORDS).contains(item)) {
            float dmgIncrease = level * ATTACK_DAMAGE_INCREASE_PERCENT;
            float dmg = item.getDamage() + 1;
            String bonusDmg = getBonusDmg(dmg, dmgIncrease);
            String bonusSpeed = getBonusSpeed(item, level);
            if (level > 0) {
                swordTooltips.add(Component.literal(bonusDmg)
                        .withStyle(BLUE));
                swordTooltips.add(Component.literal(bonusSpeed)
                        .withStyle(BLUE));
                String damageIncreasePercent = DECIMAL_FORMATER.format(dmgIncrease) + "% de dégâts en plus";
                swordTooltips.add(Component.literal(damageIncreasePercent)
                        .withStyle(RED));
                String attackSpeedIncreasePercent = DECIMAL_FORMATER.format(
                        (long) ATTACK_SPEED_INCREASE_PERCENT * level) + "% plus rapide";
                swordTooltips.add(Component.literal(attackSpeedIncreasePercent)
                        .withStyle(RED));
            }
        }
        return swordTooltips;
    }

    private static String getBonusDmg(float dmg, float dmgIncrease) {
        String bonusDmg = "";
        if (dmgIncrease > 0) {
            float dmgBonus = dmg * (1 + dmgIncrease / 100) - dmg;
            String isPlural = (dmg * (1 + dmgIncrease / 100) - dmg) > 1 ? "s" : "";
            bonusDmg = TOOLTIPS_TAB + "+" +
                    DECIMAL_FORMATER.format(dmgBonus) +
                    " dégât" + isPlural + " en plus";
        }
        return bonusDmg;
    }

    private static String getBonusSpeed(SwordItem item, int level) {
        String bonusSpeed = "";
        float speed = (float) Math.round(
                (ATTACK_SPEED.getDefaultValue() +
                        item.getDefaultAttributeModifiers(MAINHAND)
                                .get(ATTACK_SPEED).stream().toList().get(0).getAmount())
                        * 10) / 10;
        if (level > 0) {
            float speedBonus = speed * ((float) (level * ATTACK_SPEED_INCREASE_PERCENT)) / 100f;
            bonusSpeed = TOOLTIPS_TAB + "+" +
                    getDecimalFormater(2).format(speedBonus) +
                    " de vitesse en plus";
        }
        return bonusSpeed;
    }

    private static boolean attributeNotPresent(ItemStack weapon, AttributeModifier attribute) {
        return !weapon.getAttributeModifiers(MAINHAND).containsValue(attribute);
    }

    private static int getXpFromKilling(LivingEntity entity) {
        if (entityTagHas(HARMLESS, entity.getType())) return 10;
        if (entityTagHas(HARMFUL, entity.getType())) return 30;
        if (entityTagHas(DANGEROUS, entity.getType())) return 70;
        if (entityTagHas(VERY_DANGEROUS, entity.getType())) return 150;
        if (entityTagHas(BOSS, entity.getType())) return 1000;
        return 10;

    }
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event){
        if(KeyBinding.OPEN_MENU_KEY.consumeClick()){
            ModPackets.sendToServer(new OpenClassMenuC2SPacket());

        }
    }
    @Mod.EventBusSubscriber(modid = RPGMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("thirst_hud", ThirstHudOverlay.HUD_THIRST);
            event.registerAboveAll("message_hud", MessagesHudOverlay.HUD_MESSAGE);
        }



        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){
            event.register(KeyBinding.OPEN_MENU_KEY);
        }
    }

}


