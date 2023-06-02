package xyz.bluspring.cblegacy.bitbag;

import xyz.bluspring.cblegacy.helpers.ModUtil;
import xyz.bluspring.cblegacy.items.ItemChiseledBit;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBit extends Slot
{

	public SlotBit(
			final IInventory inventoryIn,
			final int index,
			final int xPosition,
			final int yPosition )
	{
		super( inventoryIn, index, xPosition, yPosition );
	}

	@Override
	public boolean isItemValid(
			final ItemStack stack )
	{
		return ModUtil.notEmpty( stack ) && stack.getItem() instanceof ItemChiseledBit;
	}

}
