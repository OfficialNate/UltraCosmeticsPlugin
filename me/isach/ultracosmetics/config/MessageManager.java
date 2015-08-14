package me.isach.ultracosmetics.config;

import net.md_5.bungee.api.ChatColor;

/**
 * Created by sacha on 03/08/15.
 */
public class MessageManager {
    private static SettingsManager settingsManager;

    /**
     * Set up the messages in the config.
     */
    public MessageManager() {
        this.settingsManager = SettingsManager.getMessages();
        loadMessages();
    }

    /**
     * Set up the messages in the config.
     */
    private void loadMessages() {
        addMessage("Prefix", "&l&oCosmetics >&r");
        addMessage("No-Permission", "%prefix% &c&lYou don't have the permission!");
        addMessage("Cosmetic-Disabled", "%prefix% &c&lThis cosmetic is disabled!");
        addMessage("Invalid-Gadget", "%prefix% &c&lInvalid Gadget!");
        addMessage("Invalid-Pet", "%prefix% &c&lInvalid Pet!");
        addMessage("Invalid-Mount", "%prefix% &c&lInvalid Mount!");
        addMessage("Invalid-Effect", "%prefix% &c&lInvalid Effect!");
        addMessage("Invalid-Menu", "%prefix% &c&lInvalid Menu!");

        // Gadgets
        addMessage("Gadgets.Equip", "%prefix% &9You equipped %gadgetname%");
        addMessage("Gadgets.Unequip", "%prefix% &9You unequipped %gadgetname%");
        addMessage("Gadgets.Countdown-Message", "%prefix% &c&lYou can't use %gadgetname% &c&lfor %time%s!");
        addMessage("Gadgets.PaintballGun.name", "&b&lPaintball Gun");
        addMessage("Gadgets.BatBlaster.name", "&7&lBat Blaster");
        addMessage("Gadgets.MelonThrower.name", "&a&lMelon Thrower");
        addMessage("Gadgets.EtherealPearl.name", "&5&lEthereal Pearl");
        addMessage("Gadgets.FleshHook.name", "&7&lFlesh Hook");
        addMessage("Gadgets.DiscoBall.name", "&d&lDisco Ball");
        addMessage("Gadgets.ColorBomb.name", "&d&lColor Bomb");
        addMessage("Gadgets.Chickenator.name", "&f&lChickenator");
        addMessage("Gadgets.PortalGun.name", "&c&lPortal &9&lGun");
        addMessage("Gadgets.BlizzardBlaster.name", "&b&lBlizzard Blaster");
        addMessage("Gadgets.ThorHammer.name", "&f&lThor's Hammer");
        addMessage("Gadgets.SmashDown.name", "&c&lSmashDown");
        addMessage("Gadgets.ExplosiveSheep.name", "&4&lExplosive Sheep");
        addMessage("Gadgets.AntiGravity.name", "&d&lAnti Gravity");
      //  addMessage("Gadgets.Tsunami.name", "&9&lTsunami");

        // MOUNTS
        addMessage("Mounts.DruggedHorse.menu-name", "&2&lDrugged Horse");
        addMessage("Mounts.DruggedHorse.entity-displayname", "&l%playername%'s drugged horse");
        addMessage("Mounts.InfernalHorror.menu-name", "&4&lInfernal Horror");
        addMessage("Mounts.InfernalHorror.entity-displayname", "&l%playername%'s infernal horror");
        addMessage("Mounts.GlacialSteed.menu-name", "&b&lGlacial Steed");
        addMessage("Mounts.GlacialSteed.entity-displayname", "&l%playername%'s glacial steed");
        addMessage("Mounts.WalkingDead.menu-name", "&2&lWalking Dead");
        addMessage("Mounts.WalkingDead.entity-displayname", "&l%playername%'s walking dead");
        addMessage("Mounts.MountOfFire.menu-name", "&c&lMount of Fire");
        addMessage("Mounts.MountOfFire.entity-displayname", "&l%playername%'s mount of fire");
        addMessage("Mounts.MountOfWater.menu-name", "&9&lMount of Water");
        addMessage("Mounts.MountOfWater.entity-displayname", "&l%playername%'s mount of water");
        addMessage("Mounts.EcologistHorse.menu-name", "&a&lEcologist Horse");
        addMessage("Mounts.EcologistHorse.entity-displayname", "&l%playername%'s ecologist horse");
        addMessage("Mounts.Snake.menu-name", "&6&lSnake");
        addMessage("Mounts.Snake.entity-displayname", "&l%playername%'s snake");
        addMessage("Mounts.Spawn", "%prefix% &9You spawned %mountname%");
        addMessage("Mounts.Despawn", "%prefix% &9You despawned %mountname%");

        // GADGETS
        addMessage("Particle-Effects.Summon", "%prefix% &9You summoned %effectname%");
        addMessage("Particle-Effects.Unsummon", "%prefix% &9You unsummoned %effectname%");
        addMessage("Particle-Effects.RainCloud.name", "&9&lRain Cloud");
        addMessage("Particle-Effects.SnowCloud.name", "&f&lSnow Cloud");
        addMessage("Particle-Effects.BloodHelix.name", "&4&lBlood Helix");
        addMessage("Particle-Effects.FrostLord.name", "&b&lFrost Lord");
        addMessage("Particle-Effects.FlameRings.name", "&c&lFlame Rings");
        // addMessage("Particle-Effects.AngelWings.name", "&f&lAngel Wings");
        addMessage("Particle-Effects.GreenSparks.name", "&a&lGreen Sparks");
        addMessage("Particle-Effects.InLove.name", "&c&lIn Love");

        // PETS
        addMessage("Pets.Piggy.menu-name", "&d&lPiggy");
        addMessage("Pets.Piggy.entity-displayname", "&l%playername%'s piggy");
        addMessage("Pets.Sheep.menu-name", "&f&lSheep");
        addMessage("Pets.Sheep.entity-displayname", "&l%playername%'s sheep");
        addMessage("Pets.EasterBunny.menu-name", "&6&lEaster Bunny");
        addMessage("Pets.EasterBunny.entity-displayname", "&l%playername%'s easter bunny");
        addMessage("Pets.Cow.menu-name", "&c&lCow");
        addMessage("Pets.Cow.entity-displayname", "&l%playername%'s cow");
        addMessage("Pets.Kitty.menu-name", "&9&lKitty");
        addMessage("Pets.Kitty.entity-displayname", "&l%playername%'s kitty");
        addMessage("Pets.Dog.menu-name", "&7&lDog");
        addMessage("Pets.Dog.entity-displayname", "&l%playername%'s dog");
        addMessage("Pets.Chick.menu-name", "&e&lChick");
        addMessage("Pets.Chick.entity-displayname", "&l%playername%'s chick");
        addMessage("Pets.Spawn", "%prefix% &9You spawned %petname%");
        addMessage("Pets.Despawn", "%prefix% &9You despawned %petname%");

        addMessage("Menu.Gadgets", "&9&lGadgets");
        addMessage("Menu.Particle-Effects", "&b&lParticle Effects");
        addMessage("Menu.Mounts", "&6&lMounts");
        addMessage("Menu.Pets", "&a&lPets");
        addMessage("Menu.Activate", "&b&lActivate");
        addMessage("Menu.Deactivate", "&c&lDeactivate");
        addMessage("Menu.Spawn", "&b&lSpawn");
        addMessage("Menu.Despawn", "&c&lDespawn");
        addMessage("Menu.Summon", "&b&lSummon");
        addMessage("Menu.Unsummon", "&c&lUnsummon");
    }

    /**
     * Add a message in the messages.yml file.
     *
     * @param path    The config path.
     * @param message The config value.
     */
    public static void addMessage(String path, String message) {
        settingsManager.addDefault(path, message);
    }

    /**
     * Gets a message.
     *
     * @param path The path of the message in the config.
     * @return a message from a config path.
     */
    public static String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', (String) settingsManager.get(path)).replaceAll("%prefix%", ((String) settingsManager.get("Prefix")).replaceAll("&", "§"));
    }

}
