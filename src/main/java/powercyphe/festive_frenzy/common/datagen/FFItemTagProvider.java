package powercyphe.festive_frenzy.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import powercyphe.festive_frenzy.common.registry.FFBlocks;
import powercyphe.festive_frenzy.common.registry.FFItems;
import powercyphe.festive_frenzy.common.registry.FFTags;

import java.util.concurrent.CompletableFuture;

public class FFItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public FFItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateTagBuilder(FFTags.Items.CANDY_CANES)
                .add(FFItems.RED_CANDY_CANE, FFItems.GREEN_CANDY_CANE);
        fromItemLike(FFTags.Items.CANDY_CANE_BLOCKS,
                FFBlocks.RED_CANDY_CANE_BLOCK, FFBlocks.GREEN_CANDY_CANE_BLOCK, FFBlocks.MIXED_CANDY_CANE_BLOCK);

        getOrCreateTagBuilder(FFTags.Items.CANDY_POUCH_FOOD)
                .add(FFItems.PEPPERMINT, FFItems.GINGERBREAD_MAN, FFItems.CANDIED_APPLE,
                        FFItems.SNOWFLAKE_COOKIE, FFItems.TREE_COOKIE, FFItems.STAR_COOKIE);

        fromItemLike(FFTags.Items.PRESENTS, FFBlocks.PRESENTS);
        fromItemLike(FFTags.Items.BAUBLES, FFBlocks.BAUBLES);
        fromItemLike(FFTags.Items.TINSEL, FFBlocks.TINSEL);

        getOrCreateTagBuilder(FFTags.Items.ITEMS_WITH_EXPLOSION_POWER)
                .add(Items.GUNPOWDER);


        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(FFItems.SHARPENED_CANDY_CANE);

        fromItemLike(ItemTags.STAIRS, FFBlocks.GINGERBREAD_STAIRS, FFBlocks.GINGERBREAD_BRICK_STAIRS,
                        FFBlocks.PACKED_SNOW_STAIRS, FFBlocks.POLISHED_PACKED_SNOW_STAIRS,
                        FFBlocks.PACKED_SNOW_BRICK_STAIRS, FFBlocks.CUT_BLUE_ICE_STAIRS,
                        FFBlocks.POLISHED_BLUE_ICE_STAIRS, FFBlocks.BLUE_ICE_BRICK_STAIRS
                );
        fromItemLike(ItemTags.SLABS, FFBlocks.GINGERBREAD_SLAB, FFBlocks.GINGERBREAD_BRICK_SLAB,
                        FFBlocks.PACKED_SNOW_SLAB, FFBlocks.POLISHED_PACKED_SNOW_SLAB,
                        FFBlocks.PACKED_SNOW_BRICK_SLAB, FFBlocks.CUT_BLUE_ICE_SLAB,
                        FFBlocks.POLISHED_BLUE_ICE_SLAB, FFBlocks.BLUE_ICE_BRICK_SLAB
                );
        fromItemLike(ItemTags.WALLS, FFBlocks.GINGERBREAD_WALL, FFBlocks.GINGERBREAD_BRICK_WALL,
                        FFBlocks.PACKED_SNOW_WALL, FFBlocks.POLISHED_PACKED_SNOW_WALL,
                        FFBlocks.PACKED_SNOW_BRICK_WALL, FFBlocks.CUT_BLUE_ICE_WALL,
                        FFBlocks.POLISHED_BLUE_ICE_WALL, FFBlocks.BLUE_ICE_BRICK_WALL
                );

        fromItemLike(ItemTags.DOORS, FFBlocks.GINGERBREAD_DOOR);
        fromItemLike(ItemTags.TRAPDOORS, FFBlocks.GINGERBREAD_TRAPDOOR);
        fromItemLike(ItemTags.BUTTONS, FFBlocks.GINGERBREAD_BUTTON);
    }

    public void fromItemLike(TagKey<Item> itemTag, ItemLike... itemLikes) {
        for (ItemLike itemLike : itemLikes) {
            getOrCreateTagBuilder(itemTag)
                    .add(itemLike.asItem());
        }
    }
}
