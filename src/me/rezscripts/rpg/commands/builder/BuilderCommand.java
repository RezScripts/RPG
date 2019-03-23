package me.rezscripts.rpg.commands.builder;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.commands.RPGAbstractCommand;
import me.rezscripts.rpgexperience.players.Rank;
import me.rezscripts.rpgexperience.utils.RMessages;

public class BuilderCommand extends RPGAbstractCommand {

    public BuilderCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(Player p, PlayerDataRPG pd, String[] args) {
        if (args.length != 1) {
            p.sendMessage(ChatColor.RED + "Use as /builder <name>");
        } else if (args.length == 1) {
            Player p2 = plugin.getServer().getPlayer(args[0]);
            if (p2 != null && p2.isValid() && p2.isOnline()) {
                PlayerDataRPG pd2 = plugin.getPD(p2);
                pd2.setRank(Rank.BUILDER);
                p.sendMessage(ChatColor.GREEN + p2.getName() + "'s rank set to " + pd2.getChatRankPrefix().trim() + ChatColor.GREEN + ".");
                p2.sendMessage(ChatColor.GREEN + "Your rank was set to " + pd2.getChatRankPrefix().trim() + ChatColor.GREEN + ".");
                RMessages.announce(ChatColor.GREEN + ChatColor.BOLD.toString()+ p2.getName() + " was given builder permissions by " + p.getName() + ".");
            } else {
                p.sendMessage("User is not online.");
            }
        }
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
