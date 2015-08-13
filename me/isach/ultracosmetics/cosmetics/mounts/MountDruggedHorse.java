package me.isach.ultracosmetics.cosmetics.mounts;

import me.isach.ultracosmetics.Core;
import me.isach.ultracosmetics.util.UtilParticles;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHorse;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

/**
 * Created by sacha on 10/08/15.
 */
public class MountDruggedHorse extends Mount {

    public MountDruggedHorse(UUID owner) {
        super(EntityType.HORSE, Material.SUGAR, (byte) 0, "DruggedHorse", "ultracosmetics.mounts.druggedhorse", owner, MountType.DRUGGEDHORSE);

        if (owner != null) {

            if (ent instanceof Horse) {
                Horse horse = (Horse) ent;

                horse.setColor(Horse.Color.CHESTNUT);
                horse.setVariant(Horse.Variant.HORSE);

                EntityHorse entityHorse = ((CraftHorse) horse).getHandle();

                entityHorse.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(1.1D);
                horse.setJumpStrength(1.3);
            }
            Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 10000000, 1));
                }
            }, 1);
        }
    }

    @Override
    void onUpdate() {
        UtilParticles.play(ent.getLocation().clone().add(0, 1, 0), Effect.FIREWORKS_SPARK, 0, 0, 0.4f, 0.2f, 0.4f, 0, 1);
        UtilParticles.play(ent.getLocation().clone().add(0, 1, 0), Effect.SPELL, 0, 0, 0.4f, 0.2f, 0.4f, 0, 5);
        for (int i = 0; i < 5; i++)
            UtilParticles.play(ent.getLocation().clone().add(0, 1, 0), Effect.POTION_SWIRL_TRANSPARENT, 2, 2, 5 / 255f, 255 / 255f, 0, 1, 0);
        UtilParticles.play(ent.getLocation().clone().add(0, 1, 0), Effect.WITCH_MAGIC, 0, 0, 0.4f, 0.2f, 0.4f, 0, 5);
    }
}
