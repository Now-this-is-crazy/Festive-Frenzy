package powercyphe.festive_frenzy.common.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import powercyphe.festive_frenzy.common.registry.FFItems;

public class FFLootTableModifier {

    public static void init() {
        LootTableEvents.MODIFY.register(((key, tableBuilder, source, registries) -> {
            if (source.isBuiltin() && key.equals(BuiltInLootTables.FISHING_TREASURE)) {
                tableBuilder.modifyPools(tb -> tb.add(LootItem.lootTableItem(FFItems.CANDY_POUCH)));
            }
        }));
    }

}
