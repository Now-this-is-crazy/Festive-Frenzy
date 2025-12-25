package powercyphe.festive_frenzy.mixin.snowloggable;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.common.util.SnowLoggable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockBehavior$BlockStateBaseMixin {

    @Shadow
    public abstract Block getBlock();

    @Shadow
    protected abstract BlockState asState();

    @ModifyExpressionValue(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;getCollisionShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;"))
    private VoxelShape festive_frenzy$snowloggableCollisionShape(VoxelShape original, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (this.getBlock() instanceof SnowLoggable snowLoggable) {
            int layers = snowLoggable.getSnowLayers(this.asState());
            original = Shapes.or(original, snowLoggable.getShapeByLayers(layers-1));
        }

        return original;
    }

    @ModifyReturnValue(method = "getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("RETURN"))
    private VoxelShape festive_frenzy$snowloggableShape(VoxelShape original, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (this.getBlock() instanceof SnowLoggable snowLoggable) {
            original = Shapes.or(original, snowLoggable.getSnowLayerShape(this.asState(), blockPos));
        }

        return original;
    }
}
