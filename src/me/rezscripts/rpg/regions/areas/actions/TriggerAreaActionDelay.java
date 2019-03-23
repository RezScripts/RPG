package me.rezscipts.rpg.regions.areas.actions;

import org.bukkit.entity.Player;

import me.rezscipts.rpg.PlayerDataRPG;
import me.rezscipts.rpg.regions.areas.TriggerAreaAction;

public class TriggerAreaActionDelay extends TriggerAreaAction {

    public long delay;

    public TriggerAreaActionDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public void act(Player p, PlayerDataRPG pd) {
    }

}
