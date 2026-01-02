package powercyphe.festive_frenzy.common.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import powercyphe.festive_frenzy.common.entity.FrostflakeProjectileEntity;
import powercyphe.festive_frenzy.common.registry.FFSounds;

public class FrostflakeCannonItem extends Item {
    public FrostflakeCannonItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemCooldowns cooldowns = player.getCooldowns();

        if (!cooldowns.isOnCooldown(stack)) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), FFSounds.FROSTFLAKE_CANNON_SHOOT, SoundSource.NEUTRAL,
                    1F, 0.875F + (level.getRandom().nextFloat() * 0.3F));

            if (level instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 7; i++) {
                    Projectile.spawnProjectileFromRotation(((lvl, owner, s) ->
                            new FrostflakeProjectileEntity(owner, lvl)), serverLevel, stack, player, 0.0F, 1.5F, 5F);
                }
            }

            stack.consume(1, player);
            if (!player.isCreative()) {
                cooldowns.addCooldown(stack, 15);
            }
            return InteractionResult.CONSUME;
        }
        return super.use(level, player, hand);
    }
}
