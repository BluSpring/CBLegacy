package xyz.bluspring.cblegacy.commands;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import xyz.bluspring.cblegacy.api.IBitAccess;
import xyz.bluspring.cblegacy.api.IBitBrush;
import xyz.bluspring.cblegacy.api.IChiselAndBitsAPI;
import xyz.bluspring.cblegacy.core.ChiselsAndBits;
import xyz.bluspring.cblegacy.helpers.DeprecationHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.bluspring.cblegacy.api.APIExceptions;

public class SetBit extends CommandBase
{

	@Override
	public String getCommandName()
	{
		return "setbit";
	}

	@Override
	public int getRequiredPermissionLevel()
	{
		return 2;
	}

	@Override
	public String getCommandUsage(
			final ICommandSender sender )
	{
		return "commands.setbit.usage";
	}

	@Override
	public void execute(
			final MinecraftServer server,
			final ICommandSender sender,
			final String[] args ) throws CommandException
	{
		if ( args.length < 7 )
		{
			throw new WrongUsageException( "commands.setbit.usage" );
		}
		else
		{
			final BlockPos blockpos = parseBlockPos( sender, args, 0, false );
			final BlockPos bitpos = parseBlockPos( sender, args, 3, false );

			final Block block = CommandBase.getBlockByText( sender, args[6] );
			int meta = 0;

			if ( args.length >= 8 )
			{
				meta = parseInt( args[7], 0, 15 );
			}

			final IBlockState state = DeprecationHelper.getStateFromMeta( block, meta );
			final World world = sender.getEntityWorld();

			if ( !world.isBlockLoaded( blockpos ) )
			{
				throw new CommandException( "commands.setbit.outOfWorld", new Object[0] );
			}
			else
			{
				try
				{
					final IChiselAndBitsAPI api = ChiselsAndBits.getApi();
					final IBitAccess ba = api.getBitAccess( world, blockpos );

					final IBitBrush bit = ba.getBitAt( bitpos.getX(), bitpos.getY(), bitpos.getZ() );
					final IBitBrush brush = api.createBrushFromState( state );

					if ( bit == brush )
					{
						throw new CommandException( "commands.setbit.noChange" );
					}
					else
					{
						ba.setBitAt( bitpos.getX(), bitpos.getY(), bitpos.getZ(), brush );
						ba.commitChanges( true );
						sender.setCommandStat( CommandResultStats.Type.AFFECTED_BLOCKS, 1 );
						notifyCommandListener( sender, this, "commands.setbit.success" );
					}
				}
				catch ( final APIExceptions.CannotBeChiseled e )
				{
					throw new CommandException( "commands.setbit.cannotChiselBlock" );
				}
				catch ( final APIExceptions.InvalidBitItem e )
				{
					throw new CommandException( "commands.setbit.invalidState" );
				}
				catch ( final APIExceptions.SpaceOccupied e )
				{
					throw new CommandException( "commands.setbit.spaceOccupied" );
				}

			}
		}
	}

	@Override
	public List<String> getTabCompletionOptions(
			final MinecraftServer server,
			final ICommandSender sender,
			final String[] args,
			@Nullable final BlockPos pos )
	{
		return args.length > 0 && args.length <= 3 ? getTabCompletionCoordinate( args, 0, pos )
				: args.length == 7 ? getListOfStringsMatchingLastWord( args, Block.REGISTRY.getKeys() ) : Collections.<String> emptyList();
	}
}
