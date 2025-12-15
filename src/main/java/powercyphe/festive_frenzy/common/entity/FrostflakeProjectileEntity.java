package powercyphe.festive_frenzy.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import powercyphe.festive_frenzy.common.registry.FFDamageTypes;
import powercyphe.festive_frenzy.common.registry.FFEntities;
import powercyphe.festive_frenzy.common.registry.FFParticles;
import powercyphe.festive_frenzy.common.registry.FFSounds;
import powercyphe.festive_frenzy.common.util.VelocityBasedRotationImpl;

public class FrostflakeProjectileEntity extends Projectile {
    private int nextParticleTick = 0;

    public FrostflakeProjectileEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public FrostflakeProjectileEntity(LivingEntity owner, Level level) {
        this(owner.getX(), owner.getEyeY() - 0.1F, owner.getZ(), level);
        this.setOwner(owner);
    }

    public FrostflakeProjectileEntity(double x, double y, double z, Level level) {
        super(FFEntities.FROSTFLAKE_PROJECTILE, level);
        this.setPos(x, y, z);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {}

    @Override
    public boolean canUsePortal(boolean bl) {
        return true;
    }

    @Override
    public void tick() {
        if (!tryFreezeWater()) {
            this.applyGravity();
            this.applyInertia();
            HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            Vec3 vec3;
            if (hitResult.getType() != HitResult.Type.MISS) {
                vec3 = hitResult.getLocation();
            } else {
                vec3 = this.position().add(this.getDeltaMovement());
            }

            this.setPos(vec3);
            this.updateRotation();
            this.applyEffectsFromBlocks();
            super.tick();
            if (hitResult.getType() != HitResult.Type.MISS && this.isAlive()) {
                this.hitTargetOrDeflectSelf(hitResult);
            }

            if (this.level().isClientSide()) {
                if (this.tickCount >= this.nextParticleTick) {
                    this.nextParticleTick += RandomSource.create().nextInt(3) + 2;
                    this.level().addParticle((ParticleOptions) FFParticles.FROSTFLAKE_TRAIL, this.getX(), this.getY() + this.getBbHeight() / 2, this.getZ(), 0, 0, 0);
                }
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.hurt(FFDamageTypes.createSource(this.level().registryAccess(), FFDamageTypes.FROSTFLAKE, this, this.getOwner()), 0.5F);
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);

        if (this.level() instanceof ServerLevel serverLevel) {
            this.playSound(FFSounds.FROSTFLAKE_PROJECTILE_HIT);
            serverLevel.sendParticles((ParticleOptions) FFParticles.FROSTFLAKE, this.getX(), this.getY() + this.getEyeHeight(), this.getZ(),
                    RandomSource.create().nextInt(4, 7), 0, 0, 0, 0.5);
        }
        this.discard();
    }

    private void applyInertia() {
        Vec3 vec3 = this.getDeltaMovement();
        float g;
        if (this.isInWater()) {
            g = 0F;
            this.discard();
        } else {
            g = 0.99F;
        }

        this.setDeltaMovement(vec3.scale(g));
    }

    private boolean tryFreezeWater() {
        Level level = this.level();

        if (this.isInWater()) {
            BlockPos blockPos = this.blockPosition();

            if (level.getFluidState(blockPos).is(Fluids.WATER) && level.getBlockState(blockPos).canBeReplaced()) {
                level.setBlockAndUpdate(blockPos, Blocks.FROSTED_ICE.defaultBlockState());

                this.discard();
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doWaterSplashEffect() {}

    @Override
    protected double getDefaultGravity() {
        return 0.03;
    }
}
