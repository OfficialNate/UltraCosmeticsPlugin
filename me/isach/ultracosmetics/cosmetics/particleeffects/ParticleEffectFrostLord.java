package me.isach.ultracosmetics.cosmetics.particleeffects;

import me.isach.ultracosmetics.util.UtilParticles;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.util.Vector;

import java.util.UUID;

/**
 * Created by sacha on 12/08/15.
 */
public class ParticleEffectFrostLord extends ParticleEffect {

    int step = 0;
    float stepY = 0;
    float radius = 1.5f;


    public ParticleEffectFrostLord(UUID owner) {
        super(Effect.SNOW_SHOVEL, Material.PACKED_ICE, (byte) 0x0, "FrostLord", "ultracosmetics.particleeffects.frostlord", owner, ParticleEffectType.FROSTLORD, 2);
        if (owner != null) {

        }
    }

    @Override
    void onUpdate() {
        for (int i = 0; i < 6; i++) {
            Location location = getPlayer().getLocation();
            double inc = (2 * Math.PI) / 100;
            double angle = step * inc + stepY + i;
            Vector v = new Vector();
            v.setX(Math.cos(angle) * radius);
            v.setZ(Math.sin(angle) * radius);
            UtilParticles.play(location.add(v).add(0, stepY, 0), Effect.SNOW_SHOVEL);
            location.subtract(v).subtract(0, stepY, 0);
            if (stepY < 3) {
                radius -= 0.022;
                stepY += 0.045;
            } else {
                stepY = 0;
                step = 0;
                radius = 1.5f;
                location.getWorld().playSound(location, Sound.DIG_SNOW, .5f, 1.5f);
                UtilParticles.play(location.clone().add(0, 3, 0), Effect.SNOW_SHOVEL, 0, 0, 0, 0, 0, 0.3f, 40);
            }
        }
    }

}
