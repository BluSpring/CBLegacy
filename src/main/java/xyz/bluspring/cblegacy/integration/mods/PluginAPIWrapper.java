package xyz.bluspring.cblegacy.integration.mods;

import xyz.bluspring.cblegacy.api.IChiselsAndBitsAddon;
import xyz.bluspring.cblegacy.core.ChiselsAndBits;
import xyz.bluspring.cblegacy.integration.IntegrationBase;

public class PluginAPIWrapper extends IntegrationBase
{
	final IChiselsAndBitsAddon addon;

	public PluginAPIWrapper(
			final IChiselsAndBitsAddon addon )
	{
		this.addon = addon;
	}

	@Override
	public void init()
	{
		addon.onReadyChiselsAndBits( ChiselsAndBits.getApi() );
	}

}
