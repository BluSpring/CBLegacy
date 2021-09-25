package mod.chiselsandbits.data.tags;

import com.google.common.collect.Lists;
import com.ldtteam.datagenerators.tags.TagJson;
import mod.chiselsandbits.api.util.constants.Constants;
import mod.chiselsandbits.registrars.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChiselTagGenerator implements IDataProvider
{
    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event)
    {
        event.getGenerator().addProvider(new ChiselTagGenerator(event.getGenerator()));
    }

    private final DataGenerator generator;

    private ChiselTagGenerator(final DataGenerator generator) {this.generator = generator;}

    @Override
    public void run(@NotNull final DirectoryCache cache) throws IOException
    {
        final TagJson json = new TagJson();
        json.setValues(
          Lists.newArrayList(
            ModItems.ITEM_CHISEL_DIAMOND.getId().toString(),
            ModItems.ITEM_CHISEL_GOLD.getId().toString(),
            ModItems.ITEM_CHISEL_IRON.getId().toString(),
            ModItems.ITEM_CHISEL_STONE.getId().toString(),
            ModItems.ITEM_CHISEL_NETHERITE.getId().toString()
          )
        );

        final Path tagFolder = this.generator.getOutputFolder().resolve(Constants.DataGenerator.ITEM_TAGS_DIR);
        final Path chiselableTagPath = tagFolder.resolve("chisel.json");

        IDataProvider.save(Constants.DataGenerator.GSON, cache, json.serialize(), chiselableTagPath);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Chisel tag generator";
    }
}
