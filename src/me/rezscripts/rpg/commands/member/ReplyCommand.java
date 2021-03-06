package me.rezscripts.rpg.commands.member;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.chat.ChatManager;
import me.rezscripts.rpg.commands.RPGAbstractCommand;

public class ReplyCommand extends RPGAbstractCommand {

    public ReplyCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(Player p, PlayerDataRPG pd, String[] args) {
        String s = ChatManager.lastReceivedWhisperFrom.get(p.getName());
        if (s == null) {
            p.sendMessage(ChatColor.RED + "You have no one to reply to...");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < args.length; k++) {
                sb.append(args[k]);
                sb.append(' ');
            }
            ChatManager.sendWhisper(p, s, pd, sb.toString());
        }
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
