package me.rezscipts.rpg.mobs.spells;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.slikey.effectlib.util.ParticleEffect;
import me.rezscipts.rpg.mobs.MobData;
import me.rezscipts.rpgexperience.utils.RParticles;

public class InvisibleSpell extends MobSpell {

    private int ticks;

    private long cooldown;

    public InvisibleSpell(int ticks, long cooldown) {
        this.ticks = ticks;
        this.cooldown = cooldown;
    }

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        RParticles.showWithOffset(ParticleEffect.SMOKE_NORMAL, caster.getEyeLocation(), 1.0, 5);
        caster.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, ticks, 0));
        caster.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, ticks, 1));
    }

    @Override
    public long getCastDelay() {
        return cooldown;
    }
}
