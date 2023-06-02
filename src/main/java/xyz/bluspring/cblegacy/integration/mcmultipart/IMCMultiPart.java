package xyz.bluspring.cblegacy.integration.mcmultipart;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.bluspring.cblegacy.chiseledblock.TileEntityBlockChiseled;
import xyz.bluspring.cblegacy.chiseledblock.data.VoxelBlob;

public interface IMCMultiPart
{
	void swapRenderIfPossible(
			TileEntity current,
			TileEntityBlockChiseled newTileEntity );

	void removePartIfPossible(
			TileEntity te );

	TileEntityBlockChiseled getPartIfPossible(
			World world,
			BlockPos pos,
			boolean create );

	void triggerPartChange(
			TileEntity te );

	boolean isMultiPart(
			World w,
			BlockPos pos );

	void populateBlobWithUsedSpace(
			World w,
			BlockPos pos,
			VoxelBlob blob );

	boolean rotate(
			World world,
			BlockPos pos,
			EntityPlayer player );

	TileEntityBlockChiseled getPartFromBlockAccess(
			IBlockAccess w,
			BlockPos pos );

}