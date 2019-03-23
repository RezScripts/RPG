package me.rezscripts.rpg.commands.builder;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.commands.RPGAbstractCommand;

public class RPGReplaceCommand extends RPGAbstractCommand {

    public static HashMap<UUID, PermissionAttachment> worldedit_pa = new HashMap<UUID, PermissionAttachment>();

    public RPGReplaceCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(final Player p, PlayerDataRPG pd, String[] args) {
        if (args.length != 3) {
            p.sendMessage("/rpgreplace <size> <from-id> <to-id>");
            return;
        }
        String size = args[0];
        String fromID = args[1];
        String toID = args[2];
        for (int k = 0; k < 16; k++)
            p.performCommand("/replacenear " + size + " " + fromID + ":" + k + " " + toID + ":" + k);
        p.sendMessage("Finished replacing with matching data values.");
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
