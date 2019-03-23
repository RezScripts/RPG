  package me.rezscripts.rpg;

import java.io.File;

import me.rezscripts.rpgexperience.quests.QuestManager;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;

import me.rezscripts.rpg.buffs.BuffManager;
import me.rezscripts.rpg.buycraft.BuycraftManager;
import me.rezscripts.rpg.chat.ChatManager;
import me.rezscripts.rpg.combat.CombatManager;
import me.rezscripts.rpg.commands.CommandManager;
import me.rezscripts.rpg.commands.RPGAbstractCommand;
import me.rezscripts.rpg.drops.DropManager;
import me.rezscripts.rpg.dungeons.DungeonManager;
import me.rezscripts.rpg.economy.EconomyManager;
import me.rezscripts.rpg.economy.ShardManager;
import me.rezscripts.rpg.fakes.FakeManager;
import me.rezscripts.rpg.fun.GrappleManager;
import me.rezscripts.rpg.general.EnvironmentManager;
import me.rezscripts.rpg.general.SchematicManager;
import me.rezscripts.rpg.general.StealthManager;
import me.rezscripts.rpg.guilds.GuildManager;
import me.rezscripts.rpg.haybales.HaybaleManager;
import me.rezscripts.rpg.help.HelpManager;
import me.rezscripts.rpg.holograms.HologramManager;
import me.rezscripts.rpg.horses.HorseManager;
import me.rezscripts.rpg.items.ItemManager;
import me.rezscripts.rpg.mobs.EntityRegister;
import me.rezscripts.rpg.mobs.MobData;
import me.rezscripts.rpg.mobs.MobManager;
import me.rezscripts.rpg.music.MusicManager;
import me.rezscripts.rpg.npcs.NPCManager;
import me.rezscripts.rpg.npcs.generics.GenericNPCManager;
import me.rezscripts.rpg.particles.ParticleManager;
import me.rezscripts.rpg.parties.PartyManager;
import me.rezscripts.rpg.rebirths.RebirthManager;
import me.rezscripts.rpg.rebirths.RebirthManager;
import me.rezscripts.rpg.regions.RegionManager;
import me.rezscripts.rpg.rewards.RewardsManager;
import me.rezscripts.rpg.shops.ShopManager;
import me.rezscripts.rpg.skills.SkillManager;
import me.rezscripts.rpg.soaring.SoaringManager;
import me.rezscripts.rpg.spells.SpellManager;
import me.rezscripts.rpg.tips.TipManager;
import me.rezscripts.rpg.top.TopManager;
import me.rezscripts.rpg.trades.TradeManager;
import me.rezscripts.rpg.trinkets.TrinketManager;
import me.rezscripts.rpg.utils.gson.RPGItemAdapter;
import me.rezscripts.rpg.vip.BoostManager;
import me.rezscripts.rpg.votes.VoteManager;
import me.rezscripts.rpg.warps.WarpManager;
import me.rezscripts.rpg.worldboss.WorldBossManager;
import me.rezscripts.rpgexperience.PlayerData;
import me.rezscripts.rpgexperience.RPGCore;
import me.rezscripts.rpgexperience.options.RPGOption;
import me.rezscripts.rpgexperience.pets.PetManager;
import me.rezscripts.rpgexperience.utils.RScheduler;
import me.rezscripts.rpgexperience.utils.RTicks;

public class RPG extends RPGCore {

    public static final String GAME_WORLD = "main";
    public static final String LOBBY_WORLD = "lobby";
    public static final String TUTORIAL_WORLD = "tutorial";
    public static final String BOSS_WORLD = "worldboss";

    public static RPG plugin;

    private static Location tutorialSpawnLoc = null;

    public static Location getTutorialSpawn() {
        if (tutorialSpawnLoc != null)
            return tutorialSpawnLoc;
        World w = plugin.getServer().getWorld(RPG.TUTORIAL_WORLD); 
        return tutorialSpawnLoc = new Location(w, -270.5, 63.0, -372.5, 0f, 0f);
    }

    @Override
    public void onEnable() {
        plugin = this;
        AbstractManagerRPG.plugin = this;
        RPGAbstractCommand.plugin = this;
        setPlayerdataClass(PlayerDataRPG.class);
        RPGItemAdapter.register();

        new DropManager(this); // before loadWorlds
        loadWorlds();
        MobData.plugin = this;
        EntityRegister.registerEntities();

        File f = getDataFolder();
        if (!f.exists())
            f.mkdirs();

        getServer().createWorld(new WorldCreator(LOBBY_WORLD));
        getServer().createWorld(new WorldCreator(GAME_WORLD));
        getServer().createWorld(new WorldCreator(TUTORIAL_WORLD));
        getServer().createWorld(new WorldCreator("dungeon"));

        new EnvironmentManager(this);
        new CommandManager(this);

        this.unloadManager(me.rezscripts.rpgexperience.chat.ChatManager.class); // use custom chat manager for RPG
        new ChatManager(this);
        new CombatManager(this);
        new ItemManager(this); //must be before mobmanager
        new MobManager(this);
        new GrappleManager(this);
        new SpellManager(this);
        new WarpManager(this);
        new StealthManager(this);
        new EconomyManager(this);
        new ShardManager(this);
        new SchematicManager(this);
        //        new MobBossManager(this);
        new RegionManager(this);
        new NPCManager(this);
        //        new PacketManager(this);
        new BuffManager(this);
        new GuildManager(this);
        new TradeManager(this);
        new PartyManager(this);
        new TipManager(this);
        new ShopManager(this);
        new TrinketManager(this);
        new ParticleManager(this);
        new HologramManager(this);
        if (!RPG.TEST_REALM)
            new VoteManager(this);
        new RewardsManager(this);
        new BuycraftManager(this);
        new HelpManager(this);
        new DungeonManager(this);
        new HaybaleManager(this);
        new HorseManager(this);
        new BoostManager(this);
        new RebirthManager(this);
        new MusicManager(this);
        new GenericNPCManager(this);
        new SkillManager(this);
        new PetManager(this);
        new FakeManager(this);
        new SoaringManager(this);
        new WorldBossManager(this);
        new TopManager(this);

        System.out.println("Successfully loaded RPG.");

        postLoad();
    }

    public void postLoad() {
        RScheduler.schedule(plugin, new Runnable() {
            public void run() {
                for (World w : getServer().getWorlds()) {
                    for (Chunk chunk : w.getLoadedChunks()) {
                        NPCManager.handleChunk(chunk);
                        DungeonManager.handleChunk(chunk);
                        HologramManager.handleChunk(chunk);
                        EnvironmentManager.handleChunk(chunk);
                    }
                }
            }
        }, RTicks.seconds(1));
        RScheduler.schedule(plugin, new Runnable() {
            public void run() {
                for (PlayerData pd : getAllPlayerData()) {
                    if (!(pd instanceof PlayerDataRPG))
                        continue;
                    PlayerDataRPG pdr = (PlayerDataRPG) pd;
                    if (pdr.getOption(RPGOption.SOFT_LAUNCH)) {
                        pd.sendMessage(ChatColor.GRAY + "> The world is incomplete and we are still building stuff!");
                        pd.sendMessage(ChatColor.GRAY + "> We are working hard to get more things out soon!");
                        pd.sendMessage(ChatColor.GRAY + "> Keep an eye on the forums at rpgexperience.net for updates.");
                        pd.sendMessage(ChatColor.GRAY + "> Hide this message in /options (end of the second row).");
                    }
                }
                RScheduler.schedule(plugin, this, RTicks.seconds(300));
            }
        }, RTicks.seconds(5));
    }

    @Override
    public void onDisable() {
        try {
            WarpManager.saveWarps();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            EntityRegister.unregisterEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ParticleManager.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadWorlds() {
        for (World w : getServer().getWorlds()) {
            EnvironmentManager.despawnEntities(w.getEntities().toArray(new Entity[w.getEntities().size()]));
            w.setThunderDuration(0);
            w.setWeatherDuration(0);
            w.setStorm(false);
            w.setThundering(false);
            if (!w.getName().equals(BOSS_WORLD))
                w.setTime(0);
        }
    }

    @Override
    public PlayerDataRPG getPD(Object o) {
        return (PlayerDataRPG) super.getPD(o);
    }
}
