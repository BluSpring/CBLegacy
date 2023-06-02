package xyz.bluspring.cblegacy.network.packets;

import xyz.bluspring.cblegacy.helpers.ChiselToolType;
import xyz.bluspring.cblegacy.interfaces.IChiselModeItem;
import xyz.bluspring.cblegacy.modes.ChiselMode;
import xyz.bluspring.cblegacy.modes.IToolMode;
import xyz.bluspring.cblegacy.modes.PositivePatternMode;
import xyz.bluspring.cblegacy.modes.TapeMeasureModes;
import xyz.bluspring.cblegacy.network.ModPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TextComponentTranslation;

public class PacketSetChiselMode extends ModPacket
{

	public IToolMode mode = ChiselMode.SINGLE;
	public ChiselToolType type = ChiselToolType.CHISEL;
	public boolean chatNotification = false;

	@Override
	public void server(
			final EntityPlayerMP player )
	{
		final ItemStack ei = player.getHeldItemMainhand();
		if ( ei != null && ei.getItem() instanceof IChiselModeItem )
		{
			final IToolMode originalMode = type.getMode( ei );
			mode.setMode( ei );

			if ( originalMode != mode && chatNotification )
			{
				Minecraft.getMinecraft().thePlayer.addChatComponentMessage( new TextComponentTranslation( mode.getName().toString() ), true );
			}
		}
	}

	@Override
	public void getPayload(
			final PacketBuffer buffer )
	{
		buffer.writeBoolean( chatNotification );
		buffer.writeEnumValue( type );
		buffer.writeEnumValue( (Enum<?>) mode );
	}

	@Override
	public void readPayload(
			final PacketBuffer buffer )
	{
		chatNotification = buffer.readBoolean();
		type = buffer.readEnumValue( ChiselToolType.class );

		if ( type == ChiselToolType.BIT || type == ChiselToolType.CHISEL )
		{
			mode = buffer.readEnumValue( ChiselMode.class );
		}
		else if ( type == ChiselToolType.POSITIVEPATTERN )
		{
			mode = buffer.readEnumValue( PositivePatternMode.class );
		}
		else if ( type == ChiselToolType.TAPEMEASURE )
		{
			mode = buffer.readEnumValue( TapeMeasureModes.class );
		}
	}

}
