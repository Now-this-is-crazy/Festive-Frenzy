package powercyphe.festive_frenzy.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.entity.BaubleProjectileEntity;
import powercyphe.festive_frenzy.common.registry.ModBlocks;
import powercyphe.festive_frenzy.common.registry.ModItems;
import powercyphe.festive_frenzy.common.registry.ModSounds;
import powercyphe.festive_frenzy.common.world.BaubleExplosion;

import java.util.List;

public class BaubleBlockItem extends BlockItem {

    public BaubleBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.BAUBLE_THROW, SoundCategory.NEUTRAL, 0.5F, 0.9F + (world.getRandom().nextFloat() * 0.2F));
        if (!world.isClient()) {
            BaubleProjectileEntity baubleProjectile = new BaubleProjectileEntity(world, user, stack);
            baubleProjectile.setItem(stack);
            baubleProjectile.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(baubleProjectile);
            if (!user.isCreative() && stack.contains(ModItems.Components.EXPLOSION_STRENGTH)) {
                int explosionStrength = stack.getOrDefault(ModItems.Components.EXPLOSION_STRENGTH, 0);
                user.getItemCooldownManager().set(stack.getItem(), (int) Math.pow(1.5, explosionStrength));
            }
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.isCreative()) {
            stack.decrement(1);
        }
        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (stack.contains(ModItems.Components.EXPLOSION_STRENGTH)) {
            tooltip.add(Text.translatable("festive_frenzy.explosion_strength", stack.get(ModItems.Components.EXPLOSION_STRENGTH)).formatted(Formatting.GRAY));
            if (stack.contains(ModItems.Components.EXPLOSION_MODIFICATION)) {
                BaubleExplosion.ExplosionModification modification = stack.get(ModItems.Components.EXPLOSION_MODIFICATION);
                tooltip.add(Text.translatable("festive_frenzy.explosion_modification_" + modification.getName()).setStyle(Style.EMPTY.withColor(modification.getColor())));
            }
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    public static ItemStack getItemStack(@Nullable DyeColor color) {
        return new ItemStack(get(color));
    }

    public static Block get(@Nullable DyeColor dyeColor) {
        if (dyeColor == null) {
            return ModBlocks.WHITE_BAUBLE;
        } else {
            return switch (dyeColor) {
                case WHITE -> ModBlocks.WHITE_BAUBLE;
                case LIGHT_GRAY -> ModBlocks.LIGHT_GRAY_BAUBLE;
                case GRAY -> ModBlocks.GRAY_BAUBLE;
                case BLACK -> ModBlocks.BLACK_BAUBLE;
                case BROWN -> ModBlocks.BROWN_BAUBLE;
                case RED -> ModBlocks.RED_BAUBLE;
                case ORANGE -> ModBlocks.ORANGE_BAUBLE;
                case YELLOW -> ModBlocks.YELLOW_BAUBLE;
                case LIME -> ModBlocks.LIME_BAUBLE;
                case GREEN -> ModBlocks.GREEN_BAUBLE;
                case CYAN -> ModBlocks.CYAN_BAUBLE;
                case LIGHT_BLUE -> ModBlocks.LIGHT_BLUE_BAUBLE;
                case BLUE -> ModBlocks.BLUE_BAUBLE;
                default -> ModBlocks.PURPLE_BAUBLE;
                case MAGENTA -> ModBlocks.MAGENTA_BAUBLE;
                case PINK -> ModBlocks.PINK_BAUBLE;
            };
        }
    }
}
