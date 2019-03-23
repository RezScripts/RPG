package me.rezscripts.rpg.commands.member;

import me.rezscripts.rpg.RPG;
import me.rezscripts.rpg.regions.RegionManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.commands.RPGAbstractCommand;

public class RegionCommand extends RPGAbstractCommand {

    public RegionCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(Player p, PlayerDataRPG pd, String[] args) {
        if(pd.region != null) {
           pd.region.sendWelcome(p, null); 
            p.sendMessage("Region: " + pd.region.name);
        }
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
