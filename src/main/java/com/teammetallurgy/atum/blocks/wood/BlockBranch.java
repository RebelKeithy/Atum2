package com.teammetallurgy.atum.blocks.wood;

import com.teammetallurgy.atum.init.AtumItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BlockBranch extends Block {
    public static final EnumProperty<Direction> FACING = EnumProperty.create("facing", Direction.class);
    private static final BooleanProperty NORTH = BooleanProperty.create("north");
    private static final BooleanProperty EAST = BooleanProperty.create("east");
    private static final BooleanProperty SOUTH = BooleanProperty.create("south");
    private static final BooleanProperty WEST = BooleanProperty.create("west");
    private static final BooleanProperty UP = BooleanProperty.create("up");
    private static final BooleanProperty DOWN = BooleanProperty.create("down");

    private static final Map<Direction, AxisAlignedBB> bounds;
    private static final Map<Direction, AxisAlignedBB> connectedBounds;

    private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(5 / 16D, 5 / 16D, 5 / 16D, 1.0D, 11 / 16D, 11 / 16D);
    private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 5 / 16D, 5 / 16D, 11 / 16D, 11 / 16D, 11 / 16D);
    private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(5 / 16D, 5 / 16D, 0.0D, 11 / 16D, 11 / 16D, 11 / 16D);
    private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(5 / 16D, 5 / 16D, 5 / 16D, 11 / 16D, 11 / 16D, 1.0D);
    private static final AxisAlignedBB UP_AABB = new AxisAlignedBB(5 / 16D, 5 / 16D, 5 / 16D, 11 / 16D, 1.0D, 11 / 16D);
    private static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(5 / 16D, 0.0D, 5 / 16D, 11 / 16D, 11 / 16D, 11 / 16D);

    static {
        bounds = new HashMap<>();
        connectedBounds = new HashMap<>();
        bounds.put(Direction.EAST, EAST_AABB);
        bounds.put(Direction.WEST, WEST_AABB);
        bounds.put(Direction.NORTH, NORTH_AABB);
        bounds.put(Direction.SOUTH, SOUTH_AABB);
        bounds.put(Direction.UP, UP_AABB);
        bounds.put(Direction.DOWN, DOWN_AABB);

        for (Direction facing : Direction.VALUES) {
            AxisAlignedBB box = bounds.get(facing);
            AxisAlignedBB expandedBox = new AxisAlignedBB(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
            expandedBox.expand(5 * facing.getXOffset(), 5 * facing.getYOffset(), 5 * facing.getZOffset());
            connectedBounds.put(facing, expandedBox);
        }
    }

    public BlockBranch() {
        super(Material.WOOD);
        this.setHardness(0.8F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.WOOD);
        this.setHarvestLevel("axe", 0);
        this.setLightOpacity(1);
    }

    @Override
    public void tick(BlockState state, World world, BlockPos pos, Random rand) {
        if (!this.canSurviveAt(world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!this.canSurviveAt(world, pos)) {
            world.getPendingBlockTicks().scheduleTick(pos, this, 1);
        }
    }

    private boolean canSurviveAt(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Direction facing = state.get(FACING);
        BlockState neighbor = world.getBlockState(pos.add(facing.getDirectionVec()));

        return neighbor.getMaterial() == Material.WOOD;
    }

    @Override
    @Nonnull
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return AtumItems.DEADWOOD_STICK;
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextDouble() <= 0.15F ? 1 : 0;
    }

    @Override
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean shouldSideBeRendered(BlockState state, @Nonnull IBlockReader blockAccess, @Nonnull BlockPos pos, Direction side) {
        return true;
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(BlockState state, IBlockReader source, BlockPos pos) {
        Direction facing = state.get(FACING);

        BlockState neighbor = source.getBlockState(pos.add(facing.getDirectionVec()));
        if (neighbor.getBlock() == this) {
            AxisAlignedBB box = connectedBounds.get(facing);
            AxisAlignedBB expandedBox = new AxisAlignedBB(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
            return expandedBox.expand(5 / 16D * facing.getXOffset(), 5 / 16D * facing.getYOffset(), 5 / 16D * facing.getZOffset());
        } else {
            return bounds.get(facing);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getFace().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> container) {
        container.add(FACING, NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    @Override
    @Nonnull
    public BlockState getActualState(@Nonnull BlockState state, IBlockReader world, BlockPos pos) {
        Direction Direction = state.get(FACING);
        return state.with(NORTH, Direction != Direction.NORTH && shouldConnect(Direction.NORTH, world, pos))
                .with(EAST, Direction != Direction.EAST && shouldConnect(Direction.EAST, world, pos))
                .with(SOUTH, Direction != Direction.SOUTH && shouldConnect(Direction.SOUTH, world, pos))
                .with(WEST, Direction != Direction.WEST && shouldConnect(Direction.WEST, world, pos))
                .with(UP, Direction != Direction.UP && shouldConnect(Direction.UP, world, pos))
                .with(DOWN, Direction != Direction.DOWN && shouldConnect(Direction.DOWN, world, pos));
    }

    private boolean shouldConnect(Direction direction, IBlockReader world, BlockPos pos) {
        BlockState neighborState = world.getBlockState(pos.add(direction.getDirectionVec()));
        if (neighborState.getBlock() == this) {
            return neighborState.get(FACING) == direction.getOpposite();
        }
        return false;
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation rotation) {
        switch (rotation) {
            case CLOCKWISE_180:
                return state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH)).with(WEST, state.get(EAST));
            case COUNTERCLOCKWISE_90:
                return state.with(NORTH, state.get(EAST)).with(EAST, state.get(SOUTH)).with(SOUTH, state.get(WEST)).with(WEST, state.get(NORTH));
            case CLOCKWISE_90:
                return state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST)).with(WEST, state.get(SOUTH));
            default:
                return state;
        }
    }

    @Override
    @Nonnull
    public BlockState mirror(@Nonnull BlockState state, Mirror mirror) {
        switch (mirror) {
            case LEFT_RIGHT:
                return state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
            case FRONT_BACK:
                return state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
            default:
                return super.mirror(state, mirror);
        }
    }
}