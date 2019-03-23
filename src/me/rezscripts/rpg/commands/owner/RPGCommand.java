package me.rezscripts.rpg.commands.owner;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.commands.RPGAbstractCommand;
import me.rezscripts.rpgexperience.utils.RMessages;

public class RPGCommand extends RPGAbstractCommand {

    public static final String PREFIX = ChatColor.GRAY + "[0] " + ChatColor.AQUA + ChatColor.BOLD + "Bot " + ChatColor.WHITE + "Cecilia: " + ChatColor.GOLD;
    
    public RPGCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        StringBuilder sb = new StringBuilder("");
        for (String s : args) {
            sb.append(s + " ");
        }
        String message = PREFIX + ChatColor.translateAlternateColorCodes('&', sb.toString().trim());
        RMessages.announce(message);
    }

    @Override
    public void executePlayer(Player p, PlayerDataRPG pd, String[] args) {

    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
