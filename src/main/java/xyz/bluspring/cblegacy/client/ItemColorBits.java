package xyz.bluspring.cblegacy.client;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import xyz.bluspring.cblegacy.chiseledblock.BlockBitInfo;
import xyz.bluspring.cblegacy.core.ClientSide;
import xyz.bluspring.cblegacy.helpers.ModUtil;
import xyz.bluspring.cblegacy.items.ItemChiseledBit;
import xyz.bluspring.cblegacy.render.helpers.ModelUtil;

public class ItemColorBits implements IItemColor
{

	@Override
	public int getColorFromItemstack(
			final ItemStack stack,
			final int tint )
	{
		if ( ClientSide.instance.holdingShift() )
		{
			final IBlockState state = ModUtil.getStateById( tint >> BlockColorChisled.TINT_BITS );
			final Block blk = state.getBlock();
			final Item i = Item.getItemFromBlock( blk );
			int tintValue = tint & BlockColorChisled.TINT_MASK;

			if ( i != null )
			{
				return ModelUtil.getItemStackColor( new ItemStack( i, 1, blk.getMetaFromState( state ) ), tintValue );
			}

			return 0xffffff;
		}

		final IBlockState state = ModUtil.getStateById( ItemChiseledBit.getStackState( stack ) );
		return state == null ? 0xffffffff : BlockBitInfo.getColorFor( state, tint );
	}

}
