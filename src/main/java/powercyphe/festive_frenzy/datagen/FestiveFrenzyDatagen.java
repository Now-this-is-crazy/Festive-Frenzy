package powercyphe.festive_frenzy.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class FestiveFrenzyDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModItemTagGenerator::new);
		pack.addProvider(ModBlockTagGenerator::new);
		pack.addProvider(ModModelGenerator::new);
		pack.addProvider(ModBlockLootTableGenerator::new);
		pack.addProvider(ModRecipeGenerator::new);
	}
}
