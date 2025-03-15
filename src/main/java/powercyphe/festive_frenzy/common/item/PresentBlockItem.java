package powercyphe.festive_frenzy.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.item.component.PresentContentComponent;
import powercyphe.festive_frenzy.common.registry.ModBlocks;
import powercyphe.festive_frenzy.common.registry.ModItems;

import java.util.List;
import java.util.Objects;

public class PresentBlockItem extends BlockItem {
    public static final String ITEMS_KEY = "Items";
    public static final int MAX_STORAGE = 9;


    public PresentBlockItem(Block block, Settings settings) {
        super(block, settings);
    }


    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (!getStoredItems(stack).isEmpty()) {
            tooltip.add(Text.translatable("festive_frenzy.present_tooltip", getOccupancy(stack)).formatted(Formatting.GRAY));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public boolean canBeNested() {
        return false;
    }

    public static void setStoredItems(ItemStack present, List<ItemStack> items) {
        List<ItemStack> content = new java.util.ArrayList<>(List.of());
        content.addAll(items);
        content.removeIf(Objects::isNull);
        content.removeIf(ItemStack::isEmpty);

        present.set(ModItems.Components.PRESENT_CONTENT, new PresentContentComponent(content));
    }

    public static List<ItemStack> getStoredItems(ItemStack present) {
        return present.getOrDefault(ModItems.Components.PRESENT_CONTENT, PresentContentComponent.DEFAULT).getStacks();
    }

    public static int getOccupancy(ItemStack stack) {
        int size = 0;
        for (ItemStack i : getStoredItems(stack)) {
            if (!i.isEmpty()) size++;
        }
        return size;
    }

    public static boolean isClosed(ItemStack stack) {
        return !stack.getOrDefault(ModItems.Components.PRESENT_CONTENT, PresentContentComponent.DEFAULT).getStacks().isEmpty();
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
