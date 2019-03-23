package me.rezscripts.rpg;

import me.rezscripts.rpgexperience.AbstractManager;

public abstract class AbstractManagerRPG extends AbstractManager{
	
	public static RPG plugin;
	
	public AbstractManagerRPG(RPG pl) {
		super(pl);
		plugin = pl;
	}

}
