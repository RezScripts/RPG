package me.rezscipts.rpg.guilds;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.rezscipts.rpg.AbstractManagerRPG;
import me.rezscipts.rpg.PlayerDataRPG;
import me.rezscipts.rpg.RPG;
import me.rezscipts.rpg.classes.ClassType;
import me.rezscipts.rpgexperience.menus.MenuManager;


public class GuildManager extends AbstractManagerRPG {

    public static HashMap<String, Guild> guilds = new HashMap<String, Guild>();

    public GuildManager(RPG plugin) {
        super(plugin);
    }

    @Override
    public void initialize() {
        File dir = new File(plugin.getDataFolder(), "guilds");
        if (!dir.exists())
            dir.mkdirs();
        for (File f : dir.listFiles()) {
            if (f.getName().endsWith(".txt")) {
                readGuild(f);
            }
        }
    }

    public static void readGuild(File f) {
        //new guild, guild name, guild tag, leader UUID, Admin UUID, Members UUID...
        Scanner scan = null;
        try {
            YamlConfiguration y = new YamlConfiguration();
            y.load(f);


            scan = new Scanner(f);
            String s;
            s = scan.nextLine().trim();
            Guild g = new Guild();
            g.name = s;
            s = scan.nextLine().trim();
            g.tag = s;
            s = scan.nextLine().trim();
            g.leader = s;
            s = scan.nextLine().trim();
            g.leaderUUID = s;
            while(scan.hasNextLine()) {
                s = scan.nextLine().trim();
                String[] data = s.split(":");
                GuildMember gm = new GuildMember(data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]), ClassType.getClassType(data[5]));
                g.uuidToMember.put(gm.uuid, gm);
            }
        } catch (Exception e) {
            System.err.println("Error loading guild at " + f.getName());
            e.printStackTrace();
        } finally {
            if (scan != null)
                scan.close();
        }
    }

    public static void showMenu(Player p, PlayerDataRPG pd) {
        Inventory inventory = MenuManager.createMenu(p, "Guild Menu", 6, new Object[][] {
                {
                        0,
                        0,
                        Material.BOOK_AND_QUILL,
                        ChatColor.AQUA + "Guild Information",
                        new Object[] {
                                ChatColor.GREEN,
                                "Guilds in RPGExpereince are blah blah.",
                                null,
                                "",
                                ChatColor.AQUA,
                                "blah blah blah" },
                        new Runnable() {
                            public void run() {

                            }
                        }
                },
        });
        p.openInventory(inventory);
    }

}