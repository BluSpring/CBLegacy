package mod.chiselsandbits.client.model.data;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import mod.chiselsandbits.ChiselsAndBits;
import mod.chiselsandbits.api.IChiselsAndBitsAPI;
import mod.chiselsandbits.api.multistate.accessor.IAreaAccessor;
import mod.chiselsandbits.api.neighborhood.IBlockNeighborhood;
import mod.chiselsandbits.api.neighborhood.IBlockNeighborhoodBuilder;
import mod.chiselsandbits.api.profiling.IProfilerSection;
import mod.chiselsandbits.block.entities.ChiseledBlockEntity;
import mod.chiselsandbits.client.model.baked.chiseled.ChiselRenderType;
import mod.chiselsandbits.client.model.baked.chiseled.ChiseledBlockBakedModel;
import mod.chiselsandbits.client.model.baked.chiseled.ChiseledBlockBakedModelManager;
import mod.chiselsandbits.client.model.baked.chiseled.FluidRenderingManager;
import mod.chiselsandbits.client.model.baked.simple.CombinedModel;
import mod.chiselsandbits.profiling.ProfilingManager;
import mod.chiselsandbits.registrars.ModModelProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.ModelDataMap;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static net.minecraftforge.client.MinecraftForgeClient.getRenderLayer;

public class ChiseledBlockModelDataManager
{
    private static final ChiseledBlockModelDataManager INSTANCE = new ChiseledBlockModelDataManager();

    public static ChiseledBlockModelDataManager getInstance()
    {
        return INSTANCE;
    }

    private ExecutorService                    recalculationService;
    private final Multimap<ChunkPos, BlockPos> positionsInProcessing = Multimaps.synchronizedSetMultimap(HashMultimap.create());

    private ChiseledBlockModelDataManager()
    {
    }

    public void updateModelData(final ChiseledBlockEntity tileEntity) {
        if (!tileEntity.hasLevel() || !tileEntity.getLevel().isClientSide() || tileEntity == null)
            return;

        updateModelDataCore(tileEntity);
    }

    private void updateModelDataCore(final ChiseledBlockEntity tileEntity)
    {
        ensureThreadPoolSetup();

        positionsInProcessing.put(new ChunkPos(tileEntity.getBlockPos()), tileEntity.getBlockPos());
        final IBlockNeighborhood neighborhood = IBlockNeighborhoodBuilder.getInstance().build(
          direction -> tileEntity.getLevel().getBlockState(tileEntity.getBlockPos().offset(direction.getNormal())),
          direction -> {
              final BlockEntity otherTileEntity = tileEntity.getLevel().getBlockEntity(tileEntity.getBlockPos().offset(direction.getNormal()));
              if (otherTileEntity instanceof IAreaAccessor)
                  return (IAreaAccessor) otherTileEntity;

              return null;
          }
        );
        CompletableFuture.supplyAsync(() -> {
            BakedModel unknownRenderTypeModel;
            Map<RenderType, BakedModel> renderTypedModels = Maps.newHashMap();

            try(IProfilerSection ignored1 = ProfilingManager.getInstance().withSection("Extract model data from data"))
            {
                final RenderType currentType = getRenderLayer();
                ForgeHooksClient.setRenderLayer(null);
                try(IProfilerSection ignored2 = ProfilingManager.getInstance().withSection("Unknown render layer model building"))
                {
                    final ChiseledBlockBakedModel[] models = new ChiseledBlockBakedModel[ChiselRenderType.values().length];
                    try(IProfilerSection ignored3 = ProfilingManager.getInstance().withSection("Individual render types building"))
                    {
                        ChiselRenderType[] values = ChiselRenderType.values();
                        for (int i = 0; i < values.length; i++)
                        {
                            final ChiselRenderType chiselRenderType = values[i];
                            try (IProfilerSection ignored4 = ProfilingManager.getInstance().withSection(chiselRenderType.name()))
                            {
                                final ChiseledBlockBakedModel model = ChiseledBlockBakedModelManager.getInstance().get(
                                  tileEntity,
                                  tileEntity.getStatistics().getPrimaryState(),
                                  chiselRenderType,
                                  neighborhood::getBlockState,
                                  neighborhood::getAreaAccessor,
                                  tileEntity.getBlockPos()
                                );
                                models[i] = model;
                            }
                        }
                    }

                    try(IProfilerSection ignored3 = ProfilingManager.getInstance().withSection("Combining model"))
                    {
                        unknownRenderTypeModel = new CombinedModel(models);
                    }
                }

                try(IProfilerSection ignored2 = ProfilingManager.getInstance().withSection("Known render layer model building"))
                {

                    for (final RenderType chunkBufferLayer : RenderType.chunkBufferLayers())
                    {
                        try(IProfilerSection ignored3 = ProfilingManager.getInstance().withSection("Known render layer model building for: " + chunkBufferLayer.toString()))
                        {
                            ForgeHooksClient.setRenderLayer(chunkBufferLayer);
                            if (tileEntity.getStatistics().getStateCounts().isEmpty() ||
                                  (tileEntity.getStatistics().getStateCounts().size() == 1 && tileEntity.getStatistics().getStateCounts().containsKey(Blocks.AIR.defaultBlockState()))) {
                                continue;
                            }

                            BakedModel baked;
                            if (FluidRenderingManager.getInstance().isFluidRenderType(chunkBufferLayer))
                            {
                                try(IProfilerSection ignored4 = ProfilingManager.getInstance().withSection("Solid and fluid model building"))
                                {

                                    final ChiseledBlockBakedModel solidModel;
                                    try(IProfilerSection ignored5 = ProfilingManager.getInstance().withSection("Solid"))
                                    {
                                        solidModel = ChiseledBlockBakedModelManager.getInstance().get(
                                          tileEntity,
                                          tileEntity.getStatistics().getPrimaryState(),
                                          ChiselRenderType.fromLayer(chunkBufferLayer, false),
                                          neighborhood::getBlockState,
                                          neighborhood::getAreaAccessor,
                                          tileEntity.getBlockPos()
                                        );
                                    }

                                    final ChiseledBlockBakedModel fluidModel;
                                    try(IProfilerSection ignored5 = ProfilingManager.getInstance().withSection("Fluid"))
                                    {
                                        fluidModel = ChiseledBlockBakedModelManager.getInstance().get(
                                          tileEntity,
                                          tileEntity.getStatistics().getPrimaryState(),
                                          ChiselRenderType.fromLayer(chunkBufferLayer, true),
                                          neighborhood::getBlockState,
                                          neighborhood::getAreaAccessor,
                                          tileEntity.getBlockPos()
                                        );
                                    }

                                    try(IProfilerSection ignored5 = ProfilingManager.getInstance().withSection("Model combining"))
                                    {
                                        if (solidModel.isEmpty())
                                        {
                                            baked = fluidModel;
                                        }
                                        else if (fluidModel.isEmpty())
                                        {
                                            baked = solidModel;
                                        }
                                        else
                                        {
                                            baked = new CombinedModel(solidModel, fluidModel);
                                        }
                                    }

                                }
                            }
                            else
                            {
                                try(IProfilerSection ignored4 = ProfilingManager.getInstance().withSection("Solid or fluid model building"))
                                {
                                    baked = ChiseledBlockBakedModelManager.getInstance().get(
                                      tileEntity,
                                      tileEntity.getStatistics().getPrimaryState(),
                                      ChiselRenderType.fromLayer(chunkBufferLayer, false),
                                      neighborhood::getBlockState,
                                      neighborhood::getAreaAccessor,
                                      tileEntity.getBlockPos()
                                    );
                                }
                            }

                            renderTypedModels.put(chunkBufferLayer, baked);
                        }

                    }
                }

                ForgeHooksClient.setRenderLayer(currentType);
            }

            return new ModelDataMap.Builder()
              .withInitial(
                ModModelProperties.UNKNOWN_LAYER_MODEL_PROPERTY, unknownRenderTypeModel
              )
              .withInitial(
                ModModelProperties.KNOWN_LAYER_MODEL_PROPERTY, renderTypedModels
              )
              .build();
        }, this.recalculationService)
        .thenAcceptAsync(tileEntity::setModelData, this.recalculationService)
        .thenRunAsync(() -> {
            this.positionsInProcessing.remove(new ChunkPos(tileEntity.getBlockPos()), tileEntity.getBlockPos());
        }, this.recalculationService)
        .thenRunAsync(() -> {
            if (Minecraft.getInstance().level == tileEntity.getLevel()) {
                ModelDataManager.requestModelDataRefresh(tileEntity);
                Minecraft.getInstance().level.sendBlockUpdated(
                  tileEntity.getBlockPos(),
                  tileEntity.getBlockState(),
                  tileEntity.getBlockState(),
                  8
                );
            }
        }, Minecraft.getInstance());
    }

    private void ensureThreadPoolSetup() {
        if (recalculationService == null) {
            final ClassLoader classLoader = ChiselsAndBits.class.getClassLoader();
            final AtomicInteger genericThreadCounter = new AtomicInteger();
            recalculationService = Executors.newFixedThreadPool(
              IChiselsAndBitsAPI.getInstance().getConfiguration().getClient().modelBuildingThreadCount.get(),
              runnable -> {
                  final Thread thread = new Thread(runnable);
                  thread.setContextClassLoader(classLoader);
                  thread.setName(String.format("C&B Model builder #%s", genericThreadCounter.incrementAndGet()));
                  thread.setDaemon(true);
                  return thread;
              }
            );
        }
    }
}
