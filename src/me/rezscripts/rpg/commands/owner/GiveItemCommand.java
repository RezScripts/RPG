package me.rezscripts.rpg.commands.owner;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.commands.RPGAbstractCommand;
import me.rezscripts.rpg.items.ItemManager;
import me.rezscripts.rpg.items.RPGItem;

public class GiveItemCommand extends RPGAbstractCommand {

    public GiveItemCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(Player p, PlayerDataRPG pd, String[] args) {
        String name = args[0];
        int amount = args.length > 1 ? Integer.parseInt(args[1]) : 1;
        RPGItem item = ItemManager.itemIdentifierToRPGItemMap.get(name);
        if (item == null) {
            p.sendMessage("Could not find item with name " + name + ".");
        } else {
            for (int k = 0; k < amount; k++)
                p.getInventory().addItem(item.generate());
        }
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
