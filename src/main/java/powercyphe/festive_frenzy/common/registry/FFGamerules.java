package powercyphe.festive_frenzy.common.registry;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.*;
import powercyphe.festive_frenzy.common.FestiveFrenzy;

public class FFGamerules {
    public static final GameRule<Boolean> LAYERED_MELTING_SNOW_GAMERULE =  register(
            "layered_melting_snow", booleanRule(GameRuleCategory.UPDATES, true));

    public static final GameRule<Integer> CANDY_POUCH_DROP_CHANCE_GAMERULE = register(
            "candy_pouch_drop_chance", intRule(GameRuleCategory.DROPS, 30, 0, 100));

    public static void init() {}

    public static GameRule<Boolean> booleanRule(GameRuleCategory category, boolean initialValue) {
        return new GameRule<>(category, GameRuleType.BOOL, BoolArgumentType.bool(), GameRuleTypeVisitor::visitBoolean,
                Codec.BOOL, bl -> bl ? 1 : 0, initialValue, FeatureFlagSet.of());
    }

    public static GameRule<Integer> intRule(GameRuleCategory category, int initialValue, int minValue, int maxValue) {
        return new GameRule<>(category, GameRuleType.INT, IntegerArgumentType.integer(minValue, maxValue), GameRuleTypeVisitor::visitInteger,
                Codec.INT, Integer::intValue, initialValue, FeatureFlagSet.of());
    }

    public static <T> GameRule<T> register(String id, GameRule<T> gameRule) {
        return Registry.register(BuiltInRegistries.GAME_RULE, FestiveFrenzy.id(id), gameRule);
    }
}
