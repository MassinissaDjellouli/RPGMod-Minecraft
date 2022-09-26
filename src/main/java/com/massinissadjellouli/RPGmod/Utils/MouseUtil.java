package com.massinissadjellouli.RPGmod.Utils;

//Source:https://github.com/Tutorials-By-Kaupenjoe/Forge-Tutorial-1.19/blob/main/src/main/java/net/kaupenjoe/tutorialmod/util/MouseUtil.java
public class MouseUtil {
    public static boolean isMouseOver(double mouseX, double mouseY, int x, int y) {
        return isMouseOver(mouseX, mouseY, x, y, 16);
    }

    public static boolean isMouseOver(double mouseX, double mouseY, int x, int y, int size) {
        return isMouseOver(mouseX, mouseY, x, y, size, size);
    }

    public static boolean isMouseOver(double mouseX, double mouseY, int x, int y, int sizeX, int sizeY) {
        return (mouseX >= x && mouseX <= x + sizeX) && (mouseY >= y && mouseY <= y + sizeY);
    }
}