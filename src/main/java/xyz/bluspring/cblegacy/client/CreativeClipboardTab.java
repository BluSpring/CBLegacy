package xyz.bluspring.cblegacy.client;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.FMLCommonHandler;
import xyz.bluspring.cblegacy.api.IBitAccess;
import xyz.bluspring.cblegacy.api.ItemType;
import xyz.bluspring.cblegacy.chiseledblock.NBTBlobConverter;
import xyz.bluspring.cblegacy.chiseledblock.data.VoxelBlob;
import xyz.bluspring.cblegacy.core.ChiselsAndBits;
import xyz.bluspring.cblegacy.helpers.ModUtil;
import xyz.bluspring.cblegacy.interfaces.ICacheClearable;
import xyz.bluspring.cblegacy.registry.ModItems;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CreativeClipboardTab extends CreativeTabs implements ICacheClearable
{
	static boolean renewMappings = true;
	static private List<ItemStack> myWorldItems = new ArrayList<ItemStack>();
	static private List<NBTTagCompound> myCrossItems = new ArrayList<NBTTagCompound>();
	static private ClipboardStorage clipStorage = null;

	public static void load(
			final File file )
	{
		clipStorage = new ClipboardStorage( file );
		myCrossItems = clipStorage.read();
	}

	static public void addItem(
			final ItemStack iss )
	{
		// this is a client side things.
		if ( FMLCommonHandler.instance().getEffectiveSide().isClient() )
		{
			final IBitAccess bitData = ChiselsAndBits.getApi().createBitItem( iss );

			if ( bitData == null )
			{
				return;
			}

			final ItemStack is = bitData.getBitsAsItem( null, ItemType.CHISLED_BLOCK, true );

			if ( is == null )
			{
				return;
			}

			// remove duplicates if they exist...
			for ( final NBTTagCompound isa : myCrossItems )
			{
				if ( isa.equals( is.getTagCompound() ) )
				{
					myCrossItems.remove( isa );
					break;
				}
			}

			// add item to front...
			myCrossItems.add( 0, is.getTagCompound() );

			// remove extra items from back..
			while ( myCrossItems.size() > ChiselsAndBits.getConfig().creativeClipboardSize && !myCrossItems.isEmpty() )
			{
				myCrossItems.remove( myCrossItems.size() - 1 );
			}

			clipStorage.write( myCrossItems );
			myWorldItems.clear();
			renewMappings = true;
		}
	}

	public CreativeClipboardTab()
	{
		super( ChiselsAndBits.MODID + ".Clipboard" );
		ChiselsAndBits.getInstance().addClearable( this );
	}

	@Override
	public ItemStack getTabIconItem()
	{
		final ModItems cbitems = ChiselsAndBits.getItems();
		return new ItemStack( ModUtil.firstNonNull(
				cbitems.itemPositiveprint,
				cbitems.itemNegativeprint,
				cbitems.itemBitBag,
				cbitems.itemChiselDiamond,
				cbitems.itemChiselGold,
				cbitems.itemChiselIron,
				cbitems.itemChiselStone,
				cbitems.itemWrench ) );
	}

	@Override
	public void displayAllRelevantItems(
			final NonNullList<ItemStack> itemList )
	{
		if ( renewMappings )
		{
			myWorldItems.clear();
			renewMappings = false;

			for ( final NBTTagCompound nbt : myCrossItems )
			{
				final NBTBlobConverter c = new NBTBlobConverter();
				c.readChisleData( nbt.getCompoundTag( ModUtil.NBT_BLOCKENTITYTAG ), VoxelBlob.VERSION_ANY );

				// recalculate.
				c.updateFromBlob();

				final ItemStack worldItem = c.getItemStack( false );

				if ( worldItem != null )
				{
					myWorldItems.add( worldItem );
				}
			}
		}

		itemList.addAll( myWorldItems );
	}

	@Override
	public void clearCache()
	{
		renewMappings = true;
	}

}
