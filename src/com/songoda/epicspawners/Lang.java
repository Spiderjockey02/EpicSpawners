package com.songoda.epicspawners;

import com.songoda.epicspawners.Utils.Methods;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public enum Lang {

    PREFIX("prefix", "&8[&6EpicSpawners&8]"),

    ON_UPGRADE("On-upgrade", "&7You've upgraded this spawner to &6{NEW}x&7."),

    YOU_MAXED("You-Maxed", "&7You maxed out this spawner at &6{NEW}x&7."),

    ON_DOWNGRADE("On-downgrade", "&7You've downgraded this spawner to &6{NEW}x&7."),

    PLACE("Place", "&7You placed a &6{TYPE}&7."),

    BREAK("Brake", "&7You broke a &6{TYPE}&7."),

    OMNI_TAKE("Omni-take", "&7You took a &6{TYPE}&7 from the OmniSpawner."),

    ONLY_SPAWNS("Only-Spawns", "&7Only spawns on &6{SPAWNS}&7."),

    PREFERRED_SPAWNS("Preferred-Spawns", "&7Prefers to spawn on &6{SPAWNS}&7."),

    MAXED("Maxed", "&7This spawner is already maxed out!"),

    SSTATS_TITLE("SStats-title", "&5&lSpawnerStats"),

    SSTATS("SStats", "&7Your current spawner stats:"),

    CLICK_BOOST("Click-boost", "&7Right-Click to &aboost&7."),

    CLICK_CONVERT("Click-convert", "&7Left-Click to &aConvert&7."),

    TYPE_MISMATCH("Type-mismatch", "&7These spawners cannot be combined because they're different mobs."),

    ONLY_ONE("Only-One", "&cYou can only combine one spawner at a time."),

    CANNOT_MERGE_TWO_OMNI("Cannot-merge-two-omni", "&cYou can't merge two OmniSpawners."),

    OMNI_FULL("Omni", "&cThe omni spawner refuses to absorb any more spawners of that type."),

    ADD_OMNI("Add-omni", "&7Your spawner was absorbed into the OmniSpawner."),

    ALERT("Alert", "&7You need &6{GOAL} &7more kills to get a &6{TYPE} Spawner&7."),

    NO_PERMS("No-perms", "&7You do not have permission to do this."),

    ON_GOAL("On-goal", "&7Once you obtain enough &7kills, a Monster Spawner will drop."),

    SAME_TYPE("same-type", "&7This already spawns &6{TYPE}s&7."),

    NEED_MORE("Need-more", "&7You need &6{AMT} &7Monster Eggs to convert this spawner."),

    DROPPED("Dropped", "&7A &6{TYPE} Spawner &7has been dropped!"),

    NO_KILLS("No-kills", "&7You currently have no mob kills. Go kill something!"),

    BLACKLISTED("Blacklisted", "&cThat action is disabled."),

    GIVE("Give", "&7You have been given {AMT} &6{TYPE}&7."),

    BOOST_TITLE("Boost-title", "&cBoosting {SPAWNER} &cby {AMT}"),

    BOOST_APPLIED("Boost-applied", "&6Your boost has been applied."),

    BOOST_FOR("Boost-for", "&7&lBoost for &6&l{AMT} &7&lMinutes"),

    XPTITLE("Xp-upgrade-title", "&aUpgrade with XP"),
    XPLORE("Xp-upgrade-lore", "&7Cost: &a{COST} Levels"),

    ECOTITLE("Eco-upgrade-title", "&aUpgrade with ECO"),
    ECOLORE("Eco-upgrade-lore", "&7Cost: &a${COST}"),

    STATSTITLE("Stats-title", "&6Spawner Stats"),
    STATSSPAWNS("Stats-spawns", "&7Spawned &a{AMT} &e{TYPE}'s &7so far."),
    STATSBOOSTED("Stats-boosted", "&a&lCurrently boosted!|&7Adding &6{AMT} &7extra &6{TYPE}'s &7to every spawn.|&7Expires in &6{TIME}&7."),

    CANTAFFORD("Cant-afford", "&7You cannot afford this upgrade."),

    SPAWNER_SHOP("Spawner-shop", "Spawner Shop"),
    SHOP_LORE("Shop-lore", "&7Click here to purchase."),
    CONVERT_LORE("Convert-lore", "&7Click here to Convert."),
    SPAWNER_SHOW("Spawner-show", "&8Buy a {TYPE}"),
    EXIT("Exit", "&cExit"),
    NEXT("Next", "&9Next"),
    CONFIRM("Confirm", "&a&lConfirm"),
    BACK("Back", "&9Back"),
    CANNOT_AFFORD("Cannot-afford", "&cYou cannot afford to buy this."),
    PURCHASE_SUCCESS("Purchase_success", "&9Your purchase was successful."),

    BUY_PRICE("Buy-price", "&7Buy Price: &a{COST}"),

    ADD_1("Add-1", "&a&lAdd 1"),
    ADD_10("Add-10", "&a&lAdd 10"),
    SET_64("Set-64", "&a&lSet to 64"),

    REM_1("Remove-1", "&c&lRemove 1"),
    REM_10("Remove-10", "&c&lRemove 10"),
    SET_1("Set-1", "&c&lSet to 1"),

    PLACE_COST_WARN("Place-cost-warn", "&7You will be charged &6${COST} &7if you do not break this spawner within 1 minute."),
    BREAK_COST_WARN("Break-cost-warn", "&7You will be charged &6${COST} &7for breaking this spawner. Break again to confirm."),

    BREAK_COST_CANTAFFORD("Break-cost-cant-afford", "&7You cannot afford to break this spawner."),

    NOT_FACTION("Not-Faction", "&cYou must be in this faction to do that."),

    FORCE_DENY("Force-Deny", "&cThis spawner is too close to another spawner."),
    Merge_Distance("Merge-Distance", "&7This spawner has been combined with another spawner close by.."),
    SPAWNER_CONVERT("Spawner-Convert", "Spawner Convert"),
    CONVERT_SUCCESS("Convert-success", "&9Your conversion was successful."),

    SPAWNER_INFO_TITLE("Spawner-info-title", "&a&lUseful Information"),
    SPAWNER_INFO("Spawner-info", "{[LEVELUP]You can stack or \"levelup\" this spawner by: [Using another spawner on this spawner][Leveling up with XP][Leveling up with ECONOMY].}|{[DROP]A <TYPE> spawner will drop when you kill <AMT> [natural ]<TYPE>'s}|{[OMNI][Stacking spawners of different types will create an OmniSpawner].}|{[WATER][Spawners will repel water].}|{[REDSTONE][You can disable this spawner by applying redstone power to it].}|{[INVSTACK][You can stack spawners from your inventory by placing them on top of each other.]}"),
    SPAWNER_INFO_NEXT("Spawner-info-next", "&aClick to go to the next page."),
    SPAWNER_INFO_BACK("Spawner-info-back", "&aClick to go back to the first page.");

    private String path;
    private String def;
    private static FileConfiguration LANG;

    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }

    public static void setFile(final FileConfiguration config) {
        LANG = config;
    }

    public String getDefault() {
        return this.def;
    }

    public String getPath() {
        return this.path;
    }

    public String getConfigValue() {
        return getConfigValue(null, null, null);
    }

    public String getConfigValue(String arg) {
        return getConfigValue(arg, null, null);
    }

    public String getConfigValue(String arg, String arg2) {
        return getConfigValue(arg, arg2, null);
    }

    public String getConfigValue(int arg, String arg2) {
        return getConfigValue(Integer.toString(arg), arg2, null);
    }

        public String getConfigValue(String arg, String arg2, String arg3) {
        String value = ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, this.def));

            if (arg != null) {
                value = value.replace("{COST}", arg);
                value = value.replace("{GOAL}", arg);
                value = value.replace("{NEW}", arg);
                value = value.replace("{MAX}", arg);
                value = value.replace("{AMT}", arg);
                value = value.replace("{LEVEL}", arg);
                value = value.replace("{NEWM}", arg);
                value = value.replace("{SPAWNS}", arg);
                if (arg2 != null) {
                    value = value.replace("{SPAWNER}", arg2);
                    value = value.replace("{TYPE}", arg2);
                }
                value = value.replace("{TYPE}", arg);
                if (arg3 != null)
                    value = value.replace("{TIME}", arg3);
            }

            if (arg2 != null) {
                value = value.replace("{COST}", arg2).replace("{GOAL}", arg2).replace("{NEW}", arg2).replace("{MAX}", arg2).replace("{AMT}", arg2)
                        .replace("{LEVEL}", arg2).replace("{TYPE}", arg2).replace("{NEWM}", arg2);
            }

        return value;
    }
}
