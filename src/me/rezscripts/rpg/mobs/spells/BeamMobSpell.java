package me.rezscipts.rpg.mobs.spells;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.slikey.effectlib.util.ParticleEffect;
import me.rezscipts.rpg.mobs.MobData;
import me.rezscipts.rpg.spells.Spell;
import me.rezscipts.rpgexperience.utils.RMath;
import me.rezscipts.rpgexperience.utils.RParticles;
import me.rezscipts.rpgexperience.utils.RScheduler;


public class BeamMobSpell extends MobSpell {

    private long cooldown;
    private int range;

    public BeamMobSpell() {
        this.cooldown = 4000;
        this.range = 8;
    }

    public BeamMobSpell(long cooldown, int range) {
        this.cooldown = cooldown;
        this.range = range;
    }

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        final Vector dir = target.getLocation().toVector().subtract(caster.getLocation().toVector()).normalize();
        final Location start = caster.getLocation().add(0, caster.getEyeHeight() * 0.75, 0).add(dir.multiply(0.5));
        int count = 0;
        final ArrayList<Entity> hit = new ArrayList<Entity>();
        ArrayList<Location> locs = RMath.calculateVectorPath(start.clone(), dir, range, 1, true);
        for (int k = 0; k < locs.size(); k++) {
            final Location loc = locs.get(k);
            RScheduler.schedule(Spell.plugin, new Runnable() {
                public void run() {
                    RParticles.show(ParticleEffect.CRIT, loc);
                    int damage = md.getDamage();
                    hit.addAll(Spell.damageNearby(damage, caster, loc, 1.0, hit, true, false, true));
                }
            }, 2 * count);
            if (k % 2 == 0)
                count++;
        }
    }

    @Override
    public long getCastDelay() {
        return cooldown;
    }
}

