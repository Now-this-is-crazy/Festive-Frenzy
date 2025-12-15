package powercyphe.festive_frenzy.mixin.snowloggable.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexSorting;
import net.minecraft.client.renderer.SectionBufferBuilderPack;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.client.renderer.chunk.SectionCompiler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.common.util.SnowLoggable;

@Mixin(SectionCompiler.class)
public abstract class SectionCompilerMixin {

    @Shadow
    @Final
    private BlockRenderDispatcher blockRenderer;

    @WrapOperation(method = "compile", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/BlockRenderDispatcher;renderBatched(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLnet/minecraft/util/RandomSource;)V"))
    private void festive_frenzy$renderSnowlogged(BlockRenderDispatcher instance, BlockState state, BlockPos blockPos, BlockAndTintGetter blockAndTintGetter, PoseStack poseStack, VertexConsumer vertexConsumer, boolean bl, RandomSource randomSource, Operation<Void> original,
                                                 SectionPos sectionPos, RenderChunkRegion renderChunkRegion, VertexSorting vertexSorting, SectionBufferBuilderPack sectionBufferBuilderPack) {
        Block block = state.getBlock();
        if (block instanceof SnowLoggable snowLoggable) {
            int layers = snowLoggable.getSnowLayers(state);

            if (layers > 0) {
                BlockState snowState = Blocks.SNOW.defaultBlockState()
                        .setValue(SnowLayerBlock.LAYERS, layers);
                this.blockRenderer.renderBatched(snowState, blockPos, renderChunkRegion, poseStack, vertexConsumer, true, randomSource);
            }
        }

        original.call(instance, state, blockPos, renderChunkRegion, poseStack, vertexConsumer, bl, randomSource);
    }
}
