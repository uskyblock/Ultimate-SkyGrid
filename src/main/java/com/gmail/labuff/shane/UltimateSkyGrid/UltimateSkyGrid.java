package com.gmail.labuff.shane.UltimateSkyGrid;

import org.bukkit.plugin.java.*;
import org.bukkit.configuration.file.*;
import org.bukkit.generator.*;
import java.util.logging.*;
import java.io.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.*;
import org.bukkit.block.*;
import org.bukkit.*;
import java.util.*;

public final class UltimateSkyGrid extends JavaPlugin
{
    public FileConfiguration configMain;
    public File dirPlayers;
    public File conFileMain;
    public File dataFolder;
    public static int cdelay;
    public static int cHeight;
    public static int cNetherHeight;
    public static int cEndHeight;
    public static int cMythic;
    public static int cUnique;
    public static int cRare;
    public static int cUncommon;
    public static int cMax;
    public static int cMin;
    public static int cChMythic;
    public static int cChRare;
    public static int cNethRare;
    public static int cNethChRare;
    public static int cEndChRare;
    public static int cEndRare;
    public static boolean genGlass;
    public static boolean allBlocksOneWorld;
    public static String cName;
    public static String cNetherName;
    public static String cEndName;
    public static int[] iMythic;
    public static int[] iUnique;
    public static int[] iRare;
    public static int[] iUncommon;
    public static int[] iAbundant;
    public static int[] iNormMythic;
    public static int[] iNormUnique;
    public static int[] iNormRare;
    public static int[] iNormUncommon;
    public static int[] iNormAbundant;
    public static int[] iChMythic;
    public static int[] iChMythicAmount;
    public static int[] iNormChMythic;
    public static int[] iNormChMythicAmount;
    public static int[] iChRare;
    public static int[] iChRareAmount;
    public static int[] iNormChRare;
    public static int[] iNormChRareAmount;
    public static int[] iChNormal;
    public static int[] iChNormalAmount;
    public static int[] iNormChNormal;
    public static int[] iNormChNormalAmount;
    public static int[] iNethBlkRare;
    public static int[] iNethBlkNorm;
    public static int[] iEndBlkRare;
    public static int[] iEndBlkNorm;
    public static int[] iNethChRare;
    public static int[] iNethChRareAmount;
    public static int[] iNethChNorm;
    public static int[] iNethChNormAmount;
    public static int[] iEndChRare;
    public static int[] iEndChRareAmount;
    public static int[] iEndChNorm;
    public static int[] iEndChNormAmount;
    
    public void onEnable() {
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        final File dFolder = this.getDataFolder();
        final File drPlyrs = new File(this.getDataFolder(), "Players");
        final File cnFlMn = new File(this.getDataFolder(), "config.yml");
        this.dirPlayers = drPlyrs;
        this.conFileMain = cnFlMn;
        this.configMain = this.getConfig();
        this.dataFolder = dFolder;
        if (!this.dirPlayers.exists()) {
            this.dirPlayers.mkdir();
        }
        if (this.conFileMain.exists()) {
            this.configMain = (FileConfiguration)YamlConfiguration.loadConfiguration(this.conFileMain);
        }
        else {
            copyConfig(this.conFileMain, this.getClass());
        }
        this.initConfig();
        this.getLogger().info("UltimateSkyGrid Version v0.2.3 Enabled/Reloaded");
    }
    
    public ChunkGenerator getDefaultWorldGenerator(final String worldName, final String id) {
        return new UltimateSkyGridGenerator();
    }
    
    private static void copyConfig(final File config, final Class<? extends UltimateSkyGrid> cl) {
        try {
            final InputStream in = cl.getResourceAsStream("/" + config.getName());
            final FileOutputStream out = new FileOutputStream(config);
            final byte[] buffer = new byte[512];
            int i;
            while ((i = in.read(buffer)) != -1) {
                out.write(buffer, 0, i);
            }
            out.close();
        }
        catch (FileNotFoundException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Plugin jar does not appear to have the required config file for writing", e);
        }
        catch (IOException e2) {
            Bukkit.getLogger().log(Level.SEVERE, null, e2);
        }
    }
    
    public void initConfig() {
        final FileConfiguration config = this.configMain;
        UltimateSkyGrid.cdelay = config.getInt("Delay", 0);
        UltimateSkyGrid.cHeight = config.getInt("World_Height", 128);
        UltimateSkyGrid.cMythic = config.getInt("Mythic_Block_Probability", 4);
        UltimateSkyGrid.cNetherHeight = config.getInt("NetherConfig.Nether_World_Height", 128);
        UltimateSkyGrid.cEndHeight = config.getInt("EndConfig.End_World_Height", 128);
        UltimateSkyGrid.cUnique = config.getInt("Unique_Block_Probability", 181);
        UltimateSkyGrid.cRare = config.getInt("Rare_Block_Probability", 1801);
        UltimateSkyGrid.cUncommon = config.getInt("Uncommon_Block_Probability", 4001);
        UltimateSkyGrid.cMax = config.getInt("Spawn_Max", 500);
        UltimateSkyGrid.cMin = config.getInt("Spawn_Min", 0);
        UltimateSkyGrid.cName = config.getString("World_Name", "Skygrid");
        UltimateSkyGrid.cNetherName = config.getString("NetherConfig.Nether_World_Name", "NetherSkygrid");
        UltimateSkyGrid.cEndName = config.getString("EndConfig.End_World_Name", "EndSkygrid");
        UltimateSkyGrid.cChMythic = config.getInt("Chest_Prob_Mythic", 2);
        UltimateSkyGrid.cChRare = config.getInt("Chest_Prob_Rare", 6);
        UltimateSkyGrid.genGlass = config.getBoolean("ReplaceAirWithGlass", false);
        UltimateSkyGrid.allBlocksOneWorld = config.getBoolean("AllBlocksOneWorld", true);
        UltimateSkyGrid.cNethRare = config.getInt("NetherConfig.NetherBlocks.RareProb", 1);
        UltimateSkyGrid.cNethChRare = config.getInt("NetherConfig.NetherChestItems.RareProb", 2);
        UltimateSkyGrid.cEndRare = config.getInt("EndConfig.EndBlocks.RareProb", 1);
        UltimateSkyGrid.cEndChRare = config.getInt("EndConfig.EndChestItems.RareProb", 1);
        final String[] sMythic = config.getString("BlockGroups.Mythic").split(" ");
        final String[] sUnique = config.getString("BlockGroups.Unique").split(" ");
        final String[] sRare = config.getString("BlockGroups.Rare").split(" ");
        final String[] sUncommon = config.getString("BlockGroups.Uncommon").split(" ");
        final String[] sAbundant = config.getString("BlockGroups.Abundant").split(" ");
        final String[] sNethBlkRare = config.getString("NetherConfig.NetherBlocks.Rare").split(" ");
        final String[] sNethBlkNorm = config.getString("NetherConfig.NetherBlocks.Normal").split(" ");
        final String[] sEndBlkRare = config.getString("EndConfig.EndBlocks.Rare").split(" ");
        final String[] sEndBlkNorm = config.getString("EndConfig.EndBlocks.Normal").split(" ");
        final String[] sChMythic = config.getString("ChestItems.Mythic").split(" ");
        final String[] sChRare = config.getString("ChestItems.Rare").split(" ");
        final String[] sChNormal = config.getString("ChestItems.Normal").split(" ");
        final String[] sNethChRare = config.getString("NetherConfig.NetherChestItems.Rare").split(" ");
        final String[] sNethChNorm = config.getString("NetherConfig.NetherChestItems.Normal").split(" ");
        final String[] sEndChRare = config.getString("EndConfig.EndChestItems.Rare").split(" ");
        final String[] sEndChNorm = config.getString("EndConfig.EndChestItems.Normal").split(" ");
        final String[] sNormMythic = config.getString("NormalConfig.BlockGroups.Mythic").split(" ");
        final String[] sNormUnique = config.getString("NormalConfig.BlockGroups.Unique").split(" ");
        final String[] sNormRare = config.getString("NormalConfig.BlockGroups.Rare").split(" ");
        final String[] sNormUncommon = config.getString("NormalConfig.BlockGroups.Uncommon").split(" ");
        final String[] sNormAbundant = config.getString("NormalConfig.BlockGroups.Abundant").split(" ");
        final String[] sNormChMythic = config.getString("NormalConfig.ChestItems.Mythic").split(" ");
        final String[] sNormChRare = config.getString("NormalConfig.ChestItems.Rare").split(" ");
        final String[] sNormChNormal = config.getString("NormalConfig.ChestItems.Normal").split(" ");
        UltimateSkyGrid.iMythic = this.stringArrayToIntArray(sMythic);
        UltimateSkyGrid.iUnique = this.stringArrayToIntArray(sUnique);
        UltimateSkyGrid.iRare = this.stringArrayToIntArray(sRare);
        UltimateSkyGrid.iUncommon = this.stringArrayToIntArray(sUncommon);
        UltimateSkyGrid.iAbundant = this.stringArrayToIntArray(sAbundant);
        UltimateSkyGrid.iNethBlkRare = this.stringArrayToIntArray(sNethBlkRare);
        UltimateSkyGrid.iNethBlkNorm = this.stringArrayToIntArray(sNethBlkNorm);
        UltimateSkyGrid.iEndBlkRare = this.stringArrayToIntArray(sEndBlkRare);
        UltimateSkyGrid.iEndBlkNorm = this.stringArrayToIntArray(sEndBlkNorm);
        UltimateSkyGrid.iNormMythic = this.stringArrayToIntArray(sNormMythic);
        UltimateSkyGrid.iNormUnique = this.stringArrayToIntArray(sNormUnique);
        UltimateSkyGrid.iNormRare = this.stringArrayToIntArray(sNormRare);
        UltimateSkyGrid.iNormUncommon = this.stringArrayToIntArray(sNormUncommon);
        UltimateSkyGrid.iNormAbundant = this.stringArrayToIntArray(sNormAbundant);
        UltimateSkyGrid.iChMythic = this.positionalStringArrayToIntArray(sChMythic, 0);
        UltimateSkyGrid.iChMythicAmount = this.positionalStringArrayToIntArray(sChMythic, 1);
        UltimateSkyGrid.iChRare = this.positionalStringArrayToIntArray(sChRare, 0);
        UltimateSkyGrid.iChRareAmount = this.positionalStringArrayToIntArray(sChRare, 1);
        UltimateSkyGrid.iChNormal = this.positionalStringArrayToIntArray(sChNormal, 0);
        UltimateSkyGrid.iChNormalAmount = this.positionalStringArrayToIntArray(sChNormal, 1);
        UltimateSkyGrid.iNethChRare = this.positionalStringArrayToIntArray(sNethChRare, 0);
        UltimateSkyGrid.iNethChRareAmount = this.positionalStringArrayToIntArray(sNethChRare, 1);
        UltimateSkyGrid.iNethChNorm = this.positionalStringArrayToIntArray(sNethChNorm, 0);
        UltimateSkyGrid.iNethChNormAmount = this.positionalStringArrayToIntArray(sNethChNorm, 1);
        UltimateSkyGrid.iEndChRare = this.positionalStringArrayToIntArray(sEndChRare, 0);
        UltimateSkyGrid.iEndChRareAmount = this.positionalStringArrayToIntArray(sEndChRare, 1);
        UltimateSkyGrid.iEndChNorm = this.positionalStringArrayToIntArray(sEndChNorm, 0);
        UltimateSkyGrid.iEndChNormAmount = this.positionalStringArrayToIntArray(sEndChNorm, 1);
        UltimateSkyGrid.iNormChMythic = this.positionalStringArrayToIntArray(sNormChMythic, 0);
        UltimateSkyGrid.iNormChMythicAmount = this.positionalStringArrayToIntArray(sNormChMythic, 1);
        UltimateSkyGrid.iNormChRare = this.positionalStringArrayToIntArray(sNormChRare, 0);
        UltimateSkyGrid.iNormChRareAmount = this.positionalStringArrayToIntArray(sNormChRare, 1);
        UltimateSkyGrid.iNormChNormal = this.positionalStringArrayToIntArray(sNormChNormal, 0);
        UltimateSkyGrid.iNormChNormalAmount = this.positionalStringArrayToIntArray(sNormChNormal, 1);
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        final String cmdName = cmd.getName();
        if (!(sender instanceof Player) && cmdName.equalsIgnoreCase("usg")) {
            sender.sendMessage("Only a player can execute this command.");
            return true;
        }
        final Player player = (Player)sender;
        if (!cmdName.equalsIgnoreCase("usg")) {
            return false;
        }
        this.initPlayerConfigFile(player, this.getClass());
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Correct usage of this command is /usg sethome or /usg home (/usg reload for admin config reloading)");
            return true;
        }
        if (args.length >= 2) {
            player.sendMessage(ChatColor.RED + "Too many arguments. Correct usage of this command is /usg sethome or /usg home (/usg reload for admin config reloading)");
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!player.hasPermission("UltimateSkyGrid.reload")) {
                player.sendMessage(ChatColor.RED + "You do not have permission for this command");
                return true;
            }
            player.sendMessage(ChatColor.BLUE + "Reloading configuration file...");
            this.onEnable();
            player.sendMessage(ChatColor.BLUE + "Configuration reloaded.");
            return true;
        }
        else if (args[0].equalsIgnoreCase("sethome")) {
            if (!player.hasPermission("UltimateSkyGrid.sethome")) {
                player.sendMessage(ChatColor.RED + "You do not have permission for this command");
                return true;
            }
            this.setHome(player);
            return true;
        }
        else {
            if (!args[0].equalsIgnoreCase("home")) {
                player.sendMessage(ChatColor.RED + "Correct usage of this command is /usg sethome or /usg home");
                return false;
            }
            if (!player.hasPermission("UltimateSkyGrid.home")) {
                player.sendMessage(ChatColor.RED + "You do not have permission for this command");
                return true;
            }
            final File pFile = new File(this.dirPlayers, String.valueOf(player.getName()) + ".yml");
            final FileConfiguration pConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(pFile);
            final String wName = UltimateSkyGrid.cName;
            final String nWName = UltimateSkyGrid.cNetherName;
            final String eWName = UltimateSkyGrid.cEndName;
            final World skygrid = this.getServer().getWorld(wName);
            final World nSkygrid = this.getServer().getWorld(nWName);
            final World eSkygrid = this.getServer().getWorld(eWName);
            final World curWorld = player.getWorld();
            World selWorld = this.getServer().getWorld(wName);
            String selName = UltimateSkyGrid.cName;
            String cWorldName = "World_Name";
            final int w = UltimateSkyGrid.cdelay;
            int x = 0;
            int y = 0;
            int z = 0;
            if (curWorld != nSkygrid || curWorld != eSkygrid) {
                selName = wName;
                selWorld = skygrid;
                x = pConfig.getInt("homex");
                y = pConfig.getInt("homey");
                z = pConfig.getInt("homez");
            }
            if (curWorld == nSkygrid) {
                cWorldName = "Nether_World_Name";
                selName = nWName;
                selWorld = nSkygrid;
                x = pConfig.getInt("netherhomex");
                y = pConfig.getInt("netherhomey");
                z = pConfig.getInt("netherhomez");
            }
            if (curWorld == eSkygrid) {
                cWorldName = "End_World_Name";
                selName = eWName;
                selWorld = eSkygrid;
                x = pConfig.getInt("endhomex");
                y = pConfig.getInt("endhomey");
                z = pConfig.getInt("endhomez");
            }
            if (selWorld == null) {
                this.getLogger().severe("Config value: " + cWorldName + ": = null");
                this.getLogger().info(cWorldName + ": " + selName + " in config doesn't exist. Make sure the config name matches the actual world name, case sensitive.");
                player.sendMessage(ChatColor.RED + "Error: UltimateSkyGrid world names are not set up correctly. Tell an Admin");
                return true;
            }
            final Block home = selWorld.getBlockAt(x, y, z);
            final Location homeLoc = home.getLocation().add(0.5, 0.0, 0.5);
            if (w > 0) {
                player.sendMessage(ChatColor.GREEN + "Waiting " + ChatColor.BLUE + w + ChatColor.GREEN + " seconds before you port...");
                new TeleportDelay(player, homeLoc, selName).runTaskLater((Plugin)this, (long)(w * 20));
                return true;
            }
            player.sendMessage(ChatColor.GREEN + "Teleporting you to your home in " + selName);
            player.teleport(homeLoc);
            return true;
        }
    }
    
    public void initPlayerConfigFile(final Player player, final Class<? extends UltimateSkyGrid> cl) {
        final File mFile = new File(this.getDataFolder(), "config.yml");
        final FileConfiguration mConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(mFile);
        final File pFile = new File(this.dirPlayers, String.valueOf(player.getName()) + ".yml");
        final int wH = mConfig.getInt("World_Height");
        try {
            if (pFile.exists()) {
                final FileConfiguration pConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(pFile);
                pConfig.save(pFile);
            }
            else {
                final InputStream in = cl.getResourceAsStream("/defaultplayer.yml");
                final FileOutputStream out = new FileOutputStream(pFile);
                final byte[] buffer = new byte[512];
                int i;
                while ((i = in.read(buffer)) != -1) {
                    out.write(buffer, 0, i);
                }
                out.close();
                final FileConfiguration pConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(pFile);
                pConfig.addDefault("homex", (Object)0);
                pConfig.addDefault("homey", (Object)(wH - 3));
                pConfig.addDefault("homez", (Object)0);
                pConfig.addDefault("netherhomex", (Object)0);
                pConfig.addDefault("netherhomey", (Object)(wH - 3));
                pConfig.addDefault("netherhomez", (Object)0);
                pConfig.addDefault("endhomex", (Object)0);
                pConfig.addDefault("endhomey", (Object)(wH - 3));
                pConfig.addDefault("endhomez", (Object)0);
                pConfig.set("homex", (Object)this.randCoord());
                pConfig.set("homey", (Object)(wH - 3));
                pConfig.set("homez", (Object)this.randCoord());
                pConfig.set("netherhomex", (Object)this.randCoord());
                pConfig.set("netherhomey", (Object)(wH - 3));
                pConfig.set("netherhomez", (Object)this.randCoord());
                pConfig.set("endhomex", (Object)this.randCoord());
                pConfig.set("endhomey", (Object)(wH - 3));
                pConfig.set("endhomez", (Object)this.randCoord());
                pConfig.save(pFile);
            }
        }
        catch (FileNotFoundException e) {
            Bukkit.getLogger().log(Level.SEVERE, "defaultplayer.yml is missing from the jar file", e);
        }
        catch (IOException e2) {
            Bukkit.getLogger().log(Level.SEVERE, null, e2);
        }
    }
    
    public void setHome(final Player player) {
        final int x = player.getLocation().getBlockX();
        final int y = player.getLocation().getBlockY();
        final int z = player.getLocation().getBlockZ();
        final World curWorld = player.getWorld();
        if (curWorld != this.getServer().getWorld(UltimateSkyGrid.cName) && curWorld != this.getServer().getWorld(UltimateSkyGrid.cNetherName) && curWorld != this.getServer().getWorld(UltimateSkyGrid.cEndName)) {
            player.sendMessage(ChatColor.RED + "You cant set your skygrid home in this world.");
            return;
        }
        try {
            final File pConFile = new File(this.dirPlayers, String.valueOf(player.getName()) + ".yml");
            if (pConFile.exists()) {
                final FileConfiguration pConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(pConFile);
                if (curWorld == this.getServer().getWorld(UltimateSkyGrid.cName)) {
                    pConfig.set("homex", (Object)x);
                    pConfig.set("homey", (Object)y);
                    pConfig.set("homez", (Object)z);
                    pConfig.save(pConFile);
                    player.sendMessage(ChatColor.BLUE + "Your Skygrid home is now set to: " + ChatColor.GREEN + x + ChatColor.BLUE + ":X " + ChatColor.GREEN + y + ChatColor.BLUE + ":Y " + ChatColor.GREEN + z + ChatColor.BLUE + ":Z ");
                    return;
                }
                if (curWorld == this.getServer().getWorld(UltimateSkyGrid.cNetherName)) {
                    pConfig.set("netherhomex", (Object)x);
                    pConfig.set("netherhomey", (Object)y);
                    pConfig.set("netherhomez", (Object)z);
                    pConfig.save(pConFile);
                    player.sendMessage(ChatColor.BLUE + "Your NetherSkyGrid home is now set to: " + ChatColor.GREEN + x + ChatColor.BLUE + ":X " + ChatColor.GREEN + y + ChatColor.BLUE + ":Y " + ChatColor.GREEN + z + ChatColor.BLUE + ":Z ");
                    return;
                }
                pConfig.set("endhomex", (Object)x);
                pConfig.set("endhomey", (Object)y);
                pConfig.set("endhomez", (Object)z);
                pConfig.save(pConFile);
                player.sendMessage(ChatColor.BLUE + "Your EndSkyGrid home is now set to: " + ChatColor.GREEN + x + ChatColor.BLUE + ":X " + ChatColor.GREEN + y + ChatColor.BLUE + ":Y " + ChatColor.GREEN + z + ChatColor.BLUE + ":Z ");
            }
            else {
                player.sendMessage(ChatColor.RED + "Your player config file must not have initialized properly. Talk to an admin");
            }
        }
        catch (FileNotFoundException e) {
            Bukkit.getLogger().log(Level.SEVERE, null, e);
        }
        catch (IOException e2) {
            Bukkit.getLogger().log(Level.SEVERE, null, e2);
        }
    }
    
    public File getFolder() {
        return this.dataFolder;
    }
    
    public File getDefConfigFile() {
        return this.conFileMain;
    }
    
    public int randCoord() {
        final Random r = new Random();
        final Random r2 = new Random();
        final int b = r2.nextInt(2);
        int a;
        for (a = r.nextInt(UltimateSkyGrid.cMax - UltimateSkyGrid.cMin + 1) + UltimateSkyGrid.cMin; a % 4 != 0; ++a) {}
        if (b == 0) {
            a = -a;
            return a;
        }
        return a;
    }
    
    public int[] stringArrayToIntArray(final String[] stringArray) {
        final int[] newArray = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            try {
                newArray[i] = Integer.parseInt(stringArray[i]);
            }
            catch (NumberFormatException e) {
                Bukkit.getLogger().log(Level.SEVERE, "Invalid integer in string array!", e);
            }
        }
        return newArray;
    }
    
    public int[] positionalStringArrayToIntArray(final String[] stringArray, final int pos) {
        final int[] newArray = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            final String sP = stringArray[i];
            final String[] sPA = sP.split(":");
            try {
                newArray[i] = Integer.parseInt(sPA[pos]);
            }
            catch (NumberFormatException e) {
                Bukkit.getLogger().log(Level.SEVERE, "Invalid integer in string array, or your format is wrong! <ID>:<AMOUNT>", e);
            }
        }
        return newArray;
    }
}
