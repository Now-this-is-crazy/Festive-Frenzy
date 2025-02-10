package powercyphe.festive_frenzy.item;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.BundleTooltipData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.registry.ModBlocks;
import powercyphe.festive_frenzy.registry.ModSounds;

import java.util.List;
import java.util.Optional;

public class PresentBlockItem extends BlockItem {
    public static final String ITEMS_KEY = "Items";
    public static final String CLOSED_KEY = "isClosed";
    public static final int MAX_STORAGE = 9;


    public PresentBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (!getStoredItems(stack).isEmpty()) {
            tooltip.add(Text.translatable("festive_frenzy.present_tooltip", getOccupancy(stack)).formatted(Formatting.GRAY));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean canBeNested() {
        return false;
    }

    public static void setStoredItems(ItemStack present, DefaultedList<ItemStack> items) {
        NbtCompound nbt = present.getOrCreateNbt();
        NbtList nbtList = new NbtList();
        for (ItemStack item : items) {
            NbtCompound nbtCompound = new NbtCompound();
            item.writeNbt(nbtCompound);
            nbtList.add(0, nbtCompound);
        }
        nbt.put(ITEMS_KEY, nbtList);
    }

    public static DefaultedList<ItemStack> getStoredItems(ItemStack present) {
        NbtCompound nbt = present.getNbt();
        if (nbt != null) {
            NbtList nbtList = nbt.getList(ITEMS_KEY, 10);
            DefaultedList<ItemStack> stacks = DefaultedList.of();
            for (NbtElement nbtElement : nbtList) {
                if (nbtElement instanceof NbtCompound nbtCompound) {
                    stacks.add(ItemStack.fromNbt(nbtCompound));
                }
            }
            return stacks;
        }
        return DefaultedList.ofSize(0, ItemStack.EMPTY);
    }

    public static int getOccupancy(ItemStack stack) {
        int size = 0;
        for (ItemStack i : getStoredItems(stack)) {
            if (!i.isEmpty()) size++;
        }
        return size;
    }

    public static boolean isClosed(ItemStack stack) {
        return stack.getOrCreateNbt().getBoolean(CLOSED_KEY);
    }

    public static void setClosed(ItemStack stack, boolean bl) {
        stack.getOrCreateNbt().putBoolean(CLOSED_KEY, bl);
    }

    public static ItemStack getItemStack(@Nullable DyeColor color) {
        return new ItemStack(get(color));
    }

    public static Block get(@Nullable DyeColor dyeColor) {
        if (dyeColor == null) {
            return ModBlocks.WHITE_PRESENT;
        } else {
            return switch (dyeColor) {
                case WHITE -> ModBlocks.WHITE_PRESENT;
                case LIGHT_GRAY -> ModBlocks.LIGHT_GRAY_PRESENT;
                case GRAY -> ModBlocks.GRAY_PRESENT;
                case BLACK -> ModBlocks.BLACK_PRESENT;
                case BROWN -> ModBlocks.BROWN_PRESENT;
                case RED -> ModBlocks.RED_PRESENT;
                case ORANGE -> ModBlocks.ORANGE_PRESENT;
                case YELLOW -> ModBlocks.YELLOW_PRESENT;
                case LIME -> ModBlocks.LIME_PRESENT;
                case GREEN -> ModBlocks.GREEN_PRESENT;
                case CYAN -> ModBlocks.CYAN_PRESENT;
                case LIGHT_BLUE -> ModBlocks.LIGHT_BLUE_PRESENT;
                case BLUE -> ModBlocks.BLUE_PRESENT;
                default -> ModBlocks.PURPLE_PRESENT;
                case MAGENTA -> ModBlocks.MAGENTA_PRESENT;
                case PINK -> ModBlocks.PINK_PRESENT;
            };
        }
    }

}
