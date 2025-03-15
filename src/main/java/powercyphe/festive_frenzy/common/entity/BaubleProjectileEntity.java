package powercyphe.festive_frenzy.common.entity;

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
import powercyphe.festive_frenzy.common.item.BaubleBlockItem;
import powercyphe.festive_frenzy.common.registry.ModBlocks;
import powercyphe.festive_frenzy.common.registry.ModEntities;
import powercyphe.festive_frenzy.common.registry.ModItems;
import powercyphe.festive_frenzy.common.registry.ModSounds;
import powercyphe.festive_frenzy.common.world.BaubleExplosion;

public class BaubleProjectileEntity extends ThrownItemEntity {
    private static final TrackedData<Integer> EXPLOSION_STRENGTH = DataTracker.registerData(BaubleProjectileEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<BaubleExplosion.ExplosionModification> EXPLOSION_MODIFICATION = DataTracker.registerData(BaubleProjectileEntity.class, ModEntities.TrackedData.EXPLOSION_MODIFICATION);
    private static final TrackedData<Vector3f> ROTATION_RANDOM = DataTracker.registerData(BaubleProjectileEntity.class, TrackedDataHandlerRegistry.VECTOR3F);

    public BaubleProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);

    }

    public BaubleProjectileEntity(World world, LivingEntity owner) {
        super(ModEntities.BAUBLE_PROJECTILE, owner, world);
    }

    public BaubleProjectileEntity(World world, LivingEntity owner, ItemStack stack) {
        this(world, owner);
        this.setExplosionStrength(stack.getOrDefault(ModItems.Components.EXPLOSION_STRENGTH, 0));
        this.setExplosionModification(stack.getOrDefault(ModItems.Components.EXPLOSION_MODIFICATION, BaubleExplosion.ExplosionModification.NONE));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(EXPLOSION_STRENGTH, 0);
        builder.add(EXPLOSION_MODIFICATION, BaubleExplosion.ExplosionModification.NONE);
        builder.add(ROTATION_RANDOM, new Vector3f(
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

    public void setExplosionModification(BaubleExplosion.ExplosionModification modification) {
        this.getDataTracker().set(EXPLOSION_MODIFICATION, modification);

    }

    public void setExplosionModification(String name) {
        BaubleExplosion.ExplosionModification modification = BaubleExplosion.ExplosionModification.fromName(name);
        if (modification != null) {
            this.setExplosionModification(modification);
        }

    }

    public BaubleExplosion.ExplosionModification getExplosionModification() {
        return this.getDataTracker().get(EXPLOSION_MODIFICATION);
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
        this.setExplosionStrength(nbt.getInt(ModItems.Components.EXPLOSION_STRENGTH_KEY));
        this.setExplosionModification(nbt.getString(ModItems.Components.EXPLOSION_MODIFICATION_KEY));
        this.setRotationRandom(new Vector3f(
                nbt.getFloat("baubleRotationX"),
                nbt.getFloat("baubleRotationY"),
                nbt.getFloat("baubleRotationZ")
        ));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt(ModItems.Components.EXPLOSION_STRENGTH_KEY, this.getExplosionStrength());
        nbt.putString(ModItems.Components.EXPLOSION_MODIFICATION_KEY, this.getExplosionModification().getName());
        nbt.putFloat("baubleRotationX", this.getRotationRandom().x);
        nbt.putFloat("baubleRotationY", this.getRotationRandom().y);
        nbt.putFloat("baubleRotationZ", this.getRotationRandom().z);
    }
}
