package powercyphe.festive_frenzy.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import powercyphe.festive_frenzy.common.registry.FFItems;
import powercyphe.festive_frenzy.common.registry.FFLootTables;
import powercyphe.festive_frenzy.common.registry.FFTags;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class FFLootTableProvider extends SimpleFabricLootTableProvider {
    public FFLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup, LootContextParamSets.EMPTY);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        consumer.accept(FFLootTables.CANDY_POUCH, new LootTable.Builder()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(3))
                        .add(TagEntry.expandTag(FFTags.Items.CANDY_CANES).setWeight(30)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3), true)))
                        .add(TagEntry.expandTag(FFTags.Items.CANDY_POUCH_FOOD).setWeight(7)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2), true)))
                )
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(FFItems.FROSTFLAKE_CANNON)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3,7))))
                        .conditionally(LootItemRandomChanceCondition.randomChance(0.3F).build())
                )
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(FFItems.SHARPENED_CANDY_CANE))
                        .conditionally(LootItemRandomChanceCondition.randomChance(0.01F).build())
                )
        );
    }
}
