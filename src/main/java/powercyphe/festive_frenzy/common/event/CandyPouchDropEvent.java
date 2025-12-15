package powercyphe.festive_frenzy.common.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.GameRules;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.registry.FFGamerules;
import powercyphe.festive_frenzy.common.registry.FFItems;

public class CandyPouchDropEvent implements ServerLivingEntityEvents.AfterDeath {

    @Override
    public void afterDeath(LivingEntity entity, DamageSource damageSource) {
        if (entity.level() instanceof ServerLevel serverLevel && entity instanceof Mob) {
            GameRules gameRules = serverLevel.getGameRules();

            if (gameRules.getRule(GameRules.RULE_DOENTITYDROPS).get()) {
                GameRules.IntegerValue gamerule = gameRules.getRule(FFGamerules.CANDY_POUCH_DROP_CHANCE_GAMERULE);

                if (RandomSource.create().nextFloat() * 100 < gamerule.get()) {
                    entity.spawnAtLocation(serverLevel, FFItems.CANDY_POUCH, 1);
                }
            }
        }
    }
}
