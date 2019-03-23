package me.rezscipts.rpg.warps;

import org.bukkit.Location;
import org.bukkit.World;

import me.rezscipts.rpg.RPG;

public enum WarpLocation {
	/**
	 * We can edit the names and locations and any given time just waiting on builders
	 */
    KORWYN(RPG.GAME_WORLD, -139.5, 50.5, -718.5, -180, -23),
    SELITH(RPG.GAME_WORLD, -776.5, 43.5, -1476, 0, 0),
    LEMIA(RPG.GAME_WORLD, -194.5, 68.5, -346.5, -180, 0),
    WORLDBOSS(RPG.BOSS_WORLD, -249.5, 112.5, -945.5, -180, 0),

    OLD_LEMIA(RPG.GAME_WORLD, -231.5, 36.5, -1240.5, 0, 0),
    OLD_MARU_ISLAND(RPG.GAME_WORLD, -231.5, 36.5, -1240.5, 0, 0),
    OLD_ERLEN(RPG.GAME_WORLD, -231.5, 36.5, -1240.5, 0, 0),
    OLD_LIPTUS(RPG.GAME_WORLD, -231.5, 36.5, -1240.5, 0, 0),
    OLD_KOBAZA(RPG.GAME_WORLD, -231.5, 36.5, -1240.5, 0, 0),
    OLD_PERION(RPG.GAME_WORLD, -231.5, 36.5, -1240.5, 0, 0),
    OLD_ELLINIA(RPG.GAME_WORLD, -231.5, 36.5, -1240.5, 0, 0);

    private Location loc = null;

    private String world;
    private double x, y, z;
    private float yaw, pitch;

    WarpLocation(String world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location getLocation() {
        if (loc != null)
            return loc;
        World w = RPG.plugin.getServer().getWorld(world);
        if (w == null) {
            try {
                throw new Exception("Invalid WarpLocation world: " + world);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return getMainWorld().getSpawnLocation();
        }
        loc = new Location(w, x, y, z, yaw, pitch);
        return loc;
    }

    public Location getMutableLocation() {
        return getLocation().clone();
    }

    public static World getMainWorld() {
        return RPG.plugin.getServer().getWorld(RPG.GAME_WORLD);
    }

}
