package mod.francescozucca.immersiverefining.client.renderer;

import mod.francescozucca.immersiverefining.block.entity.FluidTankBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class TankEntityRenderer implements BlockEntityRenderer<FluidTankBlockEntity> {

    public TankEntityRenderer(BlockEntityRendererFactory.Context ctx){}
    @Override
    public void render(FluidTankBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        float h = (float) entity.getAmount() / 8000f;
        
    }
}
