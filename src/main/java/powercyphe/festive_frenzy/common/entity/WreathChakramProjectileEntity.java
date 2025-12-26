package powercyphe.festive_frenzy.common.entity;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicates;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.item.WreathChakramItem;
import powercyphe.festive_frenzy.common.registry.FFEntities;
import powercyphe.festive_frenzy.common.registry.FFItems;
import powercyphe.festive_frenzy.common.registry.FFParticles;
import powercyphe.festive_frenzy.common.registry.FFSounds;
import powercyphe.festive_frenzy.common.util.FFUtil;

import java.util.ArrayList;
import java.util.List;

public class WreathChakramProjectileEntity extends AbstractArrow {
    private static final EntityDataAccessor<Integer> DATA_SLOT = SynchedEntityData.defineId(WreathChakramProjectileEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_RICOCHET = SynchedEntityData.defineId(WreathChakramProjectileEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_ENCHANTED = SynchedEntityData.defineId(WreathChakramProjectileEntity.class, EntityDataSerializers.BOOLEAN);

    private final List<Integer> hitEntities = new ArrayList<>();
    private int hitsBeforeReturn = 0;
    private boolean isReturning = false;

    private float spinRotation = RandomSource.create().nextFloat();
    private float lastSpinRotation = this.spinRotation;

    public WreathChakramProjectileEntity(EntityType<WreathChakramProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public WreathChakramProjectileEntity(ServerLevel level, LivingEntity owner, ItemStack stack) {
        super(FFEntities.WREATH_CHAKRAM_PROJECTILE, owner, level, stack, null);

        this.setRicochet(WreathChakramItem.hasRicochetEnchant(level.registryAccess(), stack));
        this.getEntityData().set(DATA_ENCHANTED, stack.hasFoil());

        this.pickup = (owner instanceof Player player && player.isCreative()) ? Pickup.CREATIVE_ONLY : Pickup.ALLOWED;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_ENCHANTED, false);
        builder.define(DATA_RICOCHET, false);
        builder.define(DATA_SLOT, -1);
        super.defineSynchedData(builder);
    }

    @Override
    public void tick() {
        this.applyMovement();

        if (this.level().isClientSide()) {
            if (this.tickCount % 2 == 0) {
                Vec3 parVel = this.getDeltaMovement().scale(-1.87);
                this.level().addParticle((ParticleOptions) FFParticles.HOLLY_LEAF, this.getRandomX(0.8), this.getRandomY(), this.getRandomZ(0.8),
                        parVel.x(), parVel.y(), parVel.z());
            }

            this.lastSpinRotation = this.spinRotation;
            this.spinRotation += (float) Math.max(1.1, this.getDeltaMovement().length() * 0.41);
        }

        super.tick();
    }

    public void applyMovement() {
        Vec3 pos = this.position();
        Vec3 vel = this.getDeltaMovement();

        if (this.isReturning()) {
            if (this.tryReturn()) {
                BlockHitResult movementResult = this.level().clipIncludingBorder(new ClipContext(pos, pos.add(vel),
                        ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
                EntityHitResult hitResult = this.findHitEntity(pos, movementResult.getLocation());

                if (hitResult != null) {
                    this.onHitEntity(hitResult);
                }
            } else {
                if (this.level() instanceof ServerLevel serverLevel) {
                    ItemEntity item = this.spawnAtLocation(serverLevel, this.getPickupItem());

                    if (item != null) {
                        item.setUnlimitedLifetime();
                        item.setGlowingTag(true);
                    }
                }
                this.discard();
            }
        } else {
            if (vel.length() < 0.71) {
                this.setReturning(true);
            } else if ((this.getOwner() != null && this.distanceTo(this.getOwner()) > 21)) {
                this.applyReturnMovement(this.getOwner().getEyePosition(), 0.33);
            }
        }
    }

    public void setReturning(boolean returning) {
        this.resetHitEntities();
        this.setNoPhysics(returning);
        this.isReturning = returning;
    }

    public boolean isReturning() {
        return this.isReturning;
    }

    public boolean tryReturn() {
        Entity owner = this.getOwner();

        if (owner instanceof Player player && player.isAlive()) {
            this.applyReturnMovement(player.getEyePosition(), 2.41);
            return true;
        }

        return false;
    }

    @Override
    protected void setInGround(boolean bl) {}

    public void applyReturnMovement(Vec3 returnPos, double strength) {
        Vec3 velocity = this.getDeltaMovement();
        Vec3 towardsPlayer = returnPos.subtract(this.position()).normalize().scale(strength);

        Vec3 newVel = Vec3.ZERO;
        for (Direction.Axis axis : Direction.Axis.values()) {
            double current = velocity.get(axis);
            double expected = towardsPlayer.get(axis);

            newVel = newVel.with(axis, Mth.lerp(Math.clamp(0.08F, 0, 1), current, expected));
        }

        this.setDeltaMovement(newVel);
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        Vec3 vel = this.getDeltaMovement();
        this.setDeltaMovement(FFUtil.reflectVector(vel, hitResult.getDirection()).multiply(0.8, 0.33, 0.8));

        if (this.hitsBeforeReturn++ > 5) {
            this.setReturning(true);
        }

        this.playSound(FFSounds.WREATH_CHAKRAM_HIT, 0.5F, 0.8F + RandomSource.create().nextFloat() * 0.4F);
        if (this.level() instanceof ServerLevel serverLevel) {
            Vec3 parLoc = hitResult.getLocation().relative(hitResult.getDirection(), 0.2);
            serverLevel.sendParticles((ParticleOptions) FFParticles.HOLLY_LEAF, parLoc.x(), parLoc.y(), parLoc.z(),
                    7, 0.07, 0.025, 0.07, 0.5);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        if (entity instanceof LivingEntity livingEntity) {

            // Durability Damage
            ItemStack stack = this.getPickupItem();
            if (stack.nextDamageWillBreak()) {
                this.playSound(SoundEvents.ITEM_BREAK, 0.5F, 1F);
                this.discard();
            } else {
                stack.hurtWithoutBreaking(1, this.getOwner() instanceof Player player ? player : null);
                this.setPickupItemStack(stack);
            }

            // Entity Damage
            if (this.level() instanceof ServerLevel serverLevel) {
                this.addToHitEntities(livingEntity);

                if (this.isRemoved()) {
                    serverLevel.sendParticles((ParticleOptions) FFParticles.HOLLY_LEAF, this.getX(), this.getY(), this.getZ(),
                            14, 0.07, 0.025, 0.07, 2);
                } else if (this.shouldRicochet() && !this.isReturning()) {
                    if (this.hitsBeforeReturn++ > 7) {
                        this.setReturning(true);
                    } else {
                        Vec3 targetPos = this.findRicochetTarget();

                        if (targetPos != null) {
                            Vec3 ricochetVel = targetPos.subtract(this.position());
                            this.setDeltaMovement(ricochetVel.normalize());
                            this.playSound(FFSounds.WREATH_CHAKRAM_RICOCHET, 1F, 0.8F + (this.hitsBeforeReturn / 6F) * 0.4F);
                        }
                    }
                }

                DamageSource source = this.damageSources().arrow(this,
                        (this.getOwner() instanceof LivingEntity owner) ? owner : null);
                float damage = EnchantmentHelper.modifyDamage(
                        serverLevel, stack, livingEntity, source, 3F);

                livingEntity.hurtServer(serverLevel, source, damage);
                EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, entity, source, stack);
            }

        }
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        return super.canHitEntity(entity) && !this.hitEntities.contains(entity.getId()) && entity != this.getOwner();
    }

    public void addToHitEntities(Entity entity) {
        this.hitEntities.add(entity.getId());
    }

    public void resetHitEntities() {
        this.hitEntities.clear();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.008;
    }

    @Override
    public void playerTouch(Player player) {
        if (!this.level().isClientSide() && (this.isReturning() || this.tickCount > 10)) {
            if (player == this.getOwner() && this.tryPickup(player)) {
                player.take(this, 1);
                this.discard();
            }

        }
    }

    @Override
    protected boolean tryPickup(Player player) {
        if (this.pickup == Pickup.ALLOWED) {
            int savedSlot = this.getSavedSlot();
            ItemStack stack = this.getPickupItem();
            if (savedSlot != -1 && player.getInventory().getItem(savedSlot).isEmpty()) {
                player.getInventory().setItem(savedSlot, stack);
                return true;
            }
        }
        return super.tryPickup(player);
    }

    @Override
    public ItemStack getPickupItem() {
        return super.getPickupItem();
    }

    @Override
    public ItemStack getDefaultPickupItem() {
        return FFItems.WREATH_CHAKRAM.getDefaultInstance();
    }

    public void setRicochet(boolean shouldRicochet) {
        this.getEntityData().set(DATA_RICOCHET, shouldRicochet);
    }

    public boolean shouldRicochet() {
        return this.getEntityData().get(DATA_RICOCHET);
    }

    @Nullable
    public Vec3 findRicochetTarget() {
        AABB box = AABB.ofSize(this.position(), 27, 14, 27);
        List<Entity> entities = this.level().getEntities(this, box, EntitySelector.LIVING_ENTITY_STILL_ALIVE
                .and(other -> !this.hitEntities.contains(other.getId()) && !other.equals(this.getOwner()))
                .and(other -> other instanceof LivingEntity living && living.hasLineOfSight(this)));
        if (!entities.isEmpty()) {
            double lastDistance = -1;
            Entity nextEntity = null;

            for (Entity entity : entities) {
                double distance = entity.distanceTo(this);
                if (distance < lastDistance || lastDistance == -1) {
                    nextEntity = entity;
                    lastDistance = distance;
                }
            }

            return nextEntity.getEyePosition();
        }

        return null;
    }

    public boolean isEnchanted() {
        return this.getEntityData().get(DATA_ENCHANTED);
    }

    public void setSavedSlot(int slot) {
        this.getEntityData().set(DATA_SLOT, slot);
    }

    public int getSavedSlot() {
        return this.getEntityData().get(DATA_SLOT);
    }

    public float getSpinRotation(float tickProgress) {
        return Mth.lerp(tickProgress, this.lastSpinRotation, this.spinRotation);
    }
}
