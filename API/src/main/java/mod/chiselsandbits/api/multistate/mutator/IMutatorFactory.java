package mod.chiselsandbits.api.multistate.mutator;

import mod.chiselsandbits.api.IChiselsAndBitsAPI;
import mod.chiselsandbits.api.multistate.mutator.world.IWorldAreaMutator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;

/**
 * Allows for the creation of new mutators used to modify chiselable areas.
 */
public interface IMutatorFactory
{

    /**
     * Gives access to the the mutator factory.
     * @return The mutator factory.
     */
    static IMutatorFactory getInstance() {
        return IChiselsAndBitsAPI.getInstance().getMutatorFactory();
    }

    /**
     * Creates a mutator which mutates a particular block only.
     *
     * @param world The world to mutate in.
     * @param pos The position to mutate.
     * @return The mutator.
     */
    IWorldAreaMutator in(
      final LevelAccessor world,
      final BlockPos pos
    );

    /**
     * Creates a mutator which mutates a given area.
     *
     * @param world The world to mutate in.
     * @param from The block to function as a start point.
     * @param to The block to function as an end point.
     * @return The mutator.
     */
    IWorldAreaMutator covering(
      final LevelAccessor world,
      final BlockPos from,
      final BlockPos to
    );

    /**
     * Creates a mutator which mutates a given area.
     *
     * @param world The world to mutate in.
     * @param from The start point.
     * @param to The end point.
     * @return The mutator.
     */
    IWorldAreaMutator covering(
      final LevelAccessor world,
      final Vec3 from,
      final Vec3 to
    );
}
