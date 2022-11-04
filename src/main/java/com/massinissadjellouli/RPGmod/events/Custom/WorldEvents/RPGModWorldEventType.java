package com.massinissadjellouli.RPGmod.events.Custom.WorldEvents;

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
}
