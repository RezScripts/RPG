package me.rezscripts.rpg.commands.beta;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.rezscripts.rpg.PlayerDataRPG;
import me.rezscripts.rpg.commands.RPGAbstractCommand;
import me.rezscripts.rpgexperience.utils.RTicks;

public class NightVisionCommand extends RPGAbstractCommand {
    
    public NightVisionCommand(String... commandNames) {
        super(commandNames);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
    }

    @Override
    public void executePlayer(final Player p, PlayerDataRPG pd, String[] args) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, RTicks.seconds(600), 1), false);
        p.sendMessage("Gave you night vision.");
    }

    @Override
    public void executeConsole(CommandSender sender, String[] args) {
    }

}
