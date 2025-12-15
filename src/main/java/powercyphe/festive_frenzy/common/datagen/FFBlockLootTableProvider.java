package powercyphe.festive_frenzy.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import powercyphe.festive_frenzy.common.block.MultiWallDecorationBlock;
import powercyphe.festive_frenzy.common.registry.FFBlocks;

import java.util.concurrent.CompletableFuture;

public class FFBlockLootTableProvider extends FabricBlockLootTableProvider {
    protected FFBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {

        createShearsOnlyDrop(FFBlocks.SHORT_FROZEN_GRASS);
        createDoublePlantShearsDrop(FFBlocks.TALL_FROZEN_GRASS);

        // Gingerbread Blocks
        dropSelf(FFBlocks.GINGERBREAD_BLOCK);
        dropSelf(FFBlocks.GINGERBREAD_STAIRS);
        dropSelf(FFBlocks.GINGERBREAD_SLAB);
        dropSelf(FFBlocks.GINGERBREAD_WALL);

        dropSelf(FFBlocks.GINGERBREAD_BRICKS);
        dropSelf(FFBlocks.GINGERBREAD_BRICK_STAIRS);
        dropSelf(FFBlocks.GINGERBREAD_BRICK_SLAB);
        dropSelf(FFBlocks.GINGERBREAD_BRICK_WALL);

        dropSelf(FFBlocks.CHISELED_GINGERBREAD_BLOCK);

        createDoorTable(FFBlocks.GINGERBREAD_DOOR);
        dropSelf(FFBlocks.GINGERBREAD_TRAPDOOR);
        dropSelf(FFBlocks.GINGERBREAD_BUTTON);
        dropSelf(FFBlocks.GINGERBREAD_PRESSURE_PLATE);

        // Packed Snow Blocks
        dropSelf(FFBlocks.PACKED_SNOW);
        dropSelf(FFBlocks.PACKED_SNOW_STAIRS);
        dropSelf(FFBlocks.PACKED_SNOW_SLAB);
        dropSelf(FFBlocks.PACKED_SNOW_WALL);

        dropSelf(FFBlocks.POLISHED_PACKED_SNOW);
        dropSelf(FFBlocks.POLISHED_PACKED_SNOW_STAIRS);
        dropSelf(FFBlocks.POLISHED_PACKED_SNOW_SLAB);
        dropSelf(FFBlocks.POLISHED_PACKED_SNOW_WALL);

        dropSelf(FFBlocks.PACKED_SNOW_BRICKS);
        dropSelf(FFBlocks.PACKED_SNOW_BRICK_STAIRS);
        dropSelf(FFBlocks.PACKED_SNOW_BRICK_SLAB);
        dropSelf(FFBlocks.PACKED_SNOW_BRICK_WALL);

        dropSelf(FFBlocks.CHISELED_PACKED_SNOW);

        // Blue Ice Blocks
        dropSelf(FFBlocks.CUT_BLUE_ICE);
        dropSelf(FFBlocks.CUT_BLUE_ICE_STAIRS);
        dropSelf(FFBlocks.CUT_BLUE_ICE_SLAB);
        dropSelf(FFBlocks.CUT_BLUE_ICE_WALL);

        dropSelf(FFBlocks.POLISHED_BLUE_ICE);
        dropSelf(FFBlocks.POLISHED_BLUE_ICE_STAIRS);
        dropSelf(FFBlocks.POLISHED_BLUE_ICE_SLAB);
        dropSelf(FFBlocks.POLISHED_BLUE_ICE_WALL);

        dropSelf(FFBlocks.BLUE_ICE_BRICKS);
        dropSelf(FFBlocks.BLUE_ICE_BRICK_STAIRS);
        dropSelf(FFBlocks.BLUE_ICE_BRICK_SLAB);
        dropSelf(FFBlocks.BLUE_ICE_BRICK_WALL);

        dropSelf(FFBlocks.CHISELED_BLUE_ICE);


        for (Block present : FFBlocks.PRESENTS) {
            dropSelf(present);
        }
        for (Block bauble : FFBlocks.BAUBLES) {
            dropSelf(bauble);
        }
        for (Block tinsel : FFBlocks.TINSEL) {
            addMultiWallDecorationBlockDrops(tinsel);
        }

        addMultiWallDecorationBlockDrops(FFBlocks.FAIRY_LIGHTS);
        dropSelf(FFBlocks.STAR_DECORATION);
    }


    public void addMultiWallDecorationBlockDrops(Block block) {
        LootTable.Builder lootTable = new LootTable.Builder();

        LootPoolSingletonContainer.Builder<?> lootPool = LootItem.lootTableItem(block.asItem());
        for (BooleanProperty property : MultiWallDecorationBlock.DIRECTION_TO_PROPERTY.values()) {
            lootPool = lootPool.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1), true)
                    .when(new LootItemBlockStatePropertyCondition.Builder(block)
                            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, true))));
        }
        add(block, lootTable.withPool(new LootPool.Builder().add(lootPool)));
    }
}
