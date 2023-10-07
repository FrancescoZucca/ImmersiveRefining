package mod.francescozucca.immersiverefining.block;

import com.google.common.collect.ImmutableMap;
import mod.francescozucca.immersiverefining.block.entity.FluidTankBlockEntity;
import mod.francescozucca.immersiverefining.mixin.BucketItemMixin;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class TankBlock extends BlockWithEntity {
    public TankBlock() {
        super(Settings.create().requiresTool().nonOpaque());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FluidTankBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 1, 0.875);
    }

    @Override
    public boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient){
            Storage<FluidVariant> storage = FluidStorage.SIDED.find(world, pos, hit.getSide());
            if(player.getMainHandStack().getItem() instanceof BucketItem bi && storage != null) {
                try(Transaction transaction = Transaction.openOuter()){
                    long amount;
                    boolean extract = bi == Items.BUCKET;
                    FluidVariant variant;
                    if(extract){
                        variant = storage.nonEmptyIterator().next().getResource();
                        amount = storage.extract(variant, FluidConstants.BUCKET/81, transaction);
                    }else{
                        variant = FluidVariant.of(((BucketItemMixin)bi).getFluid());
                        amount = storage.insert(variant, FluidConstants.BUCKET/81, transaction);
                    }
                    if(amount == FluidConstants.BUCKET/81){
                        transaction.commit();
                        ItemStack item = player.getMainHandStack();
                        item.decrement(1);
                        if(extract){
                            player.giveItemStack(new ItemStack(variant.getFluid().getBucketItem()));
                        }else{
                            player.giveItemStack(new ItemStack(Items.BUCKET));
                        }
                    }else{
                        NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
                        if (screenHandlerFactory != null) {
                            player.openHandledScreen(screenHandlerFactory);
                        }
                    }
                }
            }else{
                NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
                if (screenHandlerFactory != null) {
                    player.openHandledScreen(screenHandlerFactory);
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.getBlock() != newState.getBlock()){
            BlockEntity be = world.getBlockEntity(pos);
            if(be instanceof FluidTankBlockEntity tbe){
                ItemScatterer.spawn(world, pos, tbe);
                world.updateComparators(pos, this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
