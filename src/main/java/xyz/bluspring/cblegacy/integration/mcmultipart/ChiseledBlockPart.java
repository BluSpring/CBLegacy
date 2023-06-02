package xyz.bluspring.cblegacy.integration.mcmultipart;

import mcmultipart.api.multipart.IMultipartTile;
import mcmultipart.api.multipart.MultipartOcclusionHelper;
import mcmultipart.api.world.IMultipartWorld;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import xyz.bluspring.cblegacy.api.BoxType;
import xyz.bluspring.cblegacy.chiseledblock.TileEntityBlockChiseled;
import xyz.bluspring.cblegacy.chiseledblock.data.VoxelBlob;

import java.util.Collection;

public class ChiseledBlockPart extends TileEntityBlockChiseled implements IMultipartTile
{

	public ChiseledBlockPart()
	{
		// required for loading.
	}

	@Override
	public void setPartWorld(
			World world )
	{
		if ( world instanceof IMultipartWorld )
		{
			getTileEntity().setWorldObj( ( (IMultipartWorld) world ).getActualWorld() );
			return;
		}

		getTileEntity().setWorldObj( world );
	}

	@Override
	public boolean isBlobOccluded(
			final VoxelBlob blob )
	{
		final ChiseledBlockPart part = new ChiseledBlockPart( null );
		part.setBlob( blob );

		// get new occlusion...
		final Collection<AxisAlignedBB> selfBoxes = part.getBoxes( BoxType.OCCLUSION );

		return MultipartOcclusionHelper.testContainerBoxIntersection( getWorld(), getPos(), selfBoxes );
	}

	@Override
	protected boolean supportsSwapping()
	{
		return false;
	}

	public ChiseledBlockPart(
			final TileEntity tileEntity )
	{
		if ( tileEntity != null )
		{
			copyFrom( (TileEntityBlockChiseled) tileEntity );

			// copy pos and world data.
			setWorldObj( tileEntity.getWorld() );
			setPos( tileEntity.getPos() );
		}
	}

	@Override
	public TileEntity getTileEntity()
	{
		return this;
	}

}
