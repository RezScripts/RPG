package me.rezscipts.rpg.regions;

import me.rezscipts.rpg.AbstractManagerRPG;
import me.rezscipts.rpg.PlayerDataRPG;
import me.rezscipts.rpg.RPG;
import me.rezscipts.rpg.regions.areas.TriggerArea;
import me.rezscipts.rpg.regions.areas.TriggerAreaAction;
import me.rezscipts.rpg.regions.areas.actions.TriggerAreaActionDelay;
import me.rezscipts.rpg.worldboss.WorldBossManager;
import me.rezscipts.rpgexperience.utils.RScheduler;
import me.rezscipts.rpgexperience.utils.RTicks;
import me.rezscipts.rpgexperience.utils.RUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RegionManager extends AbstractManagerRPG {

    protected static final String GENERAL_NAME = "The RPGExperience Continent";

    protected static final RegionPoint MIN_POINT = new RegionPoint( -1000000, -1000000, -1000000 );
    protected static final RegionPoint MAX_POINT = new RegionPoint( 1000000, 1000000, 1000000 );

    private static ArrayList<Region> regions;
    private static ArrayList<TriggerArea> areas;

    public RegionManager(RPG plugin) {
        super( plugin );
    }

    @Override
    public void initialize() {
        regions = new ArrayList<Region>();
        areas = new ArrayList<TriggerArea>();
        reload();
    }

    public static void reload() {
        regions.clear();
        for (World w : plugin.getServer().getWorlds()) {
            Region r = new Region( GENERAL_NAME, 1, 2, w, Arrays.asList( MIN_POINT, MAX_POINT ) );
            regions.add( r );
        }

        File dir = new File( plugin.getDataFolder(), "regions" );
        if (!dir.exists())
            dir.mkdirs();
        for (File f : dir.listFiles()) {
            if (f.getName().endsWith( ".YAML" )) {
                readRegion( f );
            }
        }
        System.out.println( "Loaded " + regions.size() + " regions." );
        dir = new File( plugin.getDataFolder(), "areas" );
        if (!dir.exists())
            dir.mkdirs();
        for (File f : dir.listFiles()) {
            if (f.getName().endsWith( ".YAML" )) {
                readArea( f );
            }
        }
        System.out.println( "Loaded " + areas.size() + " trigger areas." );
    }

    public static void readArea(File f) {
        YamlConfiguration yf = new YamlConfiguration();
        try {
            yf.load( f );
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return;
        }

        String worldString = yf.getString( "World" );
        String pos1String = yf.getString( "Pos1" );
        String pos2String = yf.getString( "Pos2" );
        String messageString = yf.getString( "Message" );
        String stateString = yf.getString( "State" );

        try {
            World w = plugin.getServer().getWorld( worldString );
            if (w == null) {
                throw new Exception( "Invalid world" );
            }

            ArrayList<RegionPoint> points = new ArrayList<RegionPoint>();
//                public RegionPoint(long x, long y, long z) {
            Location pos1 = RUtils.getLocationString( worldString + "=" + pos1String.replaceAll( " ", "=" ) );
            Location pos2 = RUtils.getLocationString( worldString + "=" + pos2String.replaceAll( " ", "=" ) );

            points.add( new RegionPoint( pos1.getBlockX(), pos1.getBlockY(), pos1.getBlockZ() ) );
            points.add( new RegionPoint( pos2.getBlockX(), pos2.getBlockY(), pos2.getBlockZ() ) );


            ArrayList<TriggerAreaAction> actions = new ArrayList<TriggerAreaAction>();
            long delay = 0;
            TriggerAreaAction taa = TriggerAreaAction.parse( messageString );

            if (taa == null) {
                throw new Exception( "Invalid trigger" );
            }

            if (taa != null) {
                if (taa instanceof TriggerAreaActionDelay) {
                    delay = ((TriggerAreaActionDelay) taa).delay;
                } else {
                    actions.add( taa );
                }
            }

            String regionName = f.getName().substring( 0, f.getName().indexOf( ".YAML" ) );
            if (regionName.contains( "." ))
                regionName = regionName.substring( 0, regionName.lastIndexOf( "." ) );
            TriggerArea ta = new TriggerArea( regionName, w, delay, points, actions );
            areas.add( ta );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readRegion(File f) {
        YamlConfiguration yf = new YamlConfiguration();
        try {
            yf.load( f );
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return;
        }

        String worldString = yf.getString( "World" );
        String pos1String = yf.getString( "Pos1" );
        String pos2String = yf.getString( "Pos2" );
        int reqLvl = yf.getInt( "Required Level" );
        int dangerLvl = yf.getInt( "Danger Level" );
        int startTime = yf.getInt( "Start Time" );
        int endTime = yf.getInt( "End Time" );
        int cycleLengthSeconds = yf.getInt( "Cycle Length Seconds" );
        String regionName = f.getName().substring( 0, f.getName().indexOf( ".YAML" ) );

        try {
            World w = plugin.getServer().getWorld( worldString );
            if (w == null) {
                throw new Exception( "Invalid world" );
            }
            ArrayList<RegionPoint> points = new ArrayList<RegionPoint>();
//                public RegionPoint(long x, long y, long z) {
            Location pos1 = RUtils.getLocationString( worldString + "=" + pos1String.replaceAll( " ", "=" ) );
            Location pos2 = RUtils.getLocationString( worldString + "=" + pos2String.replaceAll( " ", "=" ) );

            points.add( new RegionPoint( pos1.getBlockX(), pos1.getBlockY(), pos1.getBlockZ() ) );
            points.add( new RegionPoint( pos2.getBlockX(), pos2.getBlockY(), pos2.getBlockZ() ) );

            Region r = new Region(regionName, reqLvl, dangerLvl, w, points );
            r.startTime = startTime;
            r.endTime = endTime;
            r.timeDiff = r.endTime - r.startTime;
            r.cycleLengthSeconds = cycleLengthSeconds;
            regions.add( r );
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getFrom().getBlock().equals( event.getTo().getBlock() ))
            return;
        initiateCheck( event.getPlayer() );
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        RScheduler.schedule( plugin, () -> {
            initiateCheck( event.getPlayer() );
        }, RTicks.seconds( 1 ) );
    }

    private void initiateCheck(Player p) {
        if (p == null)
            return;
        final PlayerDataRPG pd = plugin.getPD( p );
        if (pd != null && pd.isValid()) {
            try {
                checkRegion( p, pd );
                checkArea( p, pd );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void checkArea(Player p, PlayerDataRPG pd) {
        if (!pd.loadedSQL)
            return;
        if (System.currentTimeMillis() - pd.lastAreaCheck > 200) {
            WorldBossManager.checkLocation( p );
            pd.lastAreaCheck = System.currentTimeMillis();
            TriggerArea ta = getArea( p );
            if (ta != null) {
                // only run triggerarea action once a second after the first time
                // longer cooldown must be implemented manually in TriggerArea implementations
                if (ta != pd.lastArea) { // last area has changed, force trigger immediately
                    pd.lastArea = ta;
                    pd.lastAreaTriggered = System.currentTimeMillis();
                    ta.runActions( p, pd );
                } else { // area hasn't changed (ta == last)
                    if (System.currentTimeMillis() - pd.lastAreaTriggered > 1000) { // has it been 1 sec? if so then trigger
                        pd.lastAreaTriggered = System.currentTimeMillis();
                        ta.runActions( p, pd );
                    }
                }
            }

        }
    }

    public static void checkRegion(Player p, PlayerDataRPG pd) {
        if (!pd.loadedSQL)
            return;
        boolean changedRegion = false;
        if (System.currentTimeMillis() - pd.lastRegionCheck > 200) {
            pd.lastRegionCheck = System.currentTimeMillis();
            Region r = getRegion( p );
            if (pd.region == null || pd.region != r) {
                Region last = pd.region;
                pd.region = r;
                r.sendWelcome( p, last );
                changedRegion = true;
                pd.lastRegionChange = System.currentTimeMillis();
            }
        }
        long delay = System.currentTimeMillis() - pd.lastRegionChange < 10000 ? 100 : 1000;
        if (changedRegion || System.currentTimeMillis() - pd.lastRegionTimeUpdate > delay) {
            updateTime( p, pd );
        }
    }

    public static void updateTime(Player p, PlayerDataRPG pd) {
        if (pd.region != null) {
            pd.lastRegionTimeUpdate = System.currentTimeMillis();
            //            PacketManager.registerTime(p, pd.region.getTime());
        }
    }

    public static TriggerArea getArea(Player p) {
        return getArea( p.getLocation() );
    }

    public static TriggerArea getArea(Location loc) {
        TriggerArea curr = null;
        for (TriggerArea a : areas) {
            if (a.isWithin( loc )) {
                if (curr == null || a.size < curr.size)
                    curr = a;
            }
        }
        return curr;
    }

    public static Region getRegion(Player p) {
        return getRegion( p.getLocation() );
    }

    public static Region getRegion(Location loc) {
        Region curr = null;
        for (Region r : regions) {
            if (r.isWithin( loc )) {
                if (curr == null || r.size < curr.size)
                    curr = r;
            }
        }
        if (curr == null) {
            // This should only happen if a world is loaded manually after regions have loaded
            Region r = new Region( GENERAL_NAME, 1, 2, loc.getWorld(), Arrays.asList( MIN_POINT, MAX_POINT ) );
            regions.add( r );
            return r;
        }
        return curr;
    }
}
