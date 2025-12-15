package powercyphe.festive_frenzy.client.render.entity.state;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;

public class ThrownBaubleProjectileEntityRenderState extends EntityRenderState {
    public BakedModel bakedModel;
    public ItemStack baubleStack;

    public boolean isGlowing;

    public double rotationX;
    public double rotationY;
    public double rotationZ;
}
