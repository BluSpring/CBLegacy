package xyz.bluspring.cblegacy.integration.mods;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.bluspring.cblegacy.chiseledblock.TileEntityBlockChiseled;
import xyz.bluspring.cblegacy.chiseledblock.data.VoxelBlob;
import xyz.bluspring.cblegacy.helpers.ModUtil;
import xyz.bluspring.cblegacy.interfaces.IChiseledTileContainer;

public class LittleTilesOccusionState implements IChiseledTileContainer
{

	final TileEntityBlockChiseled container;
	final World world;
	final BlockPos pos;

	public LittleTilesOccusionState(
			final World w,
			final BlockPos position,
			final TileEntityBlockChiseled chisledBlockPart )
	{
		world = w;
		pos = position;
		container = chisledBlockPart;
	}

	@Override
	public void sendUpdate()
	{
		ModUtil.sendUpdate( world, pos );
	}

	@Override
	public void saveData()
	{
		world.setBlockState( pos, container.getPreferedBlock() );
		TileEntity te = world.getTileEntity( pos );

		if ( te instanceof TileEntityBlockChiseled )
			( (TileEntityBlockChiseled) te ).copyFrom( container );
	}

	@Override
	public boolean isBlobOccluded(
			final VoxelBlob blob )
	{
		return false;
	}

}
