package powercyphe.festive_frenzy.common.datagen;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.packs.LootData;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WoolCarpetBlock;
import oshi.util.tuples.Triplet;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.block.BaubleBlock;
import powercyphe.festive_frenzy.common.block.PresentBlock;
import powercyphe.festive_frenzy.common.block.TinselBlock;
import powercyphe.festive_frenzy.common.recipe.TransmuteStonecutterRecipe;
import powercyphe.festive_frenzy.common.registry.FFBlocks;
import powercyphe.festive_frenzy.common.registry.FFItems;
import powercyphe.festive_frenzy.common.registry.FFTags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FFRecipeProvider extends FabricRecipeProvider {
    public FFRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            private static final HashMap<DyeColor, Tuple<Block, Block>> DYE_TO_ITEM = new HashMap<>();
            private static final List<Tuple<Block, ItemLike>> SPECIAL_PRESENTS = new ArrayList<>();

            @Override
            public void buildRecipes() {

                // Frozen Grass
                this.shapeless(RecipeCategory.BUILDING_BLOCKS, FFBlocks.SHORT_FROZEN_GRASS)
                        .requires(Items.SHORT_GRASS)
                        .requires(Items.SNOWBALL)
                        .unlockedBy("has_short_grass", has(Items.SHORT_GRASS))
                        .save(exporter);

                this.shapeless(RecipeCategory.BUILDING_BLOCKS, FFBlocks.TALL_FROZEN_GRASS)
                        .requires(Items.TALL_GRASS)
                        .requires(Items.SNOWBALL)
                        .unlockedBy("has_tall_grass", has(Items.TALL_GRASS))
                        .save(exporter);

                // Other Items
                this.shaped(RecipeCategory.MISC, FFItems.FESTIVE_HAT)
                        .pattern("LWL")
                        .pattern("WWW")
                        .define('W', ItemTags.WOOL)
                        .define('L', Items.LEATHER)
                        .unlockedBy("has_wooL", has(ItemTags.WOOL))
                        .save(exporter);

                this.shaped(RecipeCategory.COMBAT, FFItems.SHARPENED_CANDY_CANE)
                        .pattern(" HB")
                        .pattern("FB ")
                        .pattern("SF ")
                        .define('S', Items.DIAMOND_SWORD)
                        .define('F', FFBlocks.FAIRY_LIGHTS)
                        .define('B', FFTags.Items.CANDY_CANE_BLOCKS)
                        .define('H', FFItems.HOLLY)
                        .unlockedBy("has_candy_cane", has(FFTags.Items.CANDY_CANES))
                        .save(exporter);

                this.shaped(RecipeCategory.COMBAT, FFItems.WREATH_CHAKRAM)
                        .pattern("IFI")
                        .pattern("FWF")
                        .pattern("IFI")
                        .define('W', FFBlocks.WREATH)
                        .define('F', Items.FLINT)
                        .define('I', Items.IRON_NUGGET)
                        .unlockedBy("has_iron", has(Items.IRON_INGOT))
                        .save(exporter);

                this.shapeless(RecipeCategory.COMBAT, FFItems.FROSTFLAKE_CANNON, 3)
                        .requires(Items.BLUE_ICE)
                        .requires(Items.WIND_CHARGE)
                        .requires(Items.IRON_NUGGET)
                        .unlockedBy("has_iron", has(Items.IRON_INGOT))
                        .save(exporter);

                // Candy Cane & Other Foods
                this.createBlockFamily(FFItems.RED_CANDY_CANE, FFBlocks.RED_CANDY_CANE_BLOCK, FFBlocks.RED_CANDY_CANE_STAIRS, FFBlocks.RED_CANDY_CANE_SLAB, null, 2);
                this.createBlockFamily(FFItems.GREEN_CANDY_CANE, FFBlocks.GREEN_CANDY_CANE_BLOCK, FFBlocks.GREEN_CANDY_CANE_STAIRS, FFBlocks.GREEN_CANDY_CANE_SLAB, null, 2);

                this.shaped(RecipeCategory.BUILDING_BLOCKS, FFBlocks.MIXED_CANDY_CANE_BLOCK, 2)
                        .group("mixed_candy_cane_block")
                        .pattern("RG")
                        .pattern("GR")
                        .define('R', FFItems.RED_CANDY_CANE)
                        .define('G', FFItems.GREEN_CANDY_CANE)
                        .unlockedBy("has_candy_cane", has(FFTags.Items.CANDY_CANES))
                        .save(exporter);
                this.shapeless(RecipeCategory.BUILDING_BLOCKS, FFBlocks.MIXED_CANDY_CANE_BLOCK, 2)
                        .group("mixed_candy_cane_block")
                        .requires(FFBlocks.RED_CANDY_CANE_BLOCK)
                        .requires(FFBlocks.GREEN_CANDY_CANE_BLOCK)
                        .unlockedBy("has_candy_cane", has(FFTags.Items.CANDY_CANES))
                        .save(exporter, "mixed_candy_cane_block_with_blocks");
                this.createBlockFamily(null, FFBlocks.MIXED_CANDY_CANE_BLOCK, FFBlocks.MIXED_CANDY_CANE_STAIRS, FFBlocks.MIXED_CANDY_CANE_SLAB, null, 0);


                this.shaped(RecipeCategory.BUILDING_BLOCKS, FFBlocks.PEPPERMINT_BLOCK)
                        .pattern("PP")
                        .pattern("PP")
                        .define('P', FFItems.PEPPERMINT)
                        .unlockedBy("has_peppermint", has(FFItems.PEPPERMINT))
                        .save(exporter);


                // Gingerbread
                this.shapeless(RecipeCategory.MISC, FFItems.GINGERBREAD_DOUGH, 4)
                                .requires(Items.HONEY_BOTTLE)
                                .requires(Items.WHEAT)
                                .requires(Items.SUGAR)
                                .unlockedBy("has_wheat", has(Items.WHEAT))
                                .save(exporter);


                createBlockFamily(FFItems.GINGERBREAD_DOUGH, FFBlocks.GINGERBREAD_BLOCK, FFBlocks.GINGERBREAD_STAIRS,
                        FFBlocks.GINGERBREAD_SLAB, FFBlocks.GINGERBREAD_WALL, 2);
                createBlockFamily(FFBlocks.GINGERBREAD_BLOCK, FFBlocks.GINGERBREAD_BRICKS, FFBlocks.GINGERBREAD_BRICK_STAIRS,
                        FFBlocks.GINGERBREAD_BRICK_SLAB, FFBlocks.GINGERBREAD_BRICK_WALL, 4);
                this.chiseled(RecipeCategory.BUILDING_BLOCKS, FFBlocks.CHISELED_GINGERBREAD_BLOCK, FFBlocks.GINGERBREAD_SLAB);
                this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FFBlocks.CHISELED_GINGERBREAD_BLOCK, FFBlocks.GINGERBREAD_BLOCK);

                this.doorBuilder(FFBlocks.GINGERBREAD_DOOR, Ingredient.of(FFItems.GINGERBREAD_DOUGH))
                        .unlockedBy("has_gingerbread_dough", has(FFItems.GINGERBREAD_DOUGH))
                        .save(exporter);
                this.trapdoorBuilder(FFBlocks.GINGERBREAD_TRAPDOOR, Ingredient.of(FFItems.GINGERBREAD_DOUGH))
                        .unlockedBy("has_gingerbread_dough", has(FFItems.GINGERBREAD_DOUGH))
                        .save(exporter);
                this.buttonBuilder(FFBlocks.GINGERBREAD_BUTTON, Ingredient.of(FFItems.GINGERBREAD_DOUGH))
                        .unlockedBy("has_gingerbread_dough", has(FFItems.GINGERBREAD_DOUGH))
                        .save(exporter);
                this.pressurePlateBuilder(RecipeCategory.BUILDING_BLOCKS, FFBlocks.GINGERBREAD_PRESSURE_PLATE, Ingredient.of(FFItems.GINGERBREAD_DOUGH))
                        .unlockedBy("has_gingerbread_dough", has(FFItems.GINGERBREAD_DOUGH))
                        .save(exporter);

                // Packed Snow
                createBlockFamily(Blocks.SNOW_BLOCK, FFBlocks.PACKED_SNOW, FFBlocks.PACKED_SNOW_STAIRS, 
                        FFBlocks.PACKED_SNOW_SLAB, FFBlocks.PACKED_SNOW_WALL, 4);
                createBlockFamily(FFBlocks.PACKED_SNOW, FFBlocks.POLISHED_PACKED_SNOW, FFBlocks.POLISHED_PACKED_SNOW_STAIRS,
                        FFBlocks.POLISHED_PACKED_SNOW_SLAB, FFBlocks.POLISHED_PACKED_SNOW_WALL, 4);
                createBlockFamily(FFBlocks.POLISHED_PACKED_SNOW, FFBlocks.PACKED_SNOW_BRICKS, FFBlocks.PACKED_SNOW_BRICK_STAIRS, 
                        FFBlocks.PACKED_SNOW_BRICK_SLAB, FFBlocks.PACKED_SNOW_BRICK_WALL, 4);
                this.chiseled(RecipeCategory.BUILDING_BLOCKS, FFBlocks.CHISELED_PACKED_SNOW, FFBlocks.POLISHED_PACKED_SNOW_SLAB);
                this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FFBlocks.CHISELED_PACKED_SNOW, FFBlocks.POLISHED_PACKED_SNOW);

                // Blue Ice
                createBlockFamily(Blocks.BLUE_ICE, FFBlocks.CUT_BLUE_ICE, FFBlocks.CUT_BLUE_ICE_STAIRS,
                        FFBlocks.CUT_BLUE_ICE_SLAB, FFBlocks.CUT_BLUE_ICE_WALL, 4);
                createBlockFamily(FFBlocks.CUT_BLUE_ICE, FFBlocks.POLISHED_BLUE_ICE, FFBlocks.POLISHED_BLUE_ICE_STAIRS,
                        FFBlocks.POLISHED_BLUE_ICE_SLAB, FFBlocks.POLISHED_BLUE_ICE_WALL, 4);
                createBlockFamily(FFBlocks.POLISHED_BLUE_ICE, FFBlocks.BLUE_ICE_BRICKS, FFBlocks.BLUE_ICE_BRICK_STAIRS,
                        FFBlocks.BLUE_ICE_BRICK_SLAB, FFBlocks.BLUE_ICE_BRICK_WALL, 4);
                this.chiseled(RecipeCategory.BUILDING_BLOCKS, FFBlocks.CHISELED_BLUE_ICE, FFBlocks.POLISHED_BLUE_ICE_SLAB);
                this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, FFBlocks.CHISELED_BLUE_ICE, FFBlocks.POLISHED_BLUE_ICE);


                // Misc Blocks
                this.shaped(RecipeCategory.BUILDING_BLOCKS, FFBlocks.FAIRY_LIGHTS, 6)
                        .pattern("SCS")
                        .pattern("AAA")
                        .define('S', Items.STRING)
                        .define('C', Items.COPPER_INGOT)
                        .define('A', Items.AMETHYST_SHARD)
                        .unlockedBy("has_amethyst", has(Items.AMETHYST_SHARD))
                        .save(exporter);

                this.shaped(RecipeCategory.BUILDING_BLOCKS, FFBlocks.WREATH)
                        .pattern("HHH")
                        .pattern("H H")
                        .pattern("HHH")
                        .define('H', FFItems.HOLLY)
                        .unlockedBy("has_holly", has(FFItems.HOLLY))
                        .save(exporter);

                this.shaped(RecipeCategory.BUILDING_BLOCKS, FFBlocks.STAR_DECORATION)
                        .pattern(" G ")
                        .pattern("GGG")
                        .define('G', Items.GOLD_INGOT)
                        .unlockedBy("has_gold", has(Items.GOLD_INGOT))
                        .save(exporter);


                // Presents, Baubles & Tinsel
                for (DyeColor color : DyeColor.values()) {
                    DyeItem dyeItem = DyeItem.byColor(color);
                    Item woolItem = DYE_TO_ITEM.get(color).getA().asItem();
                    Item glassItem = DYE_TO_ITEM.get(color).getB().asItem();

                    // Present
                    Item present = PresentBlock.fromColor(color).asItem();

                    TransmuteRecipeBuilder.transmute(RecipeCategory.BUILDING_BLOCKS, tag(FFTags.Items.PRESENTS),
                                    Ingredient.of(dyeItem), present)
                            .group("present_recolor")
                            .unlockedBy("has_present", has(FFTags.Items.PRESENTS))
                            .save(exporter, present + "_recolor");

                    this.shaped(RecipeCategory.BUILDING_BLOCKS, present)
                            .group("present")
                            .pattern(" S ")
                            .pattern("W W")
                            .pattern(" W ")
                            .define('W', woolItem)
                            .define('S', Items.STRING)
                            .unlockedBy("has_wool", has(ItemTags.WOOL))
                            .save(exporter);

                    this.shaped(RecipeCategory.BUILDING_BLOCKS, present)
                            .group("present")
                            .pattern(" S ")
                            .pattern("WDW")
                            .pattern(" W ")
                            .define('D', dyeItem)
                            .define('W', ItemTags.WOOL)
                            .define('S', Items.STRING)
                            .unlockedBy("has_wool", has(ItemTags.WOOL))
                            .save(exporter, present + "_with_dye");

                    // Bauble
                    Item bauble = BaubleBlock.fromColor(color).asItem();

                    TransmuteRecipeBuilder.transmute(RecipeCategory.BUILDING_BLOCKS, tag(FFTags.Items.BAUBLES),
                                    Ingredient.of(dyeItem), bauble)
                            .group("bauble_recolor")
                            .unlockedBy("has_bauble", has(FFTags.Items.BAUBLES))
                            .save(exporter, bauble + "_recolor");

                    this.shaped(RecipeCategory.BUILDING_BLOCKS, bauble, 2)
                            .group("bauble")
                            .pattern("I")
                            .pattern("G")
                            .define('G', glassItem)
                            .define('I', Items.IRON_NUGGET)
                            .unlockedBy("has_glass", has(glassItem))
                            .save(exporter);

                    this.shaped(RecipeCategory.BUILDING_BLOCKS, bauble, 2)
                            .group("bauble")
                            .pattern("I")
                            .pattern("G")
                            .pattern("D")
                            .define('D', dyeItem)
                            .define('G', Items.GLASS)
                            .define('I', Items.IRON_NUGGET)
                            .unlockedBy("has_glass", has(glassItem))
                            .save(exporter, bauble + "_with_dye");

                    // Tinsel
                    Item tinsel = TinselBlock.fromColor(color).asItem();

                    this.shapeless(RecipeCategory.BUILDING_BLOCKS, tinsel)
                            .group("tinsel_recolor")
                            .requires(tag(FFTags.Items.TINSEL))
                            .requires(dyeItem)
                            .unlockedBy("has_tinsel", has(FFTags.Items.TINSEL))
                            .save(exporter, tinsel + "_recolor");

                    this.shaped(RecipeCategory.BUILDING_BLOCKS, tinsel, 8)
                            .group("tinsel")
                            .pattern("WWW")
                            .define('W', woolItem)
                            .unlockedBy("has_wool", has(ItemTags.WOOL))
                            .save(exporter);

                    this.shaped(RecipeCategory.BUILDING_BLOCKS, tinsel, 8)
                            .group("tinsel")
                            .pattern("WWW")
                            .pattern(" D ")
                            .define('D', dyeItem)
                            .define('W', ItemTags.WOOL)
                            .unlockedBy("has_wool", has(ItemTags.WOOL))
                            .save(exporter, tinsel + "_with_dye");
                }

                for (Tuple<Block, ItemLike> tuple : SPECIAL_PRESENTS) {
                    Block present = tuple.getA();
                    ItemLike ingredient = tuple.getB();

                    TransmuteRecipeBuilder.transmute(RecipeCategory.BUILDING_BLOCKS, tag(FFTags.Items.PRESENTS),
                                    Ingredient.of(ingredient), present.asItem())
                            .group("present_recolor")
                            .unlockedBy("has_present", has(FFTags.Items.PRESENTS))
                            .save(exporter);

                    transmuteStonecutting(RecipeCategory.BUILDING_BLOCKS, tag(FFTags.Items.PRESENTS), present, 1)
                            .unlockedBy("has_present", has(FFTags.Items.PRESENTS))
                            .save(exporter, RecipeProvider.getItemName(present) + "_stonecutting");
                }
            }
            
            public void createBlockFamily(ItemLike rootItem, Block baseBlock, Block stairs, Block slab, Block wall, int baseBlockAmount) {
                ItemLike requiredItem = rootItem != null ? rootItem : baseBlock;

                // Base Block
                if (rootItem != null) {
                    this.shaped(RecipeCategory.BUILDING_BLOCKS, baseBlock, baseBlockAmount)
                            .pattern("RR")
                            .pattern("RR")
                            .define('R', rootItem)
                            .unlockedBy("has_" + rootItem, has(rootItem))
                            .save(exporter);
                    this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, baseBlock, rootItem);
                    this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, rootItem, baseBlock);
                }

                if (stairs != null) {
                    // Stairs
                    this.stairBuilder(stairs, Ingredient.of(baseBlock))
                            .unlockedBy("has_" + requiredItem, has(requiredItem))
                            .save(exporter);
                    this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, stairs, baseBlock);
                }
                if (slab != null) {
                    // Slab
                    this.slabBuilder(RecipeCategory.BUILDING_BLOCKS, slab, Ingredient.of(baseBlock))
                            .unlockedBy("has_" + requiredItem, has(requiredItem))
                            .save(exporter);
                    this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, slab, baseBlock, 2);
                }
                if (wall != null) {
                    // Wall
                    this.wallBuilder(RecipeCategory.BUILDING_BLOCKS, wall, Ingredient.of(baseBlock))
                            .unlockedBy("has_" + requiredItem, has(requiredItem))
                            .save(exporter);
                    this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, wall, baseBlock);
                }
            }

            public SingleItemRecipeBuilder transmuteStonecutting(RecipeCategory recipeCategory, Ingredient ingredient, ItemLike output, int amount) {
                return new SingleItemRecipeBuilder(recipeCategory, TransmuteStonecutterRecipe::new, ingredient, output, amount);
            }
            
            static {
                DYE_TO_ITEM.put(DyeColor.WHITE, new Tuple<>(Blocks.WHITE_WOOL, Blocks.WHITE_STAINED_GLASS));
                DYE_TO_ITEM.put(DyeColor.LIGHT_GRAY, new Tuple<>(Blocks.LIGHT_GRAY_WOOL, Blocks.LIGHT_GRAY_STAINED_GLASS));
                DYE_TO_ITEM.put(DyeColor.GRAY, new Tuple<>(Blocks.GRAY_WOOL, Blocks.GRAY_STAINED_GLASS));
                DYE_TO_ITEM.put(DyeColor.BLACK, new Tuple<>(Blocks.BLACK_WOOL, Blocks.BLACK_STAINED_GLASS));
                DYE_TO_ITEM.put(DyeColor.BROWN, new Tuple<>(Blocks.BROWN_WOOL, Blocks.BROWN_STAINED_GLASS));
                DYE_TO_ITEM.put(DyeColor.RED, new Tuple<>(Blocks.RED_WOOL, Blocks.RED_STAINED_GLASS));
                DYE_TO_ITEM.put(DyeColor.ORANGE, new Tuple<>(Blocks.ORANGE_WOOL, Blocks.ORANGE_STAINED_GLASS));
                DYE_TO_ITEM.put(DyeColor.YELLOW, new Tuple<>(Blocks.YELLOW_WOOL, Blocks.WHITE_STAINED_GLASS));
                DYE_TO_ITEM.put(DyeColor.LIME, new Tuple<>(Blocks.LIME_WOOL, Blocks.LIME_STAINED_GLASS));
                DYE_TO_ITEM.put(DyeColor.GREEN, new Tuple<>(Blocks.GREEN_WOOL, Blocks.GREEN_STAINED_GLASS));
                DYE_TO_ITEM.put(DyeColor.CYAN, new Tuple<>(Blocks.CYAN_WOOL, Blocks.CYAN_STAINED_GLASS));
                DYE_TO_ITEM.put(DyeColor.LIGHT_BLUE, new Tuple<>(Blocks.LIGHT_BLUE_WOOL, Blocks.LIGHT_BLUE_STAINED_GLASS));
                DYE_TO_ITEM.put(DyeColor.BLUE, new Tuple<>(Blocks.BLUE_WOOL, Blocks.BLUE_STAINED_GLASS));
                DYE_TO_ITEM.put(DyeColor.PURPLE, new Tuple<>(Blocks.PURPLE_WOOL, Blocks.PURPLE_STAINED_GLASS));
                DYE_TO_ITEM.put(DyeColor.MAGENTA, new Tuple<>(Blocks.MAGENTA_WOOL, Blocks.MAGENTA_STAINED_GLASS));
                DYE_TO_ITEM.put(DyeColor.PINK, new Tuple<>(Blocks.PINK_WOOL, Blocks.PINK_STAINED_GLASS));

                SPECIAL_PRESENTS.add(new Tuple<>(FFBlocks.FOLLY_PRESENT, Items.RED_TULIP));
                SPECIAL_PRESENTS.add(new Tuple<>(FFBlocks.BLOOD_PRESENT, Items.ROTTEN_FLESH));
                SPECIAL_PRESENTS.add(new Tuple<>(FFBlocks.GOLDEN_PRESENT, Items.GOLD_BLOCK));
                SPECIAL_PRESENTS.add(new Tuple<>(FFBlocks.SAND_PRESENT, Items.SAND));
                SPECIAL_PRESENTS.add(new Tuple<>(FFBlocks.KELP_PRESENT, Items.DRIED_KELP_BLOCK));
                SPECIAL_PRESENTS.add(new Tuple<>(FFBlocks.SCULK_PRESENT, Items.SCULK_CATALYST));
            }
        };
    }

    @Override
    public String getName() {
        return FestiveFrenzy.MOD_ID;
    }
}
