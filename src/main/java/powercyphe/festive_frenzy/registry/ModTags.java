package powercyphe.festive_frenzy.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import powercyphe.festive_frenzy.FestiveFrenzy;

public class ModTags {

    public static class Items {
        public static final TagKey<Item> SHARPENED_CANDY_CANES_TAG = TagKey.of(RegistryKeys.ITEM, FestiveFrenzy.id("sharpened_candy_canes"));

        public static final TagKey<Item> PRESENTS_TAG = TagKey.of(RegistryKeys.ITEM, FestiveFrenzy.id("presents"));
        public static final TagKey<Item> BAUBLES_TAG = TagKey.of(RegistryKeys.ITEM, FestiveFrenzy.id("baubles"));
        public static final TagKey<Item> TINSELS_TAG = TagKey.of(RegistryKeys.ITEM, FestiveFrenzy.id("tinsels"));
        public static final TagKey<Item> CANDY_CANES_TAG = TagKey.of(RegistryKeys.ITEM, FestiveFrenzy.id("candy_canes"));
        public static final TagKey<Item> CANDY_CANE_BLOCKS_TAG = TagKey.of(RegistryKeys.ITEM, FestiveFrenzy.id("candy_cane_blocks"));
    }

    public static class Blocks {
        public static final TagKey<Block> BAUBLE_PLACEABLE = TagKey.of(RegistryKeys.BLOCK, FestiveFrenzy.id("bauble_placeable"));
        public static final TagKey<Block> SIDE_DECO_PLACEABLE = TagKey.of(RegistryKeys.BLOCK, FestiveFrenzy.id("side_deco_placeable"));
    }

    public static void init() {}
}
