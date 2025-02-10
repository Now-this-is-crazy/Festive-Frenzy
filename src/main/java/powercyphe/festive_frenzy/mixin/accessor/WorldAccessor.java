package powercyphe.festive_frenzy.mixin.accessor;

import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(World.class)
public interface WorldAccessor {
    @Invoker("getDestructionType")
    Explosion.DestructionType festive_frenzy$getDestructionType(GameRules.Key<GameRules.BooleanRule> gameRuleKey);
}
