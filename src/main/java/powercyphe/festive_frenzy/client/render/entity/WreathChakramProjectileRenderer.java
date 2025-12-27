package powercyphe.festive_frenzy.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import powercyphe.festive_frenzy.client.render.entity.state.ThrownBaubleProjectileEntityRenderState;
import powercyphe.festive_frenzy.client.render.entity.state.WreathChakramProjectileEntityRenderState;
import powercyphe.festive_frenzy.common.entity.ThrownBaubleProjectileEntity;
import powercyphe.festive_frenzy.common.entity.WreathChakramProjectileEntity;
import powercyphe.festive_frenzy.common.registry.FFBlocks;
import powercyphe.festive_frenzy.common.registry.FFItems;

public class WreathChakramProjectileRenderer<T extends WreathChakramProjectileEntity, S extends WreathChakramProjectileEntityRenderState> extends EntityRenderer<T, WreathChakramProjectileEntityRenderState> {
    private final ItemRenderer itemRenderer;

    public WreathChakramProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void extractRenderState(T entity, WreathChakramProjectileEntityRenderState state, float tickProgress) {
        super.extractRenderState(entity, state, tickProgress);
        state.chakramStack = (entity.getPickupItem().isEmpty() ? entity.getDefaultPickupItem() : entity.getPickupItem()).copy();
        state.chakramStack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, entity.getData().isEnchanted());

        state.bakedModel = this.itemRenderer.getModel(state.chakramStack, entity.level(),
                (entity.getOwner() instanceof LivingEntity livingEntity) ? livingEntity : null , 0);

        state.rotation = new Vec2(entity.getXRot(tickProgress) * 0.5F, entity.getYRot(tickProgress));
        state.spinRotation = entity.getSpinRotation(tickProgress);
    }

    @Override
    public WreathChakramProjectileEntityRenderState createRenderState() {
        return new WreathChakramProjectileEntityRenderState();
    }

    @Override
    public void render(WreathChakramProjectileEntityRenderState state, PoseStack poseStack, MultiBufferSource multiBufferSource, int light) {
        poseStack.pushPose();
        poseStack.translate(0, 0.1, 0);

        poseStack.mulPose(Axis.YP.rotationDegrees(-state.rotation.y + (float) Math.toDegrees(state.spinRotation)));
        poseStack.mulPose(Axis.XP.rotationDegrees(-state.rotation.x + 90F));

        this.itemRenderer.render(state.chakramStack, ItemDisplayContext.NONE, false, poseStack, multiBufferSource, light, OverlayTexture.NO_OVERLAY, state.bakedModel);
        poseStack.popPose();

        super.render(state, poseStack, multiBufferSource, light);
    }
}
