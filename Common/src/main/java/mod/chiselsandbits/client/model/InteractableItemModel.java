package mod.chiselsandbits.client.model;

import com.mojang.datafixers.util.Pair;
import mod.chiselsandbits.client.model.baked.interactable.InteractableBakedItemModel;
import mod.chiselsandbits.platforms.core.client.models.loaders.IModelSpecification;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

public class InteractableItemModel implements IModelSpecification<InteractableItemModel>
{
    private static final Logger LOGGER = LogManager.getLogger();

    private ResourceLocation innerModelLocation;
    private UnbakedModel innerModel;

    public InteractableItemModel(final ResourceLocation innerModelLocation)
    {
        this.innerModelLocation = innerModelLocation;
    }

    @Override
    public BakedModel bake(
      final ModelBakery bakery,
      final Function<Material, TextureAtlasSprite> spriteGetter,
      final ModelState modelTransform,
      final ResourceLocation modelLocation)
    {
        final BakedModel innerBakedModel = this.innerModel.bake(
          bakery,
          spriteGetter,
          modelTransform,
          innerModelLocation
        );

        return new InteractableBakedItemModel(innerBakedModel);
    }

    @Override
    public Collection<Material> getTextures(
      final Function<ResourceLocation, UnbakedModel> modelGetter, final Set<Pair<String, String>> missingTextureErrors)
    {
        this.innerModel = modelGetter.apply(this.innerModelLocation);
        if (this.innerModel == null) {
            LOGGER.warn("No parent '{}' while loading model '{}'", this.innerModelLocation, this);
        }

        if (this.innerModel == null) {
            this.innerModelLocation = ModelBakery.MISSING_MODEL_LOCATION;
            this.innerModel = modelGetter.apply(this.innerModelLocation);
        }

        if (!(this.innerModel instanceof BlockModel)) {
            throw new IllegalStateException("BlockModel parent has to be a block model.");
        }

        return innerModel.getMaterials(modelGetter, missingTextureErrors);
    }
}
