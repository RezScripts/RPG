package me.rezscipts.rpg.spells.paladin;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.slikey.effectlib.util.ParticleEffect;
import me.rezscipts.rpg.PlayerDataRPG;
import me.rezscipts.rpg.spells.Spell;
import me.rezscipts.rpg.spells.SpellEffect;
import me.rezscipts.rpgexperience.utils.RParticles;

public class HolyGuardian extends SpellEffect {

    public static final String BUFF_ID = "holy guardian";
    
    @Override
    public boolean cast(final Player p, PlayerDataRPG pd, int level) {
        double multiplier = 0.5;
        switch (level) {
            case 1:
                multiplier = 0.8;
                break;
            case 2:
                multiplier = 0.75;
                break;
            case 3:
                multiplier = 0.7;
                break;
            case 4:
                multiplier = 0.65;
                break;
            case 5:
                multiplier = 0.6;
                break;
            case 6:
                multiplier = 0.55;
                break;
            case 7:
                multiplier = 0.5;
                break;
            case 8:
                multiplier = 0.45;
                break;
            case 9:
                multiplier = 0.4;
                break;
            case 10:
                multiplier = 0.35;
                break;
        }
        ArrayList<Location> locs = new ArrayList<Location>();
        final Location startLoc = p.getLocation().clone();
        locs.add(startLoc.clone().add(1, 0, 1));
        locs.add(startLoc.clone().add(1, 0, -1));
        locs.add(startLoc.clone().add(-1, 0, 1));
        locs.add(startLoc.clone().add(-1, 0, -1));
        for(Location loc : locs) {
            for(int k = 0 ; k < 5; k++) {
                RParticles.showWithSpeed(ParticleEffect.SPELL_MOB, loc, 3, 10);
                loc = loc.add(0, 1, 0);
            }
        }
        pd.giveBuff(HolyGuardian.BUFF_ID, multiplier, 6000);
        Spell.notify(p, "You feel the protection of a holy guardian.");
        Spell.notifyDelayed(p, "You feel your protection fading away.", 6);
        return true;
    }

}