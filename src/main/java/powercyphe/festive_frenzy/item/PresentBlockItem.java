package powercyphe.festive_frenzy.item;

import net.minecraft.block.Block;
import net.minecraft.client.item.BundleTooltipData;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.ColorHelper;
import powercyphe.festive_frenzy.registry.ModSounds;

import java.util.Optional;

public class PresentBlockItem extends BlockItem {
    public static final String ITEMS_KEY = "Items";
    public static final int MAX_STORAGE = 8;
    private static final int ITEM_BAR_COLOR = ColorHelper.Argb.getArgb(0, 106, 127, 194);


    public PresentBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType.equals(ClickType.RIGHT) && !otherStack.isEmpty()) {
            if (storeItem(stack, otherStack)) {
                player.playSound(ModSounds.PRESENT_INSERT, 1f, 1f);
                return true;
            }
        } else if (clickType.equals(ClickType.RIGHT)) {
            if (takeItem(stack, cursorStackReference)) {
                player.playSound(ModSounds.PRESENT_REMOVE, 1f, 1f);
                return true;
            }
        }
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        int occupancy = getOccupancy(stack);
        return Math.round(13.0F - (float) occupancy * 13.0F / (float) MAX_STORAGE);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return ITEM_BAR_COLOR;
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        DefaultedList<ItemStack> items = getStoredItems(stack);
        if (items != null) {
            return Optional.of(new BundleTooltipData(items, getOccupancy(stack)));
        }
        return super.getTooltipData(stack);
    }

    @Override
    public boolean canBeNested() {
        return false;
    }

    public static boolean storeItem(ItemStack present, ItemStack stack) {
        if (!stack.isEmpty() && stack.getItem().canBeNested()) {
            NbtCompound nbt = present.getOrCreateNbt();
            if (!nbt.contains(ITEMS_KEY)) {
                nbt.put(ITEMS_KEY, new NbtList());
            }

            int occupancy = getOccupancy(present);
            if (occupancy < MAX_STORAGE) {
                DefaultedList<ItemStack> items = getStoredItems(present);
                if (items == null) items = DefaultedList.of();
                items.add(stack);
                setStoredItems(present, items);
                stack.decrement(stack.getCount());
                return true;
            }
        }
        return false;
    }

    public static boolean takeItem(ItemStack present, StackReference cursorStack) {
        if (cursorStack.get().isEmpty()) {
            DefaultedList<ItemStack> items = getStoredItems(present);
            if (items != null && !items.isEmpty()) {
                ItemStack stack = items.remove(0);
                cursorStack.set(stack);
                setStoredItems(present, items);
                return true;
            }
        }
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
        return null;
    }

    public static int getOccupancy(ItemStack present) {
        DefaultedList<ItemStack> items = getStoredItems(present);
        if (items != null) {
            return items.size();
        }
        return 0;
    }

}
