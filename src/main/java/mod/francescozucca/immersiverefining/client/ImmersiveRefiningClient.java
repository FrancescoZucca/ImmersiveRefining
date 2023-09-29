package mod.francescozucca.immersiverefining.client;

import mod.francescozucca.immersiverefining.ImmersiveRefining;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ImmersiveRefiningClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        FluidRenderHandlerRegistry.INSTANCE.register(ImmersiveRefining.CRUDE_OIL, ImmersiveRefining.CRUDE_OIL_FLOWING, new SimpleFluidRenderHandler(
                new Identifier("minecraft:block/water_still"),
                new Identifier("minecraft:block/water_flow"),
                0x101010
        ));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getSolid(), ImmersiveRefining.CRUDE_OIL, ImmersiveRefining.CRUDE_OIL_FLOWING);

    }
}
