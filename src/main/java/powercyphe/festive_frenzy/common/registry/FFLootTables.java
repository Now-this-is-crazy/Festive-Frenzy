package powercyphe.festive_frenzy.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import powercyphe.festive_frenzy.common.FestiveFrenzy;

public class FFLootTables {

    public static final ResourceKey<LootTable> CANDY_POUCH = of("gameplay/candy_pouch");

    public static void init() {}

    public static ResourceKey<LootTable> of(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE, FestiveFrenzy.id(id));
    }
}
