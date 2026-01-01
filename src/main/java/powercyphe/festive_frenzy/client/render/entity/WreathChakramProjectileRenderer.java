package powercyphe.festive_frenzy.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import powercyphe.festive_frenzy.client.render.entity.state.WreathChakramProjectileEntityRenderState;
import powercyphe.festive_frenzy.common.entity.WreathChakramProjectileEntity;

public class WreathChakramProjectileRenderer<T extends WreathChakramProjectileEntity, S extends WreathChakramProjectileEntityRenderState> extends EntityRenderer<T, WreathChakramProjectileEntityRenderState> {
    private final ItemModelResolver itemModelResolver;

    public WreathChakramProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemModelResolver = context.getItemModelResolver();
    }

    @Override
    public void extractRenderState(T entity, WreathChakramProjectileEntityRenderState state, float tickProgress) {
        super.extractRenderState(entity, state, tickProgress);
        ItemStack chakramStack = (entity.getPickupItem().isEmpty() ? entity.getDefaultPickupItem() : entity.getPickupItem()).copy();
        chakramStack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, entity.getData().isEnchanted());

        this.itemModelResolver.updateForNonLiving(state.chakramState, chakramStack, ItemDisplayContext.NONE,
                (entity.getOwner() instanceof LivingEntity livingEntity) ? livingEntity : null);

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

        state.chakramState.render(poseStack, multiBufferSource, light, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        super.render(state, poseStack, multiBufferSource, light);
    }
}
