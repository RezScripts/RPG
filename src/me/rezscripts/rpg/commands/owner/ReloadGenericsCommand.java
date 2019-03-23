package me.rezscripts.rpg.commands.owner;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.commands.RPGAbstractCommand;
import me.rezscripts.rpg.npcs.generics.GenericNPCManager;
import me.rezscripts.rpgexperience.utils.RMessages;

public class ReloadGenericsCommand extends RPGAbstractCommand {

    public ReloadGenericsCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        GenericNPCManager.reloadGenerics();
        RMessages.announce(ChatColor.RED + "Generic NPCs reloaded for updates.");
    }

    @Override
    public void executePlayer(Player p, PlayerDataRPG pd, String[] args) {

    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
