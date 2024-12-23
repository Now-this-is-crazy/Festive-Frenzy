package powercyphe.festive_frenzy.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class FairyLightBlock extends SideDecorationBlock {
//    public static final IntProperty TYPE = IntProperty.of("type", 1, 4);

    public FairyLightBlock(Settings settings) {
        super(settings);
    }

//    @Override
//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        if (!world.isClient()) {
//            int type = state.get(TYPE) + 1;
//            world.setBlockState(pos, state.with(TYPE, type > 4 ? 1 : type));
//            return ActionResult.SUCCESS;
//        }
//        return super.onUse(state, world, pos, player, hand, hit);
//    }
}
