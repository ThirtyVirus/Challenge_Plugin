package thirtyvirus.challenge.events.inventory;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import thirtyvirus.challenge.ChallengePlugin;
import thirtyvirus.challenge.helpers.Utilities;

import java.util.Arrays;
import java.util.List;

public class InventoryClick implements Listener {

    private ChallengePlugin main = null;
    public InventoryClick(ChallengePlugin main) {
        this.main = main;
    }

    public static List<Material> FORBIDDEN_MATERIALS = Arrays.asList(
            Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.GOLDEN_PICKAXE, Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE,
            Material.WOODEN_AXE, Material.STONE_AXE, Material.GOLDEN_AXE, Material.IRON_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE,
            Material.WOODEN_SHOVEL, Material.STONE_SHOVEL, Material.GOLDEN_SHOVEL, Material.IRON_SHOVEL, Material.DIAMOND_SHOVEL, Material.NETHERITE_SHOVEL,
            Material.WOODEN_HOE, Material.STONE_HOE, Material.GOLDEN_HOE, Material.IRON_HOE, Material.DIAMOND_HOE, Material.NETHERITE_HOE,
            Material.WOODEN_SWORD, Material.STONE_SWORD, Material.GOLDEN_SWORD, Material.IRON_SWORD, Material.DIAMOND_SWORD, Material.NETHERITE_SWORD,
            Material.FISHING_ROD, Material.FLINT_AND_STEEL, Material.COMPASS, Material.CLOCK, Material.SHEARS, Material.BOW, Material.CROSSBOW, Material.SHIELD,
            Material.TRIDENT, Material.BUCKET, Material.WATER_BUCKET, Material.LAVA_BUCKET
            );

    @EventHandler
    public void playerCraftEvent(CraftItemEvent event) {
        if (FORBIDDEN_MATERIALS.contains(event.getCurrentItem().getType())) {
            Utilities.warnPlayer(event.getWhoClicked(), Arrays.asList("You somehow can't craft this item...", "It's as if, the power of the YouTube algorithm forbids it!"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

    }

}