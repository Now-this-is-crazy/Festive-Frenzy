package powercyphe.festive_frenzy.common.cca;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import powercyphe.festive_frenzy.common.entity.WreathChakramProjectileEntity;
import powercyphe.festive_frenzy.common.registry.FFComponents;

public class WreathChakramDataComponent implements AutoSyncedComponent {
    private static final String SAVED_SLOT_KEY = "savedSlot";
    private int savedSlot = -1;

    private static final String SHOULD_RICOCHET = "shouldRicochet";
    private boolean shouldRicochet = false;

    private static final String IS_ENCHANTED_KEY = "isEnchanted";
    private boolean isEnchanted = false;

    public final WreathChakramProjectileEntity wreathChakram;

    public WreathChakramDataComponent(WreathChakramProjectileEntity wreathChakram) {
        this.wreathChakram = wreathChakram;
    }

    public static WreathChakramDataComponent get(WreathChakramProjectileEntity wreathChakram) {
        return FFComponents.WREATH_CHAKRAM_DATA.get(wreathChakram);
    }

    public void sync() {
        FFComponents.WREATH_CHAKRAM_DATA.sync(this.wreathChakram);
    }

    @Override
    public void readFromNbt(CompoundTag compoundTag, HolderLookup.Provider provider) {
        this.setSavedSlot(compoundTag.contains(SAVED_SLOT_KEY) ? this.savedSlot : -1);
        this.setRicochet(compoundTag.getBooleanOr(SHOULD_RICOCHET, false));
        this.setEnchanted(compoundTag.getBooleanOr(IS_ENCHANTED_KEY, false));
    }

    @Override
    public void writeToNbt(CompoundTag compoundTag, HolderLookup.Provider provider) {
        compoundTag.putInt(SAVED_SLOT_KEY, this.getSavedSlot());
        compoundTag.putBoolean(SHOULD_RICOCHET, this.shouldRicochet());
        compoundTag.putBoolean(IS_ENCHANTED_KEY, this.isEnchanted());
    }

    public void setSavedSlot(int savedSlot) {
        this.savedSlot = savedSlot;
        this.sync();
    }

    public int getSavedSlot() {
        return this.savedSlot;
    }

    public void setRicochet(boolean shouldRicochet) {
        this.shouldRicochet = shouldRicochet;
        this.sync();
    }

    public boolean shouldRicochet() {
        return this.shouldRicochet;
    }

    public void setEnchanted(boolean isEnchanted) {
        this.isEnchanted = isEnchanted;
        this.sync();
    }

    public boolean isEnchanted() {
        return this.isEnchanted;
    }
}
