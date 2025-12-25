package powercyphe.festive_frenzy.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import powercyphe.festive_frenzy.common.block.BaubleBlock;
import powercyphe.festive_frenzy.common.item.BaubleItem;
import powercyphe.festive_frenzy.common.item.component.ExplosiveBaubleComponent;
import powercyphe.festive_frenzy.common.registry.*;
import powercyphe.festive_frenzy.common.util.VelocityBasedRotationImpl;
import powercyphe.festive_frenzy.common.world.BaubleExplosion;

public class ThrownBaubleProjectileEntity extends ThrowableItemProjectile implements VelocityBasedRotationImpl {
    private static final String IS_GLOWING_KEY = "isGlowing";
    private static final EntityDataAccessor<Boolean> DATA_IS_GLOWING = SynchedEntityData.defineId(
            ThrownBaubleProjectileEntity.class, EntityDataSerializers.BOOLEAN);

    private static final String SHOULD_DROP_ITEM_KEY = "shouldDropItem";
    private boolean shouldDropItem = true;

    private final Vec3 rotationMultipliers = new Vec3(
            (RandomSource.create().nextFloat() * (RandomSource.create().nextBoolean() ? 1F : -1F) * 0.19) + 0.07,
            (RandomSource.create().nextFloat() * (RandomSource.create().nextBoolean() ? 1F : -1F) * 0.19) + 0.07,
            (RandomSource.create().nextFloat() * (RandomSource.create().nextBoolean() ? 1F : -1F) * 0.19) + 0.07
    );
    private Vec3 lastRotation = Vec3.ZERO;
    private Vec3 rotation = Vec3.ZERO;

    public ThrownBaubleProjectileEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownBaubleProjectileEntity(Level level, double x, double y, double z, ItemStack stack) {
        super(FFEntities.THROWN_BAUBLE_PROJECTILE, x, y, z, level, stack);
    }

    public ThrownBaubleProjectileEntity(Level level, LivingEntity owner, ItemStack baubleStack) {
        super(FFEntities.THROWN_BAUBLE_PROJECTILE, owner, level, baubleStack);
        this.shouldDropItem = !(owner instanceof Player player) || !player.isCreative();
    }

    public static void fromBlock(Level level, BlockState state, BlockPos blockPos) {
        if (!level.isClientSide() && state.getBlock() instanceof BaubleBlock block) {
            Vec3 pos = blockPos.getCenter();

            ThrownBaubleProjectileEntity bauble = new ThrownBaubleProjectileEntity(level, pos.x(), pos.y() - 0.25, pos.z(), block.getBaubleStack(state));
            bauble.setGlowing(state.getValue(BaubleBlock.IS_GLOWING));
            level.addFreshEntity(bauble);
        }

    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_IS_GLOWING, false);
        super.defineSynchedData(builder);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setGlowing(compoundTag.getBoolean(IS_GLOWING_KEY));
        this.shouldDropItem = compoundTag.getBoolean(SHOULD_DROP_ITEM_KEY);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean(IS_GLOWING_KEY, this.isGlowing());
        compoundTag.putBoolean(SHOULD_DROP_ITEM_KEY, this.shouldDropItem);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.updateRotation(this.getDeltaMovement());
        }
    }

    @Override
    public Item getDefaultItem() {
        return FFBlocks.WHITE_BAUBLE.asItem();
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.hurt(FFDamageTypes.createSource(this.registryAccess(), FFDamageTypes.BAUBLE, this, this.getOwner()), 2F);
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);

        if (this.level() instanceof ServerLevel serverLevel) {
            if (this.getExplosionPower() > 0) {
                BaubleExplosion.create(serverLevel, this);
            } else if (this.shouldDropItem()) {
                this.spawnAtLocation(serverLevel, this.getItem());
            }

            this.playSound(FFSounds.BAUBLE_BREAK);
            serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY() + this.getEyeHeight(), this.getZ(),
                    RandomSource.create().nextInt(14, 30), 0, 0, 0, 0.1);
        }
        this.discard();
    }

    public boolean shouldDropItem() {
        return this.shouldDropItem;
    }

    public void setGlowing(boolean isGlowing) {
        this.getEntityData().set(DATA_IS_GLOWING, isGlowing);
    }

    public boolean isGlowing() {
        return this.getEntityData().get(DATA_IS_GLOWING);
    }

    public int getExplosionColor() {
        ItemStack stack = this.getItem();
        if (stack.getItem() instanceof BaubleItem baubleItem && baubleItem.getBlock() instanceof BaubleBlock block) {
            return block.getExplosionColor();
        }

        return -1;
    }

    public int getExplosionPower() {
        ItemStack stack = this.getItem();
        if (!stack.isEmpty()) {
            ExplosiveBaubleComponent component = ExplosiveBaubleComponent.get(stack);
            return component.explosionPower();
        }

        return 0;
    }

    public BaubleExplosion.ExplosionModification getExplosionModification() {
        ItemStack stack = this.getItem();
        if (!stack.isEmpty()) {
            ExplosiveBaubleComponent component = ExplosiveBaubleComponent.get(stack);
            return component.explosionModification();
        }

        return BaubleExplosion.ExplosionModification.NONE;
    }

    @Override
    public Vec3 getRandomRotationMultipliers() {
        return this.rotationMultipliers;
    }

    @Override
    public void setLastRotation(Vec3 lastRotation) {
        this.lastRotation = lastRotation;
    }

    @Override
    public Vec3 getLastRotation() {
        return this.lastRotation;
    }

    @Override
    public void setRotation(Vec3 rotation) {
        this.rotation = rotation;
    }

    @Override
    public Vec3 getRotation() {
        return this.rotation;
    }
}
