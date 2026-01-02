package powercyphe.festive_frenzy.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import powercyphe.festive_frenzy.client.render.entity.state.ThrownBaubleProjectileEntityRenderState;
import powercyphe.festive_frenzy.common.entity.ThrownBaubleProjectileEntity;
import powercyphe.festive_frenzy.common.registry.FFBlocks;

public class ThrownBaubleProjectileEntityRenderer<T extends ThrownBaubleProjectileEntity, S extends ThrownBaubleProjectileEntityRenderState> extends EntityRenderer<T, ThrownBaubleProjectileEntityRenderState> {
    private final BlockRenderDispatcher blockRenderDispatcher;

    public ThrownBaubleProjectileEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.blockRenderDispatcher = context.getBlockRenderDispatcher();
    }

    @Override
    public void extractRenderState(T entity, ThrownBaubleProjectileEntityRenderState state, float tickProgress) {
        super.extractRenderState(entity, state, tickProgress);

        ItemStack stack = entity.getItem().isEmpty() ? FFBlocks.WHITE_BAUBLE.asItem().getDefaultInstance() : entity.getItem();
        if (stack.getItem() instanceof BlockItem blockItem) {
            state.baubleState = blockItem.getBlock().defaultBlockState();
        } else {
            state.baubleState = FFBlocks.WHITE_BAUBLE.defaultBlockState();
        }

        state.baubleModel = this.blockRenderDispatcher.getBlockModel(state.baubleState);
        state.isGlowing = entity.getData().isGlowing();

        Vec3 rotation = entity.getRotation();
        Vec3 lastRotation = entity.getLastRotation();

        state.rotationX = Mth.lerp(tickProgress, lastRotation.x(), rotation.x());
        state.rotationY = Mth.lerp(tickProgress, lastRotation.y(), rotation.y());
        state.rotationZ = Mth.lerp(tickProgress, lastRotation.z(), rotation.z());
    }

    @Override
    public ThrownBaubleProjectileEntityRenderState createRenderState() {
        return new ThrownBaubleProjectileEntityRenderState();
    }

    @Override
    public void submit(ThrownBaubleProjectileEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (state.baubleState != null) {
            poseStack.pushPose();
            poseStack.translate(0,  0.1375, 0);

            poseStack.mulPose(Axis.XP.rotation((float) state.rotationX));
            poseStack.mulPose(Axis.YP.rotation((float) state.rotationY));
            poseStack.mulPose(Axis.ZP.rotation((float) state.rotationZ));

            poseStack.translate(-0.5, -0.5, -0.5);

            submitNodeCollector.submitBlockModel(poseStack, ItemBlockRenderTypes.getRenderType(state.baubleState),
                    state.baubleModel, 255, 255, 255, state.isGlowing ? LightTexture.FULL_BRIGHT : state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
            poseStack.popPose();
        }
        super.submit(state, poseStack, submitNodeCollector, cameraRenderState);
    }
}
