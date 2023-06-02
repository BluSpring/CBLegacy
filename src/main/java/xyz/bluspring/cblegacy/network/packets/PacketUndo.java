package xyz.bluspring.cblegacy.network.packets;

import java.util.ArrayList;
import java.util.List;

import xyz.bluspring.cblegacy.api.APIExceptions.CannotBeChiseled;
import xyz.bluspring.cblegacy.bitbag.BagInventory;
import xyz.bluspring.cblegacy.chiseledblock.data.BitIterator;
import xyz.bluspring.cblegacy.chiseledblock.data.VoxelBlob;
import xyz.bluspring.cblegacy.chiseledblock.data.VoxelBlobStateReference;
import xyz.bluspring.cblegacy.client.UndoTracker;
import xyz.bluspring.cblegacy.core.ChiselsAndBits;
import xyz.bluspring.cblegacy.core.api.BitAccess;
import xyz.bluspring.cblegacy.helpers.ActingPlayer;
import xyz.bluspring.cblegacy.helpers.ContinousChisels;
import xyz.bluspring.cblegacy.helpers.IContinuousInventory;
import xyz.bluspring.cblegacy.helpers.IItemInInventory;
import xyz.bluspring.cblegacy.helpers.InventoryBackup;
import xyz.bluspring.cblegacy.helpers.ModUtil;
import xyz.bluspring.cblegacy.helpers.BitInventoryFeeder;
import xyz.bluspring.cblegacy.items.ItemBitBag;
import xyz.bluspring.cblegacy.items.ItemChisel;
import xyz.bluspring.cblegacy.network.ModPacket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PacketUndo extends ModPacket
{

	BlockPos pos;
	VoxelBlobStateReference before;
	VoxelBlobStateReference after;

	public PacketUndo()
	{
		// walrus.
	}

	public PacketUndo(
			final BlockPos pos,
			final VoxelBlobStateReference before,
			final VoxelBlobStateReference after )
	{
		this.pos = pos;
		this.before = before;
		this.after = after;
	}

	@Override
	public void server(
			final EntityPlayerMP player )
	{
		preformAction( ActingPlayer.actingAs( player, EnumHand.MAIN_HAND ), true );
	}

	@Override
	public void getPayload(
			final PacketBuffer buffer )
	{
		buffer.writeBlockPos( pos );

		final byte[] bef = before.getByteArray();
		buffer.writeVarIntToBuffer( bef.length );
		buffer.writeBytes( bef );

		final byte[] aft = after.getByteArray();
		buffer.writeVarIntToBuffer( aft.length );
		buffer.writeBytes( aft );
	}

	@Override
	public void readPayload(
			final PacketBuffer buffer )
	{
		pos = buffer.readBlockPos();

		final int lena = buffer.readVarIntFromBuffer();
		final byte[] ta = new byte[lena];
		buffer.readBytes( ta );

		final int lenb = buffer.readVarIntFromBuffer();
		final byte[] tb = new byte[lenb];
		buffer.readBytes( tb );

		before = new VoxelBlobStateReference( ta, 0 );
		after = new VoxelBlobStateReference( tb, 0 );
	}

	public boolean preformAction(
			final ActingPlayer player,
			final boolean spawnItemsAndCommitWorldChanges )
	{
		if ( inRange( player, pos ) )
		{
			return apply( player, spawnItemsAndCommitWorldChanges );
		}

		return false;
	}

	private boolean apply(
			final ActingPlayer player,
			final boolean spawnItemsAndCommitWorldChanges )
	{
		try
		{
			final EnumFacing side = EnumFacing.UP;

			final World world = player.getWorld();
			final BitAccess ba = (BitAccess) ChiselsAndBits.getApi().getBitAccess( world, pos );

			final VoxelBlob bBefore = before.getVoxelBlob();
			final VoxelBlob bAfter = after.getVoxelBlob();

			final VoxelBlob target = ba.getNativeBlob();

			if ( target.equals( bBefore ) )
			{
				// if something horrible goes wrong in a single block change we
				// can roll it back, but it shouldn't happen since its already
				// been approved as possible.
				final InventoryBackup backup = new InventoryBackup( player.getInventory() );

				boolean successful = true;

				final IContinuousInventory selected = new ContinousChisels( player, pos, side );
				ItemStack spawnedItem = null;

				final List<BagInventory> bags = ModUtil.getBags( player );
				final List<EntityItem> spawnlist = new ArrayList<EntityItem>();

				final BitIterator bi = new BitIterator();
				while ( bi.hasNext() )
				{
					final int inBefore = bi.getNext( bBefore );
					final int inAfter = bi.getNext( bAfter );

					if ( inBefore != inAfter )
					{
						if ( inAfter == 0 )
						{
							if ( selected.isValid() )
							{
								spawnedItem = ItemChisel.chiselBlock( selected, player, target, world, pos, side, bi.x, bi.y, bi.z, spawnedItem, spawnlist );
							}
							else
							{
								successful = false;
								break;
							}
						}
						else if ( inAfter != 0 )
						{
							if ( inBefore != 0 )
							{
								if ( selected.isValid() )
								{
									spawnedItem = ItemChisel.chiselBlock( selected, player, target, world, pos, side, bi.x, bi.y, bi.z, spawnedItem, spawnlist );
								}
								else
								{
									successful = false;
									break;
								}
							}

							final IItemInInventory bit = ModUtil.findBit( player, pos, inAfter );
							if ( ModUtil.consumeBagBit( bags, inAfter, 1 ) == 1 )
							{
								bi.setNext( target, inAfter );
							}
							else if ( bit.isValid() )
							{
								if ( !player.isCreative() )
								{
									if ( !bit.consume() )
									{
										successful = false;
										break;
									}
								}

								bi.setNext( target, inAfter );
							}
							else
							{
								successful = false;
								break;
							}
						}
					}
				}

				if ( successful )
				{
					if ( spawnItemsAndCommitWorldChanges )
					{
						ba.commitChanges( true );
						BitInventoryFeeder feeder = new BitInventoryFeeder( player.getPlayer(), player.getWorld() );
						for ( final EntityItem ei : spawnlist )
						{
							feeder.addItem(ei);
							ItemBitBag.cleanupInventory( player.getPlayer(), ei.getEntityItem() );
						}
					}

					return true;
				}
				else
				{
					backup.rollback();
					UndoTracker.getInstance().addError( player, "mod.chiselsandbits.result.missing_bits" );
					return false;
				}
			}
		}
		catch ( final CannotBeChiseled e )
		{
			// error message below.
		}

		UndoTracker.getInstance().addError( player, "mod.chiselsandbits.result.has_changed" );
		return false;

	}

	private boolean inRange(
			final ActingPlayer player,
			final BlockPos pos )
	{
		if ( player.isReal() )
		{
			return true;
		}

		double reach = 6;
		if ( player.isCreative() )
		{
			reach = 32;
		}

		if ( player.getPlayer().getDistanceSq( pos ) < reach * reach )
		{
			return true;
		}

		UndoTracker.getInstance().addError( player, "mod.chiselsandbits.result.out_of_range" );
		return false;
	}

}
