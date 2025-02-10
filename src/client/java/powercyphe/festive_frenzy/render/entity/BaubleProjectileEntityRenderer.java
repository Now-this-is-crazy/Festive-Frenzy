package powercyphe.festive_frenzy.render.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import powercyphe.festive_frenzy.entity.BaubleProjectileEntity;
import powercyphe.festive_frenzy.item.BaubleBlockItem;
import powercyphe.festive_frenzy.mixin.accessor.ItemRendererAccessor;

public class BaubleProjectileEntityRenderer extends EntityRenderer<BaubleProjectileEntity> {
    private final BlockRenderManager blockRenderManager;
    private final ItemRenderer itemRenderer;

    public BaubleProjectileEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.shadowRadius = 0.15F;
        this.blockRenderManager = ctx.getBlockRenderManager();
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(BaubleProjectileEntity baubleProjectile, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        ItemStack itemStack = baubleProjectile.getStack();
        if (itemStack.getItem() instanceof BaubleBlockItem blockItem) {
            Block block = blockItem.getBlock();
            if (block != null) {
                matrixStack.push();
                matrixStack.peek().getPositionMatrix().translate(0f, 0.125f, 0f);
                BlockState blockState = block.getDefaultState();
                matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(baubleProjectile.age * baubleProjectile.getRotationRandom().x));
                matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(baubleProjectile.age * baubleProjectile.getRotationRandom().y));
                matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(baubleProjectile.age * baubleProjectile.getRotationRandom().z));
                matrixStack.scale(0.6f, 0.6f, 0.6f);
                matrixStack.translate(-0.5F, -0.5F, -0.5F);
                BakedModel model = this.blockRenderManager.getModel(blockState);
                model.getTransformation().getTransformation(ModelTransformationMode.NONE).apply(false, matrixStack);
                RenderLayer renderLayer = RenderLayers.getEntityBlockLayer(blockState, false);
                ((ItemRendererAccessor) this.itemRenderer).festive_frenzy$renderBakedItemModel(model, itemStack, light, OverlayTexture.DEFAULT_UV, matrixStack, ItemRenderer.getItemGlintConsumer(vertexConsumerProvider, renderLayer, false, false));
                matrixStack.pop();
                super.render(baubleProjectile, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
            }
        }
    }

    @Override
    public Identifier getTexture(BaubleProjectileEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
