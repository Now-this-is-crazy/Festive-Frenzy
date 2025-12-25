package powercyphe.festive_frenzy.common.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import powercyphe.festive_frenzy.common.FestiveFrenzy;

public class FFTags {

    public static class Items {
        public static final TagKey<Item> PRESENTS = key(Registries.ITEM, "presents");
        public static final TagKey<Item> BAUBLES = key(Registries.ITEM, "baubles");
        public static final TagKey<Item> TINSEL = key(Registries.ITEM, "tinsel");

        public static final TagKey<Item> CANDY_CANES = key(Registries.ITEM, "candy_canes");
        public static final TagKey<Item> CANDY_CANE_BLOCKS = key(Registries.ITEM, "candy_cane_blocks");
        public static final TagKey<Item> CANDY_POUCH_FOOD = key(Registries.ITEM, "candy_pouch_food");

        public static final TagKey<Item> ITEMS_WITH_EXPLOSION_POWER = key(Registries.ITEM, "items_with_explosion_power");

        public static final TagKey<Item> WREATH_CHAKRAM_MATERIALS = key(Registries.ITEM, "wreath_chakram_materials");
        public static final TagKey<Item> WREATH_CHAKRAM_ENCHANTABLE = key(Registries.ITEM, "enchantable/wreath_chakram");
    }

    public static class Blocks {
        public static final TagKey<Block> SUPPORTS_BAUBLES = key(Registries.BLOCK, "supports/baubles");
        public static final TagKey<Block> SUPPORTS_MULTIFACE_DECORATION = key(Registries.BLOCK, "supports/multiface_decoration");
        public static final TagKey<Block> SUPPORTS_WREATH = key(Registries.BLOCK, "supports/wreath");
        public static final TagKey<Block> SUPPORTS_STAR_DECORATION = key(Registries.BLOCK, "supports/star_decoration");
    }

    public static class Entities {
        public static final TagKey<EntityType<?>> DETACHES_BAUBLES = key(Registries.ENTITY_TYPE, "detaches_baubles");
    }

    public static void init() {}

    public static <T> TagKey<T> key(ResourceKey<Registry<T>> registry, String name) {
        return TagKey.create(registry, FestiveFrenzy.id(name));
    }
}
