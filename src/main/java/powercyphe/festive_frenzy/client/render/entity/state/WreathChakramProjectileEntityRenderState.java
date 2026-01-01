package powercyphe.festive_frenzy.client.render.entity.state;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.phys.Vec2;

public class WreathChakramProjectileEntityRenderState extends EntityRenderState {
    public ItemStackRenderState chakramState = new ItemStackRenderState();

    public Vec2 rotation;
    public float spinRotation;
}
