package me.rezscipts.rpg.holograms;

import me.rezscipts.rpg.AbstractManagerRPG;
import me.rezscipts.rpg.RPG;
import me.rezscipts.rpgexperience.utils.ChunkWrapper;
import me.rezscipts.rpgexperience.utils.RScheduler;
import me.rezscipts.rpgexperience.utils.RTicks;
import me.rezscipts.rpgexperience.utils.RUtils;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkLoadEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class HologramManager extends AbstractManagerRPG {

    public static ArrayList<Hologram> holograms = new ArrayList<Hologram>();
    public static HashMap<ChunkWrapper, ArrayList<Hologram>> hologramsPerChunk = new HashMap<ChunkWrapper, ArrayList<Hologram>>();

    public HologramManager(RPG plugin) {
        super( plugin );
    }

    @Override
    public void initialize() {
        reload();
    }

    public static void reload() {
        for (Hologram h : holograms) {
            unregister( h );
        }
        holograms.clear();
        File dir = new File( plugin.getDataFolder(), "holograms" );
        if (!dir.exists())
            dir.mkdirs();
        for (File f : dir.listFiles()) {
            if (f.getName().endsWith( ".YAML" )) {
                readHologram( f );
            }
        }
    }

    public static void readHologram(File f) {
        YamlConfiguration cf = new YamlConfiguration();
        try {
            cf.load( f );

            Location l = RUtils.getLocationString( cf.getString( "Location" ) );
            if (l == null) {
                throw new Exception( "Error: could not find holo world at " + cf.getString( "Location" ) + " in file " + f );
            }

            StringBuilder sb = new StringBuilder();
            int i = 1;
            while (cf.getConfigurationSection( "Lines" ).getConfigurationSection( "Line " + i ) != null) {
                sb.append( cf.getConfigurationSection( "Lines" ).getConfigurationSection( "Line " + i ) );
                sb.append( "\n" );
                i += 1;
            }


            Hologram holo = new Hologram( ChatColor.translateAlternateColorCodes( '&', sb.toString().trim() ), l );
            holo.spawn();
            register( holo );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void register(Hologram holo) {
        ChunkWrapper cw = new ChunkWrapper( holo.loc.getChunk() );
        if (!hologramsPerChunk.containsKey( cw )) {
            hologramsPerChunk.put( cw, new ArrayList<Hologram>() );
        }
        hologramsPerChunk.get( cw ).add( holo );
        holograms.add( holo );
    }

    public static void unregister(Hologram holo) {
        try {
            holo.despawn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ChunkWrapper cw = new ChunkWrapper( holo.loc.getChunk() );
        if (hologramsPerChunk.containsKey( cw )) {
            hologramsPerChunk.get( cw ).remove( holo );
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        handleChunk( event.getChunk() );
    }

    public static void handleChunk(Chunk chunk) {
        final ChunkWrapper cw = new ChunkWrapper( chunk );
        if (!hologramsPerChunk.containsKey( cw ))
            return;
        RScheduler.schedule( plugin, new Runnable() {
            public void run() {
                ArrayList<Hologram> holos = hologramsPerChunk.get( cw );
                for (Hologram h : holos)
                    h.spawn();
            }
        }, RTicks.seconds( 2 ) );
    }

}