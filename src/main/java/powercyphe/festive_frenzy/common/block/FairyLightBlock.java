package powercyphe.festive_frenzy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powercyphe.festive_frenzy.common.registry.ModSounds;

public class FairyLightBlock extends SideDecorationBlock {
   public static final IntProperty VARIANT = IntProperty.of("variant", 1, 5);

    public FairyLightBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(VARIANT, 1));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(VARIANT);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos blockPos, PlayerEntity player, BlockHitResult hit) {
        if (player.isSneaking()) {
            int type = state.get(VARIANT) + 1;
            Object[] variants = VARIANT.getValues().toArray();
            world.setBlockState(blockPos, state.with(VARIANT, type > (int) variants[VARIANT.getValues().size()-1] ? 1 : type));

            player.swingHand(Hand.MAIN_HAND);
            world.playSound(null, blockPos, ModSounds.FAIRY_LIGHTS_SWITCH, SoundCategory.BLOCKS);
        }
        return ActionResult.PASS;
    }


}
