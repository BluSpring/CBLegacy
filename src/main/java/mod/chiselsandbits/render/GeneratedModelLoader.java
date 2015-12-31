package mod.chiselsandbits.render;

import java.util.ArrayList;
import java.util.HashMap;

import mod.chiselsandbits.ChiselsAndBits;
import mod.chiselsandbits.render.bit.BitItemSmartModel;
import mod.chiselsandbits.render.chiseledblock.ChisledBlockSmartModel;
import mod.chiselsandbits.render.patterns.PrintSmartModel;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GeneratedModelLoader implements ICustomModelLoader
{

	private final HashMap<ResourceLocation, IFlexibleBakedModel> models = new HashMap<ResourceLocation, IFlexibleBakedModel>();
	private final ArrayList<ModelResourceLocation> res = new ArrayList<ModelResourceLocation>();

	public GeneratedModelLoader()
	{
		add( new ResourceLocation( ChiselsAndBits.MODID, "chiseled_iron" ), new ChisledBlockSmartModel() );
		add( new ResourceLocation( ChiselsAndBits.MODID, "chiseled_clay" ), new ChisledBlockSmartModel() );
		add( new ResourceLocation( ChiselsAndBits.MODID, "chiseled_cloth" ), new ChisledBlockSmartModel() );
		add( new ResourceLocation( ChiselsAndBits.MODID, "chiseled_packedIce" ), new ChisledBlockSmartModel() );
		add( new ResourceLocation( ChiselsAndBits.MODID, "chiseled_ice" ), new ChisledBlockSmartModel() );
		add( new ResourceLocation( ChiselsAndBits.MODID, "chiseled_wood" ), new ChisledBlockSmartModel() );
		add( new ResourceLocation( ChiselsAndBits.MODID, "chiseled_rock" ), new ChisledBlockSmartModel() );
		add( new ResourceLocation( ChiselsAndBits.MODID, "chiseled_glass" ), new ChisledBlockSmartModel() );

		add( new ResourceLocation( ChiselsAndBits.MODID, "models/item/block_chiseled" ), new ChisledBlockSmartModel() );

		add( new ResourceLocation( ChiselsAndBits.MODID, "models/item/block_bit" ), new BitItemSmartModel() );
		add( new ResourceLocation( ChiselsAndBits.MODID, "models/item/positiveprint_written_preview" ), new PrintSmartModel( "positiveprint", ChiselsAndBits.instance.items.itemPositiveprint ) );
		add( new ResourceLocation( ChiselsAndBits.MODID, "models/item/negativeprint_written_preview" ), new PrintSmartModel( "negativeprint", ChiselsAndBits.instance.items.itemNegativeprint ) );
		add( new ResourceLocation( ChiselsAndBits.MODID, "models/item/mirrorprint_written_preview" ), new PrintSmartModel( "mirrorprint", ChiselsAndBits.instance.items.itemMirrorprint ) );

		ChiselsAndBits.registerWithBus( this );
	}

	private void add(
			final ResourceLocation modelLocation,
			final IFlexibleBakedModel modelGen )
	{
		final ResourceLocation second = new ResourceLocation( modelLocation.getResourceDomain(), modelLocation.getResourcePath().substring( 1 + modelLocation.getResourcePath().lastIndexOf( '/' ) ) );

		res.add( new ModelResourceLocation( modelLocation, null ) );
		res.add( new ModelResourceLocation( second, null ) );

		res.add( new ModelResourceLocation( modelLocation, "inventory" ) );
		res.add( new ModelResourceLocation( second, "inventory" ) );

		res.add( new ModelResourceLocation( modelLocation, "multipart" ) );
		res.add( new ModelResourceLocation( second, "multipart" ) );

		models.put( modelLocation, modelGen );
		models.put( second, modelGen );

		models.put( new ModelResourceLocation( modelLocation, null ), modelGen );
		models.put( new ModelResourceLocation( second, null ), modelGen );

		models.put( new ModelResourceLocation( modelLocation, "inventory" ), modelGen );
		models.put( new ModelResourceLocation( second, "inventory" ), modelGen );

		models.put( new ModelResourceLocation( modelLocation, "multipart" ), modelGen );
		models.put( new ModelResourceLocation( second, "multipart" ), modelGen );
	}

	@SubscribeEvent
	public void onModelBakeEvent(
			final ModelBakeEvent event )
	{
		for ( final ModelResourceLocation rl : res )
		{
			event.modelRegistry.putObject( rl, getModel( rl ) );
		}
	}

	@Override
	public void onResourceManagerReload(
			final IResourceManager resourceManager )
	{

	}

	@Override
	public boolean accepts(
			final ResourceLocation modelLocation )
	{
		return models.containsKey( modelLocation );
	}

	@Override
	public IModel loadModel(
			final ResourceLocation modelLocation )
	{
		return new SmartModelContainer( getModel( modelLocation ) );
	}

	private IFlexibleBakedModel getModel(
			final ResourceLocation modelLocation )
	{
		try
		{
			return models.get( modelLocation );
		}
		catch ( final Exception e )
		{
			throw new RuntimeException( "The Model: " + modelLocation.toString() + " was not available was requested." );
		}
	}

}