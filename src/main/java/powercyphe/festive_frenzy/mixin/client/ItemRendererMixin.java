package powercyphe.festive_frenzy.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import powercyphe.festive_frenzy.client.FestiveFrenzyClient;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow @Final private ModelManager modelManager;

    @ModifyVariable(method = "renderSimpleItemModel", at = @At("HEAD"), index = 8, argsOnly = true)
    public BakedModel festive_frenzy$renderGuiTexture(BakedModel original, ItemStack stack, ItemDisplayContext context, boolean leftHanded, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, BakedModel bakedModel, boolean useInventoryModel) {
        if (useInventoryModel) {
            for (Tuple<Item, ModelResourceLocation> pair : FestiveFrenzyClient.GUI_MODELS) {
                Item item = pair.getA();
                ModelResourceLocation model = pair.getB();
                if (stack.is(item)) {
                    return this.modelManager.getModel(model);
                }
            }
        }

        return original;
    }
}