package me.rezscripts.rpg.commands.mod;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rezscripts.rpg.warps.WarpManager;
import me.rezscripts.rpgexperience.PlayerData;
import me.rezscripts.rpgexperience.commands.AbstractCommand;

public class SetWarpCommand extends AbstractCommand {

    public SetWarpCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(Player p, PlayerData pd, String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String s : args) {
            sb.append(s);
            sb.append(" ");
        }
        String name = sb.toString().trim().toLowerCase();
        if (WarpManager.warps.containsKey(name) || name.length() == 0) {
            p.sendMessage("Warp \"" + name.toLowerCase() + "\" already exists. Try a different name.");
        } else {
            WarpManager.addWarp(name, p.getLocation());
            WarpManager.saveWarps();
            p.sendMessage("Added warp \"" + name.toLowerCase() + "\" at your location. " + p.getLocation());
        }
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {

    }

}
