package powercyphe.festive_frenzy.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import powercyphe.festive_frenzy.block.PresentBlock;
import powercyphe.festive_frenzy.registry.ModBlockEntities;
import powercyphe.festive_frenzy.screen.PresentScreenHandler;

public class PresentBlockEntity extends LootableContainerBlockEntity {
    private DefaultedList<ItemStack> inventory;

    public PresentBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PRESENT_BLOCK_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
    }

    public void setInventory(DefaultedList<ItemStack> items) {
        if (items == null) {
            items = DefaultedList.ofSize(9, ItemStack.EMPTY);
        }
        this.inventory = items;
    }

    public DefaultedList<ItemStack> getInventory() {
        if (inventory == null) {
            return DefaultedList.ofSize(9, ItemStack.EMPTY);
        }
        return this.inventory;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        if (inventory != null) {
            Inventories.writeNbt(nbt, inventory);
        }
        super.writeNbt(nbt);
    }

    @Override
    protected Text getContainerName() {
        return Text.empty(); // Text.translatable(this.getWorld().getBlockState(this.getPos()).getBlock().getTranslationKey());
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new PresentScreenHandler(syncId, playerInventory, this, Registries.BLOCK.getId(this.getCachedState().getBlock()).getPath());
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }



    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return super.canPlayerUse(player) && !this.getCachedState().get(PresentBlock.CLOSED);
    }

    @Override
    protected DefaultedList<ItemStack> method_11282() {
        return this.inventory;
    }
}
