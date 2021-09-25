package mod.chiselsandbits.client.events;

import mod.chiselsandbits.api.util.constants.Constants;
import mod.chiselsandbits.block.entities.ChiseledPrinterBlockEntity;
import mod.chiselsandbits.client.screens.BitBagScreen;
import mod.chiselsandbits.client.screens.ChiseledPrinterScreen;
import mod.chiselsandbits.client.screens.ModificationTableScreen;
import mod.chiselsandbits.registrars.ModContainerTypes;
import mod.chiselsandbits.registrars.ModItems;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInitHandler
{

    @SubscribeEvent
    public static void onFMLClientSetup(final FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            ScreenManager.register(
              ModContainerTypes.BIT_BAG.get(),
              BitBagScreen::new
            );
            ScreenManager.register(
              ModContainerTypes.MODIFICATION_TABLE.get(),
              ModificationTableScreen::new
            );
            ScreenManager.register(
              ModContainerTypes.CHISELED_PRINTER_CONTAINER.get(),
              ChiseledPrinterScreen::new
            );
        });

        event.enqueueWork(() -> {
            ItemModelsProperties.register(ModItems.MEASURING_TAPE.get(), new ResourceLocation(Constants.MOD_ID, "is_measuring"), (stack, clientWorld, livingEntity) -> {
                if (stack.getItem() != ModItems.MEASURING_TAPE.get())
                    return 0;

                return ModItems.MEASURING_TAPE.get().getStart(stack).isPresent() ? 1 : 0;
            });
        });
    }
}
