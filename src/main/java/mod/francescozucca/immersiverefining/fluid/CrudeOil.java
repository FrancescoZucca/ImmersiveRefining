package mod.francescozucca.immersiverefining.fluid;

import mod.francescozucca.immersiverefining.ImmersiveRefining;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public abstract class CrudeOil extends BaseFluid{
    @Override
    public Fluid getFlowing() {
        return ImmersiveRefining.CRUDE_OIL_FLOWING;
    }

    @Override
    public Fluid getStill() {
        return ImmersiveRefining.CRUDE_OIL;
    }

    @Override
    public Item getBucketItem() {
        return ImmersiveRefining.CRUDE_OIL_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ImmersiveRefining.CRUDE_OIL_BLOCK.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    public static class Flowing extends CrudeOil{
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState state) {
            return false;
        }
    }

    public static class Still extends CrudeOil{

        @Override
        public int getLevel(FluidState state) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState state) {
            return true;
        }
    }
}
