package me.rezscipts.rpg.warps;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.slikey.effectlib.util.ParticleEffect;
import me.rezscipts.rpg.AbstractManagerRPG;
import me.rezscipts.rpg.RPG;
import me.rezscipts.rpg.particles.EffectFactory;
import me.rezscipts.rpg.particles.custom.misc.CustomWarpEffect;
import me.rezscipts.rpgexperience.utils.RMessages;
import me.rezscipts.rpgexperience.utils.RParticles;
import me.rezscipts.rpgexperience.utils.RScheduler;
import me.rezscipts.rpgexperience.utils.RTicks;

public class WarpManager extends AbstractManagerRPG {

    public static TreeMap<String, Location> warps = new TreeMap<String, Location>();

    public static final int WARP_TIME = 3;
    private static final double THRESHOLD = 0.1;

    private static HashSet<String> currentlyWarping = new HashSet<String>();

    public static void cleanup(String name) {
        currentlyWarping.remove(name);
    }

    public static void warp(Player p, Location dest, WarpCallback callback) {
        warp(p, dest, callback, null, WARP_TIME);
    }

    public static void warp(Player p, Location dest, WarpCallback callback, WarpChecker checker) {
        warp(p, dest, callback, checker, WARP_TIME);
    }

    public static void warp(Player p, Location dest, WarpCallback callback, WarpChecker checker, int time) {
        if (currentlyWarping.contains(p.getName())) {
            p.sendMessage(ChatColor.RED + "You are already warping!");
            return;
        }
        currentlyWarping.add(p.getName());
        try {
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, time * 20 * 3, 10, false, false));
            CustomWarpEffect we = EffectFactory.getWarpEffect(p, time);
            we.setEntity(p);
            we.start();
            String name = p.getName();
            RScheduler.schedule(plugin, new Runnable() {
                Location lastLoc = null;
                int count = time;

                private void endWarp(boolean success) {
                    if (callback != null)
                        callback.complete(success);
                    p.removePotionEffect(PotionEffectType.CONFUSION);
                    we.cancel();
                    lastLoc = null;
                    currentlyWarping.remove(name);
                }

                public void run() {
                    try {
                        if (p == null || !p.isOnline())
                            return;
                        if (lastLoc == null) {
                            lastLoc = p.getLocation();
                        } else {
                            Location curr = p.getLocation();
                            if (Math.abs(curr.getX() - lastLoc.getX()) > THRESHOLD || Math.abs(curr.getY() - lastLoc.getY()) > THRESHOLD || Math.abs(curr.getZ() - lastLoc.getZ()) > THRESHOLD) {
                                p.sendMessage("");
                                p.sendMessage(ChatColor.GRAY + ">> " + ChatColor.RED + "Warp cancelled due to movement.");
                                RMessages.sendTitle(p, ChatColor.RED + "Warp cancelled", "", 2, 30, 5);
                                endWarp(false);
                                return;
                            }
                        }
                        String checkerMsg = null;
                        if (checker != null && (checkerMsg = checker.check(p)) != null) {
                            p.sendMessage("");
                            p.sendMessage(ChatColor.GRAY + ">> " + ChatColor.RED + checkerMsg);
                            RMessages.sendTitle(p, ChatColor.RED + "Warp cancelled", "", 2, 30, 5);
                            endWarp(false);
                            return;
                        }
                        if (count == 0)
                            RMessages.sendTitle(p, "Warping now!", ChatColor.GREEN + "Move to cancel the warp", 2, 16, 2);
                        else
                            RMessages.sendTitle(p, "Warping in " + count, ChatColor.GREEN + "Move to cancel the warp", 2, 16, 2);
                        count--;
                        if (count < 0) {
                            RParticles.showWithOffset(ParticleEffect.CLOUD, p.getLocation().add(0, 1, 0), 1.5, 30);
                            p.teleport(dest);
                            RParticles.showWithOffset(ParticleEffect.CLOUD, p.getLocation().add(0, 1, 0), 1.5, 30);
                            endWarp(true);
                        } else {
                            RScheduler.schedule(plugin, this, RTicks.seconds(1));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        currentlyWarping.remove(p.getName());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            currentlyWarping.remove(p.getName());
        }
    }

    public static Location stringToLoc(String s) {
        try {
            String[] data = s.split(" ");
            double x = Double.parseDouble(data[0]);
            double y = Double.parseDouble(data[1]);
            double z = Double.parseDouble(data[2]);
            float yaw = Float.parseFloat(data[3]);
            float pitch = Float.parseFloat(data[4]);
            StringBuilder sb = new StringBuilder("");
            for (int k = 5; k < data.length; k++)
                sb.append(data[k] + " ");
            return new Location(plugin.getServer().getWorld(sb.toString().trim()), x, y, z, yaw, pitch);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String locToString(Location loc) {
        return loc.getX() + " " + loc.getY() + " " + loc.getZ() + " " + loc.getYaw() + " " + loc.getPitch() + " " + loc.getWorld().getName();
    }

    public static void addWarp(String name, Location loc) {
        warps.put(name, loc);
        saveWarps();
    }

    public static void saveWarps() {
        File f = new File(plugin.getDataFolder() + File.separator + "warps.YAML");
        YamlConfiguration yf = new YamlConfiguration();
        try {
            yf.load(f);
            int i = 1;
            for (Entry<String, Location> e : warps.entrySet()) {
                if (e != null && e.getKey() != null && e.getValue() == null)
                    continue;
                yf.createSection("Warp " + i);
                yf.getConfigurationSection("Warp " + i).set("Name",e.getKey());
                yf.getConfigurationSection("Warp " + i).set("Loc",locToString(e.getValue()));
                i+=1;
            }

            yf.save(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadWarps() {
        File f = new File(plugin.getDataFolder() + File.separator + "warps.YAML");

        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            YamlConfiguration yf = new YamlConfiguration();
            yf.load(f);
            int i = 1;
            while (yf.getConfigurationSection("Warp " + i) != null)
            {
                Location loc = stringToLoc(yf.getConfigurationSection("Warp " + i).getString("Loc"));
                String name = yf.getConfigurationSection("Warp " + i).getString("Name");

                if (loc == null){
                    Bukkit.getServer().getConsoleSender().sendMessage( "Warp: " + name + " failed to init.");
                    i+=1;
                    continue;
                }

                warps.put(name, loc);
                i+=1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WarpManager(RPG plugin) {
        super(plugin);
    }

    @Override
    public void initialize() {
        loadWarps();
    }
}
