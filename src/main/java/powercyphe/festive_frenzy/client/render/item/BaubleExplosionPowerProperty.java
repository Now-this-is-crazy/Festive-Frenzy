package powercyphe.festive_frenzy.client.render.item;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.item.component.ExplosiveBaubleComponent;
import powercyphe.festive_frenzy.common.registry.FFItems;

public record BaubleExplosionPowerProperty() implements RangeSelectItemModelProperty {
    public static final ResourceLocation ID = FestiveFrenzy.id("bauble_explosion_power");
    public static final MapCodec<? extends RangeSelectItemModelProperty> CODEC = MapCodec.unit(BaubleExplosionPowerProperty::new);

    @Override
    public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable ItemOwner itemOwner, int i) {
        return stack.getOrDefault(FFItems.Components.EXPLOSIVE_BAUBLE_COMPONENT, ExplosiveBaubleComponent.DEFAULT).explosionPower();
    }

    @Override
    public MapCodec<? extends RangeSelectItemModelProperty> type() {
        return CODEC;
    }
}
