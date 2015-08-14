package me.isach.ultracosmetics;

import me.isach.ultracosmetics.commands.UltraCosmeticsCommand;
import me.isach.ultracosmetics.commands.UltraCosmeticsTabCompleter;
import me.isach.ultracosmetics.config.MessageManager;
import me.isach.ultracosmetics.config.SettingsManager;
import me.isach.ultracosmetics.cosmetics.gadgets.*;
import me.isach.ultracosmetics.cosmetics.mounts.*;
import me.isach.ultracosmetics.cosmetics.particleeffects.*;
import me.isach.ultracosmetics.cosmetics.pets.*;
import me.isach.ultracosmetics.listeners.MenuListener;
import me.isach.ultracosmetics.listeners.PlayerListener;
import me.isach.ultracosmetics.util.BlockUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.*;

/**
 * Created by sacha on 03/08/15.
 */
public class Core extends JavaPlugin {

    public static ArrayList<Entity> noFallDamageEntities = new ArrayList<>();

    public static List<Gadget> gadgetList = new ArrayList<>();
    public static List<ParticleEffect> particleEffectList = new ArrayList<>();
    public static List<Mount> mountList = new ArrayList<>();
    public static List<Pet> petList = new ArrayList<>();
    public static List<CustomPlayer> customPlayers = new ArrayList<>();
    public static HashMap<Player, HashMap<Gadget.GadgetType, Double>> countdownMap = new HashMap<>();

    public static ArrayList<GadgetDiscoBall> discoBalls = new ArrayList<>();
    public static ArrayList<GadgetExplosiveSheep> explosiveSheep = new ArrayList<>();

    public static boolean nbapiEnabled = false;

    @Override
    public void onEnable() {

        if (Bukkit.getPluginManager().getPlugin("NoteBlockAPI") != null) {
            getServer().getConsoleSender().sendMessage("§c§l----------------------------");
            getServer().getConsoleSender().sendMessage("");
            getServer().getConsoleSender().sendMessage("  §4§lNoteBlockAPI found! Using it!");
            getServer().getConsoleSender().sendMessage("");
            getServer().getConsoleSender().sendMessage("§c§l----------------------------");
            nbapiEnabled = true;
        }

        new MessageManager();
        registerListener(new MenuListener());
        registerListener(new PlayerListener());

        // Add gadgets.
        gadgetList.add(new GadgetPaintballGun(null));
        gadgetList.add(new GadgetBatBlaster(null));
        gadgetList.add(new GadgetChickenator(null));
        gadgetList.add(new GadgetMelonThrower(null));
        gadgetList.add(new GadgetEtherealPearl(null));
        gadgetList.add(new GadgetDiscoBall(null));
        gadgetList.add(new GadgetColorBomb(null));
        gadgetList.add(new GadgetFleshHook(null));
        gadgetList.add(new GadgetPortalGun(null));
        gadgetList.add(new GadgetBlizzardBlaster(null));
        gadgetList.add(new GadgetThorHammer(null));
        gadgetList.add(new GadgetSmashDown(null));
        gadgetList.add(new GadgetExplosiveSheep(null));
        gadgetList.add(new GadgetAntiGravity(null));
        // gadgetList.add(new GadgetTsunami(null));

        // Register Mounts
        mountList.add(new MountDruggedHorse(null));
        mountList.add(new MountEcologistHorse(null));
        mountList.add(new MountGlacialSteed(null));
        mountList.add(new MountInfernalHorror(null));
        mountList.add(new MountMountOfFire(null));
        mountList.add(new MountMountOfWater(null));
        mountList.add(new MountWalkingDead(null));
        mountList.add(new MountSnake(null));

        // Register Particle Effects
        particleEffectList.add(new ParticleEffectRainCloud(null));
        particleEffectList.add(new ParticleEffectSnowCloud(null));
        particleEffectList.add(new ParticleEffectBloodHelix(null));
        particleEffectList.add(new ParticleEffectFrostLord(null));
        particleEffectList.add(new ParticleEffectFlameRings(null));
        particleEffectList.add(new ParticleEffectInLove(null));
        particleEffectList.add(new ParticleEffectGreenSparks(null));

        // Register Particle Effects
        petList.add(new PetPiggy(null));
        petList.add(new PetSheep(null));
        petList.add(new PetKitty(null));
        petList.add(new PetDog(null));
        petList.add(new PetChick(null));
        petList.add(new PetCow(null));
        petList.add(new PetEasterBunny(null));

        // Register the command
        getCommand("ultracosmetics").setExecutor(new UltraCosmeticsCommand());
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("uc");
        getCommand("ultracosmetics").setAliases(arrayList);
        getCommand("ultracosmetics").setTabCompleter(new UltraCosmeticsTabCompleter());

        // Set config things.
        SettingsManager.getConfig().addDefault("Menu-Item.Give-On-Join", true);
        SettingsManager.getConfig().addDefault("Menu-Item.Slot", 3);
        SettingsManager.getConfig().addDefault("Menu-Item.Type", "ENDER_CHEST");
        SettingsManager.getConfig().addDefault("Menu-Item.Data", 0);
        SettingsManager.getConfig().addDefault("Menu-Item.Displayname", "&6&lCosmetics");
        SettingsManager.getConfig().addDefault("No-Permission.Show-In-Lore", true);
        SettingsManager.getConfig().addDefault("No-Permission.Lore-Message-Yes", "&o&7Permission: &a&lYes!");
        SettingsManager.getConfig().addDefault("No-Permission.Lore-Message-No", "&o&7Permission: &4&lNo!");
        SettingsManager.getConfig().addDefault("No-Permission.Dont-Show-Item", false);
        SettingsManager.getConfig().addDefault("No-Permission.Custom-Item.enabled", false);
        SettingsManager.getConfig().addDefault("No-Permission.Custom-Item.Type", "INK_SACK");
        SettingsManager.getConfig().addDefault("No-Permission.Custom-Item.Data", 8);
        SettingsManager.getConfig().addDefault("No-Permission.Custom-Item.Name", "&c&lNo Permission");
        SettingsManager.getConfig().addDefault("Disabled-Items.Show-Custom-Disabled-Item", false);
        SettingsManager.getConfig().addDefault("Disabled-Items.Custom-Disabled-Item.Type", "INK_SACK");
        SettingsManager.getConfig().addDefault("Disabled-Items.Custom-Disabled-Item.Data", 8);
        SettingsManager.getConfig().addDefault("Disabled-Items.Custom-Disabled-Item.Name", "&c&lDisabled");

        SettingsManager.getConfig().addDefault("Gadget-Slot", 4);

        for (Gadget gadget : gadgetList) {
            SettingsManager.getConfig().addDefault("Gadgets." + gadget.getType().configName + ".Enabled", true);
        }

        SettingsManager.getConfig().addDefault("Mounts.DruggedHorse.Enabled", true);
        SettingsManager.getConfig().addDefault("Mounts.EcologistHorse.Enabled", true);
        SettingsManager.getConfig().addDefault("Mounts.GlacialSteed.Enabled", true);
        SettingsManager.getConfig().addDefault("Mounts.InfernalHorror.Enabled", true);
        SettingsManager.getConfig().addDefault("Mounts.MountOfFire.Enabled", true);
        SettingsManager.getConfig().addDefault("Mounts.MountOfWater.Enabled", true);
        SettingsManager.getConfig().addDefault("Mounts.Snake.Enabled", true);
        SettingsManager.getConfig().addDefault("Mounts.WalkingDead.Enabled", true);

        for (ParticleEffect particleEffect : particleEffectList) {
            SettingsManager.getConfig().addDefault("Particle-Effects." + particleEffect.getConfigName() + ".Enabled", true);
        }
        for (Pet pet : petList) {
            SettingsManager.getConfig().addDefault("Pets." + pet.getConfigName() + ".Enabled", true);
        }

        for (Player p : Bukkit.getOnlinePlayers())
            customPlayers.add(new CustomPlayer(p.getUniqueId()));

        final BukkitRunnable countdownRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    Iterator<Entity> iter = noFallDamageEntities.iterator();
                    while (iter.hasNext()) {
                        Entity ent = iter.next();
                        if (ent.isOnGround())
                            iter.remove();
                    }
                    Iterator<CustomPlayer> customPlayerIterator = customPlayers.iterator();
                    while(iter.hasNext()) {
                        CustomPlayer customPlayer = customPlayerIterator.next();
                        if(customPlayer.getPlayer() == null)
                            customPlayerIterator.remove();
                    }
                    for (Player p : countdownMap.keySet()) {
                        if (countdownMap.get(p) != null) {
                            for (Gadget.GadgetType gt : countdownMap.get(p).keySet()) {
                                double timeLeft = countdownMap.get(p).get(gt);
                                if (timeLeft > 0.05f) {
                                    timeLeft -= 0.05f;
                                    countdownMap.get(p).put(gt, timeLeft);
                                }
                            }
                            Iterator it = countdownMap.get(p).entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry pair = (Map.Entry) it.next();
                                if ((double) pair.getValue() < 0.1) {
                                    it.remove();
                                }
                            }
                        }
                    }
                } catch (Exception exc) {
                }
            }
        };
        countdownRunnable.runTaskTimerAsynchronously(Core.getPlugin(), 0, 1);

        if (nbapiEnabled) {
            File folder = new File(getDataFolder().getPath() + "/songs/");
            if ((!folder.exists()) || (folder.listFiles().length <= 0)) {

                saveResource("songs/GetLucky.nbs", true);
            }
        }
    }

    @Override
    public void onDisable() {
        BlockUtils.forceRestore();
        for (CustomPlayer cp : customPlayers) {
            cp.removeGadget();
            cp.removeMount();
            cp.removeParticleEffect();
            cp.removePet();
        }
    }

    /**
     * Gets the UltraCosmetics Plugin Object.
     *
     * @return
     */
    public static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("UltraCosmetics");
    }

    public static void registerListener(Listener listenerClass) {
        Bukkit.getPluginManager().registerEvents(listenerClass, getPlugin());
    }

    public static CustomPlayer getCustomPlayer(Player player) {
        for (CustomPlayer cp : customPlayers)
            if (cp.getPlayer().getName().equals(player.getName()))
                return cp;
        return new CustomPlayer(player.getUniqueId());
    }


}
