package powercyphe.festive_frenzy.mixin.snowloggable.client;

import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BlockStateModel;
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

@Mixin(TerrainRenderContext.class)
public abstract class SectionCompilerMixin {

    @Shadow
    public abstract void bufferModel(BlockStateModel model, BlockState blockState, BlockPos blockPos);

    @Inject(method = "bufferModel", at = @At("HEAD"))
    private void festive_frenzy$renderSnowlogged(BlockStateModel model, BlockState state, BlockPos blockPos, CallbackInfo ci) {
        Block block = state.getBlock();
        if (block instanceof SnowLoggable snowLoggable) {
            int layers = snowLoggable.getSnowLayers(state);

            if (layers > 0) {
                BlockState snowState = Blocks.SNOW.defaultBlockState()
                        .setValue(SnowLayerBlock.LAYERS, layers);
                BlockStateModel snowModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(snowState);
                this.bufferModel(snowModel, snowState, blockPos);
            }
        }

    }
}
