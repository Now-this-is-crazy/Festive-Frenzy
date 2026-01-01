package powercyphe.festive_frenzy.common.util;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class FFUtil {

    public static Vec3 reflectVector(Vec3 vec, Direction surface) {
        Vec3 surfaceVec = surface.getUnitVec3();
        return vec.subtract(surfaceVec.scale(2).multiply(vec.multiply(surfaceVec)));
    }

    public static int getSlotForItem(Inventory inventory, ItemStack stack) {
        int slot = 0;
        for (ItemStack invStack : inventory) {
            if (invStack == stack) {
                return slot;
            }
            slot++;
        }
        return -1;
    }
}
