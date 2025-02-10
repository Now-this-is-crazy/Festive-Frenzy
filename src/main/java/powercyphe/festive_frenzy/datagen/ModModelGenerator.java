package powercyphe.festive_frenzy.datagen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import powercyphe.festive_frenzy.registry.ModBlocks;
import powercyphe.festive_frenzy.registry.ModItems;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraft.data.client.BlockStateModelGenerator.CONNECTION_VARIANT_FUNCTIONS;

public class ModModelGenerator extends FabricModelProvider {

    public static List<Pair<BooleanProperty, Function<Identifier, BlockStateVariant>>> DIRECTION_VARIANTS = List.of(Pair.of(Properties.NORTH, (model) -> {
        return BlockStateVariant.create().put(VariantSettings.MODEL, model);
    }), Pair.of(Properties.EAST, (model) -> {
        return BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.Y, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true);
    }), Pair.of(Properties.SOUTH, (model) -> {
        return BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.Y, VariantSettings.Rotation.R180).put(VariantSettings.UVLOCK, true);
    }), Pair.of(Properties.WEST, (model) -> {
        return BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.Y, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true);
    }), Pair.of(Properties.UP, (model) -> {
        return BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.X, VariantSettings.Rotation.R270).put(VariantSettings.UVLOCK, true);
    }), Pair.of(Properties.DOWN, (model) -> {
        return BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.UVLOCK, true);
    }));

    public ModModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RED_CANDY_CANE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.GREEN_CANDY_CANE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MIXED_CANDY_CANE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PEPPERMINT_BLOCK);


        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PACKED_SNOW);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CHISELED_PACKED_SNOW);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.POLISHED_PACKED_SNOW)
                        .stairs(ModBlocks.POLISHED_PACKED_SNOW_STAIRS)
                                .slab(ModBlocks.POLISHED_PACKED_SNOW_SLAB)
                                        .wall(ModBlocks.POLISHED_PACKED_SNOW_WALL);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.PACKED_SNOW_BRICKS)
                        .stairs(ModBlocks.PACKED_SNOW_BRICK_STAIRS)
                                .slab(ModBlocks.PACKED_SNOW_BRICK_SLAB)
                                        .wall(ModBlocks.PACKED_SNOW_BRICK_WALL);


        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CUT_BLUE_ICE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CHISELED_BLUE_ICE);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.POLISHED_BLUE_ICE)
                .stairs(ModBlocks.POLISHED_BLUE_ICE_STAIRS)
                .slab(ModBlocks.POLISHED_BLUE_ICE_SLAB)
                .wall(ModBlocks.POLISHED_BLUE_ICE_WALL);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.BLUE_ICE_BRICKS)
                .stairs(ModBlocks.BLUE_ICE_BRICK_STAIRS)
                .slab(ModBlocks.BLUE_ICE_BRICK_SLAB)
                .wall(ModBlocks.BLUE_ICE_BRICK_WALL);


        registerSideDecoration(blockStateModelGenerator, ModBlocks.FAIRY_LIGHTS);

        for (Block block : ModBlocks.PRESENTS) {
            blockStateModelGenerator.registerSimpleState(block);
        }

        for (Block block : ModBlocks.BAUBLES) {
            blockStateModelGenerator.registerSimpleState(block);
        }

        for (Block block : ModBlocks.TINSELS) {
            registerSideDecoration(blockStateModelGenerator, block);
        }

        blockStateModelGenerator.registerSimpleState(ModBlocks.SNOW_GLOBE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.RED_CANDY_CANE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.GREEN_CANDY_CANE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PEPPERMINT, Models.GENERATED);

        itemModelGenerator.register(ModItems.CANDY_POUCH, Models.GENERATED);
        itemModelGenerator.register(ModItems.FROSTFLAKE_CANNON, Models.HANDHELD);

        for (Item item : ModItems.SHARPENED_CANDY_CANES) {
            register2dModel(itemModelGenerator, item, Models.GENERATED);
        }

        for (Block block : ModBlocks.PRESENTS) {
            itemModelGenerator.register(block.asItem(), Models.GENERATED);
        }

        for (Block block : ModBlocks.BAUBLES) {
            itemModelGenerator.register(block.asItem(), Models.GENERATED);
        }

    }

    public final void registerSideDecoration(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        blockStateModelGenerator.registerItemModel(block.asItem());
        Identifier identifier = ModelIds.getBlockModelId(block);
        MultipartBlockStateSupplier multipartBlockStateSupplier = MultipartBlockStateSupplier.create(block);
        When.PropertyCondition propertyCondition = (When.PropertyCondition) Util.make(When.create(), (propertyConditionx) -> {
            DIRECTION_VARIANTS.stream().map(Pair::getFirst).forEach((property) -> {
                if (block.getDefaultState().contains(property)) {
                    propertyConditionx.set(property, false);
                }

            });
        });
        Iterator var5 = DIRECTION_VARIANTS.iterator();

        while(var5.hasNext()) {
            Pair<BooleanProperty, Function<Identifier, BlockStateVariant>> pair = (Pair)var5.next();
            BooleanProperty booleanProperty = (BooleanProperty)pair.getFirst();
            Function<Identifier, BlockStateVariant> function = (Function)pair.getSecond();
            if (block.getDefaultState().contains(booleanProperty)) {
                multipartBlockStateSupplier.with(When.create().set(booleanProperty, true), (BlockStateVariant)function.apply(identifier));
                multipartBlockStateSupplier.with(propertyCondition, (BlockStateVariant)function.apply(identifier));
            }
        }

        blockStateModelGenerator.blockStateCollector.accept(multipartBlockStateSupplier);
    }

    public final void register2dModel(ItemModelGenerator itemModelGenerator, Item item, Model model) {
        model.upload(Registries.ITEM.getId(item).withPrefixedPath("item/").withSuffixedPath("_2d"), TextureMap.layer0(item), itemModelGenerator.writer);
    }

}
