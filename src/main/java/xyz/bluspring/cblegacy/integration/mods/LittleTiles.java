package xyz.bluspring.cblegacy.integration.mods;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import xyz.bluspring.cblegacy.chiseledblock.BlockChiseled;
import xyz.bluspring.cblegacy.chiseledblock.TileEntityBlockChiseled;
import xyz.bluspring.cblegacy.chiseledblock.data.VoxelBlob;
import xyz.bluspring.cblegacy.chiseledblock.data.VoxelBlobStateReference;
import xyz.bluspring.cblegacy.core.Log;
import xyz.bluspring.cblegacy.integration.ChiselsAndBitsIntegration;
import xyz.bluspring.cblegacy.integration.IntegrationBase;

import java.lang.reflect.Method;

@ChiselsAndBitsIntegration( "littletilescore" )
public class LittleTiles extends IntegrationBase
{

	static Class<?> clz = null;
	static Method getVoxelBlob = null;

	@Override
	public void init()
	{
		try
		{
			clz = Class.forName( "com.creativemd.littletiles.common.api.te.ILittleTileTE" );
			getVoxelBlob = clz.getMethod( "getVoxelBlob", boolean.class );
		}
		catch ( NoSuchMethodException | SecurityException | ClassNotFoundException e )
		{
			Log.info( "Little Tiles version is not new enough, or missing API : " + e.getMessage() );
		}
	}

	public static BlockPos forceConvert = BlockPos.ORIGIN;

	public static TileEntityBlockChiseled getConvertedTE(
			TileEntity te,
			boolean force ) throws Exception
	{
		if ( clz != null && getVoxelBlob != null && clz.isInstance( te ) )
		{
			VoxelBlob blob = (VoxelBlob) getVoxelBlob.invoke( te, force );
			if ( blob != null )
			{
				TileEntityBlockChiseled cte = new TileEntityBlockChiseled();

				cte.setState( cte.getBasicState()
						.withProperty( BlockChiseled.UProperty_VoxelBlob, new VoxelBlobStateReference( blob, TileEntityBlockChiseled.getPositionRandom( te.getPos() ) ) ) );

				cte.setWorldObj( te.getWorld() );
				cte.setPos( te.getPos() );
				cte.occlusionState = new LittleTilesOccusionState( te.getWorld(), te.getPos(), cte );
				return cte;
			}
		}

		return null;
	}

	public static boolean isLittleTilesBlock(
			TileEntity tileEntity )
	{
		try
		{
			if ( clz != null && getVoxelBlob != null && clz.isInstance( tileEntity ) )
			{
				VoxelBlob blob = (VoxelBlob) getVoxelBlob.invoke( tileEntity, false );
				if ( blob != null )
				{
					return true;
				}
			}
		}
		catch ( Exception e )
		{
			// false!
		}

		return false;
	}
}
