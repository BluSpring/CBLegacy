package xyz.bluspring.cblegacy.integration.mods;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import xyz.bluspring.cblegacy.core.ChiselsAndBits;
import xyz.bluspring.cblegacy.integration.ChiselsAndBitsIntegration;
import xyz.bluspring.cblegacy.integration.IntegrationBase;

@ChiselsAndBitsIntegration( "VersionChecker" )
public class VersionChecker extends IntegrationBase
{

	@Override
	public void init()
	{
		final NBTTagCompound compound = new NBTTagCompound();
		compound.setString( "curseProjectName", "chisels-bits" );
		compound.setString( "curseFilenameParser", "chiselsandbits-[].jar" );
		FMLInterModComms.sendRuntimeMessage( ChiselsAndBits.MODID, "VersionChecker", "addCurseCheck", compound );
	}

}
