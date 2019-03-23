package me.rezscipts.rpg.worldboss.bosses;

import de.slikey.effectlib.util.ParticleEffect;
import me.rezscipts.rpg.mobs.MobAI;
import me.rezscipts.rpgexperience.utils.RParticles;

public abstract class BossAIRunnable implements Runnable {

    public MobAI ai = null;
    
    public final void run() {
        if(ai.entity == null)
            return;
        if (ai.target != null && ai.counter % 2 == 0) {
            if (Math.random() < 0.5)
                RParticles.showWithOffsetPositiveY(ParticleEffect.CRIT, ai.entity.getLocation().add(0, ai.entity.getEyeHeight() * 0.5, 0), 1.0, 10);
            if (Math.random() < 0.5)
                RParticles.showWithOffsetPositiveY(ParticleEffect.FLAME, ai.entity.getLocation().add(0, ai.entity.getEyeHeight() * 0.5, 0), 1.0, 10);
            if (Math.random() < 0.5)
                RParticles.showWithOffsetPositiveY(ParticleEffect.SPELL_MOB, ai.entity.getLocation().add(0, ai.entity.getEyeHeight() * 0.5, 0), 1.0, 10);
            if (Math.random() < 0.5)
                RParticles.showWithOffsetPositiveY(ParticleEffect.CRIT_MAGIC, ai.entity.getLocation().add(0, ai.entity.getEyeHeight() * 0.5, 0), 1.0, 10);
            if (Math.random() < 0.5)
                RParticles.showWithOffsetPositiveY(ParticleEffect.SMOKE_NORMAL, ai.entity.getLocation().add(0, ai.entity.getEyeHeight() * 0.5, 0), 1.0, 10);
            if (Math.random() < 0.5)
                RParticles.showWithOffsetPositiveY(ParticleEffect.REDSTONE, ai.entity.getLocation().add(0, ai.entity.getEyeHeight() * 0.5, 0), 1.0, 10);
            if (Math.random() < 0.5)
                RParticles.showWithOffsetPositiveY(ParticleEffect.PORTAL, ai.entity.getLocation().add(0, ai.entity.getEyeHeight() * 0.5, 0), 1.0, 10);
        }
        tick();
    }
    
    public abstract void tick();

}
