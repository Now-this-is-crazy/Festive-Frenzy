package powercyphe.festive_frenzy.block.property;

import com.google.common.collect.Lists;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.Direction;
import powercyphe.festive_frenzy.item.BaubleBlockItem;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ExplosionModificationProperty extends EnumProperty<BaubleBlockItem.ExplosionModification> {
    protected ExplosionModificationProperty(String name, Collection<BaubleBlockItem.ExplosionModification> values) {
        super(name, BaubleBlockItem.ExplosionModification.class, values);
    }

    public static ExplosionModificationProperty of(String name) {
        return of(name, (explosionModification) -> {
            return true;
        });
    }

    public static ExplosionModificationProperty of(String name, Predicate<BaubleBlockItem.ExplosionModification> filter) {
        return of(name, Arrays.stream(BaubleBlockItem.ExplosionModification.values()).filter(filter).collect(Collectors.toList()));
    }

    public static ExplosionModificationProperty of(String name, BaubleBlockItem.ExplosionModification... values) {
        return of(name, Lists.newArrayList(values));
    }

    public static ExplosionModificationProperty of(String name, Collection<BaubleBlockItem.ExplosionModification> values) {
        return new ExplosionModificationProperty(name, values);
    }
}
