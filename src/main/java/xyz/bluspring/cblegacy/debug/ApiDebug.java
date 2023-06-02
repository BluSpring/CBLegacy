package xyz.bluspring.cblegacy.debug;

import xyz.bluspring.cblegacy.api.ChiselsAndBitsAddon;
import xyz.bluspring.cblegacy.api.IChiselAndBitsAPI;
import xyz.bluspring.cblegacy.api.IChiselsAndBitsAddon;

@ChiselsAndBitsAddon
public class ApiDebug implements IChiselsAndBitsAddon
{

	@Override
	public void onReadyChiselsAndBits(
			final IChiselAndBitsAPI api )
	{
		DebugAction.api = api;
	}

}
