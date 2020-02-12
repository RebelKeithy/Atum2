package com.teammetallurgy.atum.blocks.beacon.tileentity;

import com.teammetallurgy.atum.init.AtumBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class TileEntityHeartOfRa extends TileEntity {

    public TileEntityHeartOfRa() {
        super(AtumBlocks.AtumTileEntities.HEART_OF_RA);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

    @Override
    @Nonnull
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }
}