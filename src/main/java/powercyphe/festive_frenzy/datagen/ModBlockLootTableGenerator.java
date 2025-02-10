package powercyphe.festive_frenzy.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import powercyphe.festive_frenzy.block.SideDecorationBlock;
import powercyphe.festive_frenzy.registry.ModBlocks;

import java.util.ArrayList;
import java.util.List;

public class ModBlockLootTableGenerator extends FabricBlockLootTableProvider {

    public ModBlockLootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.RED_CANDY_CANE_BLOCK);
        addDrop(ModBlocks.GREEN_CANDY_CANE_BLOCK);
        addDrop(ModBlocks.MIXED_CANDY_CANE_BLOCK);
        addDrop(ModBlocks.PEPPERMINT_BLOCK);


        addDrop(ModBlocks.PACKED_SNOW);
        addDrop(ModBlocks.CHISELED_PACKED_SNOW);

        addDrop(ModBlocks.POLISHED_PACKED_SNOW);
        addDrop(ModBlocks.POLISHED_PACKED_SNOW_STAIRS);
        addDrop(ModBlocks.POLISHED_PACKED_SNOW_SLAB);
        addDrop(ModBlocks.POLISHED_PACKED_SNOW_WALL);

        addDrop(ModBlocks.PACKED_SNOW_BRICKS);
        addDrop(ModBlocks.PACKED_SNOW_BRICK_STAIRS);
        addDrop(ModBlocks.PACKED_SNOW_BRICK_SLAB);
        addDrop(ModBlocks.PACKED_SNOW_BRICK_WALL);


        addDrop(ModBlocks.CUT_BLUE_ICE);
        addDrop(ModBlocks.CHISELED_BLUE_ICE);

        addDrop(ModBlocks.POLISHED_BLUE_ICE);
        addDrop(ModBlocks.POLISHED_BLUE_ICE_STAIRS);
        addDrop(ModBlocks.POLISHED_BLUE_ICE_SLAB);
        addDrop(ModBlocks.POLISHED_BLUE_ICE_WALL);

        addDrop(ModBlocks.BLUE_ICE_BRICKS);
        addDrop(ModBlocks.BLUE_ICE_BRICK_STAIRS);
        addDrop(ModBlocks.BLUE_ICE_BRICK_SLAB);
        addDrop(ModBlocks.BLUE_ICE_BRICK_WALL);

        for (Block block : ModBlocks.PRESENTS) {
            addDrop(block);
        }

        for (Block block : ModBlocks.BAUBLES) {
            addDrop(block);
        }

        for (Block block : ModBlocks.TINSELS) {
            addDrop(block, sideDecoBlockDrops(block));
        }

        addDrop(ModBlocks.FAIRY_LIGHTS, sideDecoBlockDrops(ModBlocks.FAIRY_LIGHTS));
        addDrop(ModBlocks.SNOW_GLOBE);
    }

    public LootTable.Builder sideDecoBlockDrops(Block drop) {
        LootPool.Builder northPool = LootPool.builder();
        northPool.with(ItemEntry.builder(drop));
        northPool.rolls(ConstantLootNumberProvider.create(1f));
        northPool.bonusRolls(ConstantLootNumberProvider.create(0f));
        northPool.conditionally(new BlockStatePropertyLootCondition.Builder(drop).properties(StatePredicate.Builder.create().exactMatch(SideDecorationBlock.NORTH, true)));

        LootPool.Builder southPool = LootPool.builder();
        southPool.with(ItemEntry.builder(drop));
        southPool.rolls(ConstantLootNumberProvider.create(1f));
        southPool.bonusRolls(ConstantLootNumberProvider.create(0f));
        southPool.conditionally(new BlockStatePropertyLootCondition.Builder(drop).properties(StatePredicate.Builder.create().exactMatch(SideDecorationBlock.SOUTH, true)));

        LootPool.Builder eastPool = LootPool.builder();
        eastPool.with(ItemEntry.builder(drop));
        eastPool.rolls(ConstantLootNumberProvider.create(1f));
        eastPool.bonusRolls(ConstantLootNumberProvider.create(0f));
        eastPool.conditionally(new BlockStatePropertyLootCondition.Builder(drop).properties(StatePredicate.Builder.create().exactMatch(SideDecorationBlock.EAST, true)));

        LootPool.Builder westPool = LootPool.builder();
        westPool.with(ItemEntry.builder(drop));
        westPool.rolls(ConstantLootNumberProvider.create(1f));
        westPool.bonusRolls(ConstantLootNumberProvider.create(0f));
        westPool.conditionally(new BlockStatePropertyLootCondition.Builder(drop).properties(StatePredicate.Builder.create().exactMatch(SideDecorationBlock.WEST, true)));

        List<LootPool> pools = new ArrayList<>();
        pools.add(northPool.build());
        pools.add(southPool.build());
        pools.add(eastPool.build());
        pools.add(westPool.build());
        return LootTable.builder().pools(pools);
    }
}
