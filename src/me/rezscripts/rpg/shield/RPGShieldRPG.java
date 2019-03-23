package me.rezscipts.rpg.shield;

import java.util.List;

import me.rezscipts.rpgexperience.RPGCore;
import me.rezscipts.rpgexperience.shield.RPGShield;
import me.rezscipts.rpgexperience.shield.ShieldCheck;

public class RPGShieldRPG extends RPGShield {
	
	@Override
	public RPGCore getPlugin() {
		return RPGCore.plugin;
	}
	
	@Override
    public List<ShieldCheck> getChecks() {
        return null;
    }

    @Override
    public void halt() {
        //        getPlugin()
    }

}
