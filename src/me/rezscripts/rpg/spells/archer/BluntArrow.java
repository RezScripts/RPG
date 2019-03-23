package me.rezscipts.rpg.spells.archer;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.FixedMetadataValue;

import me.rezscipts.rpg.PlayerDataRPG;
import me.rezscipts.rpg.spells.Spell;
import me.rezscipts.rpg.spells.SpellEffect;
import me.rezscipts.rpgexperience.utils.RMetadata;

public class BluntArrow extends SpellEffect {

    @Override
    public boolean cast(Player p, final PlayerDataRPG pd, int level) {
        int knockback = 5;
        Projectile arrow = pd.shootArrow();
        arrow.setMetadata(RMetadata.META_DAMAGE, new FixedMetadataValue(Spell.plugin, 1));
        arrow.setMetadata(RMetadata.META_KNOCKBACK, new FixedMetadataValue(Spell.plugin, knockback));
        Spell.notify(p, "You shoot a blunt arrow designed for knockback.");
        return true;
    }

}
