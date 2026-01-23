package powercyphe.festive_frenzy.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
import powercyphe.festive_frenzy.common.registry.FFEnchantments;

import java.util.concurrent.CompletableFuture;

public class FFEnchantmentTagProvider extends FabricTagProvider<Enchantment> {

    public FFEnchantmentTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ENCHANTMENT, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        builder(EnchantmentTags.NON_TREASURE)
                .addOptional(FFEnchantments.PRICKLED_ENCHANTMENT)
                .addOptional(FFEnchantments.PHYTOTOXICITY_ENCHANTMENT)
                .addOptional(FFEnchantments.RICOCHET_ENCHANTMENT);
    }
}
