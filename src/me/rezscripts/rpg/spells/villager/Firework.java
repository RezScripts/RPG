package me.rezscipts.rpg.spells.villager;

import org.bukkit.entity.Player;

import me.rezscipts.rpg.PlayerDataRPG;
import me.rezscipts.rpg.spells.Spell;
import me.rezscipts.rpg.spells.SpellEffect;
import me.rezscipts.rpgexperience.utils.RParticles;

public class Firework extends SpellEffect {

    @Override
    public boolean cast(Player p, PlayerDataRPG pd, int level) {
        RParticles.spawnRandomFirework(p.getLocation());
        Spell.notify(p, "Pew pew! A beautiful firework shoots upwards.");
        pd.castedFirework = true;
        return true;
    }
}
