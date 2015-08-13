package me.isach.ultracosmetics.cosmetics.particleeffects;

import me.isach.ultracosmetics.util.MathUtils;
import me.isach.ultracosmetics.util.UtilParticles;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.util.UUID;

/**
 * Created by sacha on 12/08/15.
 */
public class ParticleEffectAngelWings extends ParticleEffect {

    public ParticleEffectAngelWings(UUID owner) {
        super(Effect.FLAME, Material.FEATHER, (byte) 0x0, "AngelWings", "ultracosmetics.particleeffects.angelwings", owner, ParticleEffectType.ANGELWINGS, 1);
        if(owner != null) {

        }
    }

    @Override
    void onUpdate() {

    }

}
