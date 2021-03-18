package thirtyvirus.challenge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import thirtyvirus.challenge.commands.MainPluginCommand;
import thirtyvirus.challenge.events.block.BlockClick;
import thirtyvirus.challenge.events.chat.TabComplete;
import thirtyvirus.challenge.events.inventory.InventoryClick;
import thirtyvirus.challenge.helpers.Utilities;

import java.io.File;
import java.util.*;

public class ChallengePlugin extends JavaPlugin {

    // console and IO
    private File langFile;
    private FileConfiguration langFileConfig;

    // chat messages
    private Map<String, String> phrases = new HashMap<String, String>();

    // core settings
    public static String prefix = "&c&l[&5&lChallengePlugin&c&l] &8&l"; // generally unchanged unless otherwise stated in config
    public static String consolePrefix = "[ChallengePlugin] ";

    // customizable settings
    public static boolean customSetting = false;
    public static boolean autoPurge = true;

    public void onEnable(){
        // load config.yml (generate one if not there)
        loadConfiguration();

        // load language.yml (generate one if not there)
        loadLangFile();

        // register commands and events
        registerCommands();
        registerEvents();

        // posts confirmation in chat
        getLogger().info(getDescription().getName() + " V: " + getDescription().getVersion() + " has been enabled");

        // example scheduled task
        if (autoPurge){
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                public void run() {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        for (Material material : InventoryClick.FORBIDDEN_MATERIALS) {
                            player.getInventory().remove(material);
                        }
                    }
                }
            }, 20, 20);
        }
    }

    public void onDisable(){
        // posts exit message in chat
        getLogger().info(getDescription().getName() + " V: " + getDescription().getVersion() + " has been disabled");
    }

    private void registerCommands() {
        getCommand("challenge").setExecutor(new MainPluginCommand(this));

        // set up tab completion
        getCommand("challenge").setTabCompleter(new TabComplete(this));
    }
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new BlockClick(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(this), this);
    }

    // load the config file and apply settings
    public void loadConfiguration() {
        // prepare config.yml (generate one if not there)
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()){
            Utilities.loadResource(this, "config.yml");
        }
        FileConfiguration config = this.getConfig();

        // general settings
        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("plugin-prefix"));

        customSetting = config.getBoolean("custom-setting");
        // put more settings here

        Bukkit.getLogger().info(consolePrefix + "Settings Reloaded from config");
    }

    // load the language file and apply settings
    public void loadLangFile() {

        // load language.yml (generate one if not there)
        langFile = new File(getDataFolder(), "language.yml");
        langFileConfig = new YamlConfiguration();
        if (!langFile.exists()){ Utilities.loadResource(this, "language.yml"); }

        try { langFileConfig.load(langFile); }
        catch (Exception e3) { e3.printStackTrace(); }

        for(String priceString : langFileConfig.getKeys(false)) {
            phrases.put(priceString, langFileConfig.getString(priceString));
        }
    }

    // getters
    public String getPhrase(String key) {
        return phrases.get(key);
    }
    public String getVersion() {
        return getDescription().getVersion();
    }

}
