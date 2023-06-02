package xyz.bluspring.cblegacy.network;

import java.util.HashMap;

import xyz.bluspring.cblegacy.network.packets.PacketAccurateSneakPlace;
import xyz.bluspring.cblegacy.network.packets.PacketBagGui;
import xyz.bluspring.cblegacy.network.packets.PacketBagGuiStack;
import xyz.bluspring.cblegacy.network.packets.PacketChisel;
import xyz.bluspring.cblegacy.network.packets.PacketClearBagGui;
import xyz.bluspring.cblegacy.network.packets.PacketOpenBagGui;
import xyz.bluspring.cblegacy.network.packets.PacketRotateVoxelBlob;
import xyz.bluspring.cblegacy.network.packets.PacketSetChiselMode;
import xyz.bluspring.cblegacy.network.packets.PacketSetColor;
import xyz.bluspring.cblegacy.network.packets.PacketSortBagGui;
import xyz.bluspring.cblegacy.network.packets.PacketSuppressInteraction;
import xyz.bluspring.cblegacy.network.packets.PacketUndo;

public enum ModPacketTypes
{
	CHISEL( PacketChisel.class ),
	OPEN_BAG_GUI( PacketOpenBagGui.class ),
	SET_CHISEL_MODE( PacketSetChiselMode.class ),
	ROTATE_VOXEL_BLOB( PacketRotateVoxelBlob.class ),
	BAG_GUI( PacketBagGui.class ),
	BAG_GUI_STACK( PacketBagGuiStack.class ),
	UNDO( PacketUndo.class ),
	CLEAR_BAG( PacketClearBagGui.class ),
	SUPRESS_INTERACTION( PacketSuppressInteraction.class ),
	SET_COLOR( PacketSetColor.class ),
	ACCURATE_PLACEMENT( PacketAccurateSneakPlace.class ),
	SORT_BAG_GUI( PacketSortBagGui.class );

	private final Class<? extends ModPacket> packetClass;

	ModPacketTypes(
			final Class<? extends ModPacket> clz )
	{
		packetClass = clz;
	}

	private static HashMap<Class<? extends ModPacket>, Integer> fromClassToId = new HashMap<Class<? extends ModPacket>, Integer>();
	private static HashMap<Integer, Class<? extends ModPacket>> fromIdToClass = new HashMap<Integer, Class<? extends ModPacket>>();

	public static void init()
	{
		for ( final ModPacketTypes p : ModPacketTypes.values() )
		{
			fromClassToId.put( p.packetClass, p.ordinal() );
			fromIdToClass.put( p.ordinal(), p.packetClass );
		}
	}

	public static int getID(
			final Class<? extends ModPacket> clz )
	{
		return fromClassToId.get( clz );
	}

	public static ModPacket constructByID(
			final int id ) throws InstantiationException, IllegalAccessException
	{
		return fromIdToClass.get( id ).newInstance();
	}

}
