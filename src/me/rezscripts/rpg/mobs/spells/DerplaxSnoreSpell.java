package me.rezscipts.rpg.mobs.spells;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.rezscipts.rpg.RPG;
import me.rezscipts.rpg.mobs.MobData;
import me.rezscipts.rpgexperience.utils.RMath;

public class DerplaxSnoreSpell extends MobSpell {

    public void castSpell(final LivingEntity caster, final MobData md, Player target) {
        md.shout("Yawwwwnnn....", 30);
        target.sendMessage(ChatColor.GRAY + "> " + ChatColor.WHITE + "You begin to feel tired and sluggish...");
        for (Entity e : RMath.getNearbyEntities(caster.getLocation(), 30)) {
            if (e instanceof Player && RPG.plugin.getPD((Player) e) != null) {
                RPG.plugin.getPD((Player) e).giveSlow(6, 2);
            }
        }
    }

    @Override
    public long getCastDelay() {
        return (int) (Math.random() * 10000) + 15000;
    }
}
