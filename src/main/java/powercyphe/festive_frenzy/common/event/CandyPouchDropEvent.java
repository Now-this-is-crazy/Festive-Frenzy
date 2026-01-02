package powercyphe.festive_frenzy.common.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.gamerules.GameRules;
import powercyphe.festive_frenzy.common.registry.FFGamerules;
import powercyphe.festive_frenzy.common.registry.FFItems;

public class CandyPouchDropEvent implements ServerLivingEntityEvents.AfterDeath {

    @Override
    public void afterDeath(LivingEntity entity, DamageSource damageSource) {
        if (entity.level() instanceof ServerLevel serverLevel && entity instanceof Monster) {
            GameRules gameRules = serverLevel.getGameRules();

            if (gameRules.get(GameRules.MOB_DROPS)) {
                int chance = gameRules.get(FFGamerules.CANDY_POUCH_DROP_CHANCE_GAMERULE);

                if (RandomSource.create().nextFloat() * 100 < chance) {
                    entity.spawnAtLocation(serverLevel, FFItems.CANDY_POUCH);
                }
            }
        }
    }
}
