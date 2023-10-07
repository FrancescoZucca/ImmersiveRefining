package mod.francescozucca.immersiverefining.client;

import mod.francescozucca.immersiverefining.ImmersiveRefining;
import mod.francescozucca.immersiverefining.client.gui.TankHandledScreen;
import mod.francescozucca.immersiverefining.client.model.ModelProvider;
import mod.francescozucca.immersiverefining.client.renderer.TankEntityRenderer;
import mod.francescozucca.immersiverefining.fluid.BaseFluid;
import mod.francescozucca.immersiverefining.mixin.BucketItemMixin;
import mod.francescozucca.immersiverefining.util.References;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

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

        BlockRenderLayerMap.INSTANCE.putBlock(ImmersiveRefining.TANK_BLOCK, RenderLayer.getCutout());
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> new ModelProvider());
        BlockEntityRendererFactories.register(ImmersiveRefining.FLUID_TANK_BET, TankEntityRenderer::new);

        HandledScreens.register(ImmersiveRefining.TANK_SH, TankHandledScreen::new);

        ColorProviderRegistry.ITEM.register(
                (stack, tintIndex) -> tintIndex == 1 ? ((BaseFluid)((BucketItemMixin)stack.getItem()).getFluid()).getColor() : -1,
                ImmersiveRefining.CRUDE_OIL_BUCKET
        );

        ClientPlayNetworking.registerGlobalReceiver(References.TANK_PACKET_ID, (client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            NbtCompound nbt = buf.readNbt();
            client.execute(() -> client.world.getBlockEntity(pos).readNbt(nbt));
        });
    }
}
