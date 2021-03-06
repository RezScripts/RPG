package me.rezscipts.rpg.spells.alchemist;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import de.slikey.effectlib.util.ParticleEffect;
import me.rezscipts.rpg.PlayerDataRPG;
import me.rezscipts.rpg.drops.DropManager;
import me.rezscipts.rpg.spells.Spell;
import me.rezscipts.rpg.spells.SpellEffect;
import me.rezscipts.rpgexperience.utils.RMetadata;
import me.rezscipts.rpgexperience.utils.RParticles;
import me.rezscipts.rpgexperience.utils.RScheduler;
import me.rezscipts.rpgexperience.utils.RTicks;

public class Bomb extends SpellEffect {

    @Override
    public boolean cast(final Player p, final PlayerDataRPG pd, int level) {
        Location loc = p.getLocation().add(0, p.getEyeHeight() * 0.8, 0);
        loc.add(p.getLocation().getDirection().normalize().multiply(0.4));
        final Item item = p.getWorld().dropItem(loc, new ItemStack(Material.FIREBALL));
        item.setMetadata(RMetadata.META_NO_PICKUP, new FixedMetadataValue(Spell.plugin, 0));
        //        ItemManager.attachLabel(item, ChatColor.BOLD + "= " + p.getName() + "'s Bomb =");
        Spell.plugin.getInstance(DropManager.class).attachLabel(item, ChatColor.DARK_GRAY.toString() + ChatColor.ITALIC + p.getName());
        Vector dir = p.getLocation().getDirection().normalize();
        dir.setY(dir.getY() * 1.1);
        dir.multiply(0.6);
        item.setVelocity(dir);
        int damage = pd.getDamage(true);
        damage *= this.functions[0].applyAsDouble(level) / 100.0;
        final int fDamage = damage;
        RScheduler.schedule(Spell.plugin, new Runnable() {
            public void run() {
                if (item == null || !item.isValid())
                    return;
                RParticles.show(ParticleEffect.EXPLOSION_LARGE, item.getLocation(), 5);
                Spell.damageNearby(fDamage, p, item.getLocation(), 3, new ArrayList<Entity>());
                DropManager.removeLabel(item);
                item.remove();
            }
        }, RTicks.seconds(1));
        Spell.notify(p, "You throw a small bomb.");
        return true;
    }
}
