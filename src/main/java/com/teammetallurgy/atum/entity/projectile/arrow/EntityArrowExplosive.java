package com.teammetallurgy.atum.entity.projectile.arrow;

import com.teammetallurgy.atum.utils.Constants;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityArrowExplosive extends CustomArrow {
    private float velocity;

    public EntityArrowExplosive(World world) {
        super(world);
    }

    public EntityArrowExplosive(World world, EntityLivingBase shooter, float velocity) {
        super(world, shooter);
        this.velocity = velocity;
    }

    @Override
    public void onUpdate() {
        if (ticksInAir > 0 && velocity == 1.0F && !inGround && world.getTotalWorldTime() % 2L == 0L) {
            world.playSound(null, getPosition(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
        super.onUpdate();
    }

    @Override
    protected void onHit(RayTraceResult rayTraceResult) {
        super.onHit(rayTraceResult);

        if (velocity == 1.0F) {
            if (!world.isRemote) {
                world.createExplosion(this, posX, posY, posZ, 2.0F, false);
            }
            this.setDead();
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(Constants.MOD_ID, "textures/arrow/arrow_explosive.png");
    }
}