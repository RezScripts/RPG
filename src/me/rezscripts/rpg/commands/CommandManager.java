package me.rezscripts.rpg.commands;

import me.rezscripts.rpg.RPG;
import me.rezscripts.rpg.commands.admin.SwapCommand;
import me.rezscripts.rpg.commands.beta.BaseMobCommand;
import me.rezscripts.rpg.commands.beta.ClearNearbyCommand;
import me.rezscripts.rpg.commands.beta.EffectsCommand;
import me.rezscripts.rpg.commands.beta.GamemodeCommand;
import me.rezscripts.rpg.commands.beta.MobsCommand;
import me.rezscripts.rpg.commands.beta.NightVisionCommand;
import me.rezscripts.rpg.commands.beta.TestEquipsCommand;
import me.rezscripts.rpg.commands.beta.TestPotionsCommand;
import me.rezscripts.rpg.commands.builder.BlockLocCommand;
import me.rezscripts.rpg.commands.builder.BuilderCommand;
import me.rezscripts.rpg.commands.builder.RPGReplaceCommand;
import me.rezscripts.rpg.commands.builder.TerraformCommand;
import me.rezscripts.rpg.commands.builder.VoxelSniperCommand;
import me.rezscripts.rpg.commands.builder.WorldEditCommand;
import me.rezscripts.rpg.commands.console.AdRewardCommand;
import me.rezscripts.rpg.commands.console.BuycraftRewardCommand;
import me.rezscripts.rpg.commands.gm.GMCommand;
import me.rezscripts.rpg.commands.gm.NPCCommand;
import me.rezscripts.rpg.commands.member.BankCommand;
import me.rezscripts.rpg.commands.member.ClassCommand;
import me.rezscripts.rpg.commands.member.ClearCommand;
import me.rezscripts.rpg.commands.member.FlightCommand;
import me.rezscripts.rpg.commands.member.GrappleCommand;
import me.rezscripts.rpg.commands.member.GuildCommand;
import me.rezscripts.rpg.commands.member.HelpCommand;
import me.rezscripts.rpg.commands.member.HorseCommand;
import me.rezscripts.rpg.commands.member.IgnoreCommand;
import me.rezscripts.rpg.commands.member.InfoCommand;
import me.rezscripts.rpg.commands.member.LocationCommand;
import me.rezscripts.rpg.commands.member.LookupCommand;
import me.rezscripts.rpg.commands.member.OnlineCommand;
import me.rezscripts.rpg.commands.member.OptionCommand;
import me.rezscripts.rpg.commands.member.PartyCommand;
import me.rezscripts.rpg.commands.member.PingCommand;
import me.rezscripts.rpg.commands.member.PlayTimeCommand;
import me.rezscripts.rpg.commands.member.RegionCommand;
import me.rezscripts.rpg.commands.member.RenameCommand;
import me.rezscripts.rpg.commands.member.ReplyCommand;
import me.rezscripts.rpg.commands.member.ResetSPCommand;
import me.rezscripts.rpg.commands.member.RewardsCommand;
import me.rezscripts.rpg.commands.member.RollCommand;
import me.rezscripts.rpg.commands.member.SalvageCommand;
import me.rezscripts.rpg.commands.member.ShardCommand;
import me.rezscripts.rpg.commands.member.SpawnCommand;
import me.rezscripts.rpg.commands.member.Spell1Command;
import me.rezscripts.rpg.commands.member.Spell2Command;
import me.rezscripts.rpg.commands.member.Spell3Command;
import me.rezscripts.rpg.commands.member.Spell4Command;
import me.rezscripts.rpg.commands.member.SpellCommand;
import me.rezscripts.rpg.commands.member.TopCommand;
import me.rezscripts.rpg.commands.member.TradeCommand;
import me.rezscripts.rpg.commands.member.TrinketCommand;
import me.rezscripts.rpg.commands.member.VoteCommand;
import me.rezscripts.rpg.commands.member.WhisperCommand;
import me.rezscripts.rpg.commands.member.WorldBossArenaCommand;
import me.rezscripts.rpg.commands.mod.SetWarpCommand;
import me.rezscripts.rpg.commands.mod.WarpCommand;
import me.rezscripts.rpg.commands.owner.*;
import me.rezscripts.rpgexperience.RPGCore;
import me.rezscripts.rpgexperience.commands.general.QuestCommand;
import me.rezscripts.rpgexperience.commands.mod.WalkSpeedCommand;
import me.rezscripts.rpgexperience.players.Rank;

public class CommandManager extends me.rezscripts.rpgexperience.commands.CommandManager {

    public CommandManager(RPGCore plugin) {
        super(plugin);
    }

    @Override
    public void initialize() {
        super.initialize();
        // Member
        register(Rank.MEMBER, new OnlineCommand("online", "players"));
        register(Rank.MEMBER, new WhisperCommand("w", "whisper", "tell", "pm", "message", "msg"));
        register(Rank.MEMBER, new ReplyCommand("r", "reply"));
        register(Rank.MEMBER, new ClassCommand("class", "classes"));
        register(Rank.MEMBER, new SpellCommand("spell", "spells", "magic", "sp", "spl", "spls"));
        register(Rank.MEMBER, new InfoCommand("info", "information", "details", "detail", "spy"));
        register(Rank.MEMBER, new ClearCommand("clear", "clearinventory"));
        register(Rank.MEMBER, new ResetSPCommand("resetsp"));
        register(Rank.MEMBER, new OptionCommand("option", "options", "opt", "o"));
        register(Rank.MEMBER, new HelpCommand("help", "commands", "command", "?"));
        register(Rank.MEMBER, new Spell1Command("spell1", "1", "s1"));
        register(Rank.MEMBER, new Spell2Command("spell2", "2", "s2"));
        register(Rank.MEMBER, new Spell3Command("spell3", "3", "s3"));
        register(Rank.MEMBER, new Spell4Command("spell4", "4", "s4"));
        register(Rank.MEMBER, new ShardCommand("shard", "shards", "eco", "econ", "economy", "bal", "balance", "gold", "money"));
        register(Rank.MEMBER, new LocationCommand("loc", "location"));
        register(Rank.MEMBER, new RegionCommand("region", "reg"));
        register(Rank.MEMBER, new QuestCommand("quest", "q", "quests"));
        //        register(Rank.MEMBER, new ResetQuestsCommand("resetquests", "resetquest"));
        register(Rank.MEMBER, new GuildCommand("guild", "g", "guilds"));
        register(Rank.MEMBER, new TradeCommand("trade"));
        register(Rank.MEMBER, new PartyCommand("party", "p"));
        register(Rank.MEMBER, new TrinketCommand("trinket", "t"));
        register(Rank.MEMBER, new LookupCommand("lookup"));
        register(Rank.MEMBER, new PlayTimeCommand("playtime", "timeplayed", "playedtime", "played"));
        register(Rank.MEMBER, new RewardsCommand("rewards", "reward", "votepoints", "votepoint", "rewardshop"));
        //        register(Rank.MEMBER, new TeleportAcceptCommand("tpa")); // removed 2.0.1
        register(Rank.MEMBER, new EffectsCommand("e", "effect", "effects", "particle", "particles"));
        register(Rank.MEMBER, new VoteCommand("vote", "votes", "voting"));
        register(Rank.MEMBER, new SpawnCommand("spawn"));
        register(Rank.MEMBER, new SalvageCommand("salvage", "sell"));
        register(Rank.MEMBER, new BankCommand("bank"));
        register(Rank.MEMBER, new RollCommand("roll", "rtd"));
        register(Rank.MEMBER, new HorseCommand("horse", "h"));
        register(Rank.MEMBER, new RenameCommand("rename"));
        register(Rank.MEMBER, new IgnoreCommand("ignore"));
        register(Rank.MEMBER, new PingCommand("ping"));
        register(Rank.MEMBER, new FlightCommand("soaring", "flight", "soar"));
        register(Rank.MEMBER, new TopCommand("top", "leader", "tops", "leaders", "leaderboard", "toplist"));

        register(Rank.MEMBER, new WorldBossArenaCommand("worldboss", "worldbossarena"));

        // Beta

        // Helper

        // Gamemaster
        register(Rank.GAMEMASTER, new NPCCommand("npc"));
        register(Rank.GAMEMASTER, new GMCommand("makegm"));

        // Builder
        register(Rank.BUILDER, new BuilderCommand("builder"));
        register(Rank.BUILDER, new VoxelSniperCommand("vxme"));
        register(Rank.BUILDER, new TerraformCommand("terraform", "tf"));
        register(Rank.BUILDER, new RPGReplaceCommand("rpgreplace"));
        register(Rank.BUILDER, new NightVisionCommand("nightvision"));
        register(RPG.TEST_REALM ? Rank.MEMBER : Rank.BUILDER, new GamemodeCommand("gamemode", "gm"));
        register(Rank.BUILDER, new WorldEditCommand("weme"));

        // Mod
        register(RPGCore.TEST_REALM ? Rank.MEMBER : Rank.MOD, new SetWarpCommand("setwarp"));
        register(RPGCore.TEST_REALM ? Rank.MEMBER : Rank.MOD, new WarpCommand("warp"));

        // Admin
        register(Rank.ADMIN, new ClearNearbyCommand("clearnearby"));
        register(Rank.ADMIN, new SwapCommand("swap"));

        // Owner
        register(Rank.OWNER, new GrappleCommand("grapple"));
        register( Rank.OWNER, new ResetQuestCommand("resetquest","resetq") );
        register(Rank.OWNER, new DBCommand("db"));
        register(Rank.OWNER, new LoadWorldCommand("loadworld"));
        register(Rank.OWNER, new SetSpawnCommand("setspawn"));
        register(Rank.OWNER, new ReflectCommand("reflect"));
        register(Rank.OWNER, new MotdCommand("motd"));
        register(Rank.OWNER, new ReflectGetCommand("reflectget"));
        register(Rank.OWNER, new XertyCommand("xerty"));
        register(Rank.OWNER, new ReloadMobsCommand("reloadmobs"));
        register(Rank.OWNER, new AnnounceCommand("announce"));
        register(RPG.TEST_REALM ? Rank.MEMBER : Rank.OWNER, new GiveItemCommand("giveitem", "item", "items"));
        register(Rank.OWNER, new ReloadItemsCommand("reloaditems"));
        register(Rank.OWNER, new OpCommand("op"));
        register(Rank.OWNER, new DeopCommand("deop"));
        register(Rank.OWNER, new ReloadCommand("reload", "rl"));
        register(Rank.OWNER, new KillCommand("kill"));
        register(Rank.OWNER, new MonitorCommand("monitor"));
        register(Rank.OWNER, new ReloadWarpsCommand("reloadwarps"));
        register(Rank.OWNER, new TPHideCommand("tphide"));
        register(Rank.OWNER, new ManaCommand("mana"));
        register(Rank.OWNER, new CrashCommand("crash"));
        register(Rank.OWNER, new BurnCommand("burn"));
        register(Rank.OWNER, new PoisonCommand("poison"));
        register(Rank.OWNER, new ReloadRegionsCommand("reloadregions"));
        register(Rank.OWNER, new ReloadQuestsCommand("reloadquests"));
        register(Rank.OWNER, new ReloadGenericsCommand("reloadgenerics"));
        register(Rank.OWNER, new MobSpawnCommand("mobspawn", "ms"));
        register(Rank.OWNER, new ShadowMuteCommand("shadowmute"));
        register(Rank.OWNER, new RPGCommand("ceciliabot", "cecilia"));
        register(Rank.OWNER, new ReloadHologramsCommand("reloadholograms", "reloadholos"));
        register(Rank.OWNER, new ReloadDungeonsCommand("reloaddungeons"));
        register(Rank.OWNER, new ReloadShopsCommand("reloadshops"));
        register(Rank.OWNER, new WalkSpeedCommand("walkspeed"));
        register(Rank.OWNER, new TestPotionsCommand("testpotions", "testpots", "testpotion"));
        register(Rank.OWNER, new TestEquipsCommand("testequips"));
        register(Rank.OWNER, new MobsCommand("mobs"));
        register(Rank.OWNER, new TPWorldCommand("tpworld"));
        register(RPG.TEST_REALM ? Rank.MEMBER : Rank.OWNER, new GenerateItemsCommand("generateitems", "generate", "generateitem"));
        register(RPG.TEST_REALM ? Rank.MEMBER : Rank.OWNER, new SeekItemCommand("seekitems", "seekitem", "seek"));
        register(RPG.TEST_REALM ? Rank.MEMBER : Rank.OWNER, new SpawnMobCommand("spawnmob"));
        register(Rank.OWNER, new ViewBankCommand("viewbank", "checkbank", "seebank"));
        register(Rank.OWNER, new BlockLocCommand("blockloc"));
        //        register(Rank.OWNER, new MaintenanceCommand("maintenance"));
//        register(RPG.TEST_REALM ? Rank.MEMBER : Rank.OWNER, new ResetQuestsCommand("resetquests", "resetquest"));
        register(Rank.OWNER, new ReloadHayCommand("reloadhay", "reloadhaybales"));
        register(Rank.OWNER, new ReloadHorsesCommand("reloadstables", "reloadhorses"));
        register(Rank.OWNER, new ShutdownCommand("shutdown"));
        register(Rank.OWNER, new FindDupesCommand("finddupes"));
        register(Rank.OWNER, new FindItemCommand("finditem"));
        register(Rank.OWNER, new SetBankCommand("setbank"));
        register(Rank.OWNER, new EditLoreCommand("editlore"));
        register(RPG.TEST_REALM ? Rank.MEMBER : Rank.OWNER, new BaseMobCommand("basemob"));
        register(RPG.TEST_REALM ? Rank.MEMBER : Rank.OWNER, new LevelCommand("level"));
        register(RPG.TEST_REALM ? Rank.MEMBER : Rank.OWNER, new HealCommand("heal"));
        register(Rank.OWNER, new RerollCommand("reroll"));
        register(Rank.OWNER, new EditNameCommand("editname"));
        register(RPG.TEST_REALM ? Rank.MEMBER : Rank.OWNER, new CompleteQuestCommand("completequest"));

        // Console - these commands are meant to be run by console, but can be used (carefully) by owner rank
        register(Rank.OWNER, new AdRewardCommand("adreward"));
        register(Rank.OWNER, new BuycraftRewardCommand("buycraftreward"));
    }
}
