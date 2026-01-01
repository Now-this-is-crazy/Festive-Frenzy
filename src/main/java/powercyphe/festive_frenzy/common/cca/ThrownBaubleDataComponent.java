package powercyphe.festive_frenzy.common.cca;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import powercyphe.festive_frenzy.common.entity.ThrownBaubleProjectileEntity;
import powercyphe.festive_frenzy.common.registry.FFComponents;

public class ThrownBaubleDataComponent implements AutoSyncedComponent {
    private static final String IS_GLOWING_KEY = "isGlowing";
    private boolean isGlowing = false;

    public final ThrownBaubleProjectileEntity thrownBauble;

    public ThrownBaubleDataComponent(ThrownBaubleProjectileEntity thrownBauble) {
        this.thrownBauble = thrownBauble;
    }

    public static ThrownBaubleDataComponent get(ThrownBaubleProjectileEntity thrownBauble) {
        return FFComponents.THROWN_BAUBLE_DATA.get(thrownBauble);
    }

    public void sync() {
        FFComponents.THROWN_BAUBLE_DATA.sync(this.thrownBauble);
    }

    @Override
    public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        this.setGlowing(tag.getBooleanOr(IS_GLOWING_KEY, false));
    }

    @Override
    public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        tag.putBoolean(IS_GLOWING_KEY, this.isGlowing());
    }

    public void setGlowing(boolean isGlowing) {
        this.isGlowing = isGlowing;
        this.sync();

    }

    public boolean isGlowing() {
        return this.isGlowing;
    }
}
