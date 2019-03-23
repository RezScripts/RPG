package me.rezscipts.rpg.mobs.spells;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import de.slikey.effectlib.util.ParticleEffect;
import me.rezscipts.rpg.PlayerDataRPG;
import me.rezscipts.rpg.RPG;
import me.rezscipts.rpg.mobs.MobData;
import me.rezscipts.rpgexperience.utils.RParticles;

public class ManaDrainSpell extends MobSpell {

    private int amount;

    private long cooldown;

    public ManaDrainSpell(int amount, long cooldown) {
        this.amount = amount;
        this.cooldown = cooldown;
    }

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        if (RPG.plugin.getPD(target) != null) {
            PlayerDataRPG pd = RPG.plugin.getPD(target);
            pd.mana -= amount;
            if (pd.mana < 0) {
                pd.mana = 0;
                pd.updateHealthManaDisplay();
                RParticles.showWithOffset(ParticleEffect.CRIT_MAGIC, target.getLocation(), 2.0, 25);
            }
        }
    }

    @Override
    public long getCastDelay() {
        return cooldown;
    }
}
