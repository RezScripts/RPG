package me.rezscripts.rpg.commands.owner;

import me.rezscripts.rpgexperience.ManagerInstances;
import me.rezscripts.rpgexperience.quests.QuestManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.commands.RPGAbstractCommand;

public class CompleteQuestCommand extends RPGAbstractCommand {

    public CompleteQuestCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(final Player p, PlayerDataRPG pd, String[] args) {
        QuestManager qm = ((QuestManager) ManagerInstances.getInstance( QuestManager.class));
        if (!qm.getQuestIDList().contains(Integer.parseInt(args[0])))
        {
            pd.sendMessage("quest with id " + args[0] + " does not exist");
            return;
        }
        qm.getQuest(Integer.parseInt(args[0])).completeQuest(p);
        p.sendMessage("Added completed quest: " + args[0]);
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
