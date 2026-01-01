package powercyphe.festive_frenzy.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;
import powercyphe.festive_frenzy.common.item.component.PresentContentsComponent;
import powercyphe.festive_frenzy.common.registry.FFItems;

import java.util.List;
import java.util.function.Consumer;

public class PresentItem extends BlockItem {
    public PresentItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        PresentContentsComponent component = PresentContentsComponent.get(stack);
        if (tooltipDisplay.shows(FFItems.Components.PRESENT_CONTENTS_COMPONENT) && component.closed()) {
            int items = component.stacks().size();
            consumer.accept(Component.translatable(
                    items == 1
                            ? "tooltip.festive_frenzy.present_contents_single"
                            : "tooltip.festive_frenzy.present_contents_plural",
                    items).withStyle(ChatFormatting.GRAY)
            );
        }
        super.appendHoverText(stack, tooltipContext, tooltipDisplay, consumer, tooltipFlag);
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }
}
