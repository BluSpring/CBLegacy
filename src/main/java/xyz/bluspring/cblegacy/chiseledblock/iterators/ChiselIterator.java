package xyz.bluspring.cblegacy.chiseledblock.iterators;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import xyz.bluspring.cblegacy.chiseledblock.data.IntegerBox;
import xyz.bluspring.cblegacy.chiseledblock.data.VoxelBlob;

public interface ChiselIterator
{

	IntegerBox getVoxelBox(
			VoxelBlob blobAt,
			boolean b );

	AxisAlignedBB getBoundingBox(
			VoxelBlob nULL_BLOB,
			boolean b );

	boolean hasNext();

	EnumFacing side();

	int x();

	int y();

	int z();

}
