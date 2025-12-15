package powercyphe.festive_frenzy.common.registry;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.level.GameRules;

public class FFGamerules {
    public static final GameRules.Key<GameRules.BooleanValue> LAYERED_MELTING_SNOW_GAMERULE = GameRuleRegistry.register(
            "layeredMeltingSnow", GameRules.Category.UPDATES, GameRuleFactory.createBooleanRule(true));

    public static final GameRules.Key<GameRules.IntegerValue> CANDY_POUCH_DROP_CHANCE_GAMERULE = GameRuleRegistry.register(
            "candyPouchDropChance", GameRules.Category.DROPS, GameRuleFactory.createIntRule(30, 0, 100));

    public static void init() {}

    public static <T extends GameRules.Value<T>> GameRules.Key<T> register(String id, GameRules.Category category, GameRules.Type<T> gamerule) {
        return GameRuleRegistry.register(id, category, gamerule);
    }
}
