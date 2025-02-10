package powercyphe.festive_frenzy.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.ColorHelper;

public class FestiveHatItem extends Item implements DyeableItem, Equipment {
    public FestiveHatItem(Settings settings) {
        super(settings);
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public int getColor(ItemStack stack) {
        return DyeableItem.super.getColor(stack) != DEFAULT_COLOR ? DyeableItem.super.getColor(stack) : ColorHelper.Argb.getArgb(0,246,85,60);
    }
}
