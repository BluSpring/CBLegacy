package mod.chiselsandbits.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import mod.chiselsandbits.api.IChiselsAndBitsAPI;
import mod.chiselsandbits.api.util.constants.Constants;
import mod.chiselsandbits.registrars.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@JeiPlugin
public class JEIPlugin implements IModPlugin
{

    private static final Logger LOGGER = LogManager.getLogger();

    public static final ResourceLocation UID = new ResourceLocation(Constants.MOD_ID, ModIds.JEI_ID);

    @Override
    public @NotNull ResourceLocation getPluginUid()
    {
        return UID;
    }

    @Override
    public void onRuntimeAvailable(final IJeiRuntime jeiRuntime)
    {
        LOGGER.info("JEI Runtime is now available.");
        IIngredientManager ingredientManager = jeiRuntime.getIngredientManager();

        if (IChiselsAndBitsAPI.getInstance().getConfiguration().getClient().injectIntoJEI.get()) {
            final NonNullList<ItemStack> bitStacks = NonNullList.create();
            ModItems.ITEM_BLOCK_BIT.get().fillItemCategory(ModItems.ITEM_BLOCK_BIT.get().getItemCategory(), bitStacks);
            if (!bitStacks.isEmpty()) {
                LOGGER.info("Injecting bits.");
                ingredientManager.addIngredientsAtRuntime(VanillaTypes.ITEM, bitStacks);
            }
        }
    }
}
