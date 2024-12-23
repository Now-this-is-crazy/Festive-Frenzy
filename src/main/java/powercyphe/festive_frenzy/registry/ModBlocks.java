package powercyphe.festive_frenzy.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import powercyphe.festive_frenzy.FestiveFrenzy;
import powercyphe.festive_frenzy.block.BaubleBlock;
import powercyphe.festive_frenzy.block.FairyLightBlock;
import powercyphe.festive_frenzy.block.PresentBlock;
import powercyphe.festive_frenzy.block.SideDecorationBlock;
import powercyphe.festive_frenzy.item.BaubleBlockItem;
import powercyphe.festive_frenzy.item.PresentBlockItem;

import java.util.HashMap;
import java.util.function.ToIntFunction;

public class ModBlocks {

    public static Block RED_CANDY_CANE_BLOCK = register("red_candy_cane_block", new Block(FabricBlockSettings.create().sounds(BlockSoundGroup.PACKED_MUD)));
    public static Block GREEN_CANDY_CANE_BLOCK = register("green_candy_cane_block", new Block(FabricBlockSettings.create().sounds(BlockSoundGroup.PACKED_MUD)));
    public static Block MIXED_CANDY_CANE_BLOCK = register("mixed_candy_cane_block", new Block(FabricBlockSettings.create().sounds(BlockSoundGroup.PACKED_MUD)));
    public static Block PEPPERMINT_BLOCK = register("peppermint_block", new Block(FabricBlockSettings.create().sounds(BlockSoundGroup.PACKED_MUD)));


    // Snow Blocks
    public static Block PACKED_SNOW = register("packed_snow", new Block(FabricBlockSettings.copyOf(Blocks.SNOW_BLOCK).strength(0.6f)));
    public static Block POLISHED_PACKED_SNOW = register("polished_packed_snow", new Block(FabricBlockSettings.copyOf(Blocks.SNOW_BLOCK).strength(0.6f)));
    public static Block POLISHED_PACKED_SNOW_STAIRS = register("polished_packed_snow_stairs", new StairsBlock(ModBlocks.POLISHED_PACKED_SNOW.getDefaultState(), FabricBlockSettings.copyOf(Blocks.SNOW_BLOCK).strength(0.6f)));
    public static Block POLISHED_PACKED_SNOW_SLAB = register("polished_packed_snow_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.SNOW_BLOCK).strength(0.6f)));
    public static Block POLISHED_PACKED_SNOW_WALL = register("polished_packed_snow_wall", new WallBlock(FabricBlockSettings.copyOf(Blocks.SNOW_BLOCK).strength(0.6f)));

    public static Block PACKED_SNOW_BRICKS = register("packed_snow_bricks", new Block(FabricBlockSettings.copyOf(Blocks.SNOW_BLOCK).strength(0.6f)));
    public static Block PACKED_SNOW_BRICK_STAIRS = register("packed_snow_brick_stairs", new StairsBlock(ModBlocks.PACKED_SNOW_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.SNOW_BLOCK).strength(0.6f)));
    public static Block PACKED_SNOW_BRICK_SLAB = register("packed_snow_brick_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.SNOW_BLOCK).strength(0.6f)));
    public static Block PACKED_SNOW_BRICK_WALL = register("packed_snow_brick_wall", new WallBlock(FabricBlockSettings.copyOf(Blocks.SNOW_BLOCK).strength(0.6f)));

    public static Block CHISELED_PACKED_SNOW = register("chiseled_packed_snow", new Block(FabricBlockSettings.copyOf(Blocks.SNOW_BLOCK).strength(0.6f)));


    // Ice Blocks
    public static Block CUT_BLUE_ICE = register("cut_blue_ice", new Block(FabricBlockSettings.copyOf(Blocks.BLUE_ICE)));
    public static Block POLISHED_BLUE_ICE = register("polished_blue_ice", new Block(FabricBlockSettings.copyOf(Blocks.BLUE_ICE)));
    public static Block POLISHED_BLUE_ICE_STAIRS = register("polished_blue_ice_stairs", new StairsBlock(ModBlocks.POLISHED_BLUE_ICE.getDefaultState(), FabricBlockSettings.copyOf(Blocks.BLUE_ICE)));
    public static Block POLISHED_BLUE_ICE_SLAB = register("polished_blue_ice_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.BLUE_ICE)));
    public static Block POLISHED_BLUE_ICE_WALL = register("polished_blue_ice_wall", new WallBlock(FabricBlockSettings.copyOf(Blocks.BLUE_ICE)));

    public static Block BLUE_ICE_BRICKS = register("blue_ice_bricks", new Block(FabricBlockSettings.copyOf(Blocks.BLUE_ICE)));
    public static Block BLUE_ICE_BRICK_STAIRS = register("blue_ice_brick_stairs", new StairsBlock(ModBlocks.BLUE_ICE_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.BLUE_ICE)));
    public static Block BLUE_ICE_BRICK_SLAB = register("blue_ice_brick_slab", new SlabBlock(FabricBlockSettings.copyOf(Blocks.BLUE_ICE)));
    public static Block BLUE_ICE_BRICK_WALL = register("blue_ice_brick_wall", new WallBlock(FabricBlockSettings.copyOf(Blocks.BLUE_ICE)));

    public static Block CHISELED_BLUE_ICE = register("chiseled_blue_ice", new Block(FabricBlockSettings.copyOf(Blocks.BLUE_ICE)));


    public static Block FAIRY_LIGHTS = register("fairy_lights", new FairyLightBlock(FabricBlockSettings.create().nonOpaque().collidable(false).luminance(9)));

    // Tinsels
    public static DefaultedList<Block> TINSELS = DefaultedList.of();

    public static Block WHITE_TINSEL = registerTinsel("white_tinsel");
    public static Block LIGHT_GRAY_TINSEL = registerTinsel("light_gray_tinsel");
    public static Block GRAY_TINSEL = registerTinsel("gray_tinsel");
    public static Block BLACK_TINSEL = registerTinsel("black_tinsel");
    public static Block BROWN_TINSEL = registerTinsel("brown_tinsel");
    public static Block RED_TINSEL = registerTinsel("red_tinsel");
    public static Block ORANGE_TINSEL = registerTinsel("orange_tinsel");
    public static Block YELLOW_TINSEL = registerTinsel("yellow_tinsel");
    public static Block LIME_TINSEL = registerTinsel("lime_tinsel");
    public static Block GREEN_TINSEL = registerTinsel("green_tinsel");
    public static Block CYAN_TINSEL = registerTinsel("cyan_tinsel");
    public static Block LIGHT_BLUE_TINSEL = registerTinsel("light_blue_tinsel");
    public static Block BLUE_TINSEL = registerTinsel("blue_tinsel");
    public static Block PURPLE_TINSEL = registerTinsel("purple_tinsel");
    public static Block MAGENTA_TINSEL = registerTinsel("magenta_tinsel");
    public static Block PINK_TINSEL = registerTinsel("pink_tinsel");

    // Baubles
    public static DefaultedList<Block> BAUBLES = DefaultedList.of();

    public static Block WHITE_BAUBLE = registerBauble("white_bauble");
    public static Block LIGHT_GRAY_BAUBLE = registerBauble("light_gray_bauble");
    public static Block GRAY_BAUBLE = registerBauble("gray_bauble");
    public static Block BLACK_BAUBLE = registerBauble("black_bauble");
    public static Block BROWN_BAUBLE = registerBauble("brown_bauble");
    public static Block RED_BAUBLE = registerBauble("red_bauble");
    public static Block ORANGE_BAUBLE = registerBauble("orange_bauble");
    public static Block YELLOW_BAUBLE = registerBauble("yellow_bauble");
    public static Block LIME_BAUBLE = registerBauble("lime_bauble");
    public static Block GREEN_BAUBLE = registerBauble("green_bauble");
    public static Block CYAN_BAUBLE = registerBauble("cyan_bauble");
    public static Block LIGHT_BLUE_BAUBLE = registerBauble("light_blue_bauble");
    public static Block BLUE_BAUBLE = registerBauble("blue_bauble");
    public static Block PURPLE_BAUBLE = registerBauble("purple_bauble");
    public static Block MAGENTA_BAUBLE = registerBauble("magenta_bauble");
    public static Block PINK_BAUBLE = registerBauble("pink_bauble");

    // Presents
    public static DefaultedList<Block> PRESENTS = DefaultedList.of();

    public static Block WHITE_PRESENT = registerPresent("white_present");
    public static Block LIGHT_GRAY_PRESENT = registerPresent("light_gray_present");
    public static Block GRAY_PRESENT = registerPresent("gray_present");
    public static Block BLACK_PRESENT = registerPresent("black_present");
    public static Block BROWN_PRESENT = registerPresent("brown_present");
    public static Block RED_PRESENT = registerPresent("red_present");
    public static Block ORANGE_PRESENT = registerPresent("orange_present");
    public static Block YELLOW_PRESENT = registerPresent("yellow_present");
    public static Block LIME_PRESENT = registerPresent("lime_present");
    public static Block GREEN_PRESENT = registerPresent("green_present");
    public static Block CYAN_PRESENT = registerPresent("cyan_present");
    public static Block LIGHT_BLUE_PRESENT = registerPresent("light_blue_present");
    public static Block BLUE_PRESENT = registerPresent("blue_present");
    public static Block PURPLE_PRESENT = registerPresent("purple_present");
    public static Block MAGENTA_PRESENT = registerPresent("magenta_present");
    public static Block PINK_PRESENT = registerPresent("pink_present");

    public static Block KELP_PRESENT = registerPresent("kelp_present");
    public static Block FOLLY_PRESENT = registerPresent("folly_present");
    public static Block GOLDEN_PRESENT = registerPresent("golden_present");
    public static Block SAND_PRESENT = registerPresent("sand_present");
    public static Block BLOOD_PRESENT = registerPresent("blood_present");
    public static Block SCULK_PRESENT = registerPresent("sculk_present");

    public static void init() {}

    public static Block register(String id, Block block) {
        block = registerWithoutItem(id, block);
        ModItems.registerBlockItem(block);
        return block;
    }

    public static Block registerWithoutItem(String id, Block block) {
        return Registry.register(Registries.BLOCK, FestiveFrenzy.id(id), block);
    }


    // Unique Block Registries
    public static Block registerPresent(String id) {
        Block block = new PresentBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).nonOpaque());
        block = registerWithoutItem(id, block);
        ModItems.register(id, new PresentBlockItem(block, new Item.Settings().maxCount(1)));
        PRESENTS.add(block);
        return block;
    }

    public static Block registerBauble(String id) {
        Block block = new BaubleBlock(FabricBlockSettings.create().nonOpaque().sounds(BlockSoundGroup.GLASS)
                .luminance(createBaubleLuminance(11)));
        block = registerWithoutItem(id, block);
        ModItems.register(id, new BaubleBlockItem(block, new Item.Settings().maxCount(16)));
        BAUBLES.add(block);
        return block;
    }

    public static Block registerTinsel(String id) {
        Block block = new SideDecorationBlock(FabricBlockSettings.create().nonOpaque().sounds(BlockSoundGroup.WOOL).collidable(false));
        block = register(id, block);
        TINSELS.add(block);
        return block;
    }

    public static ToIntFunction<BlockState> createBaubleLuminance(int litLevel) {
        return (state) -> {
            return (Boolean)state.get(BaubleBlock.GLOWING) ? litLevel : 0;
        };
    }
}
