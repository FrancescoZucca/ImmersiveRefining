package mod.francescozucca.immersiverefining;

import mod.francescozucca.immersiverefining.block.TankBlock;
import mod.francescozucca.immersiverefining.block.entity.FluidTankBlockEntity;
import mod.francescozucca.immersiverefining.fluid.CrudeOil;
import mod.francescozucca.immersiverefining.util.References;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ImmersiveRefining implements ModInitializer {

    public static FlowableFluid CRUDE_OIL;
    public static FlowableFluid CRUDE_OIL_FLOWING;
    public static Item CRUDE_OIL_BUCKET;
    public static Block CRUDE_OIL_BLOCK;

    public static Block TANK_BLOCK;

    public static BlockEntityType<FluidTankBlockEntity> FLUID_TANK_BET;
    @Override
    public void onInitialize() {

        TANK_BLOCK = Registry.register(Registries.BLOCK, id("tank"), new TankBlock());
        FLUID_TANK_BET = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                id("tank_block_entity"),
                FabricBlockEntityTypeBuilder.create(FluidTankBlockEntity::new, TANK_BLOCK).build());

        FluidStorage.SIDED.registerForBlockEntity((tank, direction) -> tank.fluidStorage, FLUID_TANK_BET);

        CRUDE_OIL = Registry.register(Registries.FLUID, id("crude_oil"), new CrudeOil.Still());
        CRUDE_OIL_FLOWING = Registry.register(Registries.FLUID, id("flowing_crude_oil"), new CrudeOil.Flowing());
        CRUDE_OIL_BUCKET = Registry.register(Registries.ITEM, id("crude_oil_bucket"), new BucketItem(CRUDE_OIL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
        CRUDE_OIL_BLOCK = Registry.register(Registries.BLOCK, id("crude_oil"), new FluidBlock(CRUDE_OIL, FabricBlockSettings.copy(Blocks.WATER)));
    }

    public static Identifier id(String name){
        return new Identifier(References.MOD_ID, name);
    }
}
