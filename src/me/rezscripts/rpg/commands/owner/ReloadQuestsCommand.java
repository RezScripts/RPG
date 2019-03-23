package me.rezscripts.rpg.commands.owner;

import me.rezscripts.rpgexperience.ManagerInstances;
import me.rezscripts.rpgexperience.quests.QuestManager;
import me.rezscripts.rpgexperience.quests.questLine.QuestLineManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.commands.RPGAbstractCommand;
import me.rezscripts.rpgexperience.utils.RMessages;

public class ReloadQuestsCommand extends RPGAbstractCommand {

    public ReloadQuestsCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ((QuestLineManager) ManagerInstances.getInstance( QuestLineManager.class)).reload();
        ((QuestManager) ManagerInstances.getInstance( QuestManager.class)).reload();
        RMessages.announce(ChatColor.RED + "Quests reloaded for updates.");
    }

    @Override
    public void executePlayer(Player p, PlayerDataRPG pd, String[] args) {

    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
