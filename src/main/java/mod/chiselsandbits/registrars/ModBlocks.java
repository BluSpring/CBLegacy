package mod.chiselsandbits.registrars;

import com.google.common.collect.Maps;
import mod.chiselsandbits.aabb.AABBManager;
import mod.chiselsandbits.api.util.constants.Constants;
import mod.chiselsandbits.block.BitStorageBlock;
import mod.chiselsandbits.block.ChiseledBlock;
import mod.chiselsandbits.block.ChiseledPrinterBlock;
import mod.chiselsandbits.block.ModificationTableBlock;
import mod.chiselsandbits.materials.MaterialManager;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public final class ModBlocks
{


    private static final DeferredRegister<Block>             BLOCK_REGISTRAR  = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

    public static final Map<Material, RegistryObject<ChiseledBlock>>     MATERIAL_TO_BLOCK_CONVERSIONS = Maps.newHashMap();

    public static final RegistryObject<BitStorageBlock> BIT_STORAGE = BLOCK_REGISTRAR.register("bit_storage", () -> new BitStorageBlock(AbstractBlock.Properties.of(Material.METAL)
                                                                                                                                                .strength(1.5F, 6.0F)
                                                                                                                                                .harvestTool(ToolType.AXE)
                                                                                                                                                .harvestLevel(1)
                                                                                                                                                .requiresCorrectToolForDrops()
                                                                                                                                                .dynamicShape()
                                                                                                                                                .noOcclusion()
                                                                                                                                                .isValidSpawn((p_test_1_, p_test_2_, p_test_3_, p_test_4_) -> false)
                                                                                                                                                .isRedstoneConductor((p_test_1_, p_test_2_, p_test_3_) -> false)
                                                                                                                                                .isSuffocating((p_test_1_, p_test_2_, p_test_3_) -> false)
                                                                                                                                                .isViewBlocking((p_test_1_, p_test_2_, p_test_3_) -> false)));

    public static final RegistryObject<ModificationTableBlock> MODIFICATION_TABLE = BLOCK_REGISTRAR.register("modification_table", () -> new ModificationTableBlock(AbstractBlock.Properties.of(Material.METAL)
                                                                                                                                                .strength(1.5F, 6.0F)
                                                                                                                                                .harvestTool(ToolType.AXE)
                                                                                                                                                .harvestLevel(1)
                                                                                                                                                .requiresCorrectToolForDrops()
                                                                                                                                                .dynamicShape()
                                                                                                                                                .noOcclusion()
                                                                                                                                                .isValidSpawn((p_test_1_, p_test_2_, p_test_3_, p_test_4_) -> false)
                                                                                                                                                .isRedstoneConductor((p_test_1_, p_test_2_, p_test_3_) -> false)
                                                                                                                                                .isSuffocating((p_test_1_, p_test_2_, p_test_3_) -> false)
                                                                                                                                                .isViewBlocking((p_test_1_, p_test_2_, p_test_3_) -> false)));

    public static final RegistryObject<ChiseledPrinterBlock> CHISELED_PRINTER = BLOCK_REGISTRAR.register(
      "chiseled_printer",
      () -> new ChiseledPrinterBlock(AbstractBlock.Properties.of(Material.METAL)
                                         .strength(1.5F, 6.0F)
                                         .harvestTool(ToolType.PICKAXE)
                                         .harvestLevel(1)
                                         .requiresCorrectToolForDrops()
                                         .dynamicShape()
                                         .noOcclusion()
                                         .isValidSpawn((p_test_1_, p_test_2_, p_test_3_, p_test_4_) -> false)
                                         .isRedstoneConductor((p_test_1_, p_test_2_, p_test_3_) -> false)
                                         .isSuffocating((p_test_1_, p_test_2_, p_test_3_) -> false)
                                         .isViewBlocking((p_test_1_, p_test_2_, p_test_3_) -> false))
    );



    private ModBlocks()
    {
        throw new IllegalStateException("Tried to initialize: ModBlocks but this is a Utility class.");
    }


    public static void onModConstruction() {
        BLOCK_REGISTRAR.register(FMLJavaModLoadingContext.get().getModEventBus());

        MaterialManager.getInstance()
          .getKnownMaterials()
          .forEach((name, material) -> MATERIAL_TO_BLOCK_CONVERSIONS.put(
            material,
            BLOCK_REGISTRAR.register(
              "chiseled" + name,
              () -> new ChiseledBlock(AbstractBlock.Properties
                                         .of(material)
                                         .strength(1.5f, 6f)
                                         .isViewBlocking((p_test_1_, p_test_2_, p_test_3_) -> false)
                                         .isRedstoneConductor((p_test_1_, p_test_2_, p_test_3_) -> false)
                                         .noOcclusion())
            )
          ));
    }
}
