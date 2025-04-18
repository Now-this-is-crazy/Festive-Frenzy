package powercyphe.festive_frenzy.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import oshi.util.tuples.Pair;
import powercyphe.festive_frenzy.FestiveFrenzy;
import powercyphe.festive_frenzy.common.recipe.BaubleStonecuttingRecipe;
import powercyphe.festive_frenzy.common.recipe.PresentStonecuttingRecipe;
import powercyphe.festive_frenzy.common.registry.ModBlocks;
import powercyphe.festive_frenzy.common.registry.ModItems;
import powercyphe.festive_frenzy.common.registry.ModTags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generate(RecipeExporter consumer) {
        List<Block> blocks = new ArrayList<>();
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.FESTIVE_HAT, 1)
                .pattern("LWL")
                .pattern("WWW")
                .input('W', ItemTags.WOOL)
                .input('L', Items.LEATHER)
                .criterion("has_wool", conditionsFromTag(ItemTags.WOOL))
                .offerTo(consumer);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.SHARPENED_CANDY_CANE, 1)
                .pattern(" CB")
                .pattern("FBC")
                .pattern("SF ")
                .input('S', Items.DIAMOND_SWORD)
                .input('F', ModBlocks.FAIRY_LIGHTS)
                .input('C', ModTags.Items.CANDY_CANES_TAG)
                .input('B', ModTags.Items.CANDY_CANE_BLOCKS_TAG)
                .criterion("has_candy_cane", conditionsFromTag(ModTags.Items.CANDY_CANES_TAG))
                .offerTo(consumer);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RED_CANDY_CANE_BLOCK, 1)
                .pattern("RR")
                .pattern("RR")
                .input('R', ModItems.RED_CANDY_CANE)
                .criterion("has_candy_cane", conditionsFromTag(ModTags.Items.CANDY_CANES_TAG))
                .group("candy_cane_block")
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GREEN_CANDY_CANE_BLOCK, 1)
                .pattern("GG")
                .pattern("GG")
                .input('G', ModItems.GREEN_CANDY_CANE)
                .criterion("has_candy_cane", conditionsFromTag(ModTags.Items.CANDY_CANES_TAG))
                .group("candy_cane_block")
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MIXED_CANDY_CANE_BLOCK, 1)
                .pattern("RG")
                .pattern("GR")
                .input('R', ModItems.RED_CANDY_CANE)
                .input('G', ModItems.GREEN_CANDY_CANE)
                .criterion("has_candy_cane", conditionsFromTag(ModTags.Items.CANDY_CANES_TAG))
                .group("candy_cane_block")
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MIXED_CANDY_CANE_BLOCK, 2)
                .pattern("RG")
                .input('R', ModBlocks.RED_CANDY_CANE_BLOCK)
                .input('G', ModBlocks.GREEN_CANDY_CANE_BLOCK)
                .criterion("has_candy_cane", conditionsFromTag(ModTags.Items.CANDY_CANES_TAG))
                .group("candy_cane_block")
                .offerTo(consumer, FestiveFrenzy.id(Registries.BLOCK.getId(ModBlocks.MIXED_CANDY_CANE_BLOCK).getPath() + "_combine"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PEPPERMINT_BLOCK, 1)
                .pattern("PP")
                .pattern("PP")
                .input('P', ModItems.PEPPERMINT)
                .criterion("has_peppermint", conditionsFromItem(ModItems.PEPPERMINT))
                .offerTo(consumer);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.FAIRY_LIGHTS, 3)
                .pattern("SCS")
                .pattern("AAA")
                .input('S', Items.STRING)
                .input('C', Items.COPPER_INGOT)
                .input('A', Items.AMETHYST_SHARD)
                .criterion("has_amethyst", conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(consumer);

        offerPolishedStoneRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.PACKED_SNOW, Blocks.SNOW_BLOCK);
        offerPolishedStoneRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_PACKED_SNOW, ModBlocks.PACKED_SNOW);
        createStairsRecipe(ModBlocks.POLISHED_PACKED_SNOW_STAIRS, Ingredient.ofItems(ModBlocks.POLISHED_PACKED_SNOW))
                .criterion("has_polished_packed_snow", conditionsFromItem(ModBlocks.POLISHED_PACKED_SNOW))
                .offerTo(consumer);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_PACKED_SNOW_SLAB, Ingredient.ofItems(ModBlocks.POLISHED_PACKED_SNOW))
                .criterion("has_polished_packed_snow", conditionsFromItem(ModBlocks.POLISHED_PACKED_SNOW))
                .offerTo(consumer);
        getWallRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_PACKED_SNOW_WALL, Ingredient.ofItems(ModBlocks.POLISHED_PACKED_SNOW))
                .criterion("has_polished_packed_snow", conditionsFromItem(ModBlocks.POLISHED_PACKED_SNOW))
                .offerTo(consumer);
        offerPolishedStoneRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.PACKED_SNOW_BRICKS, ModBlocks.POLISHED_PACKED_SNOW);
        createStairsRecipe(ModBlocks.PACKED_SNOW_BRICK_STAIRS, Ingredient.ofItems(ModBlocks.PACKED_SNOW_BRICKS))
                .criterion("has_packed_snow_bricks", conditionsFromItem(ModBlocks.PACKED_SNOW_BRICKS))
                .offerTo(consumer);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PACKED_SNOW_BRICK_SLAB, Ingredient.ofItems(ModBlocks.PACKED_SNOW_BRICKS))
                .criterion("has_packed_snow_bricks", conditionsFromItem(ModBlocks.PACKED_SNOW_BRICKS))
                .offerTo(consumer);
        getWallRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PACKED_SNOW_BRICK_WALL, Ingredient.ofItems(ModBlocks.PACKED_SNOW_BRICKS))
                .criterion("has_packed_snow_bricks", conditionsFromItem(ModBlocks.PACKED_SNOW_BRICKS))
                .offerTo(consumer);
        offerChiseledBlockRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_PACKED_SNOW, ModBlocks.POLISHED_PACKED_SNOW_SLAB);

        blocks.clear();
        blocks.add(Blocks.SNOW_BLOCK);
        blocks.add(ModBlocks.PACKED_SNOW);
        blocks.add(ModBlocks.PACKED_SNOW_BRICKS);
        blocks.add(ModBlocks.CHISELED_PACKED_SNOW);
        for (Block block : blocks) {
            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Blocks.SNOW_BLOCK, block);
            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.PACKED_SNOW, block);
            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_PACKED_SNOW, block);
            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.PACKED_SNOW_BRICKS, block);
            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_PACKED_SNOW, block);

            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_PACKED_SNOW_STAIRS, block);
            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_PACKED_SNOW_SLAB, block, 2);
            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_PACKED_SNOW_WALL, block);

            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.PACKED_SNOW_BRICK_STAIRS, block);
            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.PACKED_SNOW_BRICK_SLAB, block, 2);
            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.PACKED_SNOW_BRICK_WALL, block);
        }


        offerPolishedStoneRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_BLUE_ICE, Blocks.BLUE_ICE);
        offerPolishedStoneRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_BLUE_ICE, ModBlocks.CUT_BLUE_ICE);
        createStairsRecipe(ModBlocks.POLISHED_BLUE_ICE_STAIRS, Ingredient.ofItems(ModBlocks.POLISHED_BLUE_ICE))
                .criterion("has_polished_blue_ice", conditionsFromItem(ModBlocks.POLISHED_BLUE_ICE))
                .offerTo(consumer);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_BLUE_ICE_SLAB, Ingredient.ofItems(ModBlocks.POLISHED_BLUE_ICE))
                .criterion("has_polished_blue_ice", conditionsFromItem(ModBlocks.POLISHED_BLUE_ICE))
                .offerTo(consumer);
        getWallRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_BLUE_ICE_WALL, Ingredient.ofItems(ModBlocks.POLISHED_BLUE_ICE))
                .criterion("has_polished_blue_ice", conditionsFromItem(ModBlocks.POLISHED_BLUE_ICE))
                .offerTo(consumer);
        offerPolishedStoneRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_ICE_BRICKS, ModBlocks.POLISHED_BLUE_ICE);
        createStairsRecipe(ModBlocks.BLUE_ICE_BRICK_STAIRS, Ingredient.ofItems(ModBlocks.BLUE_ICE_BRICKS))
                .criterion("has_blue_ice_bricks", conditionsFromItem(ModBlocks.BLUE_ICE_BRICKS))
                .offerTo(consumer);
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_ICE_BRICK_SLAB, Ingredient.ofItems(ModBlocks.BLUE_ICE_BRICKS))
                .criterion("has_blue_ice_bricks", conditionsFromItem(ModBlocks.BLUE_ICE_BRICKS))
                .offerTo(consumer);
        getWallRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_ICE_BRICK_WALL, Ingredient.ofItems(ModBlocks.BLUE_ICE_BRICKS))
                .criterion("has_blue_ice_bricks", conditionsFromItem(ModBlocks.BLUE_ICE_BRICKS))
                .offerTo(consumer);
        offerChiseledBlockRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_BLUE_ICE, ModBlocks.POLISHED_BLUE_ICE_SLAB);

        blocks.clear();
        blocks.add(Blocks.BLUE_ICE);
        blocks.add(ModBlocks.CUT_BLUE_ICE);
        blocks.add(ModBlocks.POLISHED_BLUE_ICE);
        blocks.add(ModBlocks.BLUE_ICE_BRICKS);
        blocks.add(ModBlocks.CHISELED_BLUE_ICE);
        for (Block block : blocks) {
            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, Blocks.BLUE_ICE, block);
            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_BLUE_ICE, block);
            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_BLUE_ICE, block);
            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_ICE_BRICKS, block);
            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_BLUE_ICE, block);


            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_BLUE_ICE_STAIRS, block);
            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_BLUE_ICE_SLAB, block, 2);
            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_BLUE_ICE_WALL, block);

            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_ICE_BRICK_STAIRS, block);
            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_ICE_BRICK_SLAB, block, 2);
            offerStonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_ICE_BRICK_WALL, block);
        }



        HashMap<Pair<Item, Item>, List<ItemConvertible>> recipeDyeMap = new HashMap<>();
        recipeDyeMap.put(new Pair<>(Items.WHITE_DYE, Items.WHITE_WOOL), List.of(ModBlocks.WHITE_PRESENT, ModBlocks.WHITE_BAUBLE, ModBlocks.WHITE_TINSEL));
        recipeDyeMap.put(new Pair<>(Items.LIGHT_GRAY_DYE, Items.LIGHT_GRAY_WOOL), List.of(ModBlocks.LIGHT_GRAY_PRESENT, ModBlocks.LIGHT_GRAY_BAUBLE, ModBlocks.LIGHT_GRAY_TINSEL));
        recipeDyeMap.put(new Pair<>(Items.GRAY_DYE, Items.GRAY_WOOL), List.of(ModBlocks.GRAY_PRESENT, ModBlocks.GRAY_BAUBLE, ModBlocks.GRAY_TINSEL));
        recipeDyeMap.put(new Pair<>(Items.BLACK_DYE, Items.BLACK_WOOL), List.of(ModBlocks.BLACK_PRESENT, ModBlocks.BLACK_BAUBLE, ModBlocks.BLACK_TINSEL));
        recipeDyeMap.put(new Pair<>(Items.BROWN_DYE, Items.BROWN_WOOL), List.of(ModBlocks.BROWN_PRESENT, ModBlocks.BROWN_BAUBLE, ModBlocks.BROWN_TINSEL));
        recipeDyeMap.put(new Pair<>(Items.RED_DYE, Items.RED_WOOL), List.of(ModBlocks.RED_PRESENT, ModBlocks.RED_BAUBLE, ModBlocks.RED_TINSEL));
        recipeDyeMap.put(new Pair<>(Items.ORANGE_DYE, Items.ORANGE_WOOL), List.of(ModBlocks.ORANGE_PRESENT, ModBlocks.ORANGE_BAUBLE, ModBlocks.ORANGE_TINSEL));
        recipeDyeMap.put(new Pair<>(Items.YELLOW_DYE, Items.YELLOW_WOOL), List.of(ModBlocks.YELLOW_PRESENT, ModBlocks.YELLOW_BAUBLE, ModBlocks.YELLOW_TINSEL));
        recipeDyeMap.put(new Pair<>(Items.LIME_DYE, Items.LIME_WOOL), List.of(ModBlocks.LIME_PRESENT, ModBlocks.LIME_BAUBLE, ModBlocks.LIME_TINSEL));
        recipeDyeMap.put(new Pair<>(Items.GREEN_DYE, Items.GREEN_WOOL), List.of(ModBlocks.GREEN_PRESENT, ModBlocks.GREEN_BAUBLE, ModBlocks.GREEN_TINSEL));
        recipeDyeMap.put(new Pair<>(Items.CYAN_DYE, Items.CYAN_WOOL), List.of(ModBlocks.CYAN_PRESENT, ModBlocks.CYAN_BAUBLE, ModBlocks.CYAN_TINSEL));
        recipeDyeMap.put(new Pair<>(Items.LIGHT_BLUE_DYE, Items.LIGHT_BLUE_WOOL), List.of(ModBlocks.LIGHT_BLUE_PRESENT, ModBlocks.LIGHT_BLUE_BAUBLE, ModBlocks.LIGHT_BLUE_TINSEL));
        recipeDyeMap.put(new Pair<>(Items.BLUE_DYE, Items.BLUE_WOOL), List.of(ModBlocks.BLUE_PRESENT, ModBlocks.BLUE_BAUBLE, ModBlocks.BLUE_TINSEL));
        recipeDyeMap.put(new Pair<>(Items.PURPLE_DYE, Items.PURPLE_WOOL), List.of(ModBlocks.PURPLE_PRESENT, ModBlocks.PURPLE_BAUBLE, ModBlocks.PURPLE_TINSEL));
        recipeDyeMap.put(new Pair<>(Items.MAGENTA_DYE, Items.MAGENTA_WOOL), List.of(ModBlocks.MAGENTA_PRESENT, ModBlocks.MAGENTA_BAUBLE, ModBlocks.MAGENTA_TINSEL));
        recipeDyeMap.put(new Pair<>(Items.PINK_DYE, Items.PINK_WOOL), List.of(ModBlocks.PINK_PRESENT, ModBlocks.PINK_BAUBLE, ModBlocks.PINK_TINSEL));

        for (Pair<Item, Item> colorPair : recipeDyeMap.keySet()) {
            List<ItemConvertible> itemList = recipeDyeMap.get(colorPair);
            Item dye = colorPair.getA();
            Item wool = colorPair.getB();

            Item present = itemList.get(0).asItem();
            Item bauble = itemList.get(1).asItem();
            Item tinsel = itemList.get(2).asItem();

            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, present, 1)
                    .pattern(" S ")
                    .pattern("W W")
                    .pattern(" W ")
                    .input('W', wool)
                    .input('S', Items.STRING)
                    .group("colored")
                    .criterion("has_wool", conditionsFromTag(ItemTags.WOOL))
                    .criterion("has_present", conditionsFromTag(ModTags.Items.PRESENTS_TAG))
                    .offerTo(consumer);

            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, bauble, 2)
                    .pattern("N")
                    .pattern("G")
                    .pattern("D")
                    .input('N', Items.IRON_NUGGET)
                    .input('G', Items.GLASS)
                    .input('D', dye)
                    .group("bauble")
                    .criterion("has_glass", conditionsFromItem(Items.GLASS))
                    .criterion("has_bauble", conditionsFromTag(ModTags.Items.BAUBLES_TAG))
                    .offerTo(consumer);

            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, tinsel, 6)
                    .pattern("WWW")
                    .input('W', wool)
                    .group("tinsel")
                    .criterion("has_wool", conditionsFromTag(ItemTags.WOOL))
                    .criterion("has_tinsel", conditionsFromTag(ModTags.Items.TINSELS_TAG))
                    .offerTo(consumer);
            ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, tinsel, 1)
                    .input(ModTags.Items.TINSELS_TAG)
                    .input(dye)
                    .group("tinsel_recolor")
                    .criterion("has_tinsel", conditionsFromTag(ModTags.Items.TINSELS_TAG))
                    .offerTo(consumer, FestiveFrenzy.id(Registries.ITEM.getId(tinsel).getPath() + "_recolor"));

        }
        for (Block presentBlock : ModBlocks.PRESENTS) {
            Item present = presentBlock.asItem();
            new StonecuttingRecipeJsonBuilder(RecipeCategory.BUILDING_BLOCKS, PresentStonecuttingRecipe::new, Ingredient.fromTag(ModTags.Items.PRESENTS_TAG), present, 1)
                    .criterion("has_present", conditionsFromTag(ModTags.Items.PRESENTS_TAG))
                    .offerTo(consumer, FestiveFrenzy.id(Registries.ITEM.getId(present).getPath() + "_stonecutting"));
        }
        for (Block baubleBlock : ModBlocks.BAUBLES) {
            Item bauble = baubleBlock.asItem();
            new StonecuttingRecipeJsonBuilder(RecipeCategory.BUILDING_BLOCKS, BaubleStonecuttingRecipe::new, Ingredient.fromTag(ModTags.Items.BAUBLES_TAG), bauble, 1)
                    .criterion("has_bauble", conditionsFromTag(ModTags.Items.BAUBLES_TAG))
                    .offerTo(consumer, FestiveFrenzy.id(Registries.ITEM.getId(bauble).getPath() + "_stonecutting"));
        }
        for (Block tinselBlock : ModBlocks.TINSELS) {
            Item tinsel = tinselBlock.asItem();
            StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.fromTag(ModTags.Items.TINSELS_TAG), RecipeCategory.BUILDING_BLOCKS, tinsel)
                    .criterion("has_tinsel", conditionsFromTag(ModTags.Items.TINSELS_TAG))
                    .offerTo(consumer, FestiveFrenzy.id(Registries.ITEM.getId(tinsel).getPath() + "_stonecutting"));
        }
    }
}
