package powercyphe.festive_frenzy.common.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import powercyphe.festive_frenzy.common.util.SnowLoggable;

public class SnowloggablePlaceEvent implements UseBlockCallback {

    @Override
    public InteractionResult interact(Player player, Level level, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack stack = player.getItemInHand(interactionHand);
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState state = level.getBlockState(blockPos);

        if (stack.is(Items.SNOW) && state.getBlock() instanceof SnowLoggable snowLoggable) {
            int layers = snowLoggable.getSnowLayers(state);

            if (layers < 8 && snowLoggable.canBeSnowLogged(level, state, blockPos)) {
                level.playSound(player, blockPos, SoundEvents.SNOW_PLACE, SoundSource.BLOCKS, 0.5F, 1F);
                level.setBlockAndUpdate(blockPos, state.setValue(SnowLoggable.SNOW_LAYERS, layers+1));

                stack.consume(1, player);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
}
