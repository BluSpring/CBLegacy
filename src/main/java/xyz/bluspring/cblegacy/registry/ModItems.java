package xyz.bluspring.cblegacy.registry;

import xyz.bluspring.cblegacy.config.ModConfig;
import xyz.bluspring.cblegacy.debug.ItemApiDebug;
import xyz.bluspring.cblegacy.items.ItemBitBag;
import xyz.bluspring.cblegacy.items.ItemBitSaw;
import xyz.bluspring.cblegacy.items.ItemChisel;
import xyz.bluspring.cblegacy.items.ItemChiseledBit;
import xyz.bluspring.cblegacy.items.ItemMirrorPrint;
import xyz.bluspring.cblegacy.items.ItemNegativePrint;
import xyz.bluspring.cblegacy.items.ItemPositivePrint;
import xyz.bluspring.cblegacy.items.ItemTapeMeasure;
import xyz.bluspring.cblegacy.items.ItemWrench;
import net.minecraft.item.Item.ToolMaterial;

public class ModItems extends ModRegistry
{

	final public ItemChisel itemChiselStone;
	final public ItemChisel itemChiselIron;
	final public ItemChisel itemChiselGold;
	final public ItemChisel itemChiselDiamond;

	final public ItemChiseledBit itemBlockBit;
	final public ItemMirrorPrint itemMirrorprint;
	final public ItemPositivePrint itemPositiveprint;
	final public ItemNegativePrint itemNegativeprint;

	final public ItemBitBag itemBitBag;
	final public ItemWrench itemWrench;
	final public ItemBitSaw itemBitSawDiamond;
	final public ItemTapeMeasure itemTapeMeasure;

	public ModItems(
			final ModConfig config )
	{
		// register items...
		itemChiselStone = registerItem( config.enableStoneChisel, new ItemChisel( ToolMaterial.STONE ), "chisel_stone" );
		itemChiselIron = registerItem( config.enableIronChisel, new ItemChisel( ToolMaterial.IRON ), "chisel_iron" );
		itemChiselGold = registerItem( config.enableGoldChisel, new ItemChisel( ToolMaterial.GOLD ), "chisel_gold" );
		itemChiselDiamond = registerItem( config.enableDiamondChisel, new ItemChisel( ToolMaterial.DIAMOND ), "chisel_diamond" );
		itemPositiveprint = registerItem( config.enablePositivePrint, new ItemPositivePrint(), "positiveprint" );
		itemNegativeprint = registerItem( config.enableNegativePrint, new ItemNegativePrint(), "negativeprint" );
		itemMirrorprint = registerItem( config.enableMirrorPrint, new ItemMirrorPrint(), "mirrorprint" );
		itemBitBag = registerItem( config.enableBitBag, new ItemBitBag(), "bit_bag" );
		itemWrench = registerItem( config.enableWoodenWrench, new ItemWrench(), "wrench_wood" );
		itemBitSawDiamond = registerItem( config.enableBitSaw, new ItemBitSaw(), "bitsaw_diamond" );
		itemBlockBit = registerItem( config.enableChisledBits, new ItemChiseledBit(), "block_bit" );
		itemTapeMeasure = registerItem( config.enableTapeMeasure, new ItemTapeMeasure(), "tape_measure" );
		registerItem( config.enableAPITestingItem, new ItemApiDebug(), "debug" );
	}

}
