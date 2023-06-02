package xyz.bluspring.cblegacy.core;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.level.material.Material;
import xyz.bluspring.cblegacy.api.IChiselAndBitsAPI;
import xyz.bluspring.cblegacy.chiseledblock.BlockBitInfo;
import xyz.bluspring.cblegacy.chiseledblock.data.VoxelBlob;
import xyz.bluspring.cblegacy.client.UndoTracker;
import xyz.bluspring.cblegacy.client.gui.ModGuiRouter;
import xyz.bluspring.cblegacy.commands.SetBit;
import xyz.bluspring.cblegacy.config.ModConfig;
import xyz.bluspring.cblegacy.core.api.ChiselAndBitsAPI;
import xyz.bluspring.cblegacy.core.api.IMCHandler;
import xyz.bluspring.cblegacy.crafting.ModRecipes;
import xyz.bluspring.cblegacy.events.EventPlayerInteract;
import xyz.bluspring.cblegacy.events.VaporizeWater;
import xyz.bluspring.cblegacy.interfaces.ICacheClearable;
import xyz.bluspring.cblegacy.network.NetworkRouter;
import xyz.bluspring.cblegacy.registry.ModBlocks;
import xyz.bluspring.cblegacy.registry.ModItems;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChiselsAndBits implements ModInitializer
{
	public static final @Nonnull String MODID = "cb-legacy";

	private static ChiselsAndBits instance;
	private ModConfig config;
	private ModItems items;
	private ModBlocks blocks;
	private final IChiselAndBitsAPI api = new ChiselAndBitsAPI();
	private boolean loadClientAssets = false;

	List<ICacheClearable> cacheClearables = new ArrayList<ICacheClearable>();

	public ChiselsAndBits()
	{
		instance = this;
	}

	public static ChiselsAndBits getInstance()
	{
		return instance;
	}

	public static ModBlocks getBlocks()
	{
		return instance.blocks;
	}

	public static ModItems getItems()
	{
		return instance.items;
	}

	public static ModConfig getConfig()
	{
		return instance.config;
	}

	public static IChiselAndBitsAPI getApi()
	{
		return instance.api;
	}

	@EventHandler
	private void handleIMCEvent(
			final FMLInterModComms.IMCEvent event )
	{
		final IMCHandler imcHandler = new IMCHandler();
		imcHandler.handleIMCEvent( event );
	}

	@EventHandler
	public void preinit(
			final FMLPreInitializationEvent event )
	{

	}

	@EventHandler
	public void init(
			final FMLInitializationEvent event )
	{
		if ( event.getSide() == Side.CLIENT )
		{
			ClientSide.instance.init( this );
		}

		integration.init();

		registerWithBus( new EventPlayerInteract() );
		registerWithBus( new VaporizeWater() );
	}

	@EventHandler
	public void serverStart(
			final FMLServerStartingEvent e )
	{

		if ( getConfig().enableSetBitCommand )
		{
			e.registerServerCommand( new SetBit() );
		}
	}

	@EventHandler
	public void postinit(
			final FMLPostInitializationEvent event )
	{
		if ( event.getSide() == Side.CLIENT )
		{
			ClientSide.instance.postinit( this );
		}

		integration.postinit();

		NetworkRouter.instance = new NetworkRouter();
		NetworkRegistry.INSTANCE.registerGuiHandler( this, new ModGuiRouter() );
	}

	boolean idsHaveBeenMapped = false;

	@EventHandler
	public void idsMapped(
			final FMLModIdMappingEvent event )
	{
		idsHaveBeenMapped = true;
		BlockBitInfo.recalculateFluidBlocks();
		clearCache();
	}

	public void clearCache()
	{
		if ( idsHaveBeenMapped )
		{
			for ( final ICacheClearable clearable : cacheClearables )
			{
				clearable.clearCache();
			}

			addClearable( UndoTracker.getInstance() );
			VoxelBlob.clearCache();
		}
	}

	public void addClearable(
			final ICacheClearable cache )
	{
		if ( !cacheClearables.contains( cache ) )
		{
			cacheClearables.add( cache );
		}
	}

	public boolean loadClientAssets()
	{
		return loadClientAssets;
	}

	public static final File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "chiselsandbits.cfg");

	@Override
	public void onInitialize() {
		// load config...
		config = new ModConfig( configFile );

		items = new ModItems( getConfig() );
		blocks = new ModBlocks( getConfig(), FabricLoader.getInstance().getEnvironmentType() );
		registerWithBus( new ModRecipes( getConfig() ) );

		// merge most of the extra materials into the normal set.
		ChiselsAndBits.getApi().addEquivilantMaterial( Material.SPONGE, Material.CLAY );
		ChiselsAndBits.getApi().addEquivilantMaterial( Material.HEAVY_METAL, Material.METAL );
		ChiselsAndBits.getApi().addEquivilantMaterial( Material.PLANT, Material.GRASS );
		ChiselsAndBits.getApi().addEquivilantMaterial( Material.REPLACEABLE_WATER_PLANT, Material.PLANT );
		ChiselsAndBits.getApi().addEquivilantMaterial( Material.CACTUS, Material.PLANT );
		ChiselsAndBits.getApi().addEquivilantMaterial( Material.WATER_PLANT, Material.STONE );
		ChiselsAndBits.getApi().addEquivilantMaterial( Material.WEB, Material.PLANT );
		ChiselsAndBits.getApi().addEquivilantMaterial( Material.LEAVES, Material.PLANT );
		ChiselsAndBits.getApi().addEquivilantMaterial( Material.EXPLOSIVE, Material.STONE );
	}
}
