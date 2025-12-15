package powercyphe.festive_frenzy.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
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
    public void render(EntityRenderState state, PoseStack poseStack, MultiBufferSource multiBufferSource, int light) {
        poseStack.pushPose();

        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutout(this.getTextureLocation()));
        this.model.setupAnim(state);
        this.model.renderToBuffer(poseStack, vertexConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
        super.render(state, poseStack, multiBufferSource, light);
    }

    public ResourceLocation getTextureLocation() {
        return FestiveFrenzy.id("textures/entity/frostflake.png");
    }
}
