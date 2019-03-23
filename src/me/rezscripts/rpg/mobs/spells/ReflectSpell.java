package me.rezscipts.rpg.mobs.spells;

import java.util.ArrayList;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.slikey.effectlib.util.ParticleEffect;
import me.rezscipts.rpg.RPG;
import me.rezscipts.rpg.mobs.MobData;
import me.rezscipts.rpgexperience.utils.RParticles;
import me.rezscipts.rpgexperience.utils.RScheduler;
import me.rezscipts.rpgexperience.utils.RScheduler.Halter;
import me.rezscipts.rpgexperience.utils.RTicks;

public class ReflectSpell extends MobSpell {

    private int duration;

    private long cooldown;

    public ReflectSpell() {
        duration = 5;
        cooldown = 20000;
    }

    public ReflectSpell(int duration, long cooldown) {
        this.duration = duration;
        this.cooldown = cooldown;
    }

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        md.frozen = true;
        md.reflect = true;
        Halter h = new Halter();
        RScheduler.scheduleRepeating(RPG.plugin, () -> {
            if (md != null && md.entity != null && md.entity.isValid()) {
                ArrayList<Vector> vectors = new ArrayList<Vector>();
                Vector v = md.entity.getEyeLocation().getDirection().normalize();
                v.setY(0);
                vectors.add(v);
                double z = v.getZ();
                double x = v.getX();
                double radians = Math.atan(z / x);
                if (x < 0)
                    radians += Math.PI;
                for (int k = 1; k < 24; k++) {
                    Vector v2 = new Vector();
                    v2.setY(v.getY());
                    v2.setX(Math.cos(radians + k * Math.PI / 12));
                    v2.setZ(Math.sin(radians + k * Math.PI / 12));
                    vectors.add(v2.normalize());
                }
                for (Vector vec : vectors) {
                    for (int k = 0; k < 7; k++) {
                        RParticles.show(ParticleEffect.CRIT_MAGIC, md.entity.getLocation().add(vec).add(0, 0.2 * (k + 1), 0));
                    }
                }
            }
        }, 10, h);
        RScheduler.schedule(RPG.plugin, () -> {
            h.halt = true;
            if (md != null) {
                md.frozen = false;
                md.reflect = false;
            }
        }, RTicks.seconds(duration));
    }

    @Override
    public long getCastDelay() {
        return cooldown;
    }
}