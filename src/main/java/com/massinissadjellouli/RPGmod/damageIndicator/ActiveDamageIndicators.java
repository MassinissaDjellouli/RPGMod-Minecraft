package com.massinissadjellouli.RPGmod.damageIndicator;

import java.util.ArrayList;
import java.util.List;

public class ActiveDamageIndicators {
    private static final int TIME_TO_LIVE = 60;
    private static List<DamageIndicatorData> activeDamageIndicators = new ArrayList<>();

    public static void addDamageIndicator(DamageIndicatorData damageIndicator) {
        activeDamageIndicators.add(damageIndicator);
    }

    public static void updateCurrentDamageIndicators() {
        activeDamageIndicators.forEach(damageIndicatorData ->
                damageIndicatorData.addTick(1));
        activeDamageIndicators.stream().filter(
                        damageIndicatorData -> damageIndicatorData.getTicksSinceSpawn() >= TIME_TO_LIVE)
                .forEach(DamageIndicatorData::kill);

    }

    public static void flush() {
        activeDamageIndicators.forEach(DamageIndicatorData::kill);
    }
}
