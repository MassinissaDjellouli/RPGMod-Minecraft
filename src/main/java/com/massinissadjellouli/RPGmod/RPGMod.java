package com.massinissadjellouli.RPGmod;

import com.massinissadjellouli.RPGmod.block.ModBlocks;
import com.massinissadjellouli.RPGmod.block.entities.ModBlockEntities;
import com.massinissadjellouli.RPGmod.item.ModItems;
import com.massinissadjellouli.RPGmod.item.ToolTiers;
import com.massinissadjellouli.RPGmod.networking.ModPackets;
import com.massinissadjellouli.RPGmod.screen.ItemCompressorScreen;
import com.massinissadjellouli.RPGmod.screen.ModMenuTypes;
import com.massinissadjellouli.RPGmod.world.features.ModConfiguredFeatures;
import com.massinissadjellouli.RPGmod.world.features.ModPlacedFeatures;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RPGMod.MODID)
public class RPGMod
{
    public static final String MODID = "rpgmod";
    public RPGMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event){
        ModPackets.register();
    }
    private void register(IEventBus modEventBus){
        ToolTiers.registerTiers();
        ModItems.registerItems(modEventBus);
        ModBlocks.registerBlocks(modEventBus);
        ModBlockEntities.registerBlockEntities(modEventBus);
        ModMenuTypes.registerMenus(modEventBus);
        ModConfiguredFeatures.registerConfiguredFeatures(modEventBus);
        ModPlacedFeatures.registerPlacedFeatures(modEventBus);
    }

    @Mod.EventBusSubscriber(modid = MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents{
        @SubscribeEvent
        public static void onClientSetup(FMLCommonSetupEvent event){
            MenuScreens.register(ModMenuTypes.ITEM_COMPRESSOR_MENU.get(), ItemCompressorScreen::new);
        }
    }
}
