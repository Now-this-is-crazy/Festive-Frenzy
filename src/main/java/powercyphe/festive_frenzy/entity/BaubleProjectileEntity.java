package powercyphe.festive_frenzy.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.joml.Vector3f;
import powercyphe.festive_frenzy.item.BaubleBlockItem;
import powercyphe.festive_frenzy.registry.ModBlocks;
import powercyphe.festive_frenzy.registry.ModEntities;
import powercyphe.festive_frenzy.registry.ModSounds;
import powercyphe.festive_frenzy.world.BaubleExplosion;

public class BaubleProjectileEntity extends ThrownItemEntity {
    private static final TrackedData<Integer> EXPLOSION_STRENGTH = DataTracker.registerData(BaubleProjectileEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<String> EXPLOSION_MODIFICATION = DataTracker.registerData(BaubleProjectileEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Vector3f> ROTATION_RANDOM = DataTracker.registerData(BaubleProjectileEntity.class, TrackedDataHandlerRegistry.VECTOR3F);

    public BaubleProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);

    }

    public BaubleProjectileEntity(World world, LivingEntity owner) {
        super(ModEntities.BAUBLE_PROJECTILE, owner, world);
    }

    public BaubleProjectileEntity(World world, LivingEntity owner, ItemStack stack) {
        this(world, owner);
        if (stack.getOrCreateNbt().contains(BaubleBlockItem.EXPLOSION_STRENGTH_KEY)) {
            this.setExplosionStrength(stack.getOrCreateNbt().getInt(BaubleBlockItem.EXPLOSION_STRENGTH_KEY));
        }
        if (stack.getOrCreateNbt().contains(BaubleBlockItem.EXPLOSION_MODIFICATION_KEY)) {
            this.setExplosionModification(stack.getOrCreateNbt().getString(BaubleBlockItem.EXPLOSION_MODIFICATION_KEY));
        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(EXPLOSION_STRENGTH, 0);
        this.getDataTracker().startTracking(EXPLOSION_MODIFICATION, BaubleBlockItem.ExplosionModification.NONE.getName());
        this.getDataTracker().startTracking(ROTATION_RANDOM, new Vector3f(
                Random.create().nextBoolean() ? Random.create().nextFloat() * 8f : Random.create().nextFloat() * -8f,
                Random.create().nextBoolean() ? Random.create().nextFloat() * 8f : Random.create().nextFloat() * -8f,
                Random.create().nextBoolean() ? Random.create().nextFloat() * 8f : Random.create().nextFloat() * -8f
        ));
    }

    public void setExplosionStrength(int explosionStrength) {
        this.getDataTracker().set(EXPLOSION_STRENGTH, explosionStrength);

    }

    public int getExplosionStrength() {
        return this.getDataTracker().get(EXPLOSION_STRENGTH);
    }

    public void setExplosionModification(BaubleBlockItem.ExplosionModification modification) {
        this.getDataTracker().set(EXPLOSION_MODIFICATION, modification.getName());

    }

    public void setExplosionModification(String name) {
        BaubleBlockItem.ExplosionModification modification = BaubleBlockItem.ExplosionModification.fromName(name);
        if (modification != null) {
            this.setExplosionModification(modification);
        }

    }

    public BaubleBlockItem.ExplosionModification getExplosionModification() {
        return BaubleBlockItem.ExplosionModification.fromName(this.getDataTracker().get(EXPLOSION_MODIFICATION));
    }

    public void setRotationRandom(Vector3f vec3f) {
        this.getDataTracker().set(ROTATION_RANDOM, vec3f);

    }

    public Vector3f getRotationRandom() {
        return this.getDataTracker().get(ROTATION_RANDOM);
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
        return ModBlocks.WHITE_BAUBLE.asItem();
    }

    public void onHit(HitResult hitResult) {
        World world = this.getWorld();
        Vec3d impactPos = this.getPos().add(hitResult.getPos().subtract(this.getPos()));
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) hitResult).getEntity();
            entity.damage(new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(DamageTypes.ARROW), this.getOwner()), 3);
        }

        this.playSound(this.getHitSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        if (!world.isClient()) {
            if (this.getExplosionStrength() > 0) {
                BaubleExplosion.create(world,this, this.getDamageSources().explosion(this, this.getOwner()), null, impactPos.getX(), impactPos.getY(), impactPos.getZ(), this.getExplosionStrength() / 1.5f, World.ExplosionSourceType.BLOCK, this.getStack());
            }
            ItemStackParticleEffect particle = new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack());
            ((ServerWorld) world).spawnParticles(particle, impactPos.getX(), impactPos.getY(), impactPos.getZ(), Random.create().nextBetween(7, 14), 0, 0, 0, 0.1);
        }
        this.discard();
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setExplosionStrength(nbt.getInt(BaubleBlockItem.EXPLOSION_STRENGTH_KEY));
        this.setExplosionModification(nbt.getString(BaubleBlockItem.EXPLOSION_MODIFICATION_KEY));
        this.setRotationRandom(new Vector3f(
                nbt.getFloat("baubleRotationX"),
                nbt.getFloat("baubleRotationY"),
                nbt.getFloat("baubleRotationZ")
        ));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt(BaubleBlockItem.EXPLOSION_STRENGTH_KEY, this.getExplosionStrength());
        nbt.putString(BaubleBlockItem.EXPLOSION_MODIFICATION_KEY, this.getExplosionModification().getName());
        nbt.putFloat("baubleRotationX", this.getRotationRandom().x);
        nbt.putFloat("baubleRotationY", this.getRotationRandom().y);
        nbt.putFloat("baubleRotationZ", this.getRotationRandom().z);
    }
}
