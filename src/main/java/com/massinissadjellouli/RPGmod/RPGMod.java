package com.massinissadjellouli.RPGmod;

import com.massinissadjellouli.RPGmod.block.ModBlocks;
import com.massinissadjellouli.RPGmod.block.entities.ModBlockEntities;
import com.massinissadjellouli.RPGmod.entities.ModEntities;
import com.massinissadjellouli.RPGmod.item.ModItems;
import com.massinissadjellouli.RPGmod.item.ToolTiers;
import com.massinissadjellouli.RPGmod.networking.ModPackets;
import com.massinissadjellouli.RPGmod.recipe.ModRecipes;
import com.massinissadjellouli.RPGmod.screen.ClassChangeScreen;
import com.massinissadjellouli.RPGmod.screen.ItemCompressorScreen;
import com.massinissadjellouli.RPGmod.screen.ModMenuTypes;
import com.massinissadjellouli.RPGmod.screen.RarityTableScreen;
import com.massinissadjellouli.RPGmod.world.features.ModConfiguredFeatures;
import com.massinissadjellouli.RPGmod.world.features.ModPlacedFeatures;
import com.massinissadjellouli.RPGmod.effects.ModEffects;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RPGMod.MODID)
public class RPGMod
{
    public static final String MODID = "rpgmod";
    public RPGMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        register(modEventBus);
        GeckoLib.initialize();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            ModPackets.register();
            SpawnPlacements.register(ModEntities.GOBLIN.get(),
                    SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules);
        });
    }
    private void register(IEventBus modEventBus){
        ToolTiers.registerTiers();
        ModItems.registerItems(modEventBus);
        ModBlocks.registerBlocks(modEventBus);
        ModBlockEntities.registerBlockEntities(modEventBus);
        ModMenuTypes.registerMenus(modEventBus);
        ModConfiguredFeatures.registerConfiguredFeatures(modEventBus);
        ModPlacedFeatures.registerPlacedFeatures(modEventBus);
        ModRecipes.register(modEventBus);
        ModEffects.register(modEventBus);
        ModEntities.register(modEventBus);
    }

    @Mod.EventBusSubscriber(modid = MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents{
        @SubscribeEvent
        public static void onClientSetup(FMLCommonSetupEvent event){
            MenuScreens.register(ModMenuTypes.ITEM_COMPRESSOR_MENU.get(), ItemCompressorScreen::new);
            MenuScreens.register(ModMenuTypes.RARITY_TABLE_MENU.get(), RarityTableScreen::new);
            MenuScreens.register(ModMenuTypes.CHANGE_CLASS_MENU.get(), ClassChangeScreen::new);
        }
    }
}
