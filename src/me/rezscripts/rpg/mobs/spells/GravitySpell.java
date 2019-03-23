package me.rezscipts.rpg.mobs.spells;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.rezscipts.rpg.mobs.MobData;
import me.rezscipts.rpg.spells.Spell;
import me.rezscipts.rpgexperience.utils.RMath;

public class GravitySpell extends MobSpell {

    private int range;

    private long cooldown;

    public GravitySpell(int range, long cooldown) {
        this.range = range;
        this.cooldown = cooldown;
    }

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        Vector v = caster.getLocation().toVector();
        for (Entity e : RMath.getNearbyEntities(caster.getLocation(), range)) {
            if (e instanceof Player) {
                Player p = (Player) e;
                if (p != null && p.isOnline()) {
                    if (Spell.canDamage(p, false)) {
                        Vector pullVector = p.getLocation().toVector().subtract(v).normalize().multiply(-1.8);
                        pullVector.setY(pullVector.getY() + 0.35);
                        p.setVelocity(pullVector);
                    }
                }
            }
        }
    }

    @Override
    public long getCastDelay() {
        return cooldown;
    }
}
