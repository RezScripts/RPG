package me.rezscripts.rpg.commands.member;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.commands.RPGAbstractCommand;

public class BankCommand extends RPGAbstractCommand {

    public BankCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(final Player p, PlayerDataRPG pd, String[] args) {
        pd.openBank();
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
