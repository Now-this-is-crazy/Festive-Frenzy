package powercyphe.festive_frenzy.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import powercyphe.festive_frenzy.FestiveFrenzy;
import powercyphe.festive_frenzy.item.*;
import powercyphe.festive_frenzy.item.material.CandyToolMaterial;

public class ModItems {

    public static final RegistryKey<ItemGroup> FESTIVE_FRENZY_GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, FestiveFrenzy.id("festive_frenzy_group"));
    public static final ItemGroup FESTIVE_FRENZY_GROUP = FabricItemGroup.builder()
            .icon(() -> ModItems.FESTIVE_HAT.getDefaultStack())
            .displayName(Text.translatable("itemGroup.festive_frenzy"))
            .build();

    public static Item FESTIVE_HAT = register("festive_hat", new FestiveHatItem(new Item.Settings().maxCount(1)));
    public static Item CANDY_POUCH = register("candy_pouch", new CandyPouchItem(new Item.Settings()));


    public static Item RED_CANDY_CANE = register("red_candy_cane", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().hunger(3).saturationModifier(0.4f).snack().build())));
    public static Item GREEN_CANDY_CANE = register("green_candy_cane", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().hunger(3).saturationModifier(0.4f).snack().build())));
    public static Item PEPPERMINT = register("peppermint", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().hunger(5).saturationModifier(0.6f).snack().build())));

    public static Item FROSTFLAKE_CANNON = register("frostflake_cannon", new FrostflakeCannonItem(new Item.Settings()));

    public static Item SHARPENED_CANDY_CANE = register("sharpened_candy_cane", new SharpenedCandyCaneItem(new CandyToolMaterial(), 3, -2.7F, 0.5f, new Item.Settings()));




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

}
