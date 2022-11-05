package com.massinissadjellouli.RPGmod.worldEvents;

public enum RPGModWorldEventType  {
    NUIT_FEROCE(new NuitFeroceWorldEvent()),
    INVASION_NETHER(new InvasionNetherWorldEvent()),
    SOLEIL_PUISSANT(new SoleilPuissantWorldEvent()),
    GOLD_RUSH(new GoldRushWorldEvent()),
    ECLIPSE(new EclipseWorldEvent());
    private final WorldEvent event;
    public WorldEvent getEvent(){
        return event;
    }
    RPGModWorldEventType(WorldEvent event){
        this.event = event;
    }

    public static String getName(RPGModWorldEventType eventType){
        return switch (eventType) {
            case NUIT_FEROCE -> "NuitFeroce";
            case INVASION_NETHER -> "InvasionNether";
            case SOLEIL_PUISSANT -> "SoleilPuissant";
            case GOLD_RUSH -> "GoldRush";
            case ECLIPSE -> "Eclipse";
        };
    }
    public static RPGModWorldEventType getTypeFromName(String name){
        return switch (name) {
            case "NuitFeroce" -> NUIT_FEROCE;
            case "InvasionNether" -> INVASION_NETHER;
            case "SoleilPuissant" -> SOLEIL_PUISSANT;
            case "GoldRush" -> GOLD_RUSH;
            case "Eclipse" -> ECLIPSE;
            default -> null;
        };
    }
}
