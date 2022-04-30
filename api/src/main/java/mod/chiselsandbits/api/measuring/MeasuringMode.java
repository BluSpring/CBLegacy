package mod.chiselsandbits.api.measuring;

import mod.chiselsandbits.api.item.withmode.IToolMode;
import mod.chiselsandbits.platforms.core.util.constants.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static net.minecraft.world.item.DyeColor.*;

public enum MeasuringMode implements IToolMode<MeasuringType>
{
    WHITE_BIT(WHITE, MeasuringType.BIT, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.bit.white"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    ORANGE_BIT(ORANGE, MeasuringType.BIT, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.bit.orange"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    MAGENTA_BIT(MAGENTA, MeasuringType.BIT, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.bit.magenta"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    LIGHT_BLUE_BIT(LIGHT_BLUE, MeasuringType.BIT, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.bit.light-blue"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    YELLOW_BIT(YELLOW, MeasuringType.BIT, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.bit.yellow"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    LIME_BIT(LIME, MeasuringType.BIT, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.bit.lime"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    PINK_BIT(PINK, MeasuringType.BIT, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.bit.pink"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    GRAY_BIT(GRAY, MeasuringType.BIT, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.bit.gray"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    LIGHT_GRAY_BIT(LIGHT_GRAY, MeasuringType.BIT, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.bit.light-gray"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    CYAN_BIT(CYAN, MeasuringType.BIT, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.bit.cyan-gray"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    PURPLE_BIT(PURPLE, MeasuringType.BIT, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.bit.purple"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    BLUE_BIT(BLUE, MeasuringType.BIT, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.bit.blue"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    BROWN_BIT(BROWN, MeasuringType.BIT, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.bit.brown"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    GREEN_BIT(GREEN, MeasuringType.BIT, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.bit.green"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    RED_BIT(RED, MeasuringType.BIT, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.bit.red"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    BLACK_BIT(BLACK, MeasuringType.BIT, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.bit.black"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    WHITE_BLOCK(WHITE, MeasuringType.BLOCK, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.block.white"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    ORANGE_BLOCK(ORANGE, MeasuringType.BLOCK, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.block.orange"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    MAGENTA_BLOCK(MAGENTA, MeasuringType.BLOCK, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.block.magenta"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    LIGHT_BLUE_BLOCK(LIGHT_BLUE, MeasuringType.BLOCK, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.block.light-blue"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    YELLOW_BLOCK(YELLOW, MeasuringType.BLOCK, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.block.yellow"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    LIME_BLOCK(LIME, MeasuringType.BLOCK, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.block.lime"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    PINK_BLOCK(PINK, MeasuringType.BLOCK, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.block.pink"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    GRAY_BLOCK(GRAY, MeasuringType.BLOCK, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.block.gray"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    LIGHT_GRAY_BLOCK(LIGHT_GRAY, MeasuringType.BLOCK, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.block.light-gray"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    CYAN_BLOCK(CYAN, MeasuringType.BLOCK, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.block.cyan-gray"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    PURPLE_BLOCK(PURPLE, MeasuringType.BLOCK, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.block.purple"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    BLUE_BLOCK(BLUE, MeasuringType.BLOCK, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.block.blue"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    BROWN_BLOCK(BROWN, MeasuringType.BLOCK, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.block.brown"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    GREEN_BLOCK(GREEN, MeasuringType.BLOCK, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.block.green"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    RED_BLOCK(RED, MeasuringType.BLOCK, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.block.red"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    BLACK_BLOCK(BLACK, MeasuringType.BLOCK, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.block.black"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    WHITE_DISTANCE(WHITE, MeasuringType.DISTANCE, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.distance.white"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    ORANGE_DISTANCE(ORANGE, MeasuringType.DISTANCE, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.distance.orange"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    MAGENTA_DISTANCE(MAGENTA, MeasuringType.DISTANCE, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.distance.magenta"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    LIGHT_BLUE_DISTANCE(LIGHT_BLUE, MeasuringType.DISTANCE, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.distance.light-blue"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    YELLOW_DISTANCE(YELLOW, MeasuringType.DISTANCE, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.distance.yellow"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    LIME_DISTANCE(LIME, MeasuringType.DISTANCE, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.distance.lime"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    PINK_DISTANCE(PINK, MeasuringType.DISTANCE, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.distance.pink"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    GRAY_DISTANCE(GRAY, MeasuringType.DISTANCE, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.distance.gray"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    LIGHT_GRAY_DISTANCE(LIGHT_GRAY, MeasuringType.DISTANCE, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.distance.light-gray"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    CYAN_DISTANCE(CYAN, MeasuringType.DISTANCE, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.distance.cyan-gray"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    PURPLE_DISTANCE(PURPLE, MeasuringType.DISTANCE, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.distance.purple"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    BLUE_DISTANCE(BLUE, MeasuringType.DISTANCE, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.distance.blue"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    BROWN_DISTANCE(BROWN, MeasuringType.DISTANCE, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.distance.brown"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    GREEN_DISTANCE(GREEN, MeasuringType.DISTANCE, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.distance.green"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    RED_DISTANCE(RED, MeasuringType.DISTANCE, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.distance.red"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png")),
    BLACK_DISTANCE(BLACK, MeasuringType.DISTANCE, new TranslatableComponent(Constants.MOD_ID + ".measuring.types.distance.black"), new ResourceLocation(Constants.MOD_ID,"textures/icons/white.png"));

    private final DyeColor color;
    private final MeasuringType type;
    private final Component displayName;
    private final ResourceLocation icon;

    MeasuringMode(final DyeColor color, final MeasuringType type, final Component displayName, final ResourceLocation icon) {
        this.color = color;
        this.type = type;
        this.displayName = displayName;
        this.icon = icon;
    }

    @Override
    public @NotNull ResourceLocation getIcon()
    {
        return icon;
    }

    @Override
    public @NotNull Optional<MeasuringType> getGroup()
    {
        return Optional.of(getType());
    }

    @Override
    public boolean shouldRenderDisplayNameInMenu()
    {
        return false;
    }

    @Override
    public @NotNull Vec3 getColorVector()
    {
        final int color = getColor().getTextColor();

        float r = (float)(color >> 16 & 255) / 255.0F;
        float g = (float)(color >> 8 & 255) / 255.0F;
        float b = (float)(color & 255) / 255.0F;

        return new Vec3(r,g,b);
    }

    @Override
    public Component getDisplayName()
    {
        return this.displayName;
    }

    public DyeColor getColor()
    {
        return this.color;
    }

    public MeasuringType getType()
    {
        return this.type;
    }
}
