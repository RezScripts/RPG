package me.rezscipts.rpg.regions.areas.actions;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.rezscipts.rpg.PlayerDataRPG;
import me.rezscipts.rpg.regions.areas.TriggerAreaAction;
import me.rezscipts.rpgexperience.utils.RSound;

public class TriggerAreaActionPing extends TriggerAreaAction {

    @Override
    public void act(Player p, PlayerDataRPG pd) {
        RSound.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
    }

}
