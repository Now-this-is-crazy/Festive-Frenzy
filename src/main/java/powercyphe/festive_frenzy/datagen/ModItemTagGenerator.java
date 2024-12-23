package powercyphe.festive_frenzy.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import powercyphe.festive_frenzy.registry.ModBlocks;
import powercyphe.festive_frenzy.registry.ModItems;
import powercyphe.festive_frenzy.registry.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends FabricTagProvider.ItemTagProvider {
    public ModItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    public void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        int index;

        Item[] presents = new Item[ModBlocks.PRESENTS.size()];
        index = 0;
        for (Block present : ModBlocks.PRESENTS) {
            presents[index] = present.asItem();
            index++;
        }

        Item[] baubles = new Item[ModBlocks.BAUBLES.size()];
        index = 0;
        for (Block bauble : ModBlocks.BAUBLES) {
            baubles[index] = bauble.asItem();
            index++;
        }

        Item[] tinsels = new Item[ModBlocks.TINSELS.size()];
        index = 0;
        for (Block tinsel : ModBlocks.TINSELS) {
            tinsels[index] = tinsel.asItem();
            index++;
        }

        getOrCreateTagBuilder(ModTags.PRESENTS_TAG)
                .add(presents);
        getOrCreateTagBuilder(ModTags.BAUBLES_TAG)
                .add(baubles);
        getOrCreateTagBuilder(ModTags.TINSELS_TAG)
                .add(tinsels);

        getOrCreateTagBuilder(ModTags.CANDY_CANES_TAG)
                .add(ModItems.RED_CANDY_CANE)
                .add(ModItems.GREEN_CANDY_CANE);
        getOrCreateTagBuilder(ModTags.CANDY_CANE_BLOCKS_TAG)
                .add(ModBlocks.RED_CANDY_CANE_BLOCK.asItem())
                .add(ModBlocks.GREEN_CANDY_CANE_BLOCK.asItem())
                .add(ModBlocks.MIXED_CANDY_CANE_BLOCK.asItem());
    }
}
