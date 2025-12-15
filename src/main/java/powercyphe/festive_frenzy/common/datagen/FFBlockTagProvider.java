package powercyphe.festive_frenzy.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import powercyphe.festive_frenzy.common.registry.FFBlocks;
import powercyphe.festive_frenzy.common.registry.FFTags;

import java.util.concurrent.CompletableFuture;

public class FFBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public FFBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateTagBuilder(FFTags.Blocks.SUPPORTS_BAUBLES)
                .add(Blocks.CHAIN, Blocks.IRON_BARS)
                .add(Blocks.GLASS_PANE,
                        Blocks.WHITE_STAINED_GLASS_PANE, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, Blocks.GRAY_STAINED_GLASS_PANE,
                        Blocks.BLACK_STAINED_GLASS_PANE, Blocks.BROWN_STAINED_GLASS_PANE, Blocks.RED_STAINED_GLASS_PANE,
                        Blocks.ORANGE_STAINED_GLASS_PANE, Blocks.YELLOW_STAINED_GLASS_PANE, Blocks.LIME_STAINED_GLASS_PANE,
                        Blocks.GREEN_STAINED_GLASS_PANE, Blocks.CYAN_STAINED_GLASS_PANE, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE,
                        Blocks.BLUE_STAINED_GLASS_PANE, Blocks.PURPLE_STAINED_GLASS_PANE, Blocks.MAGENTA_STAINED_GLASS_PANE,
                        Blocks.PINK_STAINED_GLASS_PANE)
                .forceAddTag(BlockTags.FENCES)
                .forceAddTag(BlockTags.WALLS)
                .forceAddTag(BlockTags.LEAVES);

        getOrCreateTagBuilder(FFTags.Blocks.SUPPORTS_MULTIFACE_DECORATION)
                .forceAddTag(BlockTags.LEAVES);

        getOrCreateTagBuilder(FFTags.Blocks.SUPPORTS_STAR_DECORATION)
                .forceAddTag(BlockTags.LEAVES);



        getOrCreateTagBuilder(BlockTags.STAIRS)
                .add(FFBlocks.GINGERBREAD_STAIRS, FFBlocks.GINGERBREAD_BRICK_STAIRS,
                        FFBlocks.PACKED_SNOW_STAIRS, FFBlocks.POLISHED_PACKED_SNOW_STAIRS,
                        FFBlocks.PACKED_SNOW_BRICK_STAIRS, FFBlocks.CUT_BLUE_ICE_STAIRS,
                        FFBlocks.POLISHED_BLUE_ICE_STAIRS, FFBlocks.BLUE_ICE_BRICK_STAIRS
                );
        getOrCreateTagBuilder(BlockTags.SLABS)
                .add(FFBlocks.GINGERBREAD_SLAB, FFBlocks.GINGERBREAD_BRICK_SLAB,
                        FFBlocks.PACKED_SNOW_SLAB, FFBlocks.POLISHED_PACKED_SNOW_SLAB,
                        FFBlocks.PACKED_SNOW_BRICK_SLAB, FFBlocks.CUT_BLUE_ICE_SLAB,
                        FFBlocks.POLISHED_BLUE_ICE_SLAB, FFBlocks.BLUE_ICE_BRICK_SLAB
                );
        getOrCreateTagBuilder(BlockTags.WALLS)
                .add(FFBlocks.GINGERBREAD_WALL, FFBlocks.GINGERBREAD_BRICK_WALL,
                        FFBlocks.PACKED_SNOW_WALL, FFBlocks.POLISHED_PACKED_SNOW_WALL,
                        FFBlocks.PACKED_SNOW_BRICK_WALL, FFBlocks.CUT_BLUE_ICE_WALL,
                        FFBlocks.POLISHED_BLUE_ICE_WALL, FFBlocks.BLUE_ICE_BRICK_WALL
                );

        getOrCreateTagBuilder(BlockTags.DOORS)
                .add(FFBlocks.GINGERBREAD_DOOR);
        getOrCreateTagBuilder(BlockTags.TRAPDOORS)
                .add(FFBlocks.GINGERBREAD_TRAPDOOR);
        getOrCreateTagBuilder(BlockTags.BUTTONS)
                .add(FFBlocks.GINGERBREAD_BUTTON);
        getOrCreateTagBuilder(BlockTags.PRESSURE_PLATES)
                .add(FFBlocks.GINGERBREAD_PRESSURE_PLATE);


        getOrCreateTagBuilder(BlockTags.SNOW)
                .add(FFBlocks.CHISELED_PACKED_SNOW,
                        FFBlocks.PACKED_SNOW, FFBlocks.PACKED_SNOW_STAIRS,
                        FFBlocks.PACKED_SNOW_SLAB, FFBlocks.PACKED_SNOW_WALL,
                        FFBlocks.POLISHED_PACKED_SNOW, FFBlocks.POLISHED_PACKED_SNOW_STAIRS,
                        FFBlocks.POLISHED_PACKED_SNOW_SLAB, FFBlocks.POLISHED_PACKED_SNOW_WALL,
                        FFBlocks.PACKED_SNOW_BRICKS, FFBlocks.PACKED_SNOW_BRICK_STAIRS,
                        FFBlocks.PACKED_SNOW_BRICK_SLAB, FFBlocks.PACKED_SNOW_BRICK_WALL
                );
        getOrCreateTagBuilder(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON)
                .add(FFBlocks.CHISELED_PACKED_SNOW, FFBlocks.PACKED_SNOW,
                        FFBlocks.POLISHED_PACKED_SNOW, FFBlocks.PACKED_SNOW_BRICKS
                );
        getOrCreateTagBuilder(BlockTags.ICE)
                .add(FFBlocks.CHISELED_BLUE_ICE,
                        FFBlocks.CUT_BLUE_ICE, FFBlocks.CUT_BLUE_ICE_STAIRS,
                        FFBlocks.CUT_BLUE_ICE_SLAB, FFBlocks.CUT_BLUE_ICE_WALL,
                        FFBlocks.POLISHED_BLUE_ICE, FFBlocks.POLISHED_BLUE_ICE_STAIRS,
                        FFBlocks.POLISHED_BLUE_ICE_SLAB, FFBlocks.POLISHED_BLUE_ICE_WALL,
                        FFBlocks.BLUE_ICE_BRICKS, FFBlocks.BLUE_ICE_BRICK_STAIRS,
                        FFBlocks.BLUE_ICE_BRICK_SLAB, FFBlocks.BLUE_ICE_BRICK_WALL
                );


        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(FFBlocks.GINGERBREAD_BLOCK, FFBlocks.GINGERBREAD_STAIRS,
                        FFBlocks.GINGERBREAD_SLAB, FFBlocks.GINGERBREAD_WALL,
                        FFBlocks.GINGERBREAD_BRICKS, FFBlocks.GINGERBREAD_BRICK_STAIRS,
                        FFBlocks.GINGERBREAD_BRICK_SLAB, FFBlocks.GINGERBREAD_BRICK_WALL,
                        FFBlocks.CHISELED_GINGERBREAD_BLOCK,

                        FFBlocks.GINGERBREAD_DOOR, FFBlocks.GINGERBREAD_TRAPDOOR,
                        FFBlocks.GINGERBREAD_BUTTON, FFBlocks.GINGERBREAD_PRESSURE_PLATE,


                        FFBlocks.CHISELED_BLUE_ICE,
                        FFBlocks.CUT_BLUE_ICE, FFBlocks.CUT_BLUE_ICE_STAIRS,
                        FFBlocks.CUT_BLUE_ICE_SLAB, FFBlocks.CUT_BLUE_ICE_WALL,
                        FFBlocks.POLISHED_BLUE_ICE, FFBlocks.POLISHED_BLUE_ICE_STAIRS,
                        FFBlocks.POLISHED_BLUE_ICE_SLAB, FFBlocks.POLISHED_BLUE_ICE_WALL,
                        FFBlocks.BLUE_ICE_BRICKS, FFBlocks.BLUE_ICE_BRICK_STAIRS,
                        FFBlocks.BLUE_ICE_BRICK_SLAB, FFBlocks.BLUE_ICE_BRICK_WALL,

                        FFBlocks.RED_CANDY_CANE_BLOCK, FFBlocks.GREEN_CANDY_CANE_BLOCK,
                        FFBlocks.MIXED_CANDY_CANE_BLOCK, FFBlocks.PEPPERMINT_BLOCK
                );

        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(FFBlocks.CHISELED_PACKED_SNOW,
                        FFBlocks.PACKED_SNOW, FFBlocks.PACKED_SNOW_STAIRS,
                        FFBlocks.PACKED_SNOW_SLAB, FFBlocks.PACKED_SNOW_WALL,
                        FFBlocks.POLISHED_PACKED_SNOW, FFBlocks.POLISHED_PACKED_SNOW_STAIRS,
                        FFBlocks.POLISHED_PACKED_SNOW_SLAB, FFBlocks.POLISHED_PACKED_SNOW_WALL,
                        FFBlocks.PACKED_SNOW_BRICKS, FFBlocks.PACKED_SNOW_BRICK_STAIRS,
                        FFBlocks.PACKED_SNOW_BRICK_SLAB, FFBlocks.PACKED_SNOW_BRICK_WALL
                );
    }
}
