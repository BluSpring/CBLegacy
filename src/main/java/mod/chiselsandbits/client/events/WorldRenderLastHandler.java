package mod.chiselsandbits.client.events;

import mod.chiselsandbits.api.item.withhighlight.IWithHighlightItem;
import mod.chiselsandbits.api.util.constants.Constants;
import mod.chiselsandbits.client.render.MeasurementRenderer;
import mod.chiselsandbits.utils.ItemStackUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class WorldRenderLastHandler
{

    @SubscribeEvent
    public static void renderCustomWorldHighlight(final RenderWorldLastEvent event)
    {
        final Player playerEntity = Minecraft.getInstance().player;
        if (playerEntity == null)
            return;

        final ItemStack heldStack = ItemStackUtils.getHighlightItemStackFromPlayer(playerEntity);
        if (heldStack.isEmpty())
            return;

        final Item heldItem = heldStack.getItem();
        if (!(heldItem instanceof IWithHighlightItem))
            return;

        final IWithHighlightItem withHighlightItem = (IWithHighlightItem) heldItem;
        if (withHighlightItem.shouldDrawDefaultHighlight(playerEntity))
            return;

        withHighlightItem.renderHighlight(
          playerEntity,
          event.getContext(),
          event.getMatrixStack(),
          event.getPartialTicks(),
          event.getProjectionMatrix(),
          event.getFinishTimeNano()
        );
    }


    @SubscribeEvent
    public static void renderMeasurements(final RenderWorldLastEvent event)
    {
        MeasurementRenderer.getInstance().renderMeasurements(event);
    }
}
