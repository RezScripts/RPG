package me.rezscipts.rpg.items;

import me.rezscipts.rpg.AbstractManagerRPG;
import me.rezscipts.rpg.PlayerDataRPG;
import me.rezscipts.rpg.RPG;
import me.rezscipts.rpg.items.stats.StatAccumulator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.HashMap;

public class ItemManager extends AbstractManagerRPG {

    public static HashMap<String, String> itemNameToIdentifierMap = new HashMap<String, String>();
    public static HashMap<String, RPGItem> itemIdentifierToRPGItemMap = new HashMap<String, RPGItem>();

    public static HashMap<String, ItemRunnable> itemIdentifierToRunnableMap = new HashMap<String, ItemRunnable>();

    private static HashMap<String, ItemStack> itemIdentifierToItemstackMap = new HashMap<String, ItemStack>();

    public ItemManager(RPG plugin) {
        super( plugin );
        EtcItem.plugin = plugin;
    }

    @Override
    public void initialize() {
        reload();
    }

    public static void reload() {
        itemIdentifierToRPGItemMap.clear();
        itemNameToIdentifierMap.clear();
        itemIdentifierToRunnableMap.clear();
        itemIdentifierToItemstackMap.clear();

        File dir = new File( plugin.getDataFolder(), "items" );
        if (!dir.exists())
            dir.mkdirs();
        for (File f : dir.listFiles()) {
            if (f.getName().endsWith( ".YAML" )) {
                readItem( f );
            }
        }
        EtcItem.postInitialize();
    }

    public static boolean isItem(ItemStack item, String target) {
        if (item == null)
            return false;
        if (!item.hasItemMeta())
            return false;
        if (!item.getItemMeta().hasDisplayName())
            return false;
        String disp = item.getItemMeta().getDisplayName().replace( ChatColor.RESET.toString(), "" );
        String identifier = itemNameToIdentifierMap.get( disp );
        if (identifier == null)
            return false;
        return identifier.equals( target );
    }

    public static void registerItemRunnable(String identifier, ItemRunnable runnable) {
        itemIdentifierToRunnableMap.put( identifier, runnable );
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack item = event.getPlayer().getEquipment().getItemInMainHand();
        if (event.getHand() == EquipmentSlot.HAND && item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            String name = item.getItemMeta().getDisplayName();
            if (itemNameToIdentifierMap.containsKey( name )) {
                String identifier = itemNameToIdentifierMap.get( name );
                if (itemIdentifierToRunnableMap.containsKey( identifier )) {
                    PlayerDataRPG pd = plugin.getPD( p );
                    if (pd != null)
                        itemIdentifierToRunnableMap.get( identifier ).run( event, p, pd );
                    event.setCancelled( true );
                }
            }
        }
    }

    public boolean isSoulbound(ItemStack item) {
        if (item == null)
            return false;
        if (item.getItemMeta() == null)
            return false;
        ItemMeta im = item.getItemMeta();
        if (!im.hasLore())
            return false;
        if (im.getLore().size() < 1)
            return false;
        boolean res = ChatColor.stripColor( im.getLore().get( 0 ) ).equals( "Soulbound" );
        //        System.out.println(item + " soulbound? " + res);
        return res;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (isSoulbound( event.getItemDrop().getItemStack() )) {
            event.getPlayer().sendMessage( ChatColor.RED + "You cannot drop Soulbound items!" );
            event.setCancelled( true );
        }
    }

    private static void readItem(File f) {
        try {
            String itemIdentifier = f.getName();
            if (!itemIdentifier.contains( "_" ))
                throw new Exception( "Item type not identified (" + itemIdentifier + ")." );
            boolean equip = itemIdentifier.contains( "equip-" );
            itemIdentifier = itemIdentifier.substring( itemIdentifier.indexOf( "_" ) + 1, itemIdentifier.indexOf( ".YAML" ) );
            //            System.out.println("Item name: " + itemIdentifier + " | Equip? " + equip);

            YamlConfiguration yf = new YamlConfiguration();
            yf.load( f );

            String itemDisplayName = ChatColor.translateAlternateColorCodes( '&', yf.getString( "Display Name" ) );
            Boolean soulbound = yf.getBoolean( "Soulbound" );
            String description = yf.getString( "Description" );


            if (equip) {
                Material m = null;
                if (yf.getString( "Material" ).toUpperCase().startsWith( "ELIXIR" )) {
                    m = Material.DRAGONS_BREATH;
                } else {
                    m = Material.getMaterial( yf.getString( "Material" ) );
                }

                org.bukkit.Color leatherColor = null;
                if (yf.getString( "RGB" ) != null) {
                    String[] data = yf.getString( "RGB" ).split( "," );
                    leatherColor = Color.fromRGB( Integer.parseInt( data[0] ), Integer.parseInt( data[1] ), Integer.parseInt( data[2] ) );
                }

                int level = 0;


                if (yf.getInt( "Level" ) != 0) {
                    level = yf.getInt( "Level" );
                }

                if (m == null)
                    Log.error( "Could not find material " + yf.getString( "Material" ) + " for equipitem " + itemIdentifier );


                EquipItem item = new EquipItem( m );
                item.name = itemDisplayName;

                item.description = description;
                item.identifier = itemIdentifier;
                item.leatherColor = leatherColor;
                item.soulbound = soulbound;

                StatAccumulator sa = item.stats;
                sa.level = level;

                int i = 1;

                if (yf.getConfigurationSection( "Stats" ) != null)
                {
                    while (yf.getConfigurationSection( "Stats" ).getString( "Stat " + i) != null) {
                        String stat = yf.getConfigurationSection( "Stats" ).getString( "Stat " + i);
                        sa.parseAndAccumulate( ChatColor.stripColor( stat ).trim() );
                        Bukkit.getConsoleSender().sendMessage( i + "-" + stat);
                        i+=1;
                    }
                }

                itemNameToIdentifierMap.put( item.name, item.identifier );
                itemIdentifierToRPGItemMap.put( item.identifier, item );
            } else {
                String s = yf.getString( "Material" );
                Material m = null;

                if (s.toUpperCase().startsWith( "ELIXIR" )) {
                    m = Material.DRAGONS_BREATH;
                } else {
                    m = Material.getMaterial( s.toUpperCase() );
                }
                if (m == null)
                    Log.error( "Could not find material " + s + " for etcitem " + itemIdentifier );

                EtcItem item = new EtcItem();
                item.name = itemDisplayName;
                item.material = m;
                item.description = description;
                item.identifier = itemIdentifier;
                item.soulbound = soulbound;

                itemNameToIdentifierMap.put( item.name, item.identifier );
                itemIdentifierToRPGItemMap.put( item.identifier, item );
            }
        } catch (Exception e) {
            Log.error( "Could not read file " + f );
            e.printStackTrace();
        }
    }

    public static boolean isItemWithLevel(ItemStack item) {
        return item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getLore().size() >= 1 && item.getItemMeta().getLore().get( 0 ).contains( "Level" );
    }

    public static void fixDurability(ItemStack item) {
        if (item.getType().getMaxDurability() > 0)
            item.setDurability( (short) 0 );
    }

    public static ItemStack getItemForIdentifier(String id) {
        if (itemIdentifierToRPGItemMap.containsKey( id )) {
            if (itemIdentifierToItemstackMap.containsKey( id ))
                return itemIdentifierToItemstackMap.get( id );
            ItemStack item = itemIdentifierToRPGItemMap.get( id ).generate();
            itemIdentifierToItemstackMap.put( id, item );
            return item;
        }
        return null;
    }

    public static boolean hasItem(Player p, ItemStack item) {
        for (int k = 0; k < p.getInventory().getSize(); k++) {
            ItemStack i = p.getInventory().getItem( k );
            if (i != null && i.isSimilar( item )) {
                return true;
            }
        }
        return false;
    }

    public static boolean takeOneItem(Player p, ItemStack item) {
        for (int k = 0; k < p.getInventory().getSize(); k++) {
            ItemStack i = p.getInventory().getItem( k );
            if (i != null && i.isSimilar( item )) {
                if (i.getAmount() > 1) {
                    i.setAmount( i.getAmount() - 1 );
                    p.getInventory().setItem( k, i );
                    p.updateInventory();
                } else {
                    i.setType( Material.AIR );
                    i.setAmount( 0 );
                    p.getInventory().setItem( k, null );
                }
                return true;
            }
        }
        return false;
    }

    public static int count(Player p, String id) {
        ItemStack item = ItemManager.getItemForIdentifier( id );
        if (item == null) {
            System.out.println( "Invalid identifier: " + id );
            return 0;
        }
        return count( p, item );
    }

    public static int count(Player p, ItemStack item) {
        int total = 0;
        for (ItemStack i : p.getInventory()) {
            if (i != null && i.isSimilar( item )) {
                total += i.getAmount();
            }
        }
        return total;
    }

    public static boolean take(Player p, String id, int amount) {
        ItemStack item = ItemManager.getItemForIdentifier( id );
        if (item == null) {
            System.out.println( "Invalid identifier: " + id );
            return false;
        }
        return take( p, item, amount );
    }

    public static boolean take(Player p, ItemStack item, int amount) {
        if (count( p, item ) < amount)
            return false;
        int remaining = amount;
        for (int k = 0; k < p.getInventory().getSize(); k++) {
            ItemStack i = p.getInventory().getItem( k );
            if (i != null && i.isSimilar( item )) {
                if (i.getAmount() > remaining) {
                    i.setAmount( i.getAmount() - remaining );
                    remaining = 0;
                    p.getInventory().setItem( k, i );
                    p.updateInventory();
                } else {
                    remaining -= i.getAmount();
                    System.out.println( "subtracting " + i.getAmount() + ", " + remaining + " remaining" );
                    i.setType( Material.AIR );
                    i.setAmount( 0 );
                    p.getInventory().setItem( k, null );
                }
            }
        }
        return true;
    }
}