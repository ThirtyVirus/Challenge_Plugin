package thirtyvirus.challenge.events.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import thirtyvirus.challenge.ChallengePlugin;
import thirtyvirus.challenge.helpers.Utilities;

import static thirtyvirus.challenge.helpers.ActionSound.CLICK;

public class InventoryClick implements Listener {

    private ChallengePlugin main = null;
    public InventoryClick(ChallengePlugin main) {
        this.main = main;
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Utilities.playSound(CLICK, (Player)event.getWhoClicked());
    }

}