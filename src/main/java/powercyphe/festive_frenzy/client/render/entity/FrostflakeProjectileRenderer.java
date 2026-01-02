package powercyphe.festive_frenzy.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import powercyphe.festive_frenzy.client.FestiveFrenzyClient;
import powercyphe.festive_frenzy.client.render.entity.model.FrostflakeProjectileEntityModel;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.entity.FrostflakeProjectileEntity;

public class FrostflakeProjectileRenderer<T extends FrostflakeProjectileEntity, S extends EntityRenderState> extends EntityRenderer<T, EntityRenderState> {
    private final FrostflakeProjectileEntityModel model;

    public FrostflakeProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new FrostflakeProjectileEntityModel(context.bakeLayer(FestiveFrenzyClient.FROSTFLAKE));
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public void submit(EntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        submitNodeCollector.submitModel(this.model, state, poseStack, RenderType.entityCutout(this.getTextureLocation()),
                LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, state.outlineColor, null);

        poseStack.popPose();
        super.submit(state, poseStack, submitNodeCollector, cameraRenderState);
    }

    public ResourceLocation getTextureLocation() {
        return FestiveFrenzy.id("textures/entity/frostflake.png");
    }
}
