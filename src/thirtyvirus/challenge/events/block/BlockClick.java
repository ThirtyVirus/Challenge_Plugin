package thirtyvirus.challenge.events.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import thirtyvirus.challenge.ChallengePlugin;
import thirtyvirus.challenge.helpers.ActionSound;
import thirtyvirus.challenge.helpers.Utilities;

public class BlockClick implements Listener {

    private ChallengePlugin main = null;
    public BlockClick(ChallengePlugin main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockClick(PlayerInteractEvent event) {
        Utilities.playSound(ActionSound.ERROR, event.getPlayer());

    }

}