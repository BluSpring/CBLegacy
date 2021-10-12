package mod.chiselsandbits.registrars;

import mod.chiselsandbits.api.modification.operation.IModificationOperationGroup;
import mod.chiselsandbits.api.util.LocalStrings;
import mod.chiselsandbits.api.util.constants.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class ModModificationOperationGroups
{

    private ModModificationOperationGroups()
    {
        throw new IllegalStateException("Can not instantiate an instance of: ModChiselModeGroups. This is a utility class");
    }

    public static IModificationOperationGroup ROTATE = new IModificationOperationGroup() {
        @Override
        public @NotNull ResourceLocation getIcon()
        {
            return new ResourceLocation(Constants.MOD_ID,"textures/icons/rotate.png");
        }

        @Override
        public Component getDisplayName()
        {
            return LocalStrings.PatternModificationGroupRotate.getText();
        }
    };

    public static IModificationOperationGroup MIRROR = new IModificationOperationGroup() {
        @Override
        public @NotNull ResourceLocation getIcon()
        {
            return new ResourceLocation(Constants.MOD_ID,"textures/icons/mirror.png");
        }

        @Override
        public Component getDisplayName()
        {
            return LocalStrings.PatternModificationGroupMirror.getText();
        }
    };
}