package powercyphe.festive_frenzy.client.render.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.item.component.ExplosiveBaubleComponent;
import powercyphe.festive_frenzy.common.registry.FFItems;
import powercyphe.festive_frenzy.common.world.BaubleExplosion;

public record BaubleExplosionModificationProperty() implements SelectItemModelProperty<BaubleExplosion.ExplosionModification> {
    public static final Identifier ID = FestiveFrenzy.id("bauble_explosion_modification");
    public static final SelectItemModelProperty.Type<BaubleExplosionModificationProperty, BaubleExplosion.ExplosionModification> TYPE = Type.create(
            MapCodec.unit(BaubleExplosionModificationProperty::new), BaubleExplosion.ExplosionModification.CODEC
    );

    @Override
    public @Nullable BaubleExplosion.ExplosionModification get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int i, ItemDisplayContext itemDisplayContext) {
        return stack.getOrDefault(FFItems.Components.EXPLOSIVE_BAUBLE_COMPONENT, ExplosiveBaubleComponent.DEFAULT)
                .explosionModification();
    }

    @Override
    public Codec<BaubleExplosion.ExplosionModification> valueCodec() {
        return BaubleExplosion.ExplosionModification.CODEC;
    }

    @Override
    public Type<? extends SelectItemModelProperty<BaubleExplosion.ExplosionModification>, BaubleExplosion.ExplosionModification> type() {
        return TYPE;
    }
}
