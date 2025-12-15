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
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.item.CandyPouchItem;
import powercyphe.festive_frenzy.common.item.FrostflakeCannonItem;
import powercyphe.festive_frenzy.common.item.SharpenedCandyCaneItem;
import powercyphe.festive_frenzy.common.item.component.ExplosiveBaubleComponent;
import powercyphe.festive_frenzy.common.item.component.PresentContentsComponent;
import powercyphe.festive_frenzy.common.item.material.FFMaterials;

import java.util.List;
import java.util.function.Function;

public class FFItems {

    public static class Components {
        public static final DataComponentType<PresentContentsComponent> PRESENT_CONTENTS_COMPONENT = register(
                "present_contents", DataComponentType.<PresentContentsComponent>builder().persistent(PresentContentsComponent.CODEC)
                        .networkSynchronized(PresentContentsComponent.STREAM_CODEC).build()
        );

        public static final DataComponentType<ExplosiveBaubleComponent> EXPLOSIVE_BAUBLE_COMPONENT = register(
                "explosive_bauble", DataComponentType.<ExplosiveBaubleComponent>builder().persistent(ExplosiveBaubleComponent.CODEC)
                        .networkSynchronized(ExplosiveBaubleComponent.STREAM_CODEC).build()
        );

        public static void init() {}

        public static <T> DataComponentType<T> register(String id, DataComponentType<T> componentType) {
            return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, FestiveFrenzy.id(id), componentType);
        }
    }

    public static final ResourceKey<CreativeModeTab> FESTIVE_FRENZY_TAB_KEY = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB, FestiveFrenzy.id("festive_frenzy"));
    public static final CreativeModeTab FESTIVE_FRENZY_TAB = FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.festive_frenzy"))
            .icon(() -> FFItems.RED_CANDY_CANE.getDefaultInstance())
            .build();

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
            new MobEffectInstance(MobEffects.JUMP, 100)));
    public static final Item STAR_COOKIE = register("star_cookie", Item::new, food(new Item.Properties(), 4, 0.7F,
            new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100)));

    public static final Item CANDY_POUCH = register("candy_pouch", CandyPouchItem::new, new Item.Properties());
    public static final Item FROSTFLAKE_CANNON = register("frostflake_cannon", FrostflakeCannonItem::new, new Item.Properties());

    public static final Item SHARPENED_CANDY_CANE = register("sharpened_candy_cane", (properties ->
                    new SharpenedCandyCaneItem(properties, FFMaterials.CANDY_CANE, 4F, -2.8F, 0.5F)), new Item.Properties());

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, FESTIVE_FRENZY_TAB_KEY, FESTIVE_FRENZY_TAB);
    }

    public static Item register(String id, Function<Item.Properties, Item> itemFunction, Item.Properties properties) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, FestiveFrenzy.id(id));
        Item item = Registry.register(BuiltInRegistries.ITEM, itemKey, itemFunction.apply(properties.setId(itemKey)));

        addToGroup(item);
        return item;
    }

    public static FoodProperties food(int nutrition, float saturationModifier) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturationModifier).build();
    }

    public static Item.Properties food(Item.Properties properties, int nutrition, float saturationModifier, MobEffectInstance... effects) {
        return properties.food(food(nutrition, saturationModifier)).component(DataComponents.CONSUMABLE, Consumable.builder()
                .onConsume(new ApplyStatusEffectsConsumeEffect(List.of(effects))).build());
    }

    public static void addToGroup(Item item) {
        ItemGroupEvents.modifyEntriesEvent(FESTIVE_FRENZY_TAB_KEY).register(
                entries -> entries.accept(item));
    }
}
