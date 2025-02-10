package powercyphe.festive_frenzy.item;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.entity.BaubleProjectileEntity;
import powercyphe.festive_frenzy.registry.ModBlocks;
import powercyphe.festive_frenzy.registry.ModSounds;

import java.util.List;
import java.util.function.Consumer;

public class BaubleBlockItem extends BlockItem {
    public static String EXPLOSION_STRENGTH_KEY = "explosionStrength";
    public static String EXPLOSION_MODIFICATION_KEY = "explosion_modification";

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
            if (!user.isCreative() && stack.getOrCreateNbt().contains(EXPLOSION_STRENGTH_KEY)) {
                int explosionStrength = stack.getOrCreateNbt().getInt(EXPLOSION_STRENGTH_KEY);
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
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.getOrCreateNbt().contains(EXPLOSION_STRENGTH_KEY)) {
            tooltip.add(Text.translatable("festive_frenzy.explosion_strength", stack.getOrCreateNbt().getInt(EXPLOSION_STRENGTH_KEY)).formatted(Formatting.GRAY));
            if (stack.getOrCreateNbt().contains(EXPLOSION_MODIFICATION_KEY)) {
                ExplosionModification modification = ExplosionModification.fromName(stack.getOrCreateNbt().getString(EXPLOSION_MODIFICATION_KEY));
                tooltip.add(Text.translatable("festive_frenzy.explosion_modification_" + modification.getName()).setStyle(Style.EMPTY.withColor(modification.getColor())));
            }
        }
        super.appendTooltip(stack, world, tooltip, context);
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

    public enum ExplosionModification implements StringIdentifiable {
        NONE("none", 0xffffff, Ingredient.EMPTY, Blocks.AIR, (pos, entity, explosionStrength) -> {}),
        FIRE("fire", 0xcf8731, Ingredient.ofItems(Items.FIRE_CHARGE), Blocks.FIRE,
                (pos, entity, explosionStrength) -> {
                    double dis = Math.max(entity.getPos().distanceTo(pos), 1);
                    int ticks = (int) ((120 + (30 * explosionStrength)) / dis);
                    entity.setFireTicks(ticks);
                }),
        ICE("ice", 0xaefffa, Ingredient.ofItems(Items.ICE), Blocks.ICE,
                (pos, entity, explosionStrength) -> {
                    double dis = Math.max(entity.getPos().distanceTo(pos), 1);
                    int ticks = (int) ((360 + (60 * explosionStrength)) / dis);
                    entity.setFrozenTicks(ticks);
                })
        ;

        private final String name;
        private final int color;
        private final Ingredient ingredient;
        private final Block block;
        private final ExplosionModificationAffector consumer;

        ExplosionModification(String name, int color, Ingredient item, Block block, ExplosionModificationAffector consumer) {
            this.name = name;
            this.color = color;
            this.ingredient = item;
            this.block = block;
            this.consumer = consumer;
        }

        public String getName() {
            return this.name;
        }

        public int getColor() {
            return this.color;
        }

        public Ingredient getIngredient() {
            return this.ingredient;
        }

        public Block getBlock() {
            return this.block;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public String asString() {
            return this.name;
        }


        public static ExplosionModification fromName(@NotNull String name) {
            for (ExplosionModification modification : ExplosionModification.values()) {
                if (modification.getName().equals(name)) {
                    return modification;
                }
            }
            return ExplosionModification.NONE;
        }

        public static ExplosionModification fromItem(@NotNull ItemConvertible item) {
            for (ExplosionModification modification : ExplosionModification.values()) {
                if (!modification.getIngredient().isEmpty() && modification.getIngredient().test(item.asItem().getDefaultStack())) {
                    return modification;
                }
            }
            return null;
        }

        public static ExplosionModification fromItemStack(ItemStack stack) {
            return fromItem(stack.getItem());
        }

        public void affectEntity(Vec3d pos, Entity entity, float explosionStrength) {
            consumer.affectEntity(pos, entity, explosionStrength);
        }
    }
    public interface ExplosionModificationAffector {
        void affectEntity(Vec3d pos, Entity entity, float explosionStrength);
    }
}
