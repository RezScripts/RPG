package me.rezscipts.rpg.music;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import me.rezscipts.rpg.AbstractManagerRPG;
import me.rezscipts.rpg.RPG;
import me.rezscipts.rpg.music.api.NBSDecoder;
import me.rezscipts.rpg.music.api.RadioSongPlayer;
import me.rezscipts.rpg.music.api.Song;
import me.rezscipts.rpg.music.api.SongPlayer;
import me.rezscipts.rpgexperience.utils.RMessages;

public class MusicManager extends AbstractManagerRPG {

    private static File dir;

    public MusicManager(RPG plugin) {
        super(plugin);
    }

    @Override
    public void initialize() {
        dir = new File(plugin.getDataFolder(), "music");
        if (!dir.exists())
            dir.mkdirs();
    }

    public static HashMap<String, ArrayList<SongPlayer>> playingSongs = new HashMap<String, ArrayList<SongPlayer>>();
    public static HashMap<String, Byte> playerVolume = new HashMap<String, Byte>();

    public static boolean isReceivingSong(Player p) {
        return ((playingSongs.get(p.getName()) != null) && (!playingSongs.get(p.getName()).isEmpty()));
    }

    public static void stopPlaying(Player p) {
        if (playingSongs.get(p.getName()) == null) {
            return;
        }
        for (SongPlayer s : playingSongs.get(p.getName())) {
            s.removePlayer(p);
        }
    }

    public static void setPlayerVolume(Player p, byte volume) {
        playerVolume.put(p.getName(), volume);
    }

    public static byte getPlayerVolume(Player p) {
        Byte b = playerVolume.get(p.getName());
        if (b == null) {
            b = 100;
            playerVolume.put(p.getName(), b);
        }
        return b;
    }

    public static void playMusic(Player p) {
        RMessages.announce("playmusic for " + p);
        Song song = NBSDecoder.parse(new File(dir, "zelda.nbs"));
        SongPlayer sp = new RadioSongPlayer(song);
        sp.setAutoDestroy(true);
        sp.addPlayer(p);
        sp.setPlaying(true);
    }

}