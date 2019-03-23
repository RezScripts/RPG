package me.rezscripts.rpg.commands.member;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.commands.RPGAbstractCommand;
import me.rezscripts.rpg.help.HelpManager;

public class HelpCommand extends RPGAbstractCommand {

    public HelpCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(Player p, PlayerDataRPG pd, String[] args) {
        HelpManager.openMenu(p);
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
