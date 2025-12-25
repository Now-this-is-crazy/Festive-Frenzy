package powercyphe.festive_frenzy.mixin.client.compat.sodium;

import net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.festive_frenzy.common.util.SnowLoggable;

@Mixin(BlockRenderer.class)
public abstract class BlockRendererMixin {

    @Shadow
    public abstract void renderModel(BakedModel model, BlockState state, BlockPos pos, BlockPos origin);

    @Inject(method = "renderModel", at = @At("TAIL"))
    private void festive_frenzy$snowloggable(BakedModel model, BlockState state, BlockPos pos, BlockPos origin, CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();
        BlockRenderDispatcher blockRenderer = client.getBlockRenderer();

        Block block = state.getBlock();
        if (block instanceof SnowLoggable snowLoggable) {
            int layers = snowLoggable.getSnowLayers(state);

            if (layers > 0) {
                BlockState snowState = Blocks.SNOW.defaultBlockState()
                        .setValue(SnowLayerBlock.LAYERS, layers);
                this.renderModel(blockRenderer.getBlockModel(snowState), snowState, pos, origin);
            }
        }

    }
}
