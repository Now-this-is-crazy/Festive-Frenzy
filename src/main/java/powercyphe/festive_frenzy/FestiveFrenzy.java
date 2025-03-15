package powercyphe.festive_frenzy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import powercyphe.festive_frenzy.common.payload.PresentClosePayload;
import powercyphe.festive_frenzy.common.registry.*;
import powercyphe.festive_frenzy.common.util.ModLootTableModifier;

public class FestiveFrenzy implements ModInitializer {
	public static final String MOD_ID = "festive_frenzy";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static GameRules.Key<GameRules.IntRule> POUCH_DROP_CHANCE = GameRuleRegistry.register("candyPouchDropChance", GameRules.Category.MOBS, GameRuleFactory.createIntRule(9, 0, 100));
	public static RegistryKey<LootTable> CANDY_POUCH = RegistryKey.of(RegistryKeys.LOOT_TABLE, FestiveFrenzy.id("candy_pouch"));

	@Override
	public void onInitialize() {
		ModItems.init();
		ModItems.Components.init();
		ModBlocks.init();
		ModBlocks.Entities.init();
		ModEntities.init();
		ModEntities.TrackedData.init();
		ModParticles.init();
		ModSounds.init();
		ModNetworking.init();
		ModTags.init();
		ModRecipes.init();
		ModScreenHandlers.init();

		ModLootTableModifier.modify();

		initNetworking();

		FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent((modContainer -> {
			ResourceManagerHelper.registerBuiltinResourcePack(id("snowy_grass_plus"), modContainer, Text.literal("Snowy Grass+"), ResourcePackActivationType.DEFAULT_ENABLED);
		}));
	}

	public static void initNetworking() {
		ServerPlayNetworking.registerGlobalReceiver(PresentClosePayload.ID, new PresentClosePayload.Receiver());
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}