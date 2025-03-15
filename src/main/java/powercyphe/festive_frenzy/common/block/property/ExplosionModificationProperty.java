package powercyphe.festive_frenzy.common.block.property;

import com.google.common.collect.Lists;
import net.minecraft.state.property.EnumProperty;
import powercyphe.festive_frenzy.common.item.BaubleBlockItem;
import powercyphe.festive_frenzy.common.world.BaubleExplosion;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ExplosionModificationProperty extends EnumProperty<BaubleExplosion.ExplosionModification> {
    protected ExplosionModificationProperty(String name, Collection<BaubleExplosion.ExplosionModification> values) {
        super(name, BaubleExplosion.ExplosionModification.class, values);
    }

    public static ExplosionModificationProperty of(String name) {
        return of(name, (explosionModification) -> {
            return true;
        });
    }

    public static ExplosionModificationProperty of(String name, Predicate<BaubleExplosion.ExplosionModification> filter) {
        return of(name, Arrays.stream(BaubleExplosion.ExplosionModification.values()).filter(filter).collect(Collectors.toList()));
    }

    public static ExplosionModificationProperty of(String name, BaubleExplosion.ExplosionModification... values) {
        return of(name, Lists.newArrayList(values));
    }

    public static ExplosionModificationProperty of(String name, Collection<BaubleExplosion.ExplosionModification> values) {
        return new ExplosionModificationProperty(name, values);
    }
}
