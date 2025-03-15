package powercyphe.festive_frenzy.common.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentType;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.*;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import powercyphe.festive_frenzy.FestiveFrenzy;
import powercyphe.festive_frenzy.common.item.CandyPouchItem;
import powercyphe.festive_frenzy.common.item.FestiveHatItem;
import powercyphe.festive_frenzy.common.item.FrostflakeCannonItem;
import powercyphe.festive_frenzy.common.item.SharpenedCandyCaneItem;
import powercyphe.festive_frenzy.common.item.*;
import powercyphe.festive_frenzy.common.item.component.PresentContentComponent;
import powercyphe.festive_frenzy.common.item.material.CandyToolMaterial;
import powercyphe.festive_frenzy.common.world.BaubleExplosion;

import java.util.function.UnaryOperator;

public class ModItems {

    public static class Components {
        public static final DataComponentType<PresentContentComponent> PRESENT_CONTENT = register("present_content",
                (builder) -> { return builder.codec(PresentContentComponent.CODEC).packetCodec(PresentContentComponent.PACKET_CODEC).cache();
                });

        public static String EXPLOSION_STRENGTH_KEY = "explosion_strength";
        public static final DataComponentType<Integer> EXPLOSION_STRENGTH = register(EXPLOSION_STRENGTH_KEY,
                (builder) -> { return builder.codec(Codecs.NONNEGATIVE_INT).packetCodec(PacketCodecs.VAR_INT).cache();
                });

        public static String EXPLOSION_MODIFICATION_KEY = "explosion_modification";
        public static final DataComponentType<BaubleExplosion.ExplosionModification> EXPLOSION_MODIFICATION = register(EXPLOSION_MODIFICATION_KEY,
                (builder) -> { return builder.codec(BaubleExplosion.ExplosionModification.CODEC).packetCodec(BaubleExplosion.ExplosionModification.PACKET_CODEC).cache();
                });


        public static void init() {}

        public static <T> DataComponentType<T> register(String path, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
            return Registry.register(Registries.DATA_COMPONENT_TYPE, FestiveFrenzy.id(path), builderOperator.apply(DataComponentType.builder()).build());
        }
    }


    public static final RegistryKey<ItemGroup> FESTIVE_FRENZY_GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, FestiveFrenzy.id("festive_frenzy_group"));
    public static final ItemGroup FESTIVE_FRENZY_GROUP = FabricItemGroup.builder()
            .icon(() -> ModItems.FESTIVE_HAT.getDefaultStack())
            .displayName(Text.translatable("itemGroup.festive_frenzy"))
            .build();

    public static Item FESTIVE_HAT = register("festive_hat", new FestiveHatItem(new Item.Settings().maxCount(1)));
    public static Item CANDY_POUCH = register("candy_pouch", new CandyPouchItem(new Item.Settings()));


    public static Item RED_CANDY_CANE = register("red_candy_cane", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.4f).snack().build())));
    public static Item GREEN_CANDY_CANE = register("green_candy_cane", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(3).saturationModifier(0.4f).snack().build())));
    public static Item PEPPERMINT = register("peppermint", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(5).saturationModifier(0.6f).snack().build())));

    public static Item FROSTFLAKE_CANNON = register("frostflake_cannon", new FrostflakeCannonItem(new Item.Settings()));

    // Sharpened Candy Canes
    public static DefaultedList<Item> SHARPENED_CANDY_CANES = DefaultedList.of();

    public static Item SHARPENED_CANDY_CANE = registerSharpendCandyCane("sharpened_candy_cane");

    public static void init() {
        Registry.register(Registries.ITEM_GROUP, FESTIVE_FRENZY_GROUP_KEY, FESTIVE_FRENZY_GROUP);

    }

    public static Item registerBlockItem(Block block) {
        return register(Registries.BLOCK.getId(block), new BlockItem(block, new Item.Settings()));
    }

    public static Item register(String id, Item item) {
        return register(FestiveFrenzy.id(id), item);
    }

    public static Item register(Identifier id, Item item) {
        Item returnItem = Registry.register(Registries.ITEM, id, item);
        ItemGroupEvents.modifyEntriesEvent(FESTIVE_FRENZY_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(returnItem.getDefaultStack());
        });
        return returnItem;
    }

    // Unique Item Registries
    public static Item registerSharpendCandyCane(String id) {
        Item item = register(FestiveFrenzy.id(id), new SharpenedCandyCaneItem(new CandyToolMaterial(), new Item.Settings().attributeModifiers(SharpenedCandyCaneItem.createAttributeModifiers(new CandyToolMaterial(), 3, -2.7F, 0.5f))));
        SHARPENED_CANDY_CANES.add(item);
        return item;
    }

}
