package me.rezscipts.rpg.votes;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import me.rezscipts.rpg.AbstractManagerRPG;
import me.rezscipts.rpg.RPG;
import me.rezscipts.rpg.rewards.RewardsManager;

public class VoteManager extends AbstractManagerRPG {

    private static final String VOTE_MESSAGE = ChatColor.GRAY + "> " + ChatColor.GREEN + "Thanks for voting! Use your Reward Points with " + ChatColor.YELLOW + "/rewards" + ChatColor.GREEN + ".";

    public VoteManager(RPG plugin) {
        super(plugin);
    }

    @Override
    public void initialize() {
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVotifierEvent(VotifierEvent event) {
        Vote vote = event.getVote();
        System.out.println("Received vote: " + vote);
        String name = vote.getUsername();
        Runnable callback = () -> {
            System.out.println("Processed vote for " + name + " from " + vote.getServiceName() + ".");
        };
        RewardsManager.givePoints(name, 1, VOTE_MESSAGE, callback);
    }

}