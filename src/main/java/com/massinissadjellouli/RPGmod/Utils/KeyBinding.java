package com.massinissadjellouli.RPGmod.Utils;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding{
    public static final String KEY_CATEGORY_RPGMOD = "key.category.rpgmod.rpgmod";
    public static final String KEY_OPEN_MENU_CLASS = "key.rpgmod.class_menu";

    public static final KeyMapping OPEN_MENU_KEY = new KeyMapping(KEY_OPEN_MENU_CLASS, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_M,KEY_CATEGORY_RPGMOD);
}
