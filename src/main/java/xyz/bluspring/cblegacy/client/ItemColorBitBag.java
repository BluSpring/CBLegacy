package xyz.bluspring.cblegacy.client;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import xyz.bluspring.cblegacy.core.ChiselsAndBits;

public class ItemColorBitBag implements IItemColor
{

	@Override
	public int getColorFromItemstack(
			ItemStack stack,
			int tintIndex )
	{
		if ( tintIndex == 1 )
		{
			EnumDyeColor color = ChiselsAndBits.getItems().itemBitBag.getDyedColor( stack );
			if ( color != null )
				return color.func_193350_e();
		}

		return -1;
	}

}
