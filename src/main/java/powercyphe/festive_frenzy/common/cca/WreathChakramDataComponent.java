package powercyphe.festive_frenzy.common.cca;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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
    public void readData(ValueInput valueInput) {
        this.setSavedSlot(valueInput.getIntOr(SAVED_SLOT_KEY, -1));
        this.setRicochet(valueInput.getBooleanOr(SHOULD_RICOCHET, false));
        this.setEnchanted(valueInput.getBooleanOr(IS_ENCHANTED_KEY, false));
    }

    @Override
    public void writeData(ValueOutput valueOutput) {
        valueOutput.putInt(SAVED_SLOT_KEY, this.getSavedSlot());
        valueOutput.putBoolean(SHOULD_RICOCHET, this.shouldRicochet());
        valueOutput.putBoolean(IS_ENCHANTED_KEY, this.isEnchanted());
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
