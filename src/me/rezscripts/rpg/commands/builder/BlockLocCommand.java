package me.rezscripts.rpg.commands.builder;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.commands.RPGAbstractCommand;
import me.rezscripts.rpg.general.SchematicManager;

public class BlockLocCommand extends RPGAbstractCommand {

    public BlockLocCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(Player p, PlayerDataRPG pd, String[] args) {
        SchematicManager.giveBlockItem(p);
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
