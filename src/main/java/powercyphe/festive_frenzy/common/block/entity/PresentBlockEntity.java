package powercyphe.festive_frenzy.common.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import powercyphe.festive_frenzy.common.block.PresentBlock;
import powercyphe.festive_frenzy.common.menu.PresentMenu;
import powercyphe.festive_frenzy.common.registry.FFBlocks;

public class PresentBlockEntity extends RandomizableContainerBlockEntity implements ExtendedScreenHandlerFactory<String> {
    public static final int SLOTS = 9;
    private NonNullList<ItemStack> inventory;

    public PresentBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(FFBlocks.Entities.PRESENT_BLOCK_ENTITY, blockPos, blockState);
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    @Override
    public Component getDefaultName() {
        return Component.empty(); // Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public void setItems(NonNullList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inventory) {
        return new PresentMenu(syncId, inventory, this, this.getScreenOpeningData(null));
    }

    @Override
    public int getContainerSize() {
        return SLOTS;
    }

    @Override
    public boolean canOpen(Player player) {
         return super.canOpen(player) && !this.getBlockState().getValue(PresentBlock.CLOSED);
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        ContainerHelper.loadAllItems(valueInput, this.inventory);
        super.loadAdditional(valueInput);
    }

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        ContainerHelper.saveAllItems(valueOutput, this.inventory);
        super.saveAdditional(valueOutput);
    }

    @Override
    public String getScreenOpeningData(ServerPlayer serverPlayer) {
        return BuiltInRegistries.BLOCK.getKey(this.getBlockState().getBlock()).getPath();
    }
}
