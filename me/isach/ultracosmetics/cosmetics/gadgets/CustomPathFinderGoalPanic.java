package me.isach.ultracosmetics.cosmetics.gadgets;

import net.minecraft.server.v1_8_R3.*;

/**
 * Created by sacha on 25/07/15.
 */
public class CustomPathFinderGoalPanic extends PathfinderGoal {

    // ton mob
    private EntityCreature b;

    // speed
    protected double a;

    // random PosX
    private double c;

    // random PosY
    private double d;

    // random PosZ
    private double e;

    public CustomPathFinderGoalPanic(EntityCreature entitycreature, double d0) {
        this.b = entitycreature;
        this.a = d0;
        this.a(1);
    }

    public boolean a() {
        Vec3D vec3d = RandomPositionGenerator.a(this.b, 5, 4);
        this.c = vec3d.a;
        this.d = vec3d.b;
        this.e = vec3d.c;
        return true;
    }

    public void c() {
        this.b.getNavigation().a(this.c, this.d, this.e, this.a);
    }

    public boolean b() {
        // CraftBukkit start - introduce a temporary timeout hack until this is fixed properly
        if ((this.b.ticksLived - this.b.hurtTimestamp) > 100) {
            this.b.b((EntityLiving) null);
            return false;
        }
        // CraftBukkit end
        return !this.b.getNavigation().m();
    }


}
