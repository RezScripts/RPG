package me.rezscripts.rpg.commands.owner;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.commands.RPGAbstractCommand;

public class RezCommand extends RPGAbstractCommand {

    public RezCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        StringBuilder sb = new StringBuilder("");
        for (String s : args) {
            sb.append(s + " ");
        }
        String message = ChatColor.translateAlternateColorCodes('&', sb.toString().trim());
        for (Player p2 : plugin.getServer().getOnlinePlayers()) {
            p2.sendMessage(message);
        }
    }

    @Override
    public void executePlayer(Player p, PlayerDataRPG pd, String[] args) {

    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
