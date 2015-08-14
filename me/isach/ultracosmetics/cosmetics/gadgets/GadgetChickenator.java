package me.isach.ultracosmetics.cosmetics.gadgets;

import me.isach.ultracosmetics.Core;
import me.isach.ultracosmetics.config.MessageManager;
import me.isach.ultracosmetics.util.ItemFactory;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Created by sacha on 03/08/15.
 */
public class GadgetChickenator extends Gadget {

    static Random r = new Random();
    ArrayList<Item> items = new ArrayList<>();

    public GadgetChickenator(UUID owner) {
        super(Material.COOKED_CHICKEN, (byte) 0x0, "Chickenator", "ultracosmetics.gadgets.chickenator", 2, owner, GadgetType.CHICKENATOR);
    }

    @Override
    void onInteractRightClick() {
        final Chicken CHICKEN = (Chicken) getPlayer().getWorld().spawnEntity(getPlayer().getEyeLocation(), EntityType.CHICKEN);
        CHICKEN.setNoDamageTicks(500);
        CHICKEN.setVelocity(getPlayer().getLocation().getDirection().multiply(Math.PI / 1.5));
        getPlayer().playSound(getPlayer().getLocation(), Sound.CHICKEN_IDLE, 1.4f, 1.5f);
        getPlayer().playSound(getPlayer().getLocation(), Sound.EXPLODE, 0.3f, 1.5f);
        Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
            @Override
            public void run() {
                spawnRandomFirework(CHICKEN.getLocation());
                getPlayer().playSound(getPlayer().getLocation(), Sound.CHICKEN_HURT, 1.4f, 1.5f);
                CHICKEN.remove();
                for (int i = 0; i < 30; i++) {
                    final Item ITEM = CHICKEN.getWorld().dropItem(CHICKEN.getLocation(), ItemFactory.create(Material.COOKED_CHICKEN, (byte) 0, UUID.randomUUID().toString()));
                    ITEM.setPickupDelay(30000);
                    ITEM.setVelocity(new Vector(r.nextDouble() - 0.5, r.nextDouble() / 2.0, r.nextDouble() - 0.5));
                    items.add(ITEM);
                }
                Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        for (Item i : items)
                            i.remove();
                    }
                }, 50);
            }
        }, 9);
        getPlayer().updateInventory();
    }

    @Override
    void onUpdate() {
    }

    @Override
    public void clear() {
        for(Item i : items)
            i.remove();
    }

    public static FireworkEffect getRandomFireworkEffect() {
        FireworkEffect.Builder builder = FireworkEffect.builder();
        FireworkEffect effect = builder.flicker(false).trail(false).with(FireworkEffect.Type.BALL_LARGE).withColor(Color.fromRGB(r.nextInt(255), r.nextInt(255), r.nextInt(255))).withFade(Color.fromRGB(r.nextInt(255), r.nextInt(255), r.nextInt(255))).build();
        return effect;
    }

    public void spawnRandomFirework(Location location) {
        final ArrayList<Firework> fireworks = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            final Firework f = getPlayer().getWorld().spawn(location, Firework.class);

            FireworkMeta fm = f.getFireworkMeta();
            fm.addEffect(getRandomFireworkEffect());
            f.setFireworkMeta(fm);
            fireworks.add(f);
        }
        Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
            @Override
            public void run() {
                for (Firework f : fireworks)
                    f.detonate();
            }
        }, 2);
    }
    @Override
    void onInteractLeftClick() { }
}
