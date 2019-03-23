package me.rezscipts.rpg.spells;

import java.util.function.IntToDoubleFunction;

import org.bukkit.entity.Player;

import me.rezscipts.rpg.PlayerDataRPG;

public abstract class SpellEffect {
    protected IntToDoubleFunction[] functions;

    public abstract boolean cast(Player p, PlayerDataRPG pd, int level);
}
