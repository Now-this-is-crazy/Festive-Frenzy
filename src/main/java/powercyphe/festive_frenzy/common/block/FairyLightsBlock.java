package powercyphe.festive_frenzy.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import powercyphe.festive_frenzy.common.registry.FFParticles;
import powercyphe.festive_frenzy.common.registry.FFSounds;

import javax.swing.*;

public class FairyLightsBlock extends MultiWallDecorationBlock {
    public static IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 5);

    public FairyLightsBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(VARIANT, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VARIANT);
        super.createBlockStateDefinition(builder);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (player.isCrouching() && state.hasProperty(VARIANT)) {
            int maxVariant = VARIANT.getPossibleValues().getLast();
            int variant = state.getValue(VARIANT) + 1;

            level.setBlockAndUpdate(blockPos, state.setValue(VARIANT, variant > maxVariant ? 0 : variant));
            level.playSound(player, blockPos, FFSounds.FAIRY_LIGHTS_SWITCH, SoundSource.BLOCKS, 1F, 1F);

            if (level instanceof ServerLevel serverLevel) {
                Vec3 pos = blockHitResult.getLocation();
                serverLevel.sendParticles((ParticleOptions) FFParticles.FAIRY_SPARK, pos.x(), blockPos.getY() + 0.7, pos.z(), 3, 0.1, 0.05, 0.1, 0.2);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useWithoutItem(state, level, blockPos, player, blockHitResult);
    }
}
