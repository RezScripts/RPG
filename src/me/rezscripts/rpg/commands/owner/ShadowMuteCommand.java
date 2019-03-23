package me.rezscripts.rpg.commands.owner;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.commands.RPGAbstractCommand;
import me.rezscripts.rpgexperience.chat.ChatManager;

public class ShadowMuteCommand extends RPGAbstractCommand {

    public ShadowMuteCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Use as /shadowmute <name>");
        } else if (args.length == 1) {
            Player p2 = plugin.getServer().getPlayer(args[0]);
            if (p2 != null && p2.isValid() && p2.isOnline()) {
                if(ChatManager.shadowMute.contains(p2.getName())) {
                    sender.sendMessage(ChatColor.GREEN + p2.getName() + " was taken off the shadowmute list.");
                    ChatManager.shadowMute.remove(p2.getName());
                } else {
                    sender.sendMessage(ChatColor.GREEN + p2.getName() + " was shadowmuted!");
                    ChatManager.shadowMute.add(p2.getName());
                }
            } else {
                sender.sendMessage("User is not online.");
            }
        }
    }

    @Override
    public void executePlayer(Player p, PlayerDataRPG pd, String[] args) {

    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
