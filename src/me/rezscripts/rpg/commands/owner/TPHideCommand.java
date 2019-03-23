package me.rezscripts.rpg.commands.owner;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.commands.RPGAbstractCommand;
import me.rezscripts.rpgexperience.commands.mod.TeleportCommand;

public class TPHideCommand extends RPGAbstractCommand {

    public TPHideCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(Player p, PlayerDataRPG pd, String[] args) {

        if (TeleportCommand.noTP.contains(p.getName())) {
            p.sendMessage("people can tp to you now.");
            TeleportCommand.noTP.remove(p.getName());
        } else {
            p.sendMessage("people can't tp to you anymore!");
            TeleportCommand.noTP.add(p.getName());
        }
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
