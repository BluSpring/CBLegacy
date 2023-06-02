package xyz.bluspring.cblegacy.render.chiseledblock;

import net.minecraft.util.BlockRenderLayer;
import xyz.bluspring.cblegacy.chiseledblock.data.VoxelBlob;
import xyz.bluspring.cblegacy.chiseledblock.data.VoxelType;
import xyz.bluspring.cblegacy.client.culling.ICullTest;
import xyz.bluspring.cblegacy.client.culling.MCCullTest;

import java.security.InvalidParameterException;

public enum ChiselLayer
{
	SOLID( BlockRenderLayer.SOLID, VoxelType.SOLID ),
	SOLID_FLUID( BlockRenderLayer.SOLID, VoxelType.FLUID ),
	CUTOUT( BlockRenderLayer.CUTOUT, null ),
	CUTOUT_MIPPED( BlockRenderLayer.CUTOUT_MIPPED, null ),
	TRANSLUCENT( BlockRenderLayer.TRANSLUCENT, null );

	public final BlockRenderLayer layer;
	public final VoxelType type;

	private ChiselLayer(
			final BlockRenderLayer layer,
			final VoxelType type )
	{
		this.layer = layer;
		this.type = type;
	}

	public boolean filter(
			final VoxelBlob vb )
	{
		if ( vb == null )
		{
			return false;
		}

		if ( vb.filter( layer ) )
		{
			if ( type != null )
			{
				return vb.filterFluids( type == VoxelType.FLUID );
			}

			return true;
		}
		return false;
	}

	public static ChiselLayer fromLayer(
			final BlockRenderLayer layerInfo,
			final boolean isFluid )
	{
		switch ( layerInfo )
		{
			case CUTOUT:
				return CUTOUT;
			case CUTOUT_MIPPED:
				return CUTOUT_MIPPED;
			case SOLID:
				return isFluid ? SOLID_FLUID : SOLID;
			case TRANSLUCENT:
				return TRANSLUCENT;
		}

		throw new InvalidParameterException();
	}

	public ICullTest getTest()
	{
		return new MCCullTest();
	}

}
