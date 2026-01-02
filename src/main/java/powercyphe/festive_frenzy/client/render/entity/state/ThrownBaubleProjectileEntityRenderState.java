package powercyphe.festive_frenzy.client.render.entity.state;

import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.level.block.state.BlockState;

public class ThrownBaubleProjectileEntityRenderState extends EntityRenderState {
    public BlockStateModel baubleModel;
    public BlockState baubleState;

    public boolean isGlowing;

    public double rotationX;
    public double rotationY;
    public double rotationZ;
}
