package powercyphe.festive_frenzy.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import powercyphe.festive_frenzy.common.item.component.PresentContentsComponent;

import java.util.List;

public class PresentItem extends BlockItem {
    public PresentItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        PresentContentsComponent component = PresentContentsComponent.get(stack);
        if (component.closed()) {
            int items = component.stacks().size();
            list.addLast(Component.translatable(
                    items == 1
                            ? "tooltip.festive_frenzy.present_contents_single"
                            : "tooltip.festive_frenzy.present_contents_plural",
                    items).withStyle(ChatFormatting.GRAY)
            );
        }
        super.appendHoverText(stack, tooltipContext, list, tooltipFlag);
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }
}
