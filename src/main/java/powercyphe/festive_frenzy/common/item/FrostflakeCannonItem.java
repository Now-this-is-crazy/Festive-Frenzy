package powercyphe.festive_frenzy.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import powercyphe.festive_frenzy.common.entity.FrostflakeProjectileEntity;
import powercyphe.festive_frenzy.common.registry.ModItems;
import powercyphe.festive_frenzy.common.registry.ModSounds;

public class FrostflakeCannonItem extends Item {
    public FrostflakeCannonItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        world.playSound(null, user.getBlockPos(), ModSounds.FROSTFLAKE_CANNON_SHOOT, SoundCategory.NEUTRAL, 0.5f, ((user.getRandom().nextFloat() - user.getRandom().nextFloat()) * 0.5F + 1.2F));
        if (!world.isClient()) {
            int amount = Random.create().nextBetween(11, 14);
            FrostflakeProjectileEntity frostflakeProjectile;
            for (int i = 0; i < amount; i++) {
                frostflakeProjectile = new FrostflakeProjectileEntity(world, user);
                frostflakeProjectile.setVelocity(user.getRotationVector().addRandom(Random.create(), 0.35f).multiply(2f));
                world.spawnEntity(frostflakeProjectile);
            }
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.isCreative()) {
            user.getItemCooldownManager().set(ModItems.FROSTFLAKE_CANNON, 35);
            stack.decrement(1);
        }
        return TypedActionResult.consume(stack);
    }
}
