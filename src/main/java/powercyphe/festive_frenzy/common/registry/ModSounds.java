package powercyphe.festive_frenzy.common.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import powercyphe.festive_frenzy.FestiveFrenzy;

public class ModSounds {
    public static final SoundEvent PRESENT_OPEN = register("present_open");
    public static final SoundEvent PRESENT_UNBOX = register("present_unbox");

    public static final SoundEvent FROSTFLAKE_CANNON_SHOOT = register("frostflake_cannon_shoot");

    public static final SoundEvent BAUBLE_THROW = register("bauble_throw");
    public static final SoundEvent BAUBLE_BREAK = register("bauble_break");

    public static final SoundEvent POUCH_OPEN = register("pouch_open");

    public static final SoundEvent FAIRY_LIGHTS_SWITCH = register("fairy_lights_switch");

    public static void init() {}

    public static SoundEvent register(String path) {
        Identifier id = FestiveFrenzy.id(path);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}
