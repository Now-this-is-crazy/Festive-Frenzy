package powercyphe.festive_frenzy.common.datagen;

import com.google.gson.JsonObject;
import com.mojang.math.Quadrant;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.client.renderer.block.model.multipart.Condition;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import powercyphe.festive_frenzy.client.render.item.BaubleExplosionModificationProperty;
import powercyphe.festive_frenzy.client.render.item.BaubleExplosionPowerProperty;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.block.FairyLightsBlock;
import powercyphe.festive_frenzy.common.block.MultiWallDecorationBlock;
import powercyphe.festive_frenzy.common.registry.FFBlocks;
import powercyphe.festive_frenzy.common.registry.FFItems;
import powercyphe.festive_frenzy.common.world.BaubleExplosion;

import java.util.ArrayList;
import java.util.List;

public class FFModelProvider extends FabricModelProvider {
    public FFModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {
        generator.createCrossBlockWithDefaultItem(FFBlocks.SHORT_FROZEN_GRASS, BlockModelGenerators.PlantType.NOT_TINTED);
        createDoublePlantBlock(generator, FFBlocks.TALL_FROZEN_GRASS, BlockModelGenerators.PlantType.NOT_TINTED);

        createHollyBush(generator, FFBlocks.HOLLY_BUSH, FFItems.HOLLY);

        generator.family(FFBlocks.RED_CANDY_CANE_BLOCK)
                .stairs(FFBlocks.RED_CANDY_CANE_STAIRS).slab(FFBlocks.RED_CANDY_CANE_SLAB);
        generator.family(FFBlocks.GREEN_CANDY_CANE_BLOCK)
                .stairs(FFBlocks.GREEN_CANDY_CANE_STAIRS).slab(FFBlocks.GREEN_CANDY_CANE_SLAB);
        generator.family(FFBlocks.MIXED_CANDY_CANE_BLOCK)
                .stairs(FFBlocks.MIXED_CANDY_CANE_STAIRS).slab(FFBlocks.MIXED_CANDY_CANE_SLAB);

        generator.createTrivialCube(FFBlocks.PEPPERMINT_BLOCK);

        // Gingerbread
        generator.createTrivialCube(FFBlocks.CHISELED_GINGERBREAD_BLOCK);
        generator.family(FFBlocks.GINGERBREAD_BLOCK)
                .stairs(FFBlocks.GINGERBREAD_STAIRS).slab(FFBlocks.GINGERBREAD_SLAB).wall(FFBlocks.GINGERBREAD_WALL)
                .button(FFBlocks.GINGERBREAD_BUTTON).pressurePlate(FFBlocks.GINGERBREAD_PRESSURE_PLATE);
        generator.family(FFBlocks.GINGERBREAD_BRICKS)
                .stairs(FFBlocks.GINGERBREAD_BRICK_STAIRS).slab(FFBlocks.GINGERBREAD_BRICK_SLAB).wall(FFBlocks.GINGERBREAD_BRICK_WALL);
        generator.createDoor(FFBlocks.GINGERBREAD_DOOR);
        generator.createOrientableTrapdoor(FFBlocks.GINGERBREAD_TRAPDOOR);

        // Packed Snow
        generator.createTrivialCube(FFBlocks.CHISELED_PACKED_SNOW);
        generator.family(FFBlocks.PACKED_SNOW)
                .stairs(FFBlocks.PACKED_SNOW_STAIRS).slab(FFBlocks.PACKED_SNOW_SLAB).wall(FFBlocks.PACKED_SNOW_WALL);
        generator.family(FFBlocks.POLISHED_PACKED_SNOW)
                .stairs(FFBlocks.POLISHED_PACKED_SNOW_STAIRS).slab(FFBlocks.POLISHED_PACKED_SNOW_SLAB).wall(FFBlocks.POLISHED_PACKED_SNOW_WALL);
        generator.family(FFBlocks.PACKED_SNOW_BRICKS)
                .stairs(FFBlocks.PACKED_SNOW_BRICK_STAIRS).slab(FFBlocks.PACKED_SNOW_BRICK_SLAB).wall(FFBlocks.PACKED_SNOW_BRICK_WALL);

        // Blue Ice
        generator.createTrivialCube(FFBlocks.CHISELED_BLUE_ICE);
        generator.family(FFBlocks.CUT_BLUE_ICE)
                .stairs(FFBlocks.CUT_BLUE_ICE_STAIRS).slab(FFBlocks.CUT_BLUE_ICE_SLAB).wall(FFBlocks.CUT_BLUE_ICE_WALL);
        generator.family(FFBlocks.POLISHED_BLUE_ICE)
                .stairs(FFBlocks.POLISHED_BLUE_ICE_STAIRS).slab(FFBlocks.POLISHED_BLUE_ICE_SLAB).wall(FFBlocks.POLISHED_BLUE_ICE_WALL);
        generator.family(FFBlocks.BLUE_ICE_BRICKS)
                .stairs(FFBlocks.BLUE_ICE_BRICK_STAIRS).slab(FFBlocks.BLUE_ICE_BRICK_SLAB).wall(FFBlocks.BLUE_ICE_BRICK_WALL);

        for (Block present : FFBlocks.PRESENTS) {
            createCategorized(generator, present, "present");
        }

        for (Block bauble : FFBlocks.BAUBLES) {
            createCategorized(generator, bauble, "bauble");
        }

        for (Block tinsel : FFBlocks.TINSELS) {
            createTinsel(generator, tinsel);
        }

        createFairyLights(generator, FFBlocks.FAIRY_LIGHTS);

        generator.createNonTemplateHorizontalBlock(FFBlocks.WREATH);
        generator.registerSimpleFlatItemModel(FFBlocks.WREATH);

        generator.createCrossBlock(FFBlocks.STAR_DECORATION, BlockModelGenerators.PlantType.NOT_TINTED);
        generator.registerSimpleItemModel(FFBlocks.STAR_DECORATION.asItem(), ModelLocationUtils.getModelLocation(FFBlocks.STAR_DECORATION.asItem()));

    }

    private void createHollyBush(BlockModelGenerators generator, Block block, Item item) {
        generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(PropertyDispatch.initial(BlockStateProperties.AGE_2)
                        .generate((integer) -> BlockModelGenerators.plainVariant(
                                generator.createSuffixedVariant(block, "_stage" + integer, ModelTemplates.CROSS, TextureMapping::cross))
                        )
                )
        );
        generator.registerSimpleItemModel(item, ModelLocationUtils.getModelLocation(item));
    }

    private void createFairyLights(BlockModelGenerators generator, Block block) {
        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);

        MultiPartGenerator multiPart = MultiPartGenerator.multiPart(block);
        for (Direction direction : MultiWallDecorationBlock.DIRECTION_TO_PROPERTY.keySet()) {
            BooleanProperty property = MultiWallDecorationBlock.DIRECTION_TO_PROPERTY.get(direction);

            for (int variant : FairyLightsBlock.VARIANT.getPossibleValues()) {
                multiPart.with(BlockModelGenerators.condition().term(property, true).term(FairyLightsBlock.VARIANT, variant),
                        BlockModelGenerators.plainVariant(id.withPrefix("block/").withSuffix("_" + variant))
                        .with(VariantMutator.UV_LOCK.withValue(true)).with(VariantMutator.Y_ROT.withValue(switch (direction) {
                            case SOUTH -> Quadrant.R180;
                            case EAST -> Quadrant.R90;
                            case WEST -> Quadrant.R270;
                            case null, default -> Quadrant.R0;
                        })));
            }
        }

        generator.blockStateOutput.accept(multiPart);
        for (int variant : FairyLightsBlock.VARIANT.getPossibleValues()) {
            generator.modelOutput.accept(id.withPrefix("block/").withSuffix("_" + variant), () -> {
                JsonObject base = new JsonObject();
                base.addProperty("parent", FestiveFrenzy.id("block/multi_wall_deco").toString());

                JsonObject textures = new JsonObject();
                textures.addProperty("texture", id.withPrefix("block/fairy_lights/").withSuffix("_" + variant).toString());
                base.add("textures", textures);

                return base;
            });
        }
    }

    public void createTinsel(BlockModelGenerators generator, Block block) {
        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);

        MultiPartGenerator multiPart = MultiPartGenerator.multiPart(block);
        for (Direction direction : MultiWallDecorationBlock.DIRECTION_TO_PROPERTY.keySet()) {
            BooleanProperty property = MultiWallDecorationBlock.DIRECTION_TO_PROPERTY.get(direction);

            multiPart.with(BlockModelGenerators.condition().term(property, true),
                    BlockModelGenerators.plainVariant(id.withPrefix("block/"))
                            .with(VariantMutator.UV_LOCK.withValue(true)).with(VariantMutator.Y_ROT.withValue(switch (direction) {
                                case SOUTH -> Quadrant.R180;
                                case EAST -> Quadrant.R90;
                                case WEST -> Quadrant.R270;
                                case null, default -> Quadrant.R0;
                            })));
        }

        generator.blockStateOutput.accept(multiPart);
        generator.modelOutput.accept(id.withPrefix("block/"), () -> {
            JsonObject base = new JsonObject();
            base.addProperty("parent", FestiveFrenzy.id("block/multi_wall_deco").toString());

            JsonObject textures = new JsonObject();
            textures.addProperty("texture", id.withPrefix("block/tinsel/").toString());
            base.add("textures", textures);

            return base;
        });
    }

    private void createCategorized(BlockModelGenerators generator, Block block, String type) {
        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);

        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, BlockModelGenerators.plainVariant(id.withPrefix("block/"))));
        generator.modelOutput.accept(id.withPrefix("block/"), () -> {
            JsonObject base = new JsonObject();
            base.addProperty("parent", FestiveFrenzy.id("block/" + type).toString());

            JsonObject textures = new JsonObject();
            textures.addProperty(type, id.withPrefix("block/" + type + "/").toString());
            textures.addProperty("particle", id.withPrefix("item/" + type + "/").toString());
            base.add("textures", textures);

            return base;
        });
    }

    private void createDoublePlantBlock(BlockModelGenerators generator, Block block, BlockModelGenerators.PlantType plantType) {
        generator.registerSimpleFlatItemModel(block, "_bottom");
        generator.createDoublePlant(block, plantType);
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {

        generator.generateFlatItem(FFItems.RED_CANDY_CANE, ModelTemplates.FLAT_HANDHELD_ITEM);
        generator.generateFlatItem(FFItems.GREEN_CANDY_CANE, ModelTemplates.FLAT_HANDHELD_ITEM);
        generator.generateFlatItem(FFItems.PEPPERMINT, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FFItems.CANDIED_APPLE, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(FFItems.GINGERBREAD_DOUGH, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FFItems.GINGERBREAD_MAN, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FFItems.SNOWFLAKE_COOKIE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FFItems.TREE_COOKIE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FFItems.STAR_COOKIE, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(FFItems.CANDY_POUCH, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(FFItems.FROSTFLAKE_CANNON, ModelTemplates.FLAT_HANDHELD_ITEM);

        for (Block present : FFBlocks.PRESENTS) {
            createCategorized(generator, present, "present");
        }

        for (ItemLike bauble : FFBlocks.BAUBLES) {
            generateBauble(generator, bauble);
        }

        for (ItemLike tinsel : FFBlocks.TINSELS) {
            createCategorized(generator, tinsel, "tinsel");
        }

        generator.generateFlatItem(FFBlocks.FAIRY_LIGHTS.asItem(), ModelTemplates.FLAT_ITEM);
    }

    private void createCategorized(ItemModelGenerators generator, ItemLike item, String type) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item.asItem());
        ResourceLocation modelLocation = ModelTemplates.FLAT_ITEM.create(
                id.withPrefix("item/" + type + "/"),
                new TextureMapping()
                        .put(TextureSlot.LAYER0, id.withPrefix("item/" + type + "/"))
                        .put(TextureSlot.PARTICLE, id.withPrefix("item/" + type + "/")),
                generator.modelOutput
        );
        generator.itemModelOutput.accept(item.asItem(), ItemModelUtils.plainModel(id.withPrefix("item/" + type + "/")));
    }

    public void generateBauble(ItemModelGenerators generator, ItemLike bauble) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(bauble.asItem());

        // Base Bauble Model
        ResourceLocation baseModelLocation = ModelTemplates.TWO_LAYERED_ITEM.create(
                id.withPrefix("item/bauble/"),
                new TextureMapping()
                        .put(TextureSlot.LAYER0, id.withPrefix("item/bauble/"))
                        .put(TextureSlot.LAYER1, FestiveFrenzy.id("item/bauble/ring_default")),
                generator.modelOutput
        );
        ItemModel.Unbaked baseModel = ItemModelUtils.plainModel(baseModelLocation);

        // Bauble Models with Explosion Modification
        List<SelectItemModel.SwitchCase<BaubleExplosion.ExplosionModification>> selectProperties = new ArrayList<>();
        for (BaubleExplosion.ExplosionModification modification : BaubleExplosion.ExplosionModification.values()) {
            ResourceLocation modelLocation = ModelTemplates.TWO_LAYERED_ITEM.create(
                    id.withPrefix("item/bauble/").withSuffix("_modification_" + modification.getName()),
                    new TextureMapping()
                            .put(TextureSlot.LAYER0, id.withPrefix("item/bauble/"))
                            .put(TextureSlot.LAYER1, FestiveFrenzy.id("item/bauble/ring_trigger_" + modification.getName())),
                    generator.modelOutput
            );

            selectProperties.add(ItemModelUtils.when(modification, ItemModelUtils.plainModel(modelLocation)));
        }

        // Items Json File
        generator.itemModelOutput.accept(
                bauble.asItem(),
                ItemModelUtils.rangeSelect(new BaubleExplosionPowerProperty(), 1F, baseModel,
                        new RangeSelectItemModel.Entry(1F, ItemModelUtils.select(new BaubleExplosionModificationProperty(), baseModel, selectProperties)))
        );
    }
}
