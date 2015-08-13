package me.isach.ultracosmetics.listeners;

import me.isach.ultracosmetics.Core;
import me.isach.ultracosmetics.CustomPlayer;
import me.isach.ultracosmetics.config.MessageManager;
import me.isach.ultracosmetics.config.SettingsManager;
import me.isach.ultracosmetics.cosmetics.gadgets.*;
import me.isach.ultracosmetics.cosmetics.mounts.*;
import me.isach.ultracosmetics.cosmetics.particleeffects.*;
import me.isach.ultracosmetics.cosmetics.pets.*;
import me.isach.ultracosmetics.util.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sacha on 03/08/15.
 */
public class MenuListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() != null
                && event.getItem().hasItemMeta()
                && event.getItem().getItemMeta().hasDisplayName()
                && event.getItem().getItemMeta().getDisplayName().equals(String.valueOf(SettingsManager.getConfig().get("Menu-Item.Displayname")).replaceAll("&", "§"))) {
            event.setCancelled(true);
            if (Core.getCustomPlayer(event.getPlayer()).currentMenu == CustomPlayer.MenuCategory.GADGETS) {
                openGadgetsMenu(event.getPlayer());
            } else if (Core.getCustomPlayer(event.getPlayer()).currentMenu == CustomPlayer.MenuCategory.PARTICLEEFFECTS) {
                openParticlesMenu(event.getPlayer());
            } else if (Core.getCustomPlayer(event.getPlayer()).currentMenu == CustomPlayer.MenuCategory.MOUNTS) {
                openMountsMenu(event.getPlayer());
            } else if (Core.getCustomPlayer(event.getPlayer()).currentMenu == CustomPlayer.MenuCategory.PETS) {
                openPetsMenu(event.getPlayer());
            }
        }
    }

    public static void openPetsMenu(Player p) {
        int listSize = 0;
        for (Pet pet : Core.petList) {
            if (!pet.getType().isEnabled()) continue;
            listSize++;
        }
        int slotAmount = 45;
        if (listSize < 29)
            slotAmount = 54;
        if (listSize < 22)
            slotAmount = 45;
        if (listSize < 15)
            slotAmount = 36;
        if (listSize < 8)
            slotAmount = 27;

        Inventory inv = Bukkit.createInventory(null, slotAmount, "§lPets");

        ItemStack pets = ItemFactory.create(Material.MONSTER_EGG, (byte) 0, MessageManager.getMessage("Menu.Pets"));
        ItemStack particleEffects = ItemFactory.create(Material.MELON_SEEDS, (byte) 0, MessageManager.getMessage("Menu.Particle-Effects"));
        ItemStack gadgets = ItemFactory.create(Material.SLIME_BALL, (byte) 0, MessageManager.getMessage("Menu.Gadgets"));
        ItemStack mounts = ItemFactory.create(Material.SADDLE, (byte) 0, MessageManager.getMessage("Menu.Mounts"));

        pets.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10);

        inv.setItem(1, pets);
        inv.setItem(3, particleEffects);
        inv.setItem(5, gadgets);
        inv.setItem(7, mounts);

        for (int i = 9; i < 19; i++)
            inv.setItem(i, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
        inv.setItem(26, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
        if (slotAmount > 27) {
            inv.setItem(27, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
            inv.setItem(35, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
            if (slotAmount > 36) {
                inv.setItem(36, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
                inv.setItem(44, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
            }
        }

        int i = 19;
        for (Pet pet : Core.petList) {
            if (!pet.getType().isEnabled()) continue;
            String toggle = MessageManager.getMessage("Menu.Spawn");
            CustomPlayer cp = Core.getCustomPlayer(p);
            if (cp.currentPet != null && cp.currentPet.getType() == pet.getType())
                toggle = MessageManager.getMessage("Menu.Despawn");
            ItemStack is = ItemFactory.create(pet.getMaterial(), pet.getData(), toggle + " " + pet.getMenuName());
            if (cp.currentPet != null && cp.currentPet.getType() == pet.getType())
                is.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10);
            inv.setItem(i, is);
            if (i == 25 || i == 34 || i == 43) {
                i += 3;
            } else {
                i++;
            }
        }

        p.openInventory(inv);
        Core.getCustomPlayer(p).currentMenu = CustomPlayer.MenuCategory.PETS;
    }

    public static void openParticlesMenu(Player p) {
        int slotAmount = 45;
        if (Core.particleEffectList.size() < 15)
            slotAmount = 36;
        if (Core.particleEffectList.size() < 8)
            slotAmount = 27;

        Inventory inv = Bukkit.createInventory(null, slotAmount, "§lParticle Effects");

        ItemStack pets = ItemFactory.create(Material.MONSTER_EGG, (byte) 0, MessageManager.getMessage("Menu.Pets"));
        ItemStack particleEffects = ItemFactory.create(Material.MELON_SEEDS, (byte) 0, MessageManager.getMessage("Menu.Particle-Effects"));
        ItemStack gadgets = ItemFactory.create(Material.SLIME_BALL, (byte) 0, MessageManager.getMessage("Menu.Gadgets"));
        ItemStack mounts = ItemFactory.create(Material.SADDLE, (byte) 0, MessageManager.getMessage("Menu.Mounts"));

        particleEffects.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10);

        inv.setItem(1, pets);
        inv.setItem(3, particleEffects);
        inv.setItem(5, gadgets);
        inv.setItem(7, mounts);

        for (int i = 9; i < 19; i++)
            inv.setItem(i, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
        inv.setItem(26, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
        if (slotAmount > 27) {
            inv.setItem(27, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
            inv.setItem(35, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
            if (slotAmount > 36) {
                inv.setItem(36, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
                inv.setItem(44, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
            }
        }

        int i = 19;
        for (ParticleEffect particleEffect : Core.particleEffectList) {
            if (!particleEffect.getType().isEnabled()) continue;
            String toggle = MessageManager.getMessage("Menu.Summon");
            CustomPlayer cp = Core.getCustomPlayer(p);
            if (cp.currentParticleEffect != null && cp.currentParticleEffect.getType() == particleEffect.getType())
                toggle = MessageManager.getMessage("Menu.Unsummon");
            ItemStack is = ItemFactory.create(particleEffect.getMaterial(), particleEffect.getData(), toggle + " " + particleEffect.getName());
            if (cp.currentParticleEffect != null && cp.currentParticleEffect.getType() == particleEffect.getType())
                is.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10);
            inv.setItem(i, is);
            if (i == 25 || i == 34 || i == 43) {
                i += 3;
            } else {
                i++;
            }
        }

        p.openInventory(inv);
        Core.getCustomPlayer(p).currentMenu = CustomPlayer.MenuCategory.PARTICLEEFFECTS;
    }

    public static void openMountsMenu(Player p) {

        int listSize = 0;
        for (Mount m : Core.mountList) {
            if (!m.getType().isEnabled()) continue;
            listSize++;
        }
        int slotAmount = 45;
        if (listSize < 29)
            slotAmount = 54;
        if (listSize < 22)
            slotAmount = 45;
        if (listSize < 15)
            slotAmount = 36;
        if (listSize < 8)
            slotAmount = 27;

        Inventory inv = Bukkit.createInventory(null, slotAmount, "§lMounts");

        ItemStack pets = ItemFactory.create(Material.MONSTER_EGG, (byte) 0, MessageManager.getMessage("Menu.Pets"));
        ItemStack particleEffects = ItemFactory.create(Material.MELON_SEEDS, (byte) 0, MessageManager.getMessage("Menu.Particle-Effects"));
        ItemStack gadgets = ItemFactory.create(Material.SLIME_BALL, (byte) 0, MessageManager.getMessage("Menu.Gadgets"));
        ItemStack mounts = ItemFactory.create(Material.SADDLE, (byte) 0, MessageManager.getMessage("Menu.Mounts"));

        mounts.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10);

        inv.setItem(1, pets);
        inv.setItem(3, particleEffects);
        inv.setItem(5, gadgets);
        inv.setItem(7, mounts);

        for (int i = 9; i < 19; i++)
            inv.setItem(i, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
        inv.setItem(26, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
        if (slotAmount > 27) {
            inv.setItem(27, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
            inv.setItem(35, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
            if (slotAmount > 36) {
                inv.setItem(36, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
                inv.setItem(44, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
                if (slotAmount > 45) {
                    inv.setItem(45, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
                    inv.setItem(53, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
                }
            }
        }

        int i = 19;
        for (Mount m : Core.mountList) {
            if (!m.getType().isEnabled()) continue;
            String toggle = MessageManager.getMessage("Menu.Spawn");
            CustomPlayer cp = Core.getCustomPlayer(p);
            if (cp.currentMount != null && cp.currentMount.getType() == m.getType())
                toggle = MessageManager.getMessage("Menu.Despawn");
            ItemStack is = ItemFactory.create(m.getMaterial(), m.getData(), toggle + " " + m.getMenuName());
            if (cp.currentMount != null && cp.currentMount.getType() == m.getType())
                is.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10);
            inv.setItem(i, is);
            if (i == 25 || i == 34 || i == 43) {
                i += 3;
            } else {
                i++;
            }
        }

        p.openInventory(inv);
        Core.getCustomPlayer(p).currentMenu = CustomPlayer.MenuCategory.MOUNTS;
    }

    public static void openGadgetsMenu(Player p) {
        int slotAmount = 45;
        if (Core.gadgetList.size() < 15)
            slotAmount = 36;
        if (Core.gadgetList.size() < 8)
            slotAmount = 27;

        Inventory inv = Bukkit.createInventory(null, slotAmount, "§lGadgets");

        ItemStack pets = ItemFactory.create(Material.MONSTER_EGG, (byte) 0, MessageManager.getMessage("Menu.Pets"));
        ItemStack particleEffects = ItemFactory.create(Material.MELON_SEEDS, (byte) 0, MessageManager.getMessage("Menu.Particle-Effects"));
        ItemStack gadgets = ItemFactory.create(Material.SLIME_BALL, (byte) 0, MessageManager.getMessage("Menu.Gadgets"));
        ItemStack mounts = ItemFactory.create(Material.SADDLE, (byte) 0, MessageManager.getMessage("Menu.Mounts"));

        gadgets.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10);

        inv.setItem(1, pets);
        inv.setItem(3, particleEffects);
        inv.setItem(5, gadgets);
        inv.setItem(7, mounts);

        for (int i = 9; i < 19; i++)
            inv.setItem(i, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
        inv.setItem(26, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
        if (slotAmount > 27) {
            inv.setItem(27, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
            inv.setItem(35, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
            if (slotAmount > 36) {
                inv.setItem(36, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
                inv.setItem(44, ItemFactory.create(Material.STAINED_GLASS_PANE, (byte) 0xb, "§k"));
            }
        }

        int i = 19;
        for (Gadget g : Core.gadgetList) {
            if (!g.getType().isEnabled()) continue;
            String toggle = MessageManager.getMessage("Menu.Activate");
            CustomPlayer cp = Core.getCustomPlayer(p);
            if (cp.currentGadget != null && cp.currentGadget.getType() == g.getType())
                toggle = MessageManager.getMessage("Menu.Deactivate");
            ItemStack is = ItemFactory.create(g.getMaterial(), g.getData(), toggle + " " + g.getName());
            if (cp.currentGadget != null && cp.currentGadget.getType() == g.getType())
                is.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 10);
            inv.setItem(i, is);
            if (i == 25 || i == 34) {
                i += 3;
            } else {
                i++;
            }
        }

        p.openInventory(inv);
        Core.getCustomPlayer(p).currentMenu = CustomPlayer.MenuCategory.GADGETS;
    }

    public Mount.MountType getMountByName(String name) {
        for (Mount mount : Core.mountList) {
            if (mount.getMenuName().equals(name)) {
                return mount.getType();
            }
        }
        return null;
    }

    public Gadget.GadgetType getGadgetByName(String name) {
        for (Gadget g : Core.gadgetList) {
            if (g.getName().equals(name)) {
                return g.getType();
            }
        }
        return null;
    }

    List<Player> playerList = new ArrayList<>();

    public void activateMountByType(Mount.MountType type, final Player player) {
        if (!player.hasPermission(type.getPermission())) {
            if (!playerList.contains(player)) {
                player.sendMessage(MessageManager.getMessage("No-Permission"));
                playerList.add(player);
                Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        playerList.remove(player);
                    }
                }, 1);
            }
            return;
        }

        switch (type) {
            case DRUGGEDHORSE:
                new MountDruggedHorse(player.getUniqueId());
                break;
            case GLACIALSTEED:
                new MountGlacialSteed(player.getUniqueId());
                break;
            case INFERNALHORROR:
                new MountInfernalHorror(player.getUniqueId());
                break;
            case ECOLOGISTHORSE:
                new MountEcologistHorse(player.getUniqueId());
                break;
            case MOUNTOFFIRE:
                new MountMountOfFire(player.getUniqueId());
                break;
            case MOUNTOFWATER:
                new MountMountOfWater(player.getUniqueId());
                break;
            case SNAKE:
                new MountSnake(player.getUniqueId());
                break;
            case WALKINGDEAD:
                new MountWalkingDead(player.getUniqueId());
                break;
        }
    }

    public Pet.PetType getPetByName(String name) {
        for (Pet pet : Core.petList) {
            if (pet.getMenuName().equals(name)) {
                return pet.getType();
            }
        }
        return null;
    }

    public void activatePetByType(Pet.PetType type, final Player player) {
        if (!player.hasPermission(type.getPermission())) {
            if (!playerList.contains(player)) {
                player.sendMessage(MessageManager.getMessage("No-Permission"));
                playerList.add(player);
                Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        playerList.remove(player);
                    }
                }, 1);
            }
            return;
        }

        switch (type) {
            case PIGGY:
                new PetPiggy(player.getUniqueId());
                break;
            case COW:
                new PetCow(player.getUniqueId());
                break;
            case CHICK:
                new PetChick(player.getUniqueId());
                break;
            case SHEEP:
                new PetSheep(player.getUniqueId());
                break;
            case EASTERBUNNY:
                new PetEasterBunny(player.getUniqueId());
                break;
            case DOG:
                new PetDog(player.getUniqueId());
                break;
            case KITTY:
                new PetKitty(player.getUniqueId());
                break;
        }
    }

    public ParticleEffect.ParticleEffectType getParticleEffectByName(String name) {
        for (ParticleEffect particleEffect : Core.particleEffectList) {
            if (particleEffect.getName().equals(name)) {
                return particleEffect.getType();
            }
        }
        return null;
    }

    public void activateParticleEffectByType(ParticleEffect.ParticleEffectType type, final Player player) {
        if (!player.hasPermission(type.getPermission())) {
            if (!playerList.contains(player)) {
                player.sendMessage(MessageManager.getMessage("No-Permission"));
                playerList.add(player);
                Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        playerList.remove(player);
                    }
                }, 1);
            }
            return;
        }

        switch (type) {
            case RAINCLOUD:
                new ParticleEffectRainCloud(player.getUniqueId());
                break;
            case SNOWCLOUD:
                new ParticleEffectSnowCloud(player.getUniqueId());
                break;
            case BLOODHELIX:
                new ParticleEffectBloodHelix(player.getUniqueId());
                break;
            case FLAMERINGS:
                new ParticleEffectFlameRings(player.getUniqueId());
                break;
            case FROSTLORD:
                new ParticleEffectFrostLord(player.getUniqueId());
                break;
            case ANGELWINGS:
                new ParticleEffectAngelWings(player.getUniqueId());
                break;
            case GREENSPARKS:
                new ParticleEffectGreenSparks(player.getUniqueId());
                break;
            case INLOVE:
                new ParticleEffectInLove(player.getUniqueId());
                break;
        }
    }

    public void activateGadgetByType(Gadget.GadgetType type, final Player player) {
        if (!player.hasPermission(type.getPermission())) {
            if (!playerList.contains(player)) {
                player.sendMessage(MessageManager.getMessage("No-Permission"));
                playerList.add(player);
                Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        playerList.remove(player);
                    }
                }, 1);
            }
            return;
        }
        switch (type) {
            case BATBLASTER:
                new GadgetBatBlaster(player.getUniqueId());
                break;
            case PAINTBALLGUN:
                new GadgetPaintballGun(player.getUniqueId());
                break;
            case CHICKENATOR:
                new GadgetChickenator(player.getUniqueId());
                break;
            case MELONTHROWER:
                new GadgetMelonThrower(player.getUniqueId());
                break;
            case ETHEREALPEARL:
                new GadgetEtherealPearl(player.getUniqueId());
                break;
            case DISCOBALL:
                new GadgetDiscoBall(player.getUniqueId());
                break;
            case COLORBOMB:
                new GadgetColorBomb(player.getUniqueId());
                break;
            case FLESHHOOK:
                new GadgetFleshHook(player.getUniqueId());
                break;
            case PORTALGUN:
                new GadgetPortalGun(player.getUniqueId());
                break;
            case BLIZZARDBLASTER:
                new GadgetBlizzardBlaster(player.getUniqueId());
                break;
            case THORHAMMER:
                new GadgetThorHammer(player.getUniqueId());
                break;
            case EXPLOSIVESHEEP:
                new GadgetExplosiveSheep(player.getUniqueId());
                break;
            case SMASHDOWN:
                new GadgetSmashDown(player.getUniqueId());
                break;
            case ANTIGRAVITY:
                new GadgetAntiGravity(player.getUniqueId());
                break;
            case TSUNAMI:
                new GadgetTsunami(player.getUniqueId());
                break;
        }
    }

    @EventHandler
    public void gadgetSelection(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("§lGadgets")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;
            if (event.getCurrentItem().getItemMeta().hasDisplayName()) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageManager.getMessage("Menu.Gadgets"))
                        || event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                    return;
                }
                event.getWhoClicked().closeInventory();
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageManager.getMessage("Menu.Mounts"))) {
                    openMountsMenu((Player) event.getWhoClicked());
                    return;
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageManager.getMessage("Menu.Particle-Effects"))) {
                    openParticlesMenu((Player) event.getWhoClicked());
                    return;
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageManager.getMessage("Menu.Pets"))) {
                    openPetsMenu((Player) event.getWhoClicked());
                    return;
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith(MessageManager.getMessage("Menu.Deactivate"))) {
                    Core.getCustomPlayer((Player) event.getWhoClicked()).removeGadget();
                    return;
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith(MessageManager.getMessage("Menu.Activate"))) {
                    Core.getCustomPlayer((Player) event.getWhoClicked()).removeGadget();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 1; i < event.getCurrentItem().getItemMeta().getDisplayName().split(" ").length; i++) {
                        sb.append(event.getCurrentItem().getItemMeta().getDisplayName().split(" ")[i]);
                        try {
                            if (event.getCurrentItem().getItemMeta().getDisplayName().split(" ")[i + 1] != null)
                                sb.append(" ");
                        } catch (Exception exc) {

                        }
                    }
                    activateGadgetByType(getGadgetByName(sb.toString()), (Player) event.getWhoClicked());
                }

            }
        }
    }

    @EventHandler
    public void mountsSelection(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("§lMounts")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;
            if (event.getCurrentItem().getItemMeta().hasDisplayName()) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageManager.getMessage("Menu.Mounts"))
                        || event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                    return;
                }
                event.getWhoClicked().closeInventory();
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageManager.getMessage("Menu.Gadgets"))) {
                    openGadgetsMenu((Player) event.getWhoClicked());
                    return;
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageManager.getMessage("Menu.Particle-Effects"))) {
                    openParticlesMenu((Player) event.getWhoClicked());
                    return;
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageManager.getMessage("Menu.Pets"))) {
                    openPetsMenu((Player) event.getWhoClicked());
                    return;
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith(MessageManager.getMessage("Menu.Despawn"))) {
                    Core.getCustomPlayer((Player) event.getWhoClicked()).removeMount();
                    return;
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith(MessageManager.getMessage("Menu.Spawn"))) {
                    Core.getCustomPlayer((Player) event.getWhoClicked()).removeMount();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 1; i < event.getCurrentItem().getItemMeta().getDisplayName().split(" ").length; i++) {
                        sb.append(event.getCurrentItem().getItemMeta().getDisplayName().split(" ")[i]);
                        try {
                            if (event.getCurrentItem().getItemMeta().getDisplayName().split(" ")[i + 1] != null)
                                sb.append(" ");
                        } catch (Exception exc) {

                        }
                    }
                    activateMountByType(getMountByName(sb.toString()), (Player) event.getWhoClicked());
                }

            }
        }
    }

    @EventHandler
    public void particleEffectSelection(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("§lParticle Effects")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;
            if (event.getCurrentItem().getItemMeta().hasDisplayName()) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageManager.getMessage("Menu.Particle-Effects"))
                        || event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                    return;
                }
                event.getWhoClicked().closeInventory();
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageManager.getMessage("Menu.Gadgets"))) {
                    openGadgetsMenu((Player) event.getWhoClicked());
                    return;
                } else if (event.getCurrentItem().getType() == Material.SADDLE) {
                    openMountsMenu((Player) event.getWhoClicked());
                    return;
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageManager.getMessage("Menu.Pets"))) {
                    openPetsMenu((Player) event.getWhoClicked());
                    return;
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith(MessageManager.getMessage("Menu.Unsummon"))) {
                    Core.getCustomPlayer((Player) event.getWhoClicked()).removeParticleEffect();
                    return;
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith(MessageManager.getMessage("Menu.Summon"))) {
                    Core.getCustomPlayer((Player) event.getWhoClicked()).removeParticleEffect();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 1; i < event.getCurrentItem().getItemMeta().getDisplayName().split(" ").length; i++) {
                        sb.append(event.getCurrentItem().getItemMeta().getDisplayName().split(" ")[i]);
                        try {
                            if (event.getCurrentItem().getItemMeta().getDisplayName().split(" ")[i + 1] != null)
                                sb.append(" ");
                        } catch (Exception exc) {

                        }
                    }
                    activateParticleEffectByType(getParticleEffectByName(sb.toString()), (Player) event.getWhoClicked());
                }

            }
        }
    }

    @EventHandler
    public void petSelection(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("§lPets")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;
            if (event.getCurrentItem().getItemMeta().hasDisplayName()) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageManager.getMessage("Menu.Pets"))
                        || event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                    return;
                }
                event.getWhoClicked().closeInventory();
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageManager.getMessage("Menu.Gadgets"))) {
                    openGadgetsMenu((Player) event.getWhoClicked());
                    return;
                } else if (event.getCurrentItem().getType() == Material.SADDLE) {
                    openMountsMenu((Player) event.getWhoClicked());
                    return;
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(MessageManager.getMessage("Menu.Particle-Effects"))) {
                    openParticlesMenu((Player) event.getWhoClicked());
                    return;
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith(MessageManager.getMessage("Menu.Despawn"))) {
                    Core.getCustomPlayer((Player) event.getWhoClicked()).removePet();
                    return;
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith(MessageManager.getMessage("Menu.Spawn"))) {
                    Core.getCustomPlayer((Player) event.getWhoClicked()).removePet();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 1; i < event.getCurrentItem().getItemMeta().getDisplayName().split(" ").length; i++) {
                        sb.append(event.getCurrentItem().getItemMeta().getDisplayName().split(" ")[i]);
                        try {
                            if (event.getCurrentItem().getItemMeta().getDisplayName().split(" ")[i + 1] != null)
                                sb.append(" ");
                        } catch (Exception exc) {

                        }
                    }
                    activatePetByType(getPetByName(sb.toString()), (Player) event.getWhoClicked());
                }

            }
        }
    }
}