package powercyphe.festive_frenzy.common.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.mob_effect.FrostburnEffect;

public class FFEffects {

    public static final Holder<MobEffect> FROSTBURN = register("frostburn", new FrostburnEffect(MobEffectCategory.HARMFUL, 0x88befa,
            (ParticleOptions) FFParticles.FROSTBURN));

    public static void init() {}

    public static Holder<MobEffect> register(String id, MobEffect effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, FestiveFrenzy.id(id), effect);
    }
}
