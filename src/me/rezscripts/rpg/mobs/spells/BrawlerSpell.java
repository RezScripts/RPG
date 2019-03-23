package me.rezscipts.rpg.mobs.spells;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.rezscipts.rpg.mobs.MobData;
import me.rezscipts.rpg.spells.Spell;
import me.rezscipts.rpgexperience.utils.RMath;

public class BrawlerSpell extends MobSpell {

    private long cooldown;

    public BrawlerSpell(long cooldown) {
        this.cooldown = cooldown;
    }

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        if (Spell.canDamage(target, false) && RMath.flatDistance(target.getLocation(), caster.getLocation()) < 1.5) {
            Spell.damageEntity(target, md.getDamage(), caster, true, false);
        }
    }

    @Override
    public long getCastDelay() {
        return cooldown;
    }
}
