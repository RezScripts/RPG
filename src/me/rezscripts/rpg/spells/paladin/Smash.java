package me.rezscipts.rpg.spells.paladin;

import java.util.ArrayList;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.slikey.effectlib.util.ParticleEffect;
import me.rezscipts.rpg.PlayerDataRPG;
import me.rezscipts.rpg.spells.Spell;
import me.rezscipts.rpg.spells.SpellEffect;
import me.rezscipts.rpgexperience.utils.RParticles;

public class Smash extends SpellEffect {

    @Override
    public boolean cast(final Player p, PlayerDataRPG pd, int level) {
        int damage = pd.getDamage(true);
        damage *= functions[0].applyAsDouble(level) / 100.0;
        RParticles.show(ParticleEffect.EXPLOSION_LARGE, p.getLocation(), 5);
        Spell.damageNearby(damage, p, p.getLocation(), 3, new ArrayList<Entity>());
        Spell.notify(p, "You smash the ground with a powerful blow.");
        return true;
    }

}
