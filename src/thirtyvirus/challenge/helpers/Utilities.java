package thirtyvirus.challenge.helpers;

import com.google.common.io.ByteStreams;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import thirtyvirus.challenge.ChallengePlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public final class Utilities {

    private static Map<Player, Long> mostRecentSelect = new HashMap<>();

    // load file from JAR with comments
    public static File loadResource(Plugin plugin, String resource) {
        File folder = plugin.getDataFolder();
        if (!folder.exists())
            folder.mkdir();
        File resourceFile = new File(folder, resource);
        try {
            if (!resourceFile.exists()) {
                resourceFile.createNewFile();
                try (InputStream in = plugin.getResource(resource);
                     OutputStream out = new FileOutputStream(resourceFile)) {
                    ByteStreams.copy(in, out);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resourceFile;
    }

    // convert a location to formatted string (world,x,y,z)
    public static String toLocString(Location location) {
        if (location.equals(null)) return "";
        return location.getWorld().getName() + "," + (int)location.getX() + "," + (int)location.getY() + "," + (int)location.getZ();
    }

    // renames item
    public static ItemStack nameItem(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    // creates item that is renamed given material and name
    public static ItemStack nameItem(Material item, String name) {
        return nameItem(new ItemStack(item), name);
    }

    // set the lore of an item
    public static ItemStack loreItem(ItemStack item, List<String> lore) {
        ItemMeta meta = item.getItemMeta();

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    // makes visible string invisible to player
    public static String convertToInvisibleString(String s) {
        String hidden = "";
        for (char c : s.toCharArray()) hidden += ChatColor.COLOR_CHAR + "" + c;
        return hidden;
    }

    // make invisible string visible to player
    public static String convertToVisibleString(String s) {
        String c = "";
        c = c + ChatColor.COLOR_CHAR;
        return s.replaceAll(c, "");
    }

    // send player a collection of error messages and play error noise
    public static void warnPlayer(CommandSender sender, List<String> messages) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            playSound(ActionSound.ERROR, player);
        }

        for (String message : messages) {
            sender.sendMessage("" + ChatColor.RESET + ChatColor.RED + message);
        }
    }

    // send player a collection of messages
    public static void informPlayer(CommandSender player, List<String> messages) {
        for (String message : messages) {
            player.sendMessage(ChallengePlugin.prefix + ChatColor.RESET + ChatColor.GRAY + message);
        }
    }

    // play sound at player (version independent)
    public static void playSound(ActionSound sound, Player player) {

        switch (sound) {
            case OPEN:
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN,1,1);
                break;
            case MODIFY:
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_HIT,1,1);
                break;
            case SELECT:
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1,1);
                break;
            case CLICK:
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK,1,1);
                break;
            case POP:
                player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP,1,1);
                break;
            case BREAK:
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK,1,1);
                break;
            case ERROR:
                player.playSound(player.getLocation(), Sound.ENTITY_BAT_DEATH,1,1);
                break;
        }

    }

}
