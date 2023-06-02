package xyz.bluspring.cblegacy.helpers;

import net.minecraft.item.ItemStack;

public interface IItemInInventory
{

	boolean isValid();

	void damage(
			ActingPlayer who );

	boolean consume();

	ItemStack getStack();

	void swapWithWeapon();

	ItemStack getStackType();

}