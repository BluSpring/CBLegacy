package xyz.bluspring.cblegacy.client.culling;

import xyz.bluspring.cblegacy.chiseledblock.BlockBitInfo;

/**
 * Not in use.
 * 
 * Basic Solid Culling, culls almost all the faces but this works fine for solid
 * things.
 * 
 * This is how all culling used to work.
 */
public class SolidCullTest implements ICullTest
{

	@Override
	public boolean isVisible(
			final int mySpot,
			final int secondSpot )
	{
		return BlockBitInfo.getTypeFromStateID( mySpot ).shouldShow( BlockBitInfo.getTypeFromStateID( secondSpot ) );
	}

}
