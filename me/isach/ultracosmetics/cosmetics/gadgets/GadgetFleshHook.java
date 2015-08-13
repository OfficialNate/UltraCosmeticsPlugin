package me.isach.ultracosmetics.cosmetics.gadgets;

import me.isach.ultracosmetics.Core;
import me.isach.ultracosmetics.config.MessageManager;
import me.isach.ultracosmetics.util.ItemFactory;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by sacha on 03/08/15.
 */
public class GadgetFleshHook extends Gadget implements Listener {

    private ArrayList<Item> items = new ArrayList<>();

    public GadgetFleshHook(UUID owner) {
        super(Material.TRIPWIRE_HOOK, (byte) 0x0, MessageManager.getMessage("Gadgets.FleshHook.name"), "ultracosmetics.gadgets.fleshhook", 2, owner, GadgetType.FLESHHOOK);
        Core.registerListener(this);
    }

    @org.bukkit.event.EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        if (items.contains(event.getItem())) {
            event.setCancelled(true);
            if (event.getPlayer().getName().equals(getPlayer().getName())) {
                return;
            }
            items.remove(event.getItem());
            event.getItem().remove();
            final Player HIT = event.getPlayer();
            HIT.playEffect(EntityEffect.HURT);
            Player hitter = getPlayer();
            double dX = HIT.getLocation().getX() - hitter.getLocation().getX();
            double dY = HIT.getLocation().getY() - hitter.getLocation().getY();
            double dZ = HIT.getLocation().getZ() - hitter.getLocation().getZ();
            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
            double X = Math.sin(pitch) * Math.cos(yaw);
            double Y = Math.sin(pitch) * Math.sin(yaw);
            double Z = Math.cos(pitch);

            Vector vector = new Vector(X, Z, Y);
            HIT.setVelocity(vector.multiply(2.5D).add(new Vector(0D, 1.45D, 0D)));
            Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    Core.noFallDamageEntities.add(HIT);
                }
            }, 2);
        }
    }

    @Override
    void onInteractRightClick() {
        Item hook = getPlayer().getWorld().dropItem(getPlayer().getEyeLocation(), ItemFactory.create(Material.TRIPWIRE_HOOK, (byte) 0x0, UUID.randomUUID().toString()));
        hook.setPickupDelay(0);
        hook.setVelocity(getPlayer().getEyeLocation().getDirection().multiply(1.5));
        items.add(hook);
    }

    @Override
    void onInteractLeftClick() { }

    @Override
    void onUpdate() {
        for(Item i : items) {
            i.getWorld().spigot().playEffect(i.getLocation(), Effect.CRIT, 0, 0, 0, 0, 0, 0, 1, 32);
            i.getWorld().playSound(i.getLocation(), Sound.CLICK, .4f, 1);
            if (i.isOnGround()) {
                items.remove(i);
                i.remove();
            }
        }
    }

    @Override
    public void clear() {

    }

}
