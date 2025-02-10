package powercyphe.festive_frenzy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import powercyphe.festive_frenzy.registry.ModNetworking;
import powercyphe.festive_frenzy.registry.*;
import powercyphe.festive_frenzy.util.ModLootTableModifier;

public class FestiveFrenzy implements ModInitializer {
	public static final String MOD_ID = "festive_frenzy";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static GameRules.Key<GameRules.IntRule> POUCH_DROP_CHANCE = GameRuleRegistry.register("candyPouchDropChance", GameRules.Category.MOBS, GameRuleFactory.createIntRule(9, 0, 100));

	@Override
	public void onInitialize() {
		ModItems.init();
		ModBlocks.init();
		ModBlockEntities.init();
		ModEntities.init();
		ModParticles.init();
		ModSounds.init();
		ModNetworking.init();
		ModTags.init();
		ModRecipes.init();
		ModScreenHandlers.init();

		ModLootTableModifier.modify();

		FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent((modContainer -> {
			ResourceManagerHelper.registerBuiltinResourcePack(id("snowy_grass_plus"), modContainer, Text.literal("Snowy Grass+"), ResourcePackActivationType.DEFAULT_ENABLED);
		}));
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}