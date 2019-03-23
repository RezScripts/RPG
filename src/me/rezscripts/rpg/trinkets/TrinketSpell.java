package me.rezscipts.rpg.trinkets;

import org.bukkit.entity.Player;

import me.rezscipts.rpg.PlayerDataRPG;

public abstract class TrinketSpell {

    public abstract String getName();
    
    public abstract String getDescription();
    
    public abstract int getCooldown();
    
    public abstract boolean cast(Player p, PlayerDataRPG pd);
    
}