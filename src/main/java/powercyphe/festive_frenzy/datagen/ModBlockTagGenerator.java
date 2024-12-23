package powercyphe.festive_frenzy.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import powercyphe.festive_frenzy.registry.ModBlocks;
import powercyphe.festive_frenzy.registry.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.BAUBLE_PLACEABLE)
                .forceAddTag(BlockTags.LEAVES)
                .forceAddTag(BlockTags.FENCES)
                .forceAddTag(BlockTags.WALLS)
                .add(Blocks.CHAIN)
                .add(Blocks.IRON_BARS);

        getOrCreateTagBuilder(ModTags.SIDE_DECO_PLACEABLE)
                .forceAddTag(BlockTags.LEAVES);


        getOrCreateTagBuilder(BlockTags.SNOW)
                .add(ModBlocks.PACKED_SNOW)
                .add(ModBlocks.CHISELED_PACKED_SNOW)
                .add(ModBlocks.POLISHED_PACKED_SNOW)
                .add(ModBlocks.POLISHED_PACKED_SNOW_STAIRS)
                .add(ModBlocks.POLISHED_PACKED_SNOW_SLAB)
                .add(ModBlocks.POLISHED_PACKED_SNOW_WALL)
                .add(ModBlocks.PACKED_SNOW_BRICKS)
                .add(ModBlocks.PACKED_SNOW_BRICK_STAIRS)
                .add(ModBlocks.PACKED_SNOW_BRICK_SLAB)
                .add(ModBlocks.PACKED_SNOW_BRICK_WALL);

        getOrCreateTagBuilder(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON)
                .add(ModBlocks.PACKED_SNOW)
                .add(ModBlocks.CHISELED_PACKED_SNOW)
                .add(ModBlocks.POLISHED_PACKED_SNOW)
                .add(ModBlocks.POLISHED_PACKED_SNOW_STAIRS)
                .add(ModBlocks.POLISHED_PACKED_SNOW_SLAB)
                .add(ModBlocks.POLISHED_PACKED_SNOW_WALL)
                .add(ModBlocks.PACKED_SNOW_BRICKS)
                .add(ModBlocks.PACKED_SNOW_BRICK_STAIRS)
                .add(ModBlocks.PACKED_SNOW_BRICK_SLAB)
                .add(ModBlocks.PACKED_SNOW_BRICK_WALL);

        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(ModBlocks.PACKED_SNOW)
                .add(ModBlocks.CHISELED_PACKED_SNOW)
                .add(ModBlocks.POLISHED_PACKED_SNOW)
                .add(ModBlocks.POLISHED_PACKED_SNOW_STAIRS)
                .add(ModBlocks.POLISHED_PACKED_SNOW_SLAB)
                .add(ModBlocks.POLISHED_PACKED_SNOW_WALL)
                .add(ModBlocks.PACKED_SNOW_BRICKS)
                .add(ModBlocks.PACKED_SNOW_BRICK_STAIRS)
                .add(ModBlocks.PACKED_SNOW_BRICK_SLAB)
                .add(ModBlocks.PACKED_SNOW_BRICK_WALL);


        getOrCreateTagBuilder(BlockTags.ICE)
                .add(ModBlocks.CUT_BLUE_ICE)
                .add(ModBlocks.CHISELED_BLUE_ICE)
                .add(ModBlocks.POLISHED_BLUE_ICE)
                .add(ModBlocks.POLISHED_BLUE_ICE_STAIRS)
                .add(ModBlocks.POLISHED_BLUE_ICE_SLAB)
                .add(ModBlocks.POLISHED_BLUE_ICE_WALL)
                .add(ModBlocks.BLUE_ICE_BRICKS)
                .add(ModBlocks.BLUE_ICE_BRICK_STAIRS)
                .add(ModBlocks.BLUE_ICE_BRICK_SLAB)
                .add(ModBlocks.BLUE_ICE_BRICK_WALL);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.CUT_BLUE_ICE)
                .add(ModBlocks.CHISELED_BLUE_ICE)
                .add(ModBlocks.POLISHED_BLUE_ICE)
                .add(ModBlocks.POLISHED_BLUE_ICE_STAIRS)
                .add(ModBlocks.POLISHED_BLUE_ICE_SLAB)
                .add(ModBlocks.POLISHED_BLUE_ICE_WALL)
                .add(ModBlocks.BLUE_ICE_BRICKS)
                .add(ModBlocks.BLUE_ICE_BRICK_STAIRS)
                .add(ModBlocks.BLUE_ICE_BRICK_SLAB)
                .add(ModBlocks.BLUE_ICE_BRICK_WALL);
    }
}
