package mod.francescozucca.immersiverefining.data;

import mod.francescozucca.immersiverefining.ImmersiveRefining;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;

public class ItemModelGenerator extends FabricModelProvider {
    public ItemModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(net.minecraft.data.client.ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ImmersiveRefining.CRUDE_OIL_BUCKET, DataGenerator.item("bucket"));
    }
}
