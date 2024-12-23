package powercyphe.festive_frenzy.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import powercyphe.festive_frenzy.entity.BaubleProjectileEntity;
import powercyphe.festive_frenzy.entity.FrostflakeProjectileEntity;
import powercyphe.festive_frenzy.registry.ModItems;
import powercyphe.festive_frenzy.registry.ModSounds;

public class BaubleBlockItem extends BlockItem {
    public BaubleBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.BAUBLE_THROW, SoundCategory.NEUTRAL, 0.5F, 0.9F + (world.getRandom().nextFloat() * 0.2F));
        if (!world.isClient()) {
            BaubleProjectileEntity baubleProjectile = new BaubleProjectileEntity(world, user);
            baubleProjectile.setItem(stack);
            baubleProjectile.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(baubleProjectile);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.isCreative()) {
            stack.decrement(1);
        }
        return TypedActionResult.success(stack, world.isClient());
    }
}
