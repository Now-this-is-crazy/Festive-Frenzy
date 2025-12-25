package powercyphe.festive_frenzy.common.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import powercyphe.festive_frenzy.common.FestiveFrenzy;

public class FFSounds {

    public static final SoundEvent POUCH_OPEN = register("pouch_open");
    public static final SoundEvent FROSTFLAKE_CANNON_SHOOT = register("frostflake_cannon_shoot");
    public static final SoundEvent FROSTFLAKE_PROJECTILE_HIT = register("frostflake_projectile_hit");

    public static final SoundEvent WREATH_CHAKRAM_THROW = register("wreath_chakram_throw");
    public static final SoundEvent WREATH_CHAKRAM_HIT = register("wreath_chakram_hit");

    public static final SoundEvent PRESENT_OPEN = register("present_open");
    public static final SoundEvent PRESENT_UNBOX = register("present_unbox");
    public static final SoundEvent PRESENT_PICKUP = register("present_pickup");

    public static final SoundEvent BAUBLE_THROW = register("bauble_throw");
    public static final SoundEvent BAUBLE_BREAK = register("bauble_break");

    public static final SoundEvent FAIRY_LIGHTS_SWITCH = register("fairy_lights_switch");

    public static void init() {}

    public static SoundEvent register(String name) {
        ResourceLocation id = FestiveFrenzy.id(name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }
}
