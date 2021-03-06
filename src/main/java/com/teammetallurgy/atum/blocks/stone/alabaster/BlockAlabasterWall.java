package com.teammetallurgy.atum.blocks.stone.alabaster;

import com.google.common.collect.Maps;
import com.teammetallurgy.atum.blocks.base.BlockAtumWall;
import com.teammetallurgy.atum.utils.AtumRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import java.util.Map;

public class BlockAlabasterWall extends BlockAtumWall {
    private static final Map<BlockAlabasterBricks.Type, Block> WALLS = Maps.newEnumMap(BlockAlabasterBricks.Type.class);

    @Override
    @Nonnull
    public MapColor getMapColor(IBlockState state, IBlockAccess blockAccess, BlockPos blockPos) {
        return MapColor.QUARTZ;
    }

    public static void registerWalls() {
        for (BlockAlabasterBricks.Type type : BlockAlabasterBricks.Type.values()) {
            Block wall = new BlockAlabasterWall();
            WALLS.put(type, wall);
            AtumRegistry.registerBlock(wall, "alabaster_" + type.getName() + "_wall");
        }
    }

    public static Block getWall(BlockAlabasterBricks.Type type) {
        return WALLS.get(type);
    }
}