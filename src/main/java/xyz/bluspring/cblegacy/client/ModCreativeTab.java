package xyz.bluspring.cblegacy.client;

import xyz.bluspring.cblegacy.core.ChiselsAndBits;
import xyz.bluspring.cblegacy.helpers.ModUtil;
import xyz.bluspring.cblegacy.registry.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModCreativeTab extends CreativeTabs
{

	public ModCreativeTab()
	{
		super( ChiselsAndBits.MODID );
		setBackgroundImageName( "item_search.png" );
	}

	@Override
	public boolean hasSearchBar()
	{
		return true;
	}

	@Override
	public ItemStack getTabIconItem()
	{
		final ModItems cbitems = ChiselsAndBits.getItems();
		return new ItemStack( ModUtil.firstNonNull(
				cbitems.itemChiselDiamond,
				cbitems.itemChiselGold,
				cbitems.itemChiselIron,
				cbitems.itemChiselStone,
				cbitems.itemBitBag,
				cbitems.itemPositiveprint,
				cbitems.itemNegativeprint,
				cbitems.itemWrench ) );
	}

}
