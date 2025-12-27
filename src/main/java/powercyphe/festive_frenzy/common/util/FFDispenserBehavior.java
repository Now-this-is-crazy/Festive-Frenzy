package powercyphe.festive_frenzy.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import powercyphe.festive_frenzy.common.entity.FrostflakeProjectileEntity;
import powercyphe.festive_frenzy.common.registry.FFItems;
import powercyphe.festive_frenzy.common.registry.FFSounds;

public class FFDispenserBehavior {

    public static void init() {
        DispenserBlock.registerBehavior(FFItems.FROSTFLAKE_CANNON, new DispenseItemBehavior() {
            @Override
            public ItemStack dispense(BlockSource source, ItemStack stack) {
                ServerLevel level = source.level();
                BlockState state = source.state();
                BlockPos blockPos = source.pos();

                Direction direction = state.getNullableValue(DispenserBlock.FACING);
                if (direction != null) {
                    RandomSource random = RandomSource.create();
                    Vec3 projPos = blockPos.getCenter().relative(direction, 0.7);

                    for (int i = 0; i < 7; i++) {
                        FrostflakeProjectileEntity projectile = new FrostflakeProjectileEntity(projPos.x(), projPos.y(), projPos.z(), level);
                        projectile.setDeltaMovement(direction.getUnitVec3().offsetRandom(random, 0.2F).scale(1.8));

                        level.addFreshEntity(projectile);
                    }

                    level.playSound(null, blockPos, FFSounds.FROSTFLAKE_CANNON_SHOOT, SoundSource.BLOCKS,
                            0.5F, 0.875F + (random.nextFloat() * 0.3F));
                    stack.shrink(1);
                }

                return stack;
            }
        });
    }
}
