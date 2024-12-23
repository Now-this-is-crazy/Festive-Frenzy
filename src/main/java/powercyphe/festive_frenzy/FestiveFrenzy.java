package powercyphe.festive_frenzy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import powercyphe.festive_frenzy.network.ModNetworking;
import powercyphe.festive_frenzy.registry.*;

public class FestiveFrenzy implements ModInitializer {
	public static final String MOD_ID = "festive_frenzy";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static GameRules.Key<GameRules.IntRule> PRESENT_DROP_CHANCE = GameRuleRegistry.register("presentDropChance", GameRules.Category.MOBS, GameRuleFactory.createIntRule(9, 0, 100));

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
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}