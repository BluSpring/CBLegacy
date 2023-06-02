package xyz.bluspring.cblegacy.core.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import xyz.bluspring.cblegacy.api.IBitBrush;
import xyz.bluspring.cblegacy.helpers.ModUtil;
import xyz.bluspring.cblegacy.items.ItemChiseledBit;

import javax.annotation.Nullable;

public class BitBrush implements IBitBrush
{

	protected final int stateID;

	public BitBrush(
			final int blockStateID )
	{
		stateID = blockStateID;
	}

	@Override
	public ItemStack getItemStack(
			final int count )
	{
		if ( stateID == 0 )
		{
			return ModUtil.getEmptyStack();
		}

		return ItemChiseledBit.createStack( stateID, count, true );
	}

	@Override
	public boolean isAir()
	{
		return stateID == 0;
	}

	@Override
	public @Nullable IBlockState getState()
	{
		if ( stateID == 0 )
		{
			return null;
		}

		return ModUtil.getStateById( stateID );
	}

	@Override
	public int getStateID()
	{
		return stateID;
	}

}
