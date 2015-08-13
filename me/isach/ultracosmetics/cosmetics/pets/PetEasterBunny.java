package me.isach.ultracosmetics.cosmetics.pets;

import me.isach.ultracosmetics.Core;
import me.isach.ultracosmetics.util.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Created by sacha on 12/08/15.
 */
public class PetEasterBunny extends Pet {

    ArrayList<Byte> eggDatas = new ArrayList<>();
    Random r = new Random();

    public PetEasterBunny(UUID owner) {
        super(EntityType.RABBIT, Material.CARROT_ITEM, (byte)0x0, "EasterBunny", "ultracosmetics.pets.easterbunny", owner, PetType.EASTERBUNNY);
        if(owner != null) {
            eggDatas.add((byte) 0x32);
            eggDatas.add((byte) 0x3d);
            eggDatas.add((byte) 0x5e);
            eggDatas.add((byte) 0x36);
            eggDatas.add((byte) 0x3a);
            eggDatas.add((byte) 0x38);
            eggDatas.add((byte) 0x62);
        }
    }

    @Override
    void onUpdate() {
        final Item ITEM = ent.getWorld().dropItem(((Rabbit)ent).getEyeLocation(), ItemFactory.create(Material.MONSTER_EGG, eggDatas.get(r.nextInt(6)), UUID.randomUUID().toString()));
        ITEM.setPickupDelay(30000);
        ITEM.setVelocity(new Vector(r.nextDouble() - 0.5, r.nextDouble() / 2.0 + 0.3, r.nextDouble() - 0.5).multiply(0.4));
        items.add(ITEM);
        Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
            @Override
            public void run() {
                ITEM.remove();
                items.remove(ITEM);
            }
        }, 5);
    }
}
