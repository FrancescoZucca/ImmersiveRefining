package mod.francescozucca.immersiverefining.data;

import mod.francescozucca.immersiverefining.ImmersiveRefining;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.client.Model;

import java.util.Optional;

public class DataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ItemModelGenerator::new);
    }

    public static Model item(String parent){
        return new Model(Optional.of(ImmersiveRefining.id("item/"+parent)), Optional.empty());
    }
}
