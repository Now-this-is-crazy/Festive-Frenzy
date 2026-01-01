package powercyphe.festive_frenzy.common.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.UseCooldown;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.level.ItemLike;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.item.CandyPouchItem;
import powercyphe.festive_frenzy.common.item.FrostflakeCannonItem;
import powercyphe.festive_frenzy.common.item.SharpenedCandyCaneItem;
import powercyphe.festive_frenzy.common.item.WreathChakramItem;
import powercyphe.festive_frenzy.common.item.component.ExplosiveBaubleComponent;
import powercyphe.festive_frenzy.common.item.component.PresentContentsComponent;
import powercyphe.festive_frenzy.common.item.material.FFMaterials;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FFItems {

    public static class Tabs {

        public static final ResourceKey<CreativeModeTab> FESTIVE_FRENZY_TAB = register(
                "festive_frenzy",
                FabricItemGroup.builder()
                        .title(Component.translatable("itemGroup.festive_frenzy"))
                        .icon(FFItems.SHARPENED_CANDY_CANE::getDefaultInstance)
                        .build()
        );

        public static void init() {
            addToTab(
                    FESTIVE_HAT, CANDY_POUCH, FROSTFLAKE_CANNON,
                    SHARPENED_CANDY_CANE, WREATH_CHAKRAM,
                    RED_CANDY_CANE, GREEN_CANDY_CANE, PEPPERMINT,

                    CANDIED_APPLE, GINGERBREAD_DOUGH, GINGERBREAD_MAN,
                    SNOWFLAKE_COOKIE, TREE_COOKIE, STAR_COOKIE
            );
            addToTab(FFBlocks.PRESENTS);
            addToTab(FFBlocks.BAUBLES);
            addToTab(FFBlocks.TINSELS);

            addToTab(
                    FFBlocks.FAIRY_LIGHTS, FFBlocks.WREATH, FFBlocks.STAR_DECORATION,

                    HOLLY, FFBlocks.SHORT_FROZEN_GRASS, FFBlocks.TALL_FROZEN_GRASS,

                    FFBlocks.PACKED_SNOW, FFBlocks.PACKED_SNOW_STAIRS,
                    FFBlocks.PACKED_SNOW_SLAB, FFBlocks.PACKED_SNOW_WALL,
                    FFBlocks.POLISHED_PACKED_SNOW, FFBlocks.POLISHED_PACKED_SNOW_STAIRS,
                    FFBlocks.POLISHED_PACKED_SNOW_SLAB, FFBlocks.POLISHED_PACKED_SNOW_WALL,
                    FFBlocks.PACKED_SNOW_BRICKS, FFBlocks.PACKED_SNOW_BRICK_STAIRS,
                    FFBlocks.PACKED_SNOW_BRICK_SLAB, FFBlocks.PACKED_SNOW_BRICK_WALL,
                    FFBlocks.CHISELED_PACKED_SNOW,

                    FFBlocks.CUT_BLUE_ICE, FFBlocks.CUT_BLUE_ICE_STAIRS,
                    FFBlocks.CUT_BLUE_ICE_SLAB, FFBlocks.CUT_BLUE_ICE_WALL,
                    FFBlocks.POLISHED_BLUE_ICE, FFBlocks.POLISHED_BLUE_ICE_STAIRS,
                    FFBlocks.POLISHED_BLUE_ICE_SLAB, FFBlocks.POLISHED_BLUE_ICE_WALL,
                    FFBlocks.BLUE_ICE_BRICKS, FFBlocks.BLUE_ICE_BRICK_STAIRS,
                    FFBlocks.BLUE_ICE_BRICK_SLAB, FFBlocks.BLUE_ICE_BRICK_WALL,
                    FFBlocks.CHISELED_BLUE_ICE,

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
                    FFBlocks.GINGERBREAD_PRESSURE_PLATE, FFBlocks.GINGERBREAD_BUTTON
            );

            // Vanilla Tabs;
            addToTab(CreativeModeTabs.BUILDING_BLOCKS, entries -> {
                entries.addBefore(Items.STONE,
                        FFBlocks.GINGERBREAD_BLOCK, FFBlocks.GINGERBREAD_STAIRS,
                        FFBlocks.GINGERBREAD_SLAB, FFBlocks.GINGERBREAD_WALL,
                        FFBlocks.GINGERBREAD_BRICKS, FFBlocks.GINGERBREAD_BRICK_STAIRS,
                        FFBlocks.GINGERBREAD_BRICK_SLAB, FFBlocks.GINGERBREAD_BRICK_WALL,
                        FFBlocks.CHISELED_GINGERBREAD_BLOCK,

                        FFBlocks.GINGERBREAD_DOOR, FFBlocks.GINGERBREAD_TRAPDOOR,
                        FFBlocks.GINGERBREAD_PRESSURE_PLATE, FFBlocks.GINGERBREAD_BUTTON,

                        FFBlocks.RED_CANDY_CANE_BLOCK, FFBlocks.RED_CANDY_CANE_STAIRS, FFBlocks.RED_CANDY_CANE_SLAB,
                        FFBlocks.GREEN_CANDY_CANE_BLOCK, FFBlocks.GREEN_CANDY_CANE_STAIRS, FFBlocks.GREEN_CANDY_CANE_SLAB,
                        FFBlocks.MIXED_CANDY_CANE_BLOCK, FFBlocks.MIXED_CANDY_CANE_STAIRS, FFBlocks.MIXED_CANDY_CANE_SLAB,
                        FFBlocks.PEPPERMINT_BLOCK
                );
                entries.addBefore(Items.SEA_LANTERN,
                        FFBlocks.PACKED_SNOW, FFBlocks.PACKED_SNOW_STAIRS,
                        FFBlocks.PACKED_SNOW_SLAB, FFBlocks.PACKED_SNOW_WALL,
                        FFBlocks.POLISHED_PACKED_SNOW, FFBlocks.POLISHED_PACKED_SNOW_STAIRS,
                        FFBlocks.POLISHED_PACKED_SNOW_SLAB, FFBlocks.POLISHED_PACKED_SNOW_WALL,
                        FFBlocks.PACKED_SNOW_BRICKS, FFBlocks.PACKED_SNOW_BRICK_STAIRS,
                        FFBlocks.PACKED_SNOW_BRICK_SLAB, FFBlocks.PACKED_SNOW_BRICK_WALL,
                        FFBlocks.CHISELED_PACKED_SNOW,

                        FFBlocks.CUT_BLUE_ICE, FFBlocks.CUT_BLUE_ICE_STAIRS,
                        FFBlocks.CUT_BLUE_ICE_SLAB, FFBlocks.CUT_BLUE_ICE_WALL,
                        FFBlocks.POLISHED_BLUE_ICE, FFBlocks.POLISHED_BLUE_ICE_STAIRS,
                        FFBlocks.POLISHED_BLUE_ICE_SLAB, FFBlocks.POLISHED_BLUE_ICE_WALL,
                        FFBlocks.BLUE_ICE_BRICKS, FFBlocks.BLUE_ICE_BRICK_STAIRS,
                        FFBlocks.BLUE_ICE_BRICK_SLAB, FFBlocks.BLUE_ICE_BRICK_WALL,
                        FFBlocks.CHISELED_BLUE_ICE
                );
            });
            addToTab(CreativeModeTabs.COLORED_BLOCKS, entries -> {
                entries.addAfter(ItemStack.EMPTY, FFBlocks.PRESENTS);
                entries.addAfter(ItemStack.EMPTY, FFBlocks.BAUBLES);
                entries.addAfter(ItemStack.EMPTY, FFBlocks.TINSELS);
            });
            addToTab(CreativeModeTabs.NATURAL_BLOCKS, entries -> {
                entries.addAfter(Items.SHORT_GRASS, FFBlocks.SHORT_FROZEN_GRASS);
                entries.addAfter(Items.TALL_GRASS, FFBlocks.TALL_FROZEN_GRASS);
                entries.addAfter(Items.SWEET_BERRIES, FFItems.HOLLY);

            });
            addToTab(CreativeModeTabs.FUNCTIONAL_BLOCKS, entries -> {
                entries.addBefore(Items.SKELETON_SKULL, FFBlocks.TINSELS);
                entries.addBefore(Items.SKELETON_SKULL, FFBlocks.BAUBLES);
                entries.addBefore(Items.SKELETON_SKULL, FFBlocks.PRESENTS);

                entries.addAfter(Items.GLOW_LICHEN, FFBlocks.WREATH, FFBlocks.FAIRY_LIGHTS, FFBlocks.STAR_DECORATION);
            });
            addToTab(CreativeModeTabs.TOOLS_AND_UTILITIES, entries -> {
                entries.addBefore(Items.BUNDLE, FFItems.FESTIVE_HAT, FFItems.CANDY_POUCH);
            });
            addToTab(CreativeModeTabs.COMBAT, entries -> {
                entries.addAfter(Items.MACE, FFItems.SHARPENED_CANDY_CANE, FFItems.WREATH_CHAKRAM);
                entries.addAfter(Items.WIND_CHARGE, FFItems.FROSTFLAKE_CANNON);
            });
            addToTab(CreativeModeTabs.FOOD_AND_DRINKS, entries -> {
                entries.addAfter(Items.APPLE, FFItems.CANDIED_APPLE);
                entries.addAfter(Items.SWEET_BERRIES, FFItems.HOLLY);
                entries.addAfter(Items.PUMPKIN_PIE,
                        FFItems.RED_CANDY_CANE, FFItems.GREEN_CANDY_CANE,
                        FFItems.PEPPERMINT, FFItems.GINGERBREAD_DOUGH,
                        FFItems.GINGERBREAD_MAN, FFItems.SNOWFLAKE_COOKIE,
                        FFItems.TREE_COOKIE, FFItems.STAR_COOKIE
                );
            });
        }

        private static void addToTab(ItemLike... items) {
            ItemGroupEvents.modifyEntriesEvent(Tabs.FESTIVE_FRENZY_TAB).register(entries -> {
                for (ItemLike item : items) {
                    entries.accept(item, CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                }
            });
        }

        private static void addToTab(ResourceKey<CreativeModeTab> tab, ItemGroupEvents.ModifyEntries modifyEntries) {
            ItemGroupEvents.modifyEntriesEvent(tab).register(modifyEntries);
        }

        public static ResourceKey<CreativeModeTab> register(String id, CreativeModeTab tab) {
            ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, FestiveFrenzy.id(id));
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, tab);
            return key;
        }
    }

    public static class Components {
        public static final DataComponentType<PresentContentsComponent> PRESENT_CONTENTS_COMPONENT = register(
                "present_contents", DataComponentType.<PresentContentsComponent>builder().persistent(PresentContentsComponent.CODEC)
                        .networkSynchronized(PresentContentsComponent.STREAM_CODEC).build()
        );

        public static final DataComponentType<ExplosiveBaubleComponent> EXPLOSIVE_BAUBLE_COMPONENT = register(
                "explosive_bauble", DataComponentType.<ExplosiveBaubleComponent>builder().persistent(ExplosiveBaubleComponent.CODEC)
                        .networkSynchronized(ExplosiveBaubleComponent.STREAM_CODEC).build()
        );

        public static void init() {
        }

        public static <T> DataComponentType<T> register(String id, DataComponentType<T> componentType) {
            return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, FestiveFrenzy.id(id), componentType);
        }
    }

    // Items
    public static final Item RED_CANDY_CANE = register("red_candy_cane", Item::new, new Item.Properties().food(food(3, 1.5F)));
    public static final Item GREEN_CANDY_CANE = register("green_candy_cane", Item::new, new Item.Properties().food(food(4, 0.75F)));
    public static final Item PEPPERMINT = register("peppermint", Item::new, new Item.Properties().food(food(4, 0.7F)));
    public static final Item CANDIED_APPLE = register("candied_apple", Item::new, food(new Item.Properties(), 5, 0.75F,
            new MobEffectInstance(MobEffects.REGENERATION, 60)));

    public static final Item GINGERBREAD_DOUGH = register("gingerbread_dough", Item::new, new Item.Properties().food(food(1, 0F)));
    public static final Item GINGERBREAD_MAN = register("gingerbread_man", Item::new, new Item.Properties().food(food(6, 0.75F)));

    public static final Item SNOWFLAKE_COOKIE = register("snowflake_cookie", Item::new, food(new Item.Properties(), 4, 0.7F,
            new MobEffectInstance(MobEffects.SLOW_FALLING, 100)));
    public static final Item TREE_COOKIE = register("tree_cookie", Item::new, food(new Item.Properties(), 4, 0.7F,
            new MobEffectInstance(MobEffects.JUMP_BOOST, 100)));
    public static final Item STAR_COOKIE = register("star_cookie", Item::new, food(new Item.Properties(), 4, 0.7F,
            new MobEffectInstance(MobEffects.SPEED, 100)));

    public static final Item CANDY_POUCH = register("candy_pouch", CandyPouchItem::new, new Item.Properties());
    public static final Item FROSTFLAKE_CANNON = register("frostflake_cannon", FrostflakeCannonItem::new, new Item.Properties());

    public static final Item HOLLY = register("holly", properties -> new BlockItem(FFBlocks.HOLLY_BUSH, properties),
            food(new Item.Properties(), 3, 0.5F, new MobEffectInstance(MobEffects.POISON, 50)));

    public static final Item FESTIVE_HAT = register("festive_hat", Item::new, new Item.Properties().equippable(EquipmentSlot.HEAD).stacksTo(1));
    public static final Item SHARPENED_CANDY_CANE = register("sharpened_candy_cane", (properties ->
            new SharpenedCandyCaneItem(properties, FFMaterials.CANDY_CANE, 3F, -2.8F, 0.5F)), new Item.Properties());
    public static final Item WREATH_CHAKRAM = register("wreath_chakram", properties ->
            new WreathChakramItem(properties, FFMaterials.WREATH_CHAKRAM, 3F, -2.0F), new Item.Properties());

    public static void init() {
    }

    public static Item register(String id, Function<Item.Properties, Item> itemFunction, Item.Properties properties) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, FestiveFrenzy.id(id));
        Item item = Registry.register(BuiltInRegistries.ITEM, itemKey, itemFunction.apply(properties.setId(itemKey)));

        return item;
    }

    public static FoodProperties food(int nutrition, float saturationModifier) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturationModifier).build();
    }

    public static Item.Properties food(Item.Properties properties, int nutrition, float saturationModifier, MobEffectInstance... effects) {
        return properties.food(food(nutrition, saturationModifier)).component(DataComponents.CONSUMABLE, Consumable.builder()
                .onConsume(new ApplyStatusEffectsConsumeEffect(List.of(effects))).build());
    }
}
