package powercyphe.festive_frenzy.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import powercyphe.festive_frenzy.common.registry.FFFeatures;

public class FFDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(FFModelProvider::new);

        pack.addProvider(FFItemTagProvider::new);
        pack.addProvider(FFBlockTagProvider::new);
        pack.addProvider(FFEntityTypeTagProvider::new);
        pack.addProvider(FFEnchantmentTagProvider::new);
        pack.addProvider(FFDamageTypeTagProvider::new);

        pack.addProvider(FFRecipeProvider::new);
        pack.addProvider(FFLootTableProvider::new);
        pack.addProvider(FFBlockLootTableProvider::new);
    }
}
