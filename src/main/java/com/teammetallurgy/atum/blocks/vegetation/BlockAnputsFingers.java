package com.teammetallurgy.atum.blocks.vegetation;

import com.teammetallurgy.atum.init.AtumBlocks;
import com.teammetallurgy.atum.init.AtumItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class BlockAnputsFingers extends CropsBlock {
    private static final IntegerProperty ANPUTS_FINGERS_AGE = IntegerProperty.create("age", 0, 3);
    private static final AxisAlignedBB[] AABB = new AxisAlignedBB[]{new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.6875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D)};
    private HashMap<UUID, Integer> lastTouchedTick = new HashMap<>();

    public BlockAnputsFingers() {
        super();
    }

    @Override
    @Nonnull
    public MaterialColor getMapColor(BlockState state, IBlockReader world, BlockPos pos) {
        return MaterialColor.GRAY;
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockReader source, BlockPos pos) {
        return AABB[this.getAge(state)];
    }

    @Override
    @Nonnull
    protected Item getSeed() {
        return AtumItems.ANPUTS_FINGERS_SPORES;
    }

    @Override
    @Nonnull
    protected Item getCrop() {
        return AtumItems.ANPUTS_FINGERS_SPORES;
    }

    @Override
    @Nonnull
    protected IntegerProperty getAgeProperty() {
        return ANPUTS_FINGERS_AGE;
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    protected boolean canSustainBush(BlockState state) {
        return state.getBlock() == AtumBlocks.SAND;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return super.canPlaceBlockAt(world, pos) && this.canBlockStay(world, pos, this.getDefaultState());
    }

    @Override
    public void tick(@Nonnull BlockState state, World world, @Nonnull BlockPos pos, @Nonnull Random rand) {
        int age = this.getAge(state);
        if (age < this.getMaxAge() && ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt(8) == 0)) {
            BlockState newState = state.with(this.getAgeProperty(), age + 1);
            world.setBlockState(pos, newState, 2);
            ForgeHooks.onCropsGrowPost(world, pos, state);
        }
        this.checkAndDropBlock(world, pos, state);
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isRemote && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            MinecraftServer server = world.getInstanceServer();
            Integer lastTouched = this.lastTouchedTick.get(player.getUniqueID());
            if (server != null) {
                if (lastTouched != null && server.getTickCounter() - lastTouched < 35) return;
                if (player.getFoodStats().getFoodLevel() > 0) {
                    player.getFoodStats().addStats(-1, -0.1F);
                    this.lastTouchedTick.put(player.getUniqueID(), server.getTickCounter());
                }
            }
        }
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, BlockState state) {
        BlockState stateDown = world.getBlockState(pos.down());
        return world.getLightFor(EnumSkyBlock.SKY, pos) < 15 && stateDown.getBlock().canSustainPlant(stateDown, world, pos.down(), Direction.UP, this);
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, @Nonnull BlockState state, boolean isClient) {
        return false;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> container) {
        container.add(ANPUTS_FINGERS_AGE);
    }
}