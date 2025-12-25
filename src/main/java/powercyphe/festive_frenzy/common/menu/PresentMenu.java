package powercyphe.festive_frenzy.common.menu;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import powercyphe.festive_frenzy.common.block.entity.PresentBlockEntity;
import powercyphe.festive_frenzy.common.registry.FFBlocks;
import powercyphe.festive_frenzy.common.registry.FFMenus;
import powercyphe.festive_frenzy.common.registry.FFTags;

public class PresentMenu extends AbstractContainerMenu {
    private static final String DEFAULT_PRESENT_TYPE = BuiltInRegistries.BLOCK.getKey(FFBlocks.RED_PRESENT).getPath();
    private static final int SLOTS = PresentBlockEntity.SLOTS;

    private final Container present;
    private final String presentType;

    public PresentMenu(int i, Inventory inventory, String presentType) {
        this(i, inventory, new SimpleContainer(SLOTS), presentType);
    }

    public PresentMenu(int i, Inventory inventory, Container container, String presentType) {
        super(FFMenus.PRESENT_MENU, i);
        checkContainerSize(container, SLOTS);
        this.present = container;
        this.presentType = presentType;
        container.startOpen(inventory.player);
        this.add3x3GridSlots(container, 62, 10);
        this.addStandardInventorySlots(inventory, 8, 84);
    }

    public Container getContainer() {
        return this.present;
    }

    public String getPresentType() {
        return this.presentType == null ? DEFAULT_PRESENT_TYPE : this.presentType;
    }

    protected void add3x3GridSlots(Container container, int i, int j) {
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                int m = l + k * 3;
                this.addSlot(new PresentMenuSlot(container, m, i + l * 18, j + k * 18));
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            stack = slotStack.copy();
            if (i < 9) {
                if (!this.moveItemStackTo(slotStack, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotStack, 0, 9, false)) {
                return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
        }

        return stack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.present.stillValid(player);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.present.stopOpen(player);
    }

    public static class PresentMenuSlot extends Slot {
        public PresentMenuSlot(Container container, int i, int j, int k) {
            super(container, i, j, k);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return !stack.is(FFTags.Items.PRESENTS);
        }
    }
}
