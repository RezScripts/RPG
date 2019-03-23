package me.rezscripts.rpg.commands.owner;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.commands.RPGAbstractCommand;

public class TPWorldCommand extends RPGAbstractCommand {

    public TPWorldCommand(String... s) {
        super(s);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        World w = plugin.getServer().getWorld(args[0]);

        if (w != null) {
            ((Player) sender).teleport(w.getSpawnLocation());
            sender.sendMessage( "TP'd to world: " + w.getName());
        }else{
            sender.sendMessage( "World not found");
        }
    }

    @Override
    public void executePlayer(Player p, PlayerDataRPG pd, String[] args) {

    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {

    }

}
