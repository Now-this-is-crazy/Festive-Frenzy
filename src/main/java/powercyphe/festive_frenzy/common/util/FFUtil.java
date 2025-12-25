package powercyphe.festive_frenzy.common.util;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import powercyphe.festive_frenzy.mixin.accessor.InventoryAccessor;

import java.util.List;

public class FFUtil {

    public static Vec3 reflectVector(Vec3 vec, Direction surface) {
        Vec3 dirVec = surface.getUnitVec3();
        return vec.subtract(dirVec.scale(2).multiply(vec.multiply(dirVec)));
    }

    public static int getSlotForItem(Inventory inventory, ItemStack stack) {
        InventoryAccessor accessor = (InventoryAccessor) inventory;
        List<NonNullList<ItemStack>> compartments = accessor.festive_frenzy$getCompartments();

        int slot = 0;
        for (NonNullList<ItemStack> stacks : compartments) {
            for (ItemStack invStack : stacks) {
                if (invStack == stack) {
                    return slot;
                }
                slot++;
            }
        }
        return -1;
    }
}
