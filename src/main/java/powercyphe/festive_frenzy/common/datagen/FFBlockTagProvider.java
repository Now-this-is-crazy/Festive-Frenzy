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
        valueLookupBuilder(FFTags.Blocks.SUPPORTS_BAUBLES)
                .add(Blocks.CHAIN, Blocks.IRON_BARS)
                .add(Blocks.GLASS_PANE,
                        Blocks.WHITE_STAINED_GLASS_PANE, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, Blocks.GRAY_STAINED_GLASS_PANE,
                        Blocks.BLACK_STAINED_GLASS_PANE, Blocks.BROWN_STAINED_GLASS_PANE, Blocks.RED_STAINED_GLASS_PANE,
                        Blocks.ORANGE_STAINED_GLASS_PANE, Blocks.YELLOW_STAINED_GLASS_PANE, Blocks.LIME_STAINED_GLASS_PANE,
                        Blocks.GREEN_STAINED_GLASS_PANE, Blocks.CYAN_STAINED_GLASS_PANE, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE,
                        Blocks.BLUE_STAINED_GLASS_PANE, Blocks.PURPLE_STAINED_GLASS_PANE, Blocks.MAGENTA_STAINED_GLASS_PANE,
                        Blocks.PINK_STAINED_GLASS_PANE)
                .addOptionalTag(BlockTags.FENCES)
                .addOptionalTag(BlockTags.WALLS)
                .addOptionalTag(BlockTags.LEAVES);

        valueLookupBuilder(FFTags.Blocks.SUPPORTS_MULTIFACE_DECORATION)
                .addOptionalTag(BlockTags.LEAVES);

        valueLookupBuilder(FFTags.Blocks.SUPPORTS_WREATH)
                .addOptionalTag(BlockTags.LEAVES);

        valueLookupBuilder(FFTags.Blocks.SUPPORTS_STAR_DECORATION)
                .addOptionalTag(BlockTags.LEAVES);



        valueLookupBuilder(BlockTags.STAIRS)
                .add(FFBlocks.RED_CANDY_CANE_STAIRS, FFBlocks.GREEN_CANDY_CANE_STAIRS,
                        FFBlocks.MIXED_CANDY_CANE_STAIRS,
                        FFBlocks.GINGERBREAD_STAIRS, FFBlocks.GINGERBREAD_BRICK_STAIRS,
                        FFBlocks.PACKED_SNOW_STAIRS, FFBlocks.POLISHED_PACKED_SNOW_STAIRS,
                        FFBlocks.PACKED_SNOW_BRICK_STAIRS, FFBlocks.CUT_BLUE_ICE_STAIRS,
                        FFBlocks.POLISHED_BLUE_ICE_STAIRS, FFBlocks.BLUE_ICE_BRICK_STAIRS
                );
        valueLookupBuilder(BlockTags.SLABS)
                .add(FFBlocks.RED_CANDY_CANE_SLAB, FFBlocks.GREEN_CANDY_CANE_SLAB,
                        FFBlocks.MIXED_CANDY_CANE_SLAB,
                        FFBlocks.GINGERBREAD_SLAB, FFBlocks.GINGERBREAD_BRICK_SLAB,
                        FFBlocks.PACKED_SNOW_SLAB, FFBlocks.POLISHED_PACKED_SNOW_SLAB,
                        FFBlocks.PACKED_SNOW_BRICK_SLAB, FFBlocks.CUT_BLUE_ICE_SLAB,
                        FFBlocks.POLISHED_BLUE_ICE_SLAB, FFBlocks.BLUE_ICE_BRICK_SLAB
                );
        valueLookupBuilder(BlockTags.WALLS)
                .add(FFBlocks.GINGERBREAD_WALL, FFBlocks.GINGERBREAD_BRICK_WALL,
                        FFBlocks.PACKED_SNOW_WALL, FFBlocks.POLISHED_PACKED_SNOW_WALL,
                        FFBlocks.PACKED_SNOW_BRICK_WALL, FFBlocks.CUT_BLUE_ICE_WALL,
                        FFBlocks.POLISHED_BLUE_ICE_WALL, FFBlocks.BLUE_ICE_BRICK_WALL
                );

        valueLookupBuilder(BlockTags.DOORS)
                .add(FFBlocks.GINGERBREAD_DOOR);
        valueLookupBuilder(BlockTags.TRAPDOORS)
                .add(FFBlocks.GINGERBREAD_TRAPDOOR);
        valueLookupBuilder(BlockTags.BUTTONS)
                .add(FFBlocks.GINGERBREAD_BUTTON);
        valueLookupBuilder(BlockTags.PRESSURE_PLATES)
                .add(FFBlocks.GINGERBREAD_PRESSURE_PLATE);


        valueLookupBuilder(BlockTags.SNOW)
                .add(FFBlocks.CHISELED_PACKED_SNOW,
                        FFBlocks.PACKED_SNOW, FFBlocks.PACKED_SNOW_STAIRS,
                        FFBlocks.PACKED_SNOW_SLAB, FFBlocks.PACKED_SNOW_WALL,
                        FFBlocks.POLISHED_PACKED_SNOW, FFBlocks.POLISHED_PACKED_SNOW_STAIRS,
                        FFBlocks.POLISHED_PACKED_SNOW_SLAB, FFBlocks.POLISHED_PACKED_SNOW_WALL,
                        FFBlocks.PACKED_SNOW_BRICKS, FFBlocks.PACKED_SNOW_BRICK_STAIRS,
                        FFBlocks.PACKED_SNOW_BRICK_SLAB, FFBlocks.PACKED_SNOW_BRICK_WALL
                );
        valueLookupBuilder(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON)
                .add(FFBlocks.CHISELED_PACKED_SNOW, FFBlocks.PACKED_SNOW,
                        FFBlocks.POLISHED_PACKED_SNOW, FFBlocks.PACKED_SNOW_BRICKS
                );
        valueLookupBuilder(BlockTags.ICE)
                .add(FFBlocks.CHISELED_BLUE_ICE,
                        FFBlocks.CUT_BLUE_ICE, FFBlocks.CUT_BLUE_ICE_STAIRS,
                        FFBlocks.CUT_BLUE_ICE_SLAB, FFBlocks.CUT_BLUE_ICE_WALL,
                        FFBlocks.POLISHED_BLUE_ICE, FFBlocks.POLISHED_BLUE_ICE_STAIRS,
                        FFBlocks.POLISHED_BLUE_ICE_SLAB, FFBlocks.POLISHED_BLUE_ICE_WALL,
                        FFBlocks.BLUE_ICE_BRICKS, FFBlocks.BLUE_ICE_BRICK_STAIRS,
                        FFBlocks.BLUE_ICE_BRICK_SLAB, FFBlocks.BLUE_ICE_BRICK_WALL
                );


        valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        FFBlocks.RED_CANDY_CANE_BLOCK, FFBlocks.RED_CANDY_CANE_STAIRS, FFBlocks.RED_CANDY_CANE_SLAB,
                        FFBlocks.GREEN_CANDY_CANE_BLOCK, FFBlocks.GREEN_CANDY_CANE_STAIRS, FFBlocks.GREEN_CANDY_CANE_SLAB,
                        FFBlocks.MIXED_CANDY_CANE_BLOCK, FFBlocks.MIXED_CANDY_CANE_STAIRS, FFBlocks.MIXED_CANDY_CANE_SLAB,
                        FFBlocks.PEPPERMINT_BLOCK,

                        FFBlocks.GINGERBREAD_BLOCK, FFBlocks.GINGERBREAD_STAIRS,
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
                        FFBlocks.BLUE_ICE_BRICK_SLAB, FFBlocks.BLUE_ICE_BRICK_WALL
                );

        valueLookupBuilder(BlockTags.MINEABLE_WITH_SHOVEL)
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
