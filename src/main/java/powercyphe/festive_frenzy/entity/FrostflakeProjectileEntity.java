package powercyphe.festive_frenzy.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import powercyphe.festive_frenzy.datagen.ModDamageTypeTagGenerator;
import powercyphe.festive_frenzy.registry.ModBlocks;
import powercyphe.festive_frenzy.registry.ModDamageTypes;
import powercyphe.festive_frenzy.registry.ModEntities;
import powercyphe.festive_frenzy.registry.ModParticles;

import java.util.Objects;

public class FrostflakeProjectileEntity extends PersistentProjectileEntity {
    public FrostflakeProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world, ItemStack.EMPTY);
        this.setDamage(0.1f);
        this.pickupType = PickupPermission.DISALLOWED;
    }

    public FrostflakeProjectileEntity(World world, LivingEntity owner) {
        super(ModEntities.FROSTFLAKE_PROJECTILE, owner, world, ItemStack.EMPTY);
        this.setOwner(owner);
        this.setDamage(0.05f);
        this.pickupType = PickupPermission.DISALLOWED;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isOnFire() || this.inGround || this.age >= 110) {
            this.discard();
            return;
        }
        if (this.isTouchingWater()) {
            this.getWorld().setBlockState(this.getBlockPos(), Blocks.FROSTED_ICE.getDefaultState());
            this.discard();
            return;
        }

        if (this.getWorld().isClient()) {
            this.getWorld().addParticle(ModParticles.FROSTFLAKE_TRAIL, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        } else {
            if (this.age % 3 == 0) this.setDamage(MathHelper.clamp(this.getDamage() * 1.1, 0, 1f));
        }
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.BLOCK_GLASS_BREAK;
    }

    @Override
    protected SoundEvent getHighSpeedSplashSound() {
        return SoundEvents.INTENTIONALLY_EMPTY;
    }

    @Override
    protected SoundEvent getSplashSound() {
        return SoundEvents.INTENTIONALLY_EMPTY;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (!(entity instanceof AbstractDecorationEntity)) {
            entity.setFrozenTicks(Random.create().nextBetween(350, 400));
        }
        this.discard();
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (hitResult.getType() != HitResult.Type.MISS) {
            double x = this.getX();
            double y = this.getY();
            double z = this.getZ();

            if (hitResult.getType() == HitResult.Type.ENTITY) {
                Entity entity = ((EntityHitResult) hitResult).getEntity();
                x = entity.getX();
                z = entity.getZ();
            }

            if (this.getWorld().isClient()) {
                for (int i = 0; i < this.random.nextInt(5); i++)
                    this.getWorld().addParticle(ModParticles.FROSTFLAKE, x, y, z, 0, 0, 0);
            }
        }
        super.onCollision(hitResult);
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }
}
