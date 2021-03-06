package com.teammetallurgy.atum.blocks.beacon;

import com.google.common.collect.Maps;
import com.teammetallurgy.atum.blocks.beacon.tileentity.TileEntityRadiantBeacon;
import com.teammetallurgy.atum.blocks.glass.BlockAtumStainedGlass;
import com.teammetallurgy.atum.init.AtumBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

public class BlockRadiantBeacon extends BlockBeacon {
    public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);
    private static final HashMap<Integer, EnumDyeColor> RGB_TO_DYE = Maps.newHashMap();

    public BlockRadiantBeacon() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));
    }

    @Override
    @Nullable
    public TileEntity createNewTileEntity(@Nonnull World world, int meta) {
        return new TileEntityRadiantBeacon();
    }

    @Override
    @Nonnull
    public Material getMaterial(IBlockState state) {
        return Material.GLASS;
    }

    @Override
    @Nonnull
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
        return MapColor.getBlockColor(state.getValue(COLOR));
    }

    @Override
    @SideOnly(Side.CLIENT)
    @Nonnull
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }

    @Override
    public boolean onBlockActivated(World world, @Nonnull BlockPos pos, IBlockState state, @Nonnull EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldStack = player.getHeldItem(hand);
        if (heldStack.isEmpty()) {
            return false;
        } else {
            Item item = heldStack.getItem();
            if (!world.isRemote) {
                if (item == Item.getItemFromBlock(Blocks.STAINED_GLASS) || item == Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE)) {
                    world.setBlockState(pos, AtumBlocks.RADIANT_BEACON.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(heldStack.getMetadata())), 2);
                    if (!player.isCreative()) {
                        heldStack.shrink(1);
                    }
                } else {
                    float[] color = Block.getBlockFromItem(item).getBeaconColorMultiplier(state, world, pos, pos);
                    if (color != null) {
                        int r = (int) (color[0] * 255F);
                        int g = (int) (color[1] * 255F);
                        int b = (int) (color[2] * 255F);
                        int rgb = ((r & 0x0FF) << 16) | ((g & 0x0FF) << 8) | (b & 0x0FF);
                        EnumDyeColor dyeColor = RGB_TO_DYE.get(rgb);

                        Block block = AtumBlocks.RADIANT_BEACON;
                        if (Block.getBlockFromItem(item) == BlockAtumStainedGlass.getGlass(AtumBlocks.FRAMED_GLASS, dyeColor) || Block.getBlockFromItem(item) == BlockAtumStainedGlass.getGlass(AtumBlocks.THIN_FRAMED_GLASS, dyeColor)) {
                            block = AtumBlocks.RADIANT_BEACON_FRAMED;
                        }

                        world.setBlockState(pos, block.getDefaultState().withProperty(COLOR, dyeColor));
                        if (!player.isCreative()) {
                            heldStack.shrink(1);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(COLOR).getMetadata();
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, COLOR);
    }

    static {
        for (EnumDyeColor color : EnumDyeColor.values()) {
            RGB_TO_DYE.put(color.getColorValue(), color);
        }
    }
}