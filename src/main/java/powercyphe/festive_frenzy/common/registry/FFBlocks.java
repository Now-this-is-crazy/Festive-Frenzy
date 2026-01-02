package powercyphe.festive_frenzy.common.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.block.*;
import powercyphe.festive_frenzy.common.block.entity.PresentBlockEntity;
import powercyphe.festive_frenzy.common.block.entity.StarDecorationBlockEntity;
import powercyphe.festive_frenzy.common.item.BaubleItem;
import powercyphe.festive_frenzy.common.item.PresentItem;
import powercyphe.festive_frenzy.common.item.component.ExplosiveBaubleComponent;
import powercyphe.festive_frenzy.common.item.component.PresentContentsComponent;

import java.util.function.Function;

public class FFBlocks {

    public static class Entities {
        public static BlockEntityType<PresentBlockEntity> PRESENT_BLOCK_ENTITY = register("present",
                FabricBlockEntityTypeBuilder.create(PresentBlockEntity::new, FFBlocks.PRESENTS).build());

        public static BlockEntityType<StarDecorationBlockEntity> STAR_DECORATION_BLOCK_ENTITY = register("star_decoration",
                FabricBlockEntityTypeBuilder.create(StarDecorationBlockEntity::new, FFBlocks.STAR_DECORATION).build());

        public static void init() {}

        public static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType<T> type) {
            return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, FestiveFrenzy.id(id), type);
        }
    }

    // Blocks
    public static final Block SHORT_FROZEN_GRASS = register("short_frozen_grass", FrozenGrassBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)
            .isViewBlocking((state, blockGetter, blockPos) -> state.getValue(FrozenGrassBlock.SNOW_LAYERS) >= 8)
            .randomTicks());
    public static final Block TALL_FROZEN_GRASS = register("tall_frozen_grass", TallFrozenGrassBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS)
            .isViewBlocking((state, blockGetter, blockPos) -> state.getValue(TallFrozenGrassBlock.SNOW_LAYERS) >= 8)
            .randomTicks());

    public static final Block HOLLY_BUSH = registerWithoutItem("holly_bush", HollyBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)
            .isViewBlocking((state, blockGetter, blockPos) -> state.getValue(HollyBushBlock.SNOW_LAYERS) >= 8)
            .randomTicks());


    public static final Block RED_CANDY_CANE_BLOCK = register("red_candy_cane_block", Block::new, BlockBehaviour.Properties.of().strength(0.6F, 3F).sound(SoundType.PACKED_MUD));
    public static final Block RED_CANDY_CANE_STAIRS = register("red_candy_cane_stairs", properties ->
            new StairBlock(RED_CANDY_CANE_BLOCK.defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(RED_CANDY_CANE_BLOCK));
    public static final Block RED_CANDY_CANE_SLAB = register("red_candy_cane_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(RED_CANDY_CANE_BLOCK));

    public static final Block GREEN_CANDY_CANE_BLOCK = register("green_candy_cane_block", Block::new, BlockBehaviour.Properties.ofFullCopy(RED_CANDY_CANE_BLOCK));
    public static final Block GREEN_CANDY_CANE_STAIRS = register("green_candy_cane_stairs", properties ->
            new StairBlock(GREEN_CANDY_CANE_BLOCK.defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(GREEN_CANDY_CANE_BLOCK));
    public static final Block GREEN_CANDY_CANE_SLAB = register("green_candy_cane_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(GREEN_CANDY_CANE_BLOCK));

    public static final Block MIXED_CANDY_CANE_BLOCK = register("mixed_candy_cane_block", Block::new, BlockBehaviour.Properties.ofFullCopy(RED_CANDY_CANE_BLOCK));
    public static final Block MIXED_CANDY_CANE_STAIRS = register("mixed_candy_cane_stairs", properties ->
            new StairBlock(MIXED_CANDY_CANE_BLOCK.defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(MIXED_CANDY_CANE_BLOCK));
    public static final Block MIXED_CANDY_CANE_SLAB = register("mixed_candy_cane_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(MIXED_CANDY_CANE_BLOCK));

    public static final Block PEPPERMINT_BLOCK = register("peppermint_block", Block::new, BlockBehaviour.Properties.ofFullCopy(RED_CANDY_CANE_BLOCK));

    // Gingerbread
    public static final BlockSetType GINGERBREAD_SET = new BlockSetType("gingerbread", true, true, true, BlockSetType.PressurePlateSensitivity.EVERYTHING,
            SoundType.PACKED_MUD, SoundEvents.NETHER_WOOD_DOOR_CLOSE, SoundEvents.NETHER_WOOD_DOOR_OPEN,
            SoundEvents.NETHER_WOOD_TRAPDOOR_CLOSE, SoundEvents.NETHER_WOOD_TRAPDOOR_OPEN,
            SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_ON,
            SoundEvents.NETHER_WOOD_BUTTON_CLICK_OFF, SoundEvents.NETHER_WOOD_BUTTON_CLICK_ON);

    public static final Block GINGERBREAD_BLOCK = register("gingerbread_block", Block::new, BlockBehaviour.Properties.of().strength(0.6F, 4F).sound(SoundType.PACKED_MUD));
    public static final Block GINGERBREAD_STAIRS = register("gingerbread_stairs", properties ->
                    new StairBlock(GINGERBREAD_BLOCK.defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(GINGERBREAD_BLOCK));
    public static final Block GINGERBREAD_SLAB = register("gingerbread_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(GINGERBREAD_BLOCK));
    public static final Block GINGERBREAD_WALL = register("gingerbread_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(GINGERBREAD_BLOCK));

    public static final Block GINGERBREAD_BRICKS = register("gingerbread_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(GINGERBREAD_BLOCK));
    public static final Block GINGERBREAD_BRICK_STAIRS = register("gingerbread_brick_stairs", properties ->
                    new StairBlock(GINGERBREAD_BRICKS.defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(GINGERBREAD_BRICKS));
    public static final Block GINGERBREAD_BRICK_SLAB = register("gingerbread_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(GINGERBREAD_BRICKS));
    public static final Block GINGERBREAD_BRICK_WALL = register("gingerbread_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(GINGERBREAD_BRICKS));

    public static final Block CHISELED_GINGERBREAD_BLOCK = register("chiseled_gingerbread_block", Block::new, BlockBehaviour.Properties.ofFullCopy(GINGERBREAD_BLOCK));
    public static final Block GINGERBREAD_DOOR = register("gingerbread_door", properties -> new DoorBlock(GINGERBREAD_SET, properties),
            BlockBehaviour.Properties.ofFullCopy(GINGERBREAD_BLOCK).noOcclusion().pushReaction(PushReaction.DESTROY));
    public static final Block GINGERBREAD_TRAPDOOR = register("gingerbread_trapdoor", properties -> new TrapDoorBlock(GINGERBREAD_SET, properties),
            BlockBehaviour.Properties.ofFullCopy(GINGERBREAD_BLOCK).noOcclusion().isValidSpawn(Blocks::never));
    public static final Block GINGERBREAD_BUTTON = register("gingerbread_button", properties -> new ButtonBlock(GINGERBREAD_SET, 30, properties),
            BlockBehaviour.Properties.ofFullCopy(GINGERBREAD_BLOCK).noCollision().pushReaction(PushReaction.DESTROY));
    public static final Block GINGERBREAD_PRESSURE_PLATE = register("gingerbread_pressure_plate", properties -> new PressurePlateBlock(GINGERBREAD_SET, properties),
            BlockBehaviour.Properties.ofFullCopy(GINGERBREAD_BLOCK).forceSolidOn().noCollision().pushReaction(PushReaction.DESTROY));


    // Packed Snow
    public static final Block PACKED_SNOW = register("packed_snow", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SNOW_BLOCK).strength(1F, 6F).requiresCorrectToolForDrops());
    public static final Block PACKED_SNOW_STAIRS = register("packed_snow_stairs", (properties -> new StairBlock(PACKED_SNOW.defaultBlockState(), properties)), BlockBehaviour.Properties.ofFullCopy(PACKED_SNOW));
    public static final Block PACKED_SNOW_SLAB = register("packed_snow_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(PACKED_SNOW));
    public static final Block PACKED_SNOW_WALL = register("packed_snow_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(PACKED_SNOW));

    public static final Block POLISHED_PACKED_SNOW = register("polished_packed_snow", Block::new, BlockBehaviour.Properties.ofFullCopy(PACKED_SNOW));
    public static final Block POLISHED_PACKED_SNOW_STAIRS = register("polished_packed_snow_stairs", (properties -> new StairBlock(POLISHED_PACKED_SNOW.defaultBlockState(), properties)), BlockBehaviour.Properties.ofFullCopy(POLISHED_PACKED_SNOW));
    public static final Block POLISHED_PACKED_SNOW_SLAB = register("polished_packed_snow_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(POLISHED_PACKED_SNOW));
    public static final Block POLISHED_PACKED_SNOW_WALL = register("polished_packed_snow_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(POLISHED_PACKED_SNOW));

    public static final Block PACKED_SNOW_BRICKS = register("packed_snow_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(PACKED_SNOW));
    public static final Block PACKED_SNOW_BRICK_STAIRS = register("packed_snow_brick_stairs", (properties -> new StairBlock(PACKED_SNOW_BRICKS.defaultBlockState(), properties)), BlockBehaviour.Properties.ofFullCopy(PACKED_SNOW_BRICKS));
    public static final Block PACKED_SNOW_BRICK_SLAB = register("packed_snow_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(PACKED_SNOW_BRICKS));
    public static final Block PACKED_SNOW_BRICK_WALL = register("packed_snow_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(PACKED_SNOW_BRICKS));

    public static final Block CHISELED_PACKED_SNOW = register("chiseled_packed_snow", Block::new, BlockBehaviour.Properties.ofFullCopy(PACKED_SNOW));


    // Blue Ice Blocks
    public static final Block CUT_BLUE_ICE = register("cut_blue_ice", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_ICE).requiresCorrectToolForDrops());
    public static final Block CUT_BLUE_ICE_STAIRS = register("cut_blue_ice_stairs", (properties -> new StairBlock(CUT_BLUE_ICE.defaultBlockState(), properties)), BlockBehaviour.Properties.ofFullCopy(CUT_BLUE_ICE));
    public static final Block CUT_BLUE_ICE_SLAB = register("cut_blue_ice_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(CUT_BLUE_ICE));
    public static final Block CUT_BLUE_ICE_WALL = register("cut_blue_ice_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(CUT_BLUE_ICE));

    public static final Block POLISHED_BLUE_ICE = register("polished_blue_ice", Block::new, BlockBehaviour.Properties.ofFullCopy(CUT_BLUE_ICE));
    public static final Block POLISHED_BLUE_ICE_STAIRS = register("polished_blue_ice_stairs", (properties -> new StairBlock(POLISHED_BLUE_ICE.defaultBlockState(), properties)), BlockBehaviour.Properties.ofFullCopy(POLISHED_BLUE_ICE));
    public static final Block POLISHED_BLUE_ICE_SLAB = register("polished_blue_ice_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(POLISHED_BLUE_ICE));
    public static final Block POLISHED_BLUE_ICE_WALL = register("polished_blue_ice_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(POLISHED_BLUE_ICE));

    public static final Block BLUE_ICE_BRICKS = register("blue_ice_bricks", Block::new, BlockBehaviour.Properties.ofFullCopy(CUT_BLUE_ICE));
    public static final Block BLUE_ICE_BRICK_STAIRS = register("blue_ice_brick_stairs", (properties -> new StairBlock(BLUE_ICE_BRICKS.defaultBlockState(), properties)), BlockBehaviour.Properties.ofFullCopy(BLUE_ICE_BRICKS));
    public static final Block BLUE_ICE_BRICK_SLAB = register("blue_ice_brick_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(BLUE_ICE_BRICKS));
    public static final Block BLUE_ICE_BRICK_WALL = register("blue_ice_brick_wall", WallBlock::new, BlockBehaviour.Properties.ofFullCopy(BLUE_ICE_BRICKS));

    public static final Block CHISELED_BLUE_ICE = register("chiseled_blue_ice", Block::new, BlockBehaviour.Properties.ofFullCopy(CUT_BLUE_ICE));

    // Presents
    public static Block[] PRESENTS;

    public static final Block WHITE_PRESENT = registerPresent("white");
    public static final Block LIGHT_GRAY_PRESENT = registerPresent("light_gray");
    public static final Block GRAY_PRESENT = registerPresent("gray");
    public static final Block BLACK_PRESENT = registerPresent("black");
    public static final Block BROWN_PRESENT = registerPresent("brown");
    public static final Block RED_PRESENT = registerPresent("red");
    public static final Block ORANGE_PRESENT = registerPresent("orange");
    public static final Block YELLOW_PRESENT = registerPresent("yellow");
    public static final Block LIME_PRESENT = registerPresent("lime");
    public static final Block GREEN_PRESENT = registerPresent("green");
    public static final Block CYAN_PRESENT = registerPresent("cyan");
    public static final Block LIGHT_BLUE_PRESENT = registerPresent("light_blue");
    public static final Block BLUE_PRESENT = registerPresent("blue");
    public static final Block PURPLE_PRESENT = registerPresent("purple");
    public static final Block MAGENTA_PRESENT = registerPresent("magenta");
    public static final Block PINK_PRESENT = registerPresent("pink");

    public static final Block FOLLY_PRESENT = registerPresent("folly");
    public static final Block BLOOD_PRESENT = registerPresent("blood");
    public static final Block GOLDEN_PRESENT = registerPresent("golden");
    public static final Block SAND_PRESENT = registerPresent("sand");
    public static final Block KELP_PRESENT = registerPresent("kelp");
    public static final Block SCULK_PRESENT = registerPresent("sculk");

    // Baubles
    public static Block[] BAUBLES;

    public static final Block WHITE_BAUBLE = registerBauble("white", 0xffffff);
    public static final Block LIGHT_GRAY_BAUBLE = registerBauble("light_gray", 0xc1c3b7);
    public static final Block GRAY_BAUBLE = registerBauble("gray", 0x748987);
    public static final Block BLACK_BAUBLE = registerBauble("black", 0x393c54);
    public static final Block BROWN_BAUBLE = registerBauble("brown", 0xab672f);
    public static final Block RED_BAUBLE = registerBauble("red", 0xf43d33);
    public static final Block ORANGE_BAUBLE = registerBauble("orange", 0xf28021);
    public static final Block YELLOW_BAUBLE = registerBauble("yellow", 0xedbe2c);
    public static final Block LIME_BAUBLE = registerBauble("lime", 0x9fe54f);
    public static final Block GREEN_BAUBLE = registerBauble("green", 0x82a010);
    public static final Block CYAN_BAUBLE = registerBauble("cyan", 0x20a69c);
    public static final Block LIGHT_BLUE_BAUBLE = registerBauble("light_blue", 0x45c9e5);
    public static final Block BLUE_BAUBLE = registerBauble("blue", 0x3a74b5);
    public static final Block PURPLE_BAUBLE = registerBauble("purple", 0xab33ca);
    public static final Block MAGENTA_BAUBLE = registerBauble("magenta", 0xdf53d3);
    public static final Block PINK_BAUBLE = registerBauble("pink", 0xcf73a3);

    // Baubles
    public static Block[] TINSELS;

    public static final Block WHITE_TINSEL = registerTinsel("white");
    public static final Block LIGHT_GRAY_TINSEL = registerTinsel("light_gray");
    public static final Block GRAY_TINSEL = registerTinsel("gray");
    public static final Block BLACK_TINSEL = registerTinsel("black");
    public static final Block BROWN_TINSEL = registerTinsel("brown");
    public static final Block RED_TINSEL = registerTinsel("red");
    public static final Block ORANGE_TINSEL = registerTinsel("orange");
    public static final Block YELLOW_TINSEL = registerTinsel("yellow");
    public static final Block LIME_TINSEL = registerTinsel("lime");
    public static final Block GREEN_TINSEL = registerTinsel("green");
    public static final Block CYAN_TINSEL = registerTinsel("cyan");
    public static final Block LIGHT_BLUE_TINSEL = registerTinsel("light_blue");
    public static final Block BLUE_TINSEL = registerTinsel("blue");
    public static final Block PURPLE_TINSEL = registerTinsel("purple");
    public static final Block MAGENTA_TINSEL = registerTinsel("magenta");
    public static final Block PINK_TINSEL = registerTinsel("pink");

    public static final Block FAIRY_LIGHTS = register("fairy_lights", FairyLightsBlock::new, BlockBehaviour.Properties.ofFullCopy(WHITE_TINSEL)
            .mapColor(MapColor.COLOR_GRAY).sound(SoundType.METAL).lightLevel(state -> 11));
    public static final Block WREATH = register("wreath", WreathBlock::new, BlockBehaviour.Properties.of().strength(0.5F, 2F)
            .sound(SoundType.GRASS).noCollision().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY));
    public static final Block STAR_DECORATION = register("star_decoration", StarDecorationBlock::new, BlockBehaviour.Properties.of()
            .noOcclusion().noCollision().strength(5F).instabreak().sound(SoundType.METAL).lightLevel(state -> 9)
            .emissiveRendering((state, blockGetter, blockPos) -> !state.getValue(StarDecorationBlock.WATERLOGGED)).pushReaction(PushReaction.DESTROY));

    public static void init() {}

    public static Block register(String id, Function<BlockBehaviour.Properties, Block> blockFunction, BlockBehaviour.Properties properties) {
        Block block = registerWithoutItem(id, blockFunction, properties);
        FFItems.register(id, (itemProperties) -> new BlockItem(block, itemProperties), new Item.Properties());
        return block;
    }

    public static Block registerWithoutItem(String id, Function<BlockBehaviour.Properties, Block> blockFunction, BlockBehaviour.Properties properties) {
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, FestiveFrenzy.id(id));
        Block block = Registry.register(BuiltInRegistries.BLOCK, blockKey, blockFunction.apply(properties.setId(blockKey)));

        return block;
    }

    public static Block registerPresent(String type) {
        String id = type + "_present";

        Block block = registerWithoutItem(id, PresentBlock::new,
                BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL).strength(1F, 3F).noOcclusion());
        FFItems.register(id, properties -> new PresentItem(block, properties), new Item.Properties()
                .component(FFItems.Components.PRESENT_CONTENTS_COMPONENT, PresentContentsComponent.DEFAULT));
        return block;
    }

    public static Block registerBauble(String type, int explosionColor) {
        String id = type + "_bauble";

        Block block = registerWithoutItem(id, properties -> new BaubleBlock(explosionColor, properties),
                BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).strength(0.5F, 3F).noOcclusion()
                .lightLevel(state -> state.getValue(BaubleBlock.IS_GLOWING) ? 7 : 0)
                .emissiveRendering((state, blockGetter, blockPos) -> state.getValue(BaubleBlock.IS_GLOWING)));
        FFItems.register(id, properties -> new BaubleItem(block, properties), new Item.Properties()
                .component(FFItems.Components.EXPLOSIVE_BAUBLE_COMPONENT, ExplosiveBaubleComponent.DEFAULT));
        return block;
    }

    public static Block registerTinsel(String type) {
        return register(type + "_tinsel", TinselBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL).strength(0.2F, 3F).noCollision().noOcclusion());
    }
    
    static {
        PRESENTS = new Block[]{
                WHITE_PRESENT, LIGHT_GRAY_PRESENT, GRAY_PRESENT, BLACK_PRESENT,
                BROWN_PRESENT, RED_PRESENT, ORANGE_PRESENT, YELLOW_PRESENT,
                LIME_PRESENT, GREEN_PRESENT, CYAN_PRESENT, LIGHT_BLUE_PRESENT,
                BLUE_PRESENT, PURPLE_PRESENT, MAGENTA_PRESENT, PINK_PRESENT,
                FOLLY_PRESENT, BLOOD_PRESENT, GOLDEN_PRESENT, SAND_PRESENT, KELP_PRESENT,
                SCULK_PRESENT
        };
        BAUBLES = new Block[]{
                WHITE_BAUBLE, LIGHT_GRAY_BAUBLE, GRAY_BAUBLE, BLACK_BAUBLE,
                BROWN_BAUBLE, RED_BAUBLE, ORANGE_BAUBLE, YELLOW_BAUBLE, 
                LIME_BAUBLE, GREEN_BAUBLE, CYAN_BAUBLE, LIGHT_BLUE_BAUBLE, 
                BLUE_BAUBLE, PURPLE_BAUBLE, MAGENTA_BAUBLE, PINK_BAUBLE
        };
        TINSELS = new Block[]{
                WHITE_TINSEL, LIGHT_GRAY_TINSEL, GRAY_TINSEL, BLACK_TINSEL,
                BROWN_TINSEL, RED_TINSEL, ORANGE_TINSEL, YELLOW_TINSEL,
                LIME_TINSEL, GREEN_TINSEL, CYAN_TINSEL, LIGHT_BLUE_TINSEL,
                BLUE_TINSEL, PURPLE_TINSEL, MAGENTA_TINSEL, PINK_TINSEL
        };
    }
}
