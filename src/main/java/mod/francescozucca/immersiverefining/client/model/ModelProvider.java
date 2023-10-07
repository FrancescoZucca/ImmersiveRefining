package mod.francescozucca.immersiverefining.client.model;

import mod.francescozucca.immersiverefining.ImmersiveRefining;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ModelProvider implements ModelResourceProvider {
    @Override
    public @Nullable UnbakedModel loadModelResource(Identifier resourceId, ModelProviderContext context) throws ModelProviderException {
        if(resourceId.equals(ImmersiveRefining.id("block/tank")))
            return new TankModel();
        return null;
    }
}
