package com.teammetallurgy.atum.blocks.stone.limestone;

import com.teammetallurgy.atum.entity.animal.ScarabEntity;
import com.teammetallurgy.atum.init.AtumBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockLimestone extends Block {
    public static final BooleanProperty HAS_SCARAB = BooleanProperty.create("contains_scarab");

    public BlockLimestone() {
        super(Block.Properties.create(Material.ROCK, MaterialColor.SAND).hardnessAndResistance(1.8F, 6.0F));
        this.setDefaultState(this.stateContainer.getBaseState().with(HAS_SCARAB, false));
    }

    @Override
    public void dropBlockAsItemWithChance(World world, @Nonnull BlockPos pos, @Nonnull BlockState state, float chance, int fortune) {
        if (!world.isRemote && world.getGameRules().getBoolean("doTileDrops") && state.get(HAS_SCARAB) && RANDOM.nextDouble() <= 0.90D) {
            ScarabEntity scarab = new ScarabEntity(world);
            scarab.setLocationAndAngles((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
            world.addEntity(scarab);
            scarab.spawnExplosionParticle();
        }
        super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);
    }

    @Override
    @Nonnull
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(AtumBlocks.LIMESTONE_CRACKED);
    }

    @Override
    public void getOreDictEntries() {
        OreDictHelper.add(new ItemStack(this), "stoneLimestone", "stone");
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> container) {
        container.add(HAS_SCARAB);
    }

    @Override
    public Property[] getNonRenderingProperties() {
        return new Property[]{HAS_SCARAB};
    }
}