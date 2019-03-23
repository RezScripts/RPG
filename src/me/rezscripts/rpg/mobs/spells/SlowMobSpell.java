package me.rezscipts.rpg.mobs.spells;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.rezscipts.rpg.RPG;
import me.rezscipts.rpg.mobs.MobData;

public class SlowMobSpell extends MobSpell {

    private int duration, tier;

    private long cooldown;
    
    private String msg = null;

    public SlowMobSpell() {
        duration = 5;
        tier = 1;
        cooldown = 10000;
    }

    public SlowMobSpell(int duration, int tier, long cooldown, String msg) {
        this.duration = duration;
        this.tier = tier;
        this.cooldown = cooldown;
        this.msg = msg;
    }

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        if(msg != null)
            md.say(target, msg);
        if (RPG.plugin.getPD(target) != null)
            RPG.plugin.getPD(target).giveSlow(duration, tier);
    }

    @Override
    public long getCastDelay() {
        return cooldown;
    }
}
