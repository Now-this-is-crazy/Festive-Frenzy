package powercyphe.festive_frenzy.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import powercyphe.festive_frenzy.registry.ModEntities;
import powercyphe.festive_frenzy.registry.ModParticles;

import java.util.Objects;

public class FrostflakeProjectileEntity extends PersistentProjectileEntity {
    public FrostflakeProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world, ItemStack.EMPTY);
    }

    public FrostflakeProjectileEntity(World world, LivingEntity owner) {
        super(ModEntities.FROSTFLAKE_PROJECTILE, owner, world, ItemStack.EMPTY);
        this.setOwner(owner);
    }

    @Override
    public void tick() {
        if (this.inGround || this.age >= 110) {
            this.discard();
        }

        if (this.getWorld().isClient()) this.getWorld().addParticle(ModParticles.FROSTFLAKE_TRAIL, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        super.tick();
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
        if (!(entity instanceof AbstractDecorationEntity)) entity.setFrozenTicks(Random.create().nextBetween(270, 300));
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
    public double getDamage() {
        return 1.0;
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }
}
