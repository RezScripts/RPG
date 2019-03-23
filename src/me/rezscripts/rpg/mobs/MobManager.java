package me.rezscipts.rpg.mobs;

import me.rezscipts.rpg.AbstractManagerRPG;
import me.rezscipts.rpg.RPG;
import me.rezscipts.rpg.items.ItemManager;
import me.rezscipts.rpg.items.RPGItem;
import me.rezscipts.rpg.mobs.spells.MobSpell;
import me.rezscipts.rpg.worldboss.bosses.BossGenerator;
import me.rezscipts.rpgexperience.utils.RScheduler;
import me.rezscipts.rpgexperience.utils.RTicks;
import me.rezscipts.rpgexperience.utils.RUtils;
import net.minecraft.server.v1_10_R1.Entity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

public class MobManager extends AbstractManagerRPG {

    public static HashMap<String, MobType> mobTypes;
    public static HashMap<UUID, MobData> spawnedMobs; //these are all 3 parts of each mob
    public static HashMap<UUID, MobData> spawnedMobs_onlyMain; //each mob in rpg is 3 parts. these are the visible parts

    public static ArrayList<MobSpawn> spawns;

    private static int taskCounter = 0;

    public MobManager(RPG plugin) {
        super( plugin );
    }

    @Override
    public void initialize() {
        mobTypes = new HashMap<String, MobType>();
        spawnedMobs = new HashMap<UUID, MobData>();
        spawnedMobs_onlyMain = new HashMap<UUID, MobData>();
        spawns = new ArrayList<MobSpawn>();
        reload();
    }

    public static void reload() {
        Collection<MobData> collection = new ArrayList<MobData>();
        collection.addAll( spawnedMobs.values() );
        for (MobSpawn ms : spawns)
            ms.stop();
        for (MobData m : collection)
            m.die( false );

        mobTypes.clear();
        spawnedMobs.clear();
        spawnedMobs_onlyMain.clear();
        spawns.clear();
        File dir = new File( plugin.getDataFolder(), "mobs" );
        if (!dir.exists())
            dir.mkdirs();
        for (File f : dir.listFiles()) {
            if (f.getName().endsWith( ".YAML" )) {
                readMob( f );
            }
        }
        BossGenerator.plugin = plugin;
        dir = new File( plugin.getDataFolder(), "spawns" );
        if (!dir.exists())
            dir.mkdirs();
        for (File f : dir.listFiles()) {
            if (f.getName().endsWith( ".YAML" )) {
                readSpawn( f );
            }
        }
        Collections.shuffle( spawns );
        for (MobSpawn ms : spawns)
            ms.spawn( taskCounter++ );
    }

    public static void respawn(MobSpawn ms) {
        if (ms == null)
            return;
        RScheduler.schedule( plugin, () -> {
            ms.spawn( taskCounter++ );
        }, RTicks.seconds( ms.respawnDelay ) );
    }

    private static void readMob(File f) {
        YamlConfiguration yf = new YamlConfiguration();
        try {
            if (f.exists())
            yf.load(f);
            // name
            String name = yf.getString( "Name" );
            //type
            @SuppressWarnings("unchecked")
            Class<? extends net.minecraft.server.v1_10_R1.Entity> entityClass = (Class<? extends Entity>) Class.forName( "me.rezscipts.rpgexperience.utils.entities." + yf.getString( "Entity Class" ) );
            // level
            int level = yf.getInt( "Level" );
            // prefixes
            ArrayList<String> prefixes = new ArrayList<String>();
            if (yf.getStringList("Prefix List") != null) {
                prefixes = new ArrayList<>( yf.getStringList( "Prefix List" ).size() );
                prefixes.addAll( yf.getStringList( "Prefix List" ) );
            }
            // suffixes
            ArrayList<String> suffixes = new ArrayList<String>();
            if (yf.getStringList("Suffix List") != null) {
                suffixes = new ArrayList<>( yf.getStringList( "Suffix List" ).size() );
                suffixes.addAll( yf.getStringList( "Suffix List" ) );
            }

            long exp = (long) (MobBalance.getMobEXP( level, yf.getDouble( "XP Drop Multiply" ) ) / 100.0);
            //health
            long health = (long) (MobBalance.getMobHP( level, yf.getDouble( "Health Multiply" ) ) / 100.0);
            // damage
            // one is damage line and one is for range line
            // int level, double damageMultiplier, double rangeMultiplier

            int[] dmg = MobBalance.getMobDamage( level, yf.getInt( "Dmg Multiplier" ) / 100.0, yf.getInt( "Dmg Range Multiplier" ) / 100.0 );
            int damageLow = dmg[0];
            int damageHigh = dmg[0];
            // equips
            boolean hasSkull = false;
            ArrayList<ItemStack> equips = new ArrayList<ItemStack>();
            ConfigurationSection cs = yf.getConfigurationSection( "Equips" );
            ItemStack offhand = null;

            if (cs != null) {

                if (cs.getConfigurationSection( "Head" ) != null) {
                    ConfigurationSection csH = cs.getConfigurationSection( "Head" );
                    ItemStack i = RUtils.getItemFromYAML( csH );

                    if (i.getType().equals( Material.SKULL_ITEM )) {
                        hasSkull = true;
                    }
                    equips.add( i );
                }
                if (cs.getConfigurationSection( "Chest" ) != null) {
                    ConfigurationSection csH = cs.getConfigurationSection( "Chest" );
                    equips.add( RUtils.getItemFromYAML( csH ) );
                }
                if (cs.getConfigurationSection( "Legs" ) != null) {
                    ConfigurationSection csH = cs.getConfigurationSection( "Legs" );
                    equips.add( RUtils.getItemFromYAML( csH ) );
                }
                if (cs.getConfigurationSection( "Boots" ) != null) {
                    ConfigurationSection csH = cs.getConfigurationSection( "Boots" );
                    equips.add( RUtils.getItemFromYAML( csH ) );
                }
                if (cs.getConfigurationSection( "MainHand" ) != null) {
                    ConfigurationSection csH = cs.getConfigurationSection( "MainHand" );
                    equips.add( RUtils.getItemFromYAML( csH ) );
                }
                if (cs.getConfigurationSection( "OffHand" ) != null) {
                    ConfigurationSection csH = cs.getConfigurationSection( "OffHand" );
                    offhand = (RUtils.getItemFromYAML( csH ));
                }

            }

            // attributes
            ArrayList<MobAttribute> attributes = new ArrayList<MobAttribute>();
            ConfigurationSection at = yf.getConfigurationSection( "Attributes" );

            if (at != null) {
                int i = 1;
                while (at.getString( i + "" ) != null) {
                    MobAttribute ma = MobAttribute.get( at.getString( i + "" ) );
                    if (ma != null) {
                        attributes.add( ma );
                    } else {
                        Log.error( "Could not find MobAttribute with id " + at.getString( i + "" ) + "." );
                    }
                    i += 1;
                }
            }

            // spells
            ArrayList<MobSpell> spells = new ArrayList<MobSpell>();
            ConfigurationSection as = yf.getConfigurationSection( "Spells" );

            if (as != null) {
                int i = 1;
                while (as.getString( i + "" ) != null) {
                    MobSpell ms = MobSpellbook.getSpell( as.getString( i + "" ) );

                    if (ms != null) {
                        spells.add( ms );
                    } else {
                        Log.error( "Could not find MobSpell with id " + as.getString( i + "" ) + "." );
                    }
                    i += 1;
                }
            }

            //drops

            ArrayList<MobDrop> drops = new ArrayList<MobDrop>();
            ConfigurationSection d = yf.getConfigurationSection( "Drops" );

            if (d != null) {
                int i = 1;
                while (as.getConfigurationSection( i + "" ) != null) {
                    ConfigurationSection n = as.getConfigurationSection( i + "" );
                    if (n.getString( "RPGItem" ) != null) {
                        RPGItem item = ItemManager.itemIdentifierToRPGItemMap.get( n.getString( "RPGItem" ) );

                        if (item == null) {
                            i += 1;
                            continue;
                        }

                        int minAmount = n.getInt( "min" );
                        int maxAmount = n.getInt( "max" );
                        double chance = n.getDouble( "chance" );
                        MobDrop md = new MobDrop( item, minAmount, maxAmount, chance );
                        drops.add( md );
                    } else {
                        Log.error( "Could not find MobSpell with id " + as.getString( i + "" ) + "." );
                    }
                    i += 1;
                }
            }

            MobType mt = new MobType();
            mt.identifier = f.getName().substring( 0, f.getName().indexOf( ".YAML" ) );
            mt.name = name;
            mt.entityClass = entityClass;
            mt.level = level;
            mt.prefixes = prefixes;
            mt.suffixes = suffixes;
            mt.exp = exp;
            mt.maxHP = health;
            mt.damageLow = damageLow;
            mt.damageHigh = damageHigh;
            mt.equips = equips;
            mt.attributes = attributes;
            mt.spells = spells;
            mt.drops = drops;
            mt.hasSkull = hasSkull;
            mt.offhand = offhand;
            if (mt.identifier.startsWith( "gift_" ))
                mt.exp = 100;
            mobTypes.put( mt.identifier, mt );
        } catch (Exception e) {
            System.out.println( "Error reading file " + f.getName() );
            e.printStackTrace();
        }
    }

    public static void readSpawn(File f) {
        YamlConfiguration ym = new YamlConfiguration();
        try {
            ym.load( f );
            int i = 1;
            ArrayList<MobType> list = new ArrayList<MobType>();
            while (ym.getConfigurationSection( "Mobs" ).getString( "Mob " + i ) != null) {
                String mob = ym.getConfigurationSection( "Mobs" ).getString( "Mob " + i );

                MobType mt = MobManager.mobTypes.get( mob );
                if (mt == null) {
                    System.out.println( "Invalid mob spawn: " + mob + " in " + f.getPath() );
                    i += 1;
                    continue;
                }
                list.add( mt );
                i += 1;
            }

            ConfigurationSection cs = ym.getConfigurationSection( "Location" );
            int x = cs.getInt( "X" );
            int y = cs.getInt( "Y" );
            int z = cs.getInt( "Z" );
            World w = Bukkit.getWorld( cs.getString( "World" ) );
            int range = cs.getInt( "Spawn Range" );
            int delay = cs.getInt( "Spawn Delay" );
            int leash = cs.getInt( "Spawn Leash" );

            if (w == null) {
                System.out.println( "Invalid mob spawn loc: " + f.getName());
                return;
            }

            Location loc = new Location( w, x, y, z );
            MobSpawn ms = new MobSpawn( list.toArray( new MobType[list.size()] ), loc, range, delay, leash );
            spawns.add( ms );


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MobData createMob(String identifier, Location loc) {
        MobType mt = MobManager.mobTypes.get( identifier );
        if (mt == null) {
            Log.error( "Attempted to spawn non-existing mob " + identifier + "." );
            return null;
        }
        return mt.spawn( loc );
    }
}