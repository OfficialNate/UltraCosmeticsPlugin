package me.isach.ultracosmetics;

import me.isach.ultracosmetics.config.MessageManager;
import me.isach.ultracosmetics.cosmetics.gadgets.Gadget;
import me.isach.ultracosmetics.cosmetics.mounts.Mount;
import me.isach.ultracosmetics.cosmetics.particleeffects.ParticleEffect;
import me.isach.ultracosmetics.cosmetics.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

/**
 * Created by sacha on 03/08/15.
 */
public class CustomPlayer {

    public UUID uuid;
    public Gadget currentGadget = null;
    public Mount currentMount;
    public ParticleEffect currentParticleEffect;
    public Pet currentPet;
    public MenuCategory currentMenu = MenuCategory.GADGETS;

    public enum MenuCategory {
        GADGETS,
        PARTICLEEFFECTS,
        MOUNTS, PETS;
    }

    public CustomPlayer(UUID uuid) {
        this.uuid = uuid;
        Core.countdownMap.put(getPlayer(), null);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }


    public void removeGadget() {
        if (currentGadget != null) {
            getPlayer().sendMessage(MessageManager.getMessage("Gadgets.Unequip").replaceAll("%gadgetname%", currentGadget.getName()));
            currentGadget.clear();
            currentGadget.removeItem();
            currentGadget = null;
        }
    }

    public void removeMount() {
        if(currentMount != null) {
            currentMount.ent.remove();
            currentMount.clear();
            currentMount = null;
            getPlayer().removePotionEffect(PotionEffectType.CONFUSION);
        }
    }

    public void removePet() {
        if(currentPet != null) {
            currentPet.ent.remove();
            currentPet.clear();
            currentPet = null;
        }
    }

    public void removeParticleEffect() {
        if(currentParticleEffect != null) {
            getPlayer().sendMessage(MessageManager.getMessage("Particle-Effects.Unsummon").replaceAll("%effectname%", currentParticleEffect.getName()));
            currentParticleEffect = null;
        }
    }


    public UUID getUuid() {
        return uuid;
    }

}
