package me.rezscripts.rpg.commands.console;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.buycraft.BuycraftManager;
import me.rezscripts.rpg.commands.RPGAbstractCommand;

public class BuycraftRewardCommand extends RPGAbstractCommand {

    public BuycraftRewardCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            String name = args[0];
            String reward = args[1];
            BuycraftManager.giveReward(name, reward);
        } else if (args.length == 3 && args[0].equalsIgnoreCase("remove")) {
            String name = args[1];
            String reward = args[2];
            BuycraftManager.removeReward(name, reward);
        }
    }

    @Override
    public void executePlayer(Player p, PlayerDataRPG pd, String[] args) {
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
