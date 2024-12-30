package powercyphe.festive_frenzy.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.impl.datagen.loot.ConditionBlockLootTableGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.structure.OceanRuinStructure;
import powercyphe.festive_frenzy.registry.ModBlocks;
import powercyphe.festive_frenzy.registry.ModItems;

public class ModLootTableModifier {

    public static void modify() {
        AnyOfLootCondition.Builder cold = LocationCheckLootCondition.builder(
                LocationPredicate.Builder.create().biome(BiomeKeys.SNOWY_BEACH))
                .or(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().biome(BiomeKeys.SNOWY_PLAINS)))
                .or(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().biome(BiomeKeys.SNOWY_SLOPES)))
                .or(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().biome(BiomeKeys.SNOWY_TAIGA)))
                .or(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().biome(BiomeKeys.ICE_SPIKES)))
                .or(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().biome(BiomeKeys.FROZEN_OCEAN)))
                .or(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().biome(BiomeKeys.FROZEN_PEAKS)))
                .or(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().biome(BiomeKeys.FROZEN_RIVER)))
                .or(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().biome(BiomeKeys.DEEP_FROZEN_OCEAN)));

        LootTableEvents.MODIFY.register(((resourceManager, lootManager, identifier, tableBuilder, source) -> {
            if (source.isBuiltin() && identifier.equals(LootTables.FISHING_TREASURE_GAMEPLAY)) {
                tableBuilder.modifyPools(tb -> {
                    tb.with(ItemEntry.builder(ModItems.CANDY_POUCH)
                                    .conditionally(cold).weight(700));
                });
            }
        }));
    }
}
