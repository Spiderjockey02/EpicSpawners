package com.songoda.epicspawners.Utils;

import com.songoda.arconix.Arconix;
import com.songoda.epicspawners.EpicSpawners;
import com.songoda.epicspawners.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by songo on 6/4/2017.
 */
public class SettingsManager implements Listener {

    EpicSpawners plugin = EpicSpawners.pl();

    public Map<Player, Integer> page = new HashMap<>();

    private static ConfigWrapper defs;

    public SettingsManager() {
        plugin.saveResource("SettingDefinitions.yml", true);
        defs = new ConfigWrapper(plugin, "", "SettingDefinitions.yml");
        defs.createNewFile("Loading data file", "EpicSpawners SettingDefinitions file");
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Map<Player, String> current = new HashMap<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() != null) {
            if (e.getInventory().getTitle().equals("EpicSpawners Settings Editor")) {
                Player p = (Player) e.getWhoClicked();
                if (e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) {
                    e.setCancelled(true);
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Lang.NEXT.getConfigValue())) {
                    page.put(p, 2);
                    openEditor(p);
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(Lang.BACK.getConfigValue())) {
                    page.put(p, 1);
                    openEditor(p);
                } else if (e.getCurrentItem() != null) {
                    e.setCancelled(true);

                    String key = e.getCurrentItem().getItemMeta().getDisplayName().substring(2);

                    if (plugin.getConfig().get("settings." + key).getClass().getName().equals("java.lang.Boolean")) {
                        boolean bool = (Boolean) plugin.getConfig().get("settings." + key);
                        if (!bool)
                            plugin.getConfig().set("settings." + key, true);
                        else
                            plugin.getConfig().set("settings." + key, false);
                        finishEditing(p);
                    } else {
                        editObject(p, key);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        final Player p = e.getPlayer();
        if (current.containsKey(p)) {
            if (plugin.getConfig().get("settings." + current.get(p)).getClass().getName().equals("java.lang.Integer")) {
                plugin.getConfig().set("settings." + current.get(p), Integer.parseInt(e.getMessage()));
            } else if (plugin.getConfig().get("settings." + current.get(p)).getClass().getName().equals("java.lang.Double")) {
                plugin.getConfig().set("settings." + current.get(p), Double.parseDouble(e.getMessage()));
            } else if (plugin.getConfig().get("settings." + current.get(p)).getClass().getName().equals("java.lang.String")) {
                plugin.getConfig().set("settings." + current.get(p), e.getMessage());
            }
            finishEditing(p);
            e.setCancelled(true);
        }
    }

    public void finishEditing(Player p) {
        current.remove(p);
        plugin.saveConfig();
        openEditor(p);
    }


    public void editObject(Player p, String current) {
        this.current.put(p, current);
        p.closeInventory();
        p.sendMessage("");
        p.sendMessage(Arconix.pl().format().formatText("&7Please enter a value for &6" + current + "&7."));
        if (plugin.getConfig().get("settings." + current).getClass().getName().equals("java.lang.Integer")) {
            p.sendMessage(Arconix.pl().format().formatText("&cUse only numbers."));
        }
        p.sendMessage("");
    }

    public void openEditor(Player p) {
        int pmin = 1;

        if (page.containsKey(p))
            pmin = page.get(p);

        if (pmin != 1)
            pmin = 45;

        int pmax = pmin * 44;

        Inventory i = Bukkit.createInventory(null, 54, "EpicSpawners Settings Editor");

        int num = 0;
        int total = 0;
        ConfigurationSection cs = plugin.getConfig().getConfigurationSection("settings");
        for (String key : cs.getKeys(true)) {
            if (!key.contains("Entities")) {
                if (total >= pmin - 1 && total <= pmax) {

                    ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(Arconix.pl().format().formatText("&6" + key));
                    ArrayList<String> lore = new ArrayList<>();
                    if (plugin.getConfig().get("settings." + key).getClass().getName().equals("java.lang.Boolean")) {

                        item.setType(Material.LEVER);
                        boolean bool = (Boolean) plugin.getConfig().get("settings." + key);

                        if (!bool)
                            lore.add(Arconix.pl().format().formatText("&c" + bool));
                        else
                            lore.add(Arconix.pl().format().formatText("&a" + bool));

                    } else if (plugin.getConfig().get("settings." + key).getClass().getName().equals("java.lang.String")) {
                        item.setType(Material.PAPER);
                        String str = (String) plugin.getConfig().get("settings." + key);
                        lore.add(Arconix.pl().format().formatText("&9" + str));
                    } else if (plugin.getConfig().get("settings." + key).getClass().getName().equals("java.lang.Integer")) {
                        item.setType(Material.WATCH);

                        int in = (Integer) plugin.getConfig().get("settings." + key);
                        lore.add(Arconix.pl().format().formatText("&5" + in));
                    }
                    if (defs.getConfig().contains(key)) {
                        String text = defs.getConfig().getString(key);

                        Pattern regex = Pattern.compile("(.{1,28}(?:\\s|$))|(.{0,28})", Pattern.DOTALL);
                        Matcher m = regex.matcher(text);
                        while (m.find()) {
                            if (m.end() != text.length() || m.group().length() != 0)
                                lore.add(Arconix.pl().format().formatText("&7" + m.group()));
                        }
                    }
                    meta.setLore(lore);
                    item.setItemMeta(meta);

                    i.setItem(num, item);
                    num++;
                }
                total++;
            }
        }


        int nu = 45;
        while (nu != 54) {
            i.setItem(nu, Methods.getGlass());
            nu++;
        }


        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemStack skull = head;
        if (!plugin.v1_7)
            skull = Arconix.pl().getGUI().addTexture(head, "http://textures.minecraft.net/texture/1b6f1a25b6bc199946472aedb370522584ff6f4e83221e5946bd2e41b5ca13b");
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        if (plugin.v1_7)
            skullMeta.setOwner("MHF_ArrowRight");
        skull.setDurability((short) 3);
        skullMeta.setDisplayName(Lang.NEXT.getConfigValue());
        skull.setItemMeta(skullMeta);

        ItemStack head2 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemStack skull2 = head2;
        if (!plugin.v1_7)
            skull2 = Arconix.pl().getGUI().addTexture(head2, "http://textures.minecraft.net/texture/3ebf907494a935e955bfcadab81beafb90fb9be49c7026ba97d798d5f1a23");
        SkullMeta skull2Meta = (SkullMeta) skull2.getItemMeta();
        if (plugin.v1_7)
            skull2Meta.setOwner("MHF_ArrowLeft");
        skull2.setDurability((short) 3);
        skull2Meta.setDisplayName(Lang.BACK.getConfigValue());
        skull2.setItemMeta(skull2Meta);

        if (pmin != 1) {
            i.setItem(46, skull2);
        }
        if (pmin == 1) {
            i.setItem(52, skull);
        }

        p.openInventory(i);
    }


    public void updateSettings() {
        for (settings s : settings.values()) {
            if (s.setting.equals("Upgrade-particle-type")) {
                if (plugin.v1_7 || plugin.v1_8)
                    plugin.getConfig().addDefault("settings." + s.setting, "WITCH_MAGIC");
                else
                    plugin.getConfig().addDefault("settings." + s.setting, s.option);
            } else
                plugin.getConfig().addDefault("settings." + s.setting, s.option);
        }

        ConfigurationSection cs = plugin.getConfig().getConfigurationSection("settings");
        for (String key : cs.getKeys(true)) {
            if (!key.contains("Entities")) {
                if (!contains(key)) {
                    plugin.getConfig().set("settings." + key, null);
                }
            }
        }
    }

    public static boolean contains(String test) {
        for (settings c : settings.values()) {
            if (c.setting.equals(test)) {
                return true;
            }
        }
        return false;
    }

    public enum settings {

        o1("Force-Combine-Radius", 0),

        o2("Spawner-max", 5),

        o3("Goal", 100),

        o4("Search-Radius", "8x4x8"),

        o5("Spawner-Spawn-Equation", "{MULTI} + {RAND}"),

        o6("Spawner-Rate-Equation", "{DEFAULT} / {MULTI}"),

        o7("Alert-every", 10),

        o8("Exit-Icon", "WOOD_DOOR"),

        o9("Buy-Icon", "EMERALD"),

        o10("Alter-Delay", true),

        o11("Glass-Type-1", 7),

        o112("Glass-Type-2", 11),

        o113("Glass-Type-3", 3),

        o12("Rainbow-Glass", false),

        o13("Silktouch-spawners", true),

        o14("Silktouch-natural-drop-chance", "100%"),

        o15("Silktouch-placed-drop-chance", "100%"),

        o16("Eggs-convert-spawners", true),

        o17("Alert-place-break", true),

        o18("Sneak-for-stack", true),

        o19("OmniSpawners", true),

        o21("Omni-Limit", 3),

        o81("Boost-Multiplier", "0.5"),

        o82("Max-Player-Boost", 5),

        o83("Boost-cost", "DIAMOND:2"),

        o23("Display-Level-One", false),

        o24("Inventory-Stacking", true),

        o25("Drop-on-creeper-explosion", true),

        o26("Drop-on-tnt-explosion", true),

        o27("Spawners-dont-explode", false),

        o28("Tnt-explosion-drop-chance", "100%"),

        o29("Creeper-explosion-drop-chance", "100%"),

        o30("Hostile-mobs-attack-second", false),

        o31("Mob-kill-counting", true),

        o32("Spawner-holograms", true),

        o33("Upgrade-with-eco", true),

        o34("Upgrade-with-xp", true),

        o35("Upgrade-xp-cost", 50),

        o36("Upgrade-eco-cost", 10000),

        o37("ECO-Icon", "DOUBLE_PLANT"),

        o39("XP-Icon", "EXP_BOTTLE"),

        o40("Count-unnatural-kills", false),

        o41("Only-drop-stacked", false),

        o42("Helpful-Tips", true),

        o43("Random-Low", 1),

        o44("Random-High", 3),

        o45("Thin-Entity-data", true),

        o46("Only-drop-placed", false),

        o47("Only-charge-natural", false),

        o48("Debug-Mode", false),

        o49("Force-Combine-Deny", false),

        o50("SpawnEffect", "EXPLOSION_NORMAL"),

        o51("Max-Entities-Around-Single-Spawner", 6),

        o52("Large-Entity-Safe-Spawning", true),

        o53("Add-Spawner-To-Inventory-On-Drop", false),

        o54("Upgrade-particle-type", "SPELL_WITCH"),

        o55("Use-equations", false),

        o523("How-to", true),

        o56("XP-cost-equation", "{XPCost} * {Level}"),

        o57("ECO-cost-equation", "{ECOCost} * {Level}"),

        o58("Name-format", "&e{TYPE} &fSpawner [&c{AMT}x]"),

        o59("beta-features", false),

        o60("redstone-activate", true),

        o61("spawners-repel-liquid", true),

        o63("Sounds", true),

        o62("spawners-repel-radius", 1);

        private String setting;
        private Object option;

        private settings(String setting, Object option) {
            this.setting = setting;
            this.option = option;
        }

    }
}
