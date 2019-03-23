package me.rezscipts.rpg.spells;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.rezscipts.rpg.PlayerDataRPG;

public class PassiveSpellEffect extends SpellEffect {
    public boolean cast(Player p, PlayerDataRPG pd, int level) {
        p.sendMessage(ChatColor.RED + "Error 103 - passive cast.");
        return false;
    }
}
