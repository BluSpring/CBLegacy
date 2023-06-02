package xyz.bluspring.cblegacy.chiseledblock.serialization;

import net.minecraft.network.PacketBuffer;
import xyz.bluspring.cblegacy.chiseledblock.data.VoxelBlob;

public class CrossWorldBlobSerializer extends BlobSerializer
{

	public CrossWorldBlobSerializer(
			final PacketBuffer toInflate )
	{
		super( toInflate );
	}

	public CrossWorldBlobSerializer(
			final VoxelBlob toDeflate )
	{
		super( toDeflate );
	}

	@Override
	protected int readStateID(
			final PacketBuffer buffer )
	{
		final String name = buffer.readStringFromBuffer( 2047 );
		buffer.readStringFromBuffer( 2047 );

		return StringStates.getStateIDFromName( name );
	}

	@Override
	protected void writeStateID(
			final PacketBuffer buffer,
			final int key )
	{
		final String sname = StringStates.getNameFromStateID( key );

		buffer.writeString( sname );
		buffer.writeString( "" ); // extra data for later use.
	}

	@Override
	public int getVersion()
	{
		return VoxelBlob.VERSION_CROSSWORLD;
	}
}
