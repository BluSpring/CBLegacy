package xyz.bluspring.cblegacy.network.packets;

import xyz.bluspring.cblegacy.events.EventPlayerInteract;
import xyz.bluspring.cblegacy.network.ModPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;

public class PacketSuppressInteraction extends ModPacket
{

	public boolean newSetting = false;

	@Override
	public void server(
			final EntityPlayerMP player )
	{
		EventPlayerInteract.setPlayerSuppressionState( player, newSetting );
	}

	@Override
	public void getPayload(
			final PacketBuffer buffer )
	{
		buffer.writeBoolean( newSetting );
	}

	@Override
	public void readPayload(
			final PacketBuffer buffer )
	{
		newSetting = buffer.readBoolean();
	}

}
