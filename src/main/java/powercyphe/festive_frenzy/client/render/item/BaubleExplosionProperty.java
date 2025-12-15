package powercyphe.festive_frenzy.client.render.item;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.item.component.ExplosiveBaubleComponent;
import powercyphe.festive_frenzy.common.registry.FFItems;

public class BaubleExplosionProperty implements ClampedItemPropertyFunction {
    public static final ResourceLocation ID = FestiveFrenzy.id("bauble_explosion_power");

    @Override
    public float unclampedCall(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int i) {
        return stack.getOrDefault(FFItems.Components.EXPLOSIVE_BAUBLE_COMPONENT, ExplosiveBaubleComponent.DEFAULT).explosionPower();
    }
}
