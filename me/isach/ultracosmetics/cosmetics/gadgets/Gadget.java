package me.isach.ultracosmetics.cosmetics.gadgets;

import me.isach.ultracosmetics.Core;
import me.isach.ultracosmetics.config.MessageManager;
import me.isach.ultracosmetics.config.SettingsManager;
import me.isach.ultracosmetics.util.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * Created by sacha on 03/08/15.
 */
public abstract class Gadget implements Listener {

    public boolean useTwoInteractMethods;
    private Material material;
    private Byte data;
    private String configName;
    private double countdown;

    private GadgetType type;

    public boolean displayCountdownMessage = true;

    private String permission;

    private UUID owner;

    public Gadget(Material material, Byte data, String configName, String permission, final float countdown, final UUID owner, final GadgetType type) {
        this.material = material;
        this.data = data;
        this.configName = configName;
        this.permission = permission;
        this.countdown = countdown;
        this.type = type;
        this.useTwoInteractMethods = false;
        if (owner != null) {
            this.owner = owner;
            if(Core.getCustomPlayer(getPlayer()).currentGadget != null)
                Core.getCustomPlayer(getPlayer()).removeGadget();
            if (!getPlayer().hasPermission(permission)) {
                getPlayer().sendMessage(MessageManager.getMessage("No-Permission"));
                return;
            }
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    if (Bukkit.getPlayer(owner) != null
                            && Core.getCustomPlayer(Bukkit.getPlayer(owner)).currentGadget != null
                            && Core.getCustomPlayer(Bukkit.getPlayer(owner)).currentGadget.getType() == type) {
                        onUpdate();
                    } else {
                        cancel();
                    }
                }
            };
            runnable.runTaskTimer(Core.getPlugin(), 0, 1);
            Bukkit.getPluginManager().registerEvents(new GadgetListener(this), Core.getPlugin());
            if (getPlayer().getInventory().getItem((int) SettingsManager.getConfig().get("Gadget-Slot")) != null) {
                getPlayer().getWorld().dropItem(getPlayer().getLocation(), getPlayer().getInventory().getItem((int) SettingsManager.getConfig().get("Gadget-Slot")));
                getPlayer().getInventory().remove((int) SettingsManager.getConfig().get("Gadget-Slot"));
            }
            getPlayer().getInventory().setItem((int) SettingsManager.getConfig().get("Gadget-Slot"), ItemFactory.create(material, data, getName(), "§9Gadget"));
            getPlayer().sendMessage(MessageManager.getMessage("Gadgets.Equip").replaceAll("%gadgetname%", getName()));
            Core.getCustomPlayer(getPlayer()).currentGadget = this;
        }
    }

    public String getName() {
        return MessageManager.getMessage("Gadgets." + configName + ".name");
    }

    public Material getMaterial() {
        return this.material;
    }

    public GadgetType getType() {
        return this.type;
    }

    public Byte getData() {
        return this.data;
    }

    abstract void onInteractRightClick();

    abstract void onInteractLeftClick();

    abstract void onUpdate();

    public abstract void clear();

    protected UUID getOwner() {
        return owner;
    }

    protected Player getPlayer() {
        return Bukkit.getPlayer(owner);
    }

    public void removeItem() {
        getPlayer().getInventory().setItem((int) SettingsManager.getConfig().get("Gadget-Slot"), null);
    }

    public class GadgetListener implements Listener {
        private Gadget gadget;

        public GadgetListener(Gadget gadget) {
            this.gadget = gadget;
        }


        @EventHandler
        protected void onPlayerInteract(PlayerInteractEvent event) {
            Player player = event.getPlayer();
            UUID uuid = player.getUniqueId();
            if (!uuid.equals(gadget.owner)) return;
            ItemStack itemStack = player.getItemInHand();
            if (itemStack.getType() != gadget.material) return;
            if (itemStack.getData().getData() != gadget.data) return;
            if (player.getInventory().getHeldItemSlot() != (int) SettingsManager.getConfig().get("Gadget-Slot")) return;
            if (Core.getCustomPlayer(getPlayer()).currentGadget != gadget) return;
            event.setCancelled(true);
            player.updateInventory();
            if (type == GadgetType.PORTALGUN) {
                if (getPlayer().getTargetBlock((Set<Material>) null, 20).getType() == Material.AIR) {
                    getPlayer().sendMessage("§c§lNo blocks in range!");
                    return;
                }
            }
            if (type == GadgetType.DISCOBALL) {
                if(Core.discoBalls.size() > 0) {
                    getPlayer().sendMessage("§c§lThere is already a disco ball active!");
                    return;
                }
                if(getPlayer().getLocation().add(0, 4, 0).getBlock() != null && getPlayer().getLocation().add(0, 4, 0).getBlock().getType() != Material.AIR) {
                    getPlayer().sendMessage("§c§lNot enough space above you!");
                    return;
                }
            }
            if (type == GadgetType.EXPLOSIVESHEEP) {
                if(Core.explosiveSheep.size() > 0) {
                    getPlayer().sendMessage("§c§lThere is already an explosive sheep active!");
                    return;
                }
            }
            if (Core.countdownMap.get(getPlayer()) != null) {
                if (Core.countdownMap.get(getPlayer()).containsKey(getType())) {
                    String timeLeft = new DecimalFormat("0.0").format(Core.countdownMap.get(getPlayer()).get(getType()));
                    if (displayCountdownMessage)
                        getPlayer().sendMessage(MessageManager.getMessage("Gadgets.Countdown-Message").replaceAll("%gadgetname%", configName).replaceAll("%time%", timeLeft));
                    return;
                } else {
                    Core.countdownMap.get(getPlayer()).put(getType(), countdown);
                }
            } else {
                Core.countdownMap.remove(getPlayer());
                HashMap<GadgetType, Double> countdownMap = new HashMap<>();
                countdownMap.put(getType(), countdown);
                Core.countdownMap.put(getPlayer(), countdownMap);
            }
            if(useTwoInteractMethods) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR
                        || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                    onInteractRightClick();
                else if (event.getAction() == Action.LEFT_CLICK_BLOCK
                        || event.getAction() == Action.LEFT_CLICK_AIR)
                    onInteractLeftClick();
            } else {
                onInteractRightClick();
            }

        }

        @EventHandler
        protected void onItemDrop(PlayerDropItemEvent event) {
            if (event.getItemDrop().getItemStack().getType() == material
                    && event.getItemDrop().getItemStack().getData().getData() == data
                    && event.getItemDrop().getItemStack().getItemMeta().hasDisplayName()
                    && event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(configName)) {
                event.setCancelled(true);
            }
        }

        @EventHandler
        protected void onInventoryClick(InventoryClickEvent event) {
            if (event.getCurrentItem().getType() == material
                    && event.getCurrentItem().getData().getData() == data
                    && event.getCurrentItem().getItemMeta().hasDisplayName()
                    && event.getCurrentItem().getItemMeta().getDisplayName().equals(configName)) {
                event.setCancelled(true);
                    if (event.getWhoClicked().getItemOnCursor() != null && event.getWhoClicked().getItemOnCursor().getType() != Material.AIR)
                        event.getWhoClicked().getWorld().dropItem(event.getWhoClicked().getLocation(), event.getWhoClicked().getItemOnCursor());
            }
        }
    }

    public enum GadgetType {
        BATBLASTER("ultracosmetics.gadgets.batblaster", "BatBlaster"),
        CHICKENATOR("ultracosmetics.gadgets.chickenator", "Chickenator"),
        COLORBOMB("ultracosmetics.gadgets.colorbomb", "ColorBomb"),
        DISCOBALL("ultracosmetics.gadgets.discoball", "DiscoBall"),
        ETHEREALPEARL("ultracosmetics.gadgets.etherealpearl", "EtherealPearl"),
        FLESHHOOK("ultracosmetics.gadgets.fleshhook", "FleshHook"),
        MELONTHROWER("ultracosmetics.gadgets.melonthrower", "MelonThrower"),
        BLIZZARDBLASTER("ultracosmetics.gadgets.blizzardblaster", "BlizzardBlaster"),
        PORTALGUN("ultracosmetics.gadgets.portalgun", "PortalGun"),
        EXPLOSIVESHEEP("ultracosmetics.gadgets.explosivesheep", "ExplosiveSheep"),
        PAINTBALLGUN("ultracosmetics.gadgets.paintballgun", "PaintballGun"),
        THORHAMMER("ultracosmetics.gadgets.thorhammer", "ThorHammer"),
        ANTIGRAVITY("ultracosmetics.gadgets.antigravity", "AntiGravity"),
        SMASHDOWN("ultracosmetics.gadgets.smashdown", "SmashDown");
        //TSUNAMI("ultracosmetics.gadgets.tsunami", "Tsunami");

        String permission;
        public String configName;

        GadgetType(String permission, String configName) {
            this.permission = permission;
            this.configName = configName;
        }

        public String getPermission() {
            return permission;
        }

        public boolean isEnabled() {
            return SettingsManager.getConfig().get("Gadgets." + configName + ".Enabled");
        }
    }

}
