package powercyphe.festive_frenzy.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import powercyphe.festive_frenzy.registry.ModEntities;
import powercyphe.festive_frenzy.registry.ModSounds;

public class BaubleProjectileEntity extends ThrownItemEntity {

    public BaubleProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public BaubleProjectileEntity(World world, LivingEntity owner) {
        super(ModEntities.BAUBLE_PROJECTILE, owner, world);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.onHit(blockHitResult);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        this.onHit(entityHitResult);
    }

    public SoundEvent getHitSound() {
        return ModSounds.BAUBLE_BREAK;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemStack.EMPTY.getItem();
    }

    public void onHit(HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) hitResult).getEntity();
            if (!(entity instanceof AbstractDecorationEntity)) {
                entity.damage(new DamageSource(this.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(DamageTypes.ARROW), this.getOwner()), 3);
            }
        }

        this.playSound(this.getHitSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        if (!this.getWorld().isClient()) {
            ItemStackParticleEffect particle = new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack());
            Vec3d parPos = this.getPos().add(hitResult.getPos().subtract(this.getPos()));
            ((ServerWorld) this.getWorld()).spawnParticles(particle, parPos.getX(), parPos.getY(), parPos.getZ(), Random.create().nextBetween(7, 14), 0, 0, 0, 0.1);
        }
        this.discard();
    }
}
