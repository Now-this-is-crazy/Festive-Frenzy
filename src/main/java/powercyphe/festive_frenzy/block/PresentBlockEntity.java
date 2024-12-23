package powercyphe.festive_frenzy.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import powercyphe.festive_frenzy.registry.ModBlockEntities;

public class PresentBlockEntity extends BlockEntity {
    private DefaultedList<ItemStack> STORED_ITEMS;

    public PresentBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PRESENT_BLOCK_ENTITY, pos, state);
    }

    public void setInventory(DefaultedList<ItemStack> items) {
        if (items == null) {
            items = DefaultedList.of();
        }
        this.STORED_ITEMS = items;
    }

    public DefaultedList<ItemStack> getInventory() {
        if (STORED_ITEMS == null) {
            return DefaultedList.of();
        }
        return this.STORED_ITEMS;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, STORED_ITEMS);
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        if (STORED_ITEMS != null) {
            Inventories.writeNbt(nbt, STORED_ITEMS);
        }
        super.writeNbt(nbt);
    }
}
