package powercyphe.festive_frenzy.common.world;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.entity.ThrownBaubleProjectileEntity;
import powercyphe.festive_frenzy.common.mob_effect.FrostburnEffect;
import powercyphe.festive_frenzy.common.particle.BaubleExplosionParticleOption;
import powercyphe.festive_frenzy.common.registry.FFEffects;

import java.util.*;
import java.util.function.Predicate;

public class BaubleExplosion extends ServerExplosion {
    private final ExplosionModification explosionModification;
    private final int explosionColor;

    public BaubleExplosion(ServerLevel serverLevel, @Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionDamageCalculator explosionDamageCalculator,
                           Vec3 pos, float radius, BlockInteraction blockInteraction, ExplosionModification explosionModification, int explosionColor) {
        super(serverLevel, entity, damageSource, explosionDamageCalculator, pos, radius, false, blockInteraction);
        this.explosionModification = explosionModification;
        this.explosionColor = explosionColor;
    }

    public BaubleExplosion(ServerLevel serverLevel, ThrownBaubleProjectileEntity baubleProjectile) {
        this(serverLevel, baubleProjectile, baubleProjectile.damageSources().explosion(baubleProjectile, baubleProjectile.getOwner()),
                null, baubleProjectile.getEyePosition(), baubleProjectile.getExplosionPower() / 2F, BlockInteraction.DESTROY_WITH_DECAY,
                baubleProjectile.getExplosionModification(), baubleProjectile.getExplosionColor());
    }

    public int getExplosionColor() {
        return this.explosionColor;
    }

    public ExplosionModification getExplosionModification() {
        return this.explosionModification;
    }


    public static void create(ServerLevel serverLevel, ThrownBaubleProjectileEntity baubleProjectile) {
        BaubleExplosion baubleExplosion = new BaubleExplosion(serverLevel, baubleProjectile);
        baubleExplosion.explode();
        ParticleOptions explosionParticle = new BaubleExplosionParticleOption(baubleExplosion.getExplosionColor(), !baubleExplosion.isSmall());

        Vec3 pos = baubleExplosion.center();
        for (ServerPlayer serverPlayer : serverLevel.players()) {
            if (serverPlayer.distanceToSqr(pos) < 4096.0) {
                Optional<Vec3> optional = Optional.ofNullable(baubleExplosion.getHitPlayers().get(serverPlayer));
                serverPlayer.connection.send(new ClientboundExplodePacket(pos, optional, explosionParticle, SoundEvents.GENERIC_EXPLODE));
            }
        }
    }

    public enum ExplosionModification implements StringRepresentable {
        NONE(Items.AIR, Handler.EMPTY),

        FIRE(Items.FIRE_CHARGE, new Handler() {
            @Override
            public void affectEntity(Level level, LivingEntity entity, float distanceMultiplier, float radius) {
                entity.setRemainingFireTicks((int) (entity.getRemainingFireTicks() + (90 * distanceMultiplier)));
            }

            @Override
            public void affectBlock(Level level, BlockState state, BlockPos blockPos, float distanceMultiplier, float radius) {
                if (RandomSource.create().nextInt(8 - (int) (distanceMultiplier * 7)) == 1 && BaseFireBlock.canBePlacedAt(level, blockPos, Direction.DOWN)) {
                    level.setBlockAndUpdate(blockPos, BaseFireBlock.getState(level, blockPos));
                }
            }
        }),
        ICE(Items.BLUE_ICE, new Handler() {
            @Override
            public void affectEntity(Level level, LivingEntity entity, float distanceMultiplier, float radius) {
                int baseTicks = (int) (300 * distanceMultiplier);
                FrostburnEffect.addAccumulativeEffect(entity, baseTicks, 600);
            }

            @Override
            public void affectBlock(Level level, BlockState state, BlockPos blockPos, float distanceMultiplier, float radius) {
                if (RandomSource.create().nextInt(21 - (int) (distanceMultiplier * 20)) == 1 && state.isAir()) {
                    level.setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());
                }
            }
        })
        ;

        public static final Codec<ExplosionModification> CODEC = StringRepresentable.fromEnum(ExplosionModification::values);
        public static final StreamCodec<ByteBuf, ExplosionModification> STREAM_CODEC = ByteBufCodecs.idMapper(ByIdMap.continuous(
                ExplosionModification::getId, values(), ByIdMap.OutOfBoundsStrategy.WRAP), ExplosionModification::getId);

        private final int id;
        private final String name;

        private final ItemLike recipeItem;

        private final Handler handler;

        ExplosionModification(ItemLike recipeItem, Handler handler) {
            this.id = this.ordinal();
            this.name = this.name().toLowerCase();

            this.recipeItem = recipeItem;
            this.handler = handler;
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public ItemLike getRecipeItem() {
            return recipeItem;
        }

        @Nullable
        public static ExplosionModification fromItem(ItemStack stack) {
            for (ExplosionModification mod : ExplosionModification.values()) {
                if (stack.is(mod.getRecipeItem().asItem())) {
                    return mod;
                }
            }

            return null;
        }

        public void affectBlock(Level level, Explosion explosion, BlockPos blockPos) {
            float multiplier = 1F - Mth.clamp((float) Math.max(
                    explosion.center().distanceTo(blockPos.getCenter()) - 0.5, 0)
                    / explosion.radius(), 0F, 1F
            );
            this.getHandler().affectBlock(level, level.getBlockState(blockPos), blockPos, multiplier, explosion.radius());
        }

        public void affectEntity(Level level, Explosion explosion, LivingEntity entity) {
            float multiplier = 1F - Mth.clamp((float) Math.max(
                    Math.sqrt(entity.getBoundingBox().distanceToSqr(explosion.center())) - 0.5, 0)
                    / explosion.radius(), 0F, 1F
            );
            this.getHandler().affectEntity(level, entity, multiplier, explosion.radius());
        }

        public Handler getHandler() {
            return this.handler;
        }

        public String getTranslationKey() {
            return "tooltip.festive_frenzy.explosion_modification." + this.name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public interface Handler {
            Handler EMPTY = new Handler() {
                @Override
                public void affectEntity(Level level, LivingEntity entity, float distanceMultiplier, float radius) {}

                @Override
                public void affectBlock(Level level, BlockState state, BlockPos blockPos, float distanceMultiplier, float radius) {}
            };

            void affectEntity(Level level, LivingEntity entity, float distanceMultiplier, float radius);

            void affectBlock(Level level, BlockState state, BlockPos blockPos, float distanceMultiplier, float radius);
        }
    }
}
