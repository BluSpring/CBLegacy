package xyz.bluspring.cblegacy.interfaces;

import xyz.bluspring.cblegacy.chiseledblock.data.VoxelBlob;

public interface IChiseledTileContainer
{

	public boolean isBlobOccluded(
			VoxelBlob blob );

	public void sendUpdate();

	public void saveData();

}
