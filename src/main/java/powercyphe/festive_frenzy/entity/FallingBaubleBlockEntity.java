package powercyphe.festive_frenzy.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import powercyphe.festive_frenzy.item.BaubleBlockItem;
import powercyphe.festive_frenzy.mixin.accessor.FallingBlockEntityAccessor;
import powercyphe.festive_frenzy.registry.ModEntities;
import powercyphe.festive_frenzy.registry.ModSounds;
import powercyphe.festive_frenzy.world.BaubleExplosion;

public class FallingBaubleBlockEntity extends FallingBlockEntity {
    public int explosionStrength;
    public BaubleBlockItem.ExplosionModification explosionModification;

    public FallingBaubleBlockEntity(EntityType<? extends FallingBlockEntity> entityType, World world) {
        super(entityType, world);
    }

    public FallingBaubleBlockEntity(World world, double x, double y, double z, BlockState state) {
        super(ModEntities.FALLING_BAUBLE_BLOCK_ENTITY, world);
        ((FallingBlockEntityAccessor) this).setBlock(state);
        this.intersectionChecked = true;
        this.setPosition(x, y, z);
        this.setVelocity(Vec3d.ZERO);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
        this.setFallingBlockPos(this.getBlockPos());

    }

    public FallingBaubleBlockEntity(World world, double x, double y, double z, BlockState state, int explosionStrength, BaubleBlockItem.ExplosionModification explosionModification) {
        this(world, x, y,  z, state);
        this.explosionStrength = explosionStrength;
        this.explosionModification = explosionModification;
        this.dropItem = !(explosionStrength > 0);
    }

    @Override
    public void onLanding() {
        World world = this.getWorld();
        this.playSound(ModSounds.BAUBLE_BREAK, 1.0f, 1.0f);
        if (!world.isClient()) {
            ItemStack stack = this.getBlockState().getBlock().asItem().getDefaultStack();
            if (this.explosionStrength > 0) {
                stack.getOrCreateNbt().putString(BaubleBlockItem.EXPLOSION_MODIFICATION_KEY, this.explosionModification.getName());
                BaubleExplosion.create(world, this, this.getDamageSources().explosion(this, null), null, this.getX(), this.getY(), this.getZ(), this.explosionStrength / 1.5f, World.ExplosionSourceType.BLOCK, stack);
            }

            ItemStackParticleEffect particle = new ItemStackParticleEffect(ParticleTypes.ITEM, stack);
            ((ServerWorld) world).spawnParticles(particle, this.getX(), this.getY(), this.getZ(), Random.create().nextBetween(7, 14), 0, 0, 0, 0.1);
        }
        super.onLanding();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        if (nbt.contains(BaubleBlockItem.EXPLOSION_STRENGTH_KEY)) {
            this.explosionStrength = nbt.getInt(BaubleBlockItem.EXPLOSION_STRENGTH_KEY);
        }
        this.explosionModification = BaubleBlockItem.ExplosionModification.fromName(nbt.getString(BaubleBlockItem.EXPLOSION_MODIFICATION_KEY));
        super.readNbt(nbt);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        if (this.explosionStrength > 0) {
            nbt.putInt(BaubleBlockItem.EXPLOSION_STRENGTH_KEY, this.explosionStrength);
        }
        nbt.putString(BaubleBlockItem.EXPLOSION_MODIFICATION_KEY, this.explosionModification.getName());
        return super.writeNbt(nbt);
    }
}
