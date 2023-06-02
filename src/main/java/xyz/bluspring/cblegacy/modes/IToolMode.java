package xyz.bluspring.cblegacy.modes;

import net.minecraft.item.ItemStack;
import xyz.bluspring.cblegacy.helpers.LocalStrings;

public interface IToolMode
{

	void setMode(
			ItemStack ei );

	LocalStrings getName();

	String name();

	boolean isDisabled();

	int ordinal();

}
