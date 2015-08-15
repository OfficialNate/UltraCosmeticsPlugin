package me.isach.ultracosmetics.cosmetics.mounts;

import me.isach.ultracosmetics.Core;
import me.isach.ultracosmetics.config.MessageManager;
import me.isach.ultracosmetics.config.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * Created by sacha on 03/08/15.
 */
public abstract class Mount implements Listener {

    private Material material;
    private Byte data;
    private String name;

    private MountType type = MountType.DEFAULT;

    public EntityType entityType = EntityType.HORSE;

    private String permission;

    private UUID owner;

    public Entity ent;

    public Mount(EntityType entityType, Material material, Byte data, String configName, String permission, final UUID owner, final MountType type) {
        this.material = material;
        this.data = data;
        this.name = configName;
        this.permission = permission;
        this.type = type;
        this.entityType = entityType;
        if (owner != null) {
            this.owner = owner;
            if (!getPlayer().hasPermission(permission)) {
                getPlayer().sendMessage(MessageManager.getMessage("No-Permission"));
                return;
            }
            if(Core.getCustomPlayer(getPlayer()).currentMount != null)
                Core.getCustomPlayer(getPlayer()).removeMount();
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    if (Bukkit.getPlayer(owner) != null
                            && Core.getCustomPlayer(Bukkit.getPlayer(owner)).currentMount != null
                            && Core.getCustomPlayer(Bukkit.getPlayer(owner)).currentMount.getType() == type) {
                        onUpdate();
                    } else {
                        cancel();
                    }
                }
            };
            runnable.runTaskTimer(Core.getPlugin(), 0, 2);
            new MountListener(this);

            this.ent = getPlayer().getWorld().spawnEntity(getPlayer().getLocation(), getEntityType());
            ((Ageable) ent).setAdult();
            ent.setCustomNameVisible(true);
            ent.setCustomName(getName());
            ent.setPassenger(getPlayer());
            if (ent instanceof Horse) {
                ((Horse) ent).setDomestication(1);
                ((Horse) ent).getInventory().setSaddle(new ItemStack(Material.SADDLE));
            }

            getPlayer().sendMessage(MessageManager.getMessage("Mounts.Spawn").replaceAll("%mountname%", getMenuName()));
            Core.getCustomPlayer(getPlayer()).currentMount = this;
        }
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public String getName() {
        return MessageManager.getMessage("Mounts." + name + ".entity-displayname").replaceAll("%playername%", getPlayer().getName());
    }

    public String getMenuName() {
        return MessageManager.getMessage("Mounts." + name + ".menu-name");
    }

    public String getConfigName() { return name; }

    public Material getMaterial() {
        return this.material;
    }


    public MountType getType() {
        return this.type;
    }

    public Byte getData() {
        return this.data;
    }

    abstract void onUpdate();

    public void clear() {
        getPlayer().sendMessage(MessageManager.getMessage("Mounts.Despawn").replaceAll("%mountname%", getMenuName()));
        Core.getCustomPlayer(getPlayer()).currentMount = null;
        try {
            ent.getPassenger().eject();
        } catch (Exception exc) {
        }
        ent.remove();
        getPlayer().removePotionEffect(PotionEffectType.CONFUSION);
    }

    protected UUID getOwner() {
        return owner;
    }

    protected Player getPlayer() {
        return Bukkit.getPlayer(owner);
    }

    public class MountListener implements Listener {
        private Mount mount;

        public MountListener(Mount mount) {
            this.mount = mount;
            Core.registerListener(this);
        }

        @EventHandler
        public void onPlayerToggleSneakEvent(VehicleExitEvent event) {
            if (event.getVehicle().getCustomName().equals(getName()) && event.getExited() == getPlayer()) {
                Core.getCustomPlayer(getPlayer()).removeMount();
            }
        }

        @EventHandler
        public void onEntityDamage(EntityDamageEvent event) {
            if (event.getEntity() == ent)
                event.setCancelled(true);
            if (event.getEntity() == getPlayer()
                    && Core.getCustomPlayer(getPlayer()).currentMount != null
                    && Core.getCustomPlayer(getPlayer()).currentMount.getType() == getType()) {
                event.setCancelled(true);
            }
        }

    }

    public enum MountType {

        DEFAULT("", ""),
        DRUGGEDHORSE("ultracosmetics.mounts.druggedhorse", "DruggedHorse"),
        INFERNALHORROR("ultracosmetics.mounts.infernalhorror", "InfernalHorror"),
        GLACIALSTEED("ultracosmetics.mounts.glacialsteed", "GlacialSteed"),
        WALKINGDEAD("ultracosmetics.mounts.walkingdead", "WalkingDead"),
        MOUNTOFFIRE("ultracosmetics.mounts.mountoffire", "MountOfFire"),
        MOUNTOFWATER("ultracosmetics.mounts.mountofwater", "MountOfWater"),
        ECOLOGISTHORSE("ultracosmetics.mounts.ecologisthorse", "EcologistHorse"),
        SNAKE("ultracosmetics.mounts.snake", "Snake");


        String permission;
        String configName;

        MountType(String permission, String configName) {
            this.permission = permission;
            this.configName = configName;
        }

        public String getPermission() {
            return permission;
        }

        public boolean isEnabled() {
            return SettingsManager.getConfig().get("Mounts." + configName + ".Enabled");
        }

    }

}
