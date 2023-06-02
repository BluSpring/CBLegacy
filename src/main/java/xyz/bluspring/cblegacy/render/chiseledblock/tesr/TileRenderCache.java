package xyz.bluspring.cblegacy.render.chiseledblock.tesr;

import java.util.List;

import xyz.bluspring.cblegacy.chiseledblock.EnumTESRRenderState;
import xyz.bluspring.cblegacy.chiseledblock.TileEntityBlockChiseledTESR;
import xyz.bluspring.cblegacy.core.ClientSide;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.client.MinecraftForgeClient;

public abstract class TileRenderCache
{

	private final TileLayerRenderCache solid = new TileLayerRenderCache();
	private final TileLayerRenderCache translucent = new TileLayerRenderCache();

	public TileLayerRenderCache getLayer(
			final BlockRenderLayer layer )
	{
		if ( layer == BlockRenderLayer.TRANSLUCENT )
		{
			return translucent;
		}

		return solid;
	}

	abstract public List<TileEntityBlockChiseledTESR> getTileList();

	public EnumTESRRenderState update(
			final BlockRenderLayer layer,
			final int updateCost )
	{
		final int lastRF = ClientSide.instance.getLastRenderedFrame();
		final TileLayerRenderCache tlrc = getLayer( layer );

		// render?
		if ( layer != null && tlrc.lastRenderedFrame != lastRF )
		{
			tlrc.lastRenderedFrame = lastRF;
			return EnumTESRRenderState.RENDER;
		}

		return EnumTESRRenderState.SKIP;
	}

	public boolean hasRenderedThisFrame()
	{
		final BlockRenderLayer layer = MinecraftForgeClient.getRenderPass() == 0 ? BlockRenderLayer.SOLID : BlockRenderLayer.TRANSLUCENT;
		final TileLayerRenderCache tlrc = getLayer( layer );

		final int lastRF = ClientSide.instance.getLastRenderedFrame();
		return !( layer != null && tlrc.lastRenderedFrame != lastRF );
	}

	public void rebuild(
			final boolean convert )
	{
		solid.rebuild = true;
		translucent.rebuild = true;

		if ( convert )
		{
			solid.conversion = true;
			translucent.conversion = true;
		}
	}

}