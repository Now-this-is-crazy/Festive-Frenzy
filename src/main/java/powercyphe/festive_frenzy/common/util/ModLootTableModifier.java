package powercyphe.festive_frenzy.common.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.world.biome.BiomeKeys;
import powercyphe.festive_frenzy.common.registry.ModItems;

public class ModLootTableModifier {

    public static void modify() {
        LocationPredicate.Builder predicate = LocationPredicate.Builder.create();
        predicate.biome(
                RegistryEntryList.of(
                        new RegistryEntry[]{
                                RegistryEntry.of(BiomeKeys.SNOWY_BEACH),
                                RegistryEntry.of(BiomeKeys.SNOWY_PLAINS),
                                RegistryEntry.of(BiomeKeys.SNOWY_TAIGA),
                                RegistryEntry.of(BiomeKeys.ICE_SPIKES),
                                RegistryEntry.of(BiomeKeys.FROZEN_PEAKS),
                                RegistryEntry.of(BiomeKeys.FROZEN_RIVER),
                                RegistryEntry.of(BiomeKeys.FROZEN_OCEAN),
                                RegistryEntry.of(BiomeKeys.DEEP_FROZEN_OCEAN)
                        }));
        LootCondition.Builder cold = LocationCheckLootCondition.builder(predicate);

        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {
            if (source.isBuiltin() && key.equals(LootTables.FISHING_TREASURE_GAMEPLAY)) {
                tableBuilder.modifyPools(tb -> {
                    tb.with(ItemEntry.builder(ModItems.CANDY_POUCH)
                                    .conditionally(cold).weight(700));
                });
            }
        });
    }
}
