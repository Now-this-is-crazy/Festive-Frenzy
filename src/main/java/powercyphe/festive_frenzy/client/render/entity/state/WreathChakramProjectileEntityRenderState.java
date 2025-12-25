package powercyphe.festive_frenzy.client.render.entity.state;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class WreathChakramProjectileEntityRenderState extends EntityRenderState {
    public ItemStack chakramStack;
    public BakedModel bakedModel;

    public Vec2 rotation;
    public float spinRotation;
}
