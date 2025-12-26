package powercyphe.festive_frenzy.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import powercyphe.festive_frenzy.common.block.MultiWallDecorationBlock;
import powercyphe.festive_frenzy.common.registry.FFBlocks;
import powercyphe.festive_frenzy.common.registry.FFItems;
import powercyphe.festive_frenzy.common.util.SnowLoggable;

import java.util.concurrent.CompletableFuture;

public class FFBlockLootTableProvider extends FabricBlockLootTableProvider {
    protected FFBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        
        // Candy Blocks
        dropSelf(FFBlocks.RED_CANDY_CANE_BLOCK);
        dropSelf(FFBlocks.RED_CANDY_CANE_STAIRS);
        dropSelf(FFBlocks.RED_CANDY_CANE_SLAB);

        dropSelf(FFBlocks.GREEN_CANDY_CANE_BLOCK);
        dropSelf(FFBlocks.GREEN_CANDY_CANE_STAIRS);
        dropSelf(FFBlocks.GREEN_CANDY_CANE_SLAB);

        dropSelf(FFBlocks.MIXED_CANDY_CANE_BLOCK);
        dropSelf(FFBlocks.MIXED_CANDY_CANE_STAIRS);
        dropSelf(FFBlocks.MIXED_CANDY_CANE_SLAB);
        
        dropSelf(FFBlocks.PEPPERMINT_BLOCK);
        
        // Plants
        add(FFBlocks.SHORT_FROZEN_GRASS, block -> createGrassDrops(block)
                .withPool(createSnowloggable(block)));
        add(FFBlocks.TALL_FROZEN_GRASS, block -> createDoublePlantWithSeedDrops(block, FFBlocks.SHORT_FROZEN_GRASS)
                .withPool(createSnowloggable(block)));

        add(FFBlocks.HOLLY_BUSH, block -> createSingleItemTable(FFItems.HOLLY)
                .withPool(createSnowloggable(block)));

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

        add(FFBlocks.GINGERBREAD_DOOR, this::createDoorTable);
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
        for (Block tinsel : FFBlocks.TINSELS) {
            addMultiWallDecorationBlockDrops(tinsel);
        }

        addMultiWallDecorationBlockDrops(FFBlocks.FAIRY_LIGHTS);
        dropSelf(FFBlocks.WREATH);
        dropSelf(FFBlocks.STAR_DECORATION);
    }

    public LootPool.Builder createSnowloggable(Block block) {
        if (block instanceof SnowLoggable) {
            LootPoolSingletonContainer.Builder<?> snowball = LootItem.lootTableItem(Items.SNOWBALL).when(this.doesNotHaveSilkTouch().and(isHolding(ItemTags.SHOVELS)));
            LootPoolSingletonContainer.Builder<?> snowLayer = LootItem.lootTableItem(Blocks.SNOW).when(this.hasSilkTouch());

            for (int layers : SnowLoggable.SNOW_LAYERS.getPossibleValues()) {
                snowball = snowball.apply(SetItemCountFunction.setCount(ConstantValue.exactly(layers), false)
                        .when(new LootItemBlockStatePropertyCondition.Builder(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SnowLoggable.SNOW_LAYERS, layers))));
                snowLayer = snowLayer.apply(SetItemCountFunction.setCount(ConstantValue.exactly(layers), false)
                        .when(new LootItemBlockStatePropertyCondition.Builder(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SnowLoggable.SNOW_LAYERS, layers))));
            }
            return new LootPool.Builder().add(snowball).add(snowLayer);
        } else {
            throw new IllegalArgumentException("Cannot create Snowloggable Loot Table: " + block + " is not instance of " + SnowLoggable.class);
        }
    }


    public void addMultiWallDecorationBlockDrops(Block block) {
        LootTable.Builder lootTable = new LootTable.Builder();

        LootPoolSingletonContainer.Builder<?> lootPool = LootItem.lootTableItem(block.asItem())
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(0), false));
        for (BooleanProperty property : MultiWallDecorationBlock.DIRECTION_TO_PROPERTY.values()) {
            lootPool = lootPool.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1), true)
                    .when(new LootItemBlockStatePropertyCondition.Builder(block)
                            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, true))));
        }
        add(block, lootTable.withPool(new LootPool.Builder().add(lootPool)));
    }

    public LootItemCondition.Builder isHolding(TagKey<Item> itemTag) {
        return MatchTool.toolMatches(ItemPredicate.Builder.item().of(this.registries.lookupOrThrow(Registries.ITEM), itemTag));
    }
}
