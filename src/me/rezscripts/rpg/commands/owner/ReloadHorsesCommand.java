package me.rezscripts.rpg.commands.owner;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.commands.RPGAbstractCommand;
import me.rezscripts.rpg.horses.HorseManager;
import me.rezscripts.rpgexperience.utils.RMessages;

public class ReloadHorsesCommand extends RPGAbstractCommand {

    public ReloadHorsesCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        HorseManager.reload();
        sender.sendMessage("Stables reloaded.");
        RMessages.announce(ChatColor.RED + "Stables reloaded for updates.");
    }

    @Override
    public void executePlayer(Player p, PlayerDataRPG pd, String[] args) {

    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
