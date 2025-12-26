package powercyphe.festive_frenzy.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import powercyphe.festive_frenzy.common.FestiveFrenzy;

public class FFEnchantments {

    public static final ResourceKey<Enchantment> PRICKLED_ENCHANTMENT = key("prickled");
    public static final ResourceKey<Enchantment> PHYTOTOXICITY_ENCHANTMENT = key("phytotoxicity");
    public static final ResourceKey<Enchantment> RICOCHET_ENCHANTMENT = key("ricochet");

    public static void init() {}

    public static ResourceKey<Enchantment> key(String id) {
        return ResourceKey.create(Registries.ENCHANTMENT, FestiveFrenzy.id(id));
    }
}
