package powercyphe.festive_frenzy.world;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.block.BaubleBlock;
import powercyphe.festive_frenzy.item.BaubleBlockItem;
import powercyphe.festive_frenzy.mixin.accessor.WorldAccessor;
import powercyphe.festive_frenzy.network.BaubleExplosionS2CPacket;
import powercyphe.festive_frenzy.particle.BaubleExplosionEmitterParticleEffect;
import powercyphe.festive_frenzy.particle.BaubleExplosionParticleEffect;
import powercyphe.festive_frenzy.registry.ModParticles;

import java.util.Iterator;
import java.util.List;

public class BaubleExplosion extends Explosion {
    public ItemStack stack;
    public BaubleBlockItem.ExplosionModification explosionModification;

    public BaubleExplosion(World world, @Nullable Entity entity, double x, double y, double z, float power, List<BlockPos> affectedBlocks, ItemStack stack) {
        this(world, entity, x, y, z, power, false, Explosion.DestructionType.DESTROY_WITH_DECAY, affectedBlocks, stack);
    }

    public BaubleExplosion(World world, @Nullable Entity entity, double x, double y, double z, float power, boolean createFire, DestructionType destructionType, List<BlockPos> affectedBlocks, ItemStack stack) {
        this(world, entity, x, y, z, power, createFire, destructionType, stack);
        this.getAffectedBlocks().addAll(affectedBlocks);
    }

    public BaubleExplosion(World world, @Nullable Entity entity, double x, double y, double z, float power, boolean createFire, DestructionType destructionType, ItemStack stack) {
        this(world, entity, (DamageSource)null, (ExplosionBehavior)null, x, y, z, power, createFire, destructionType, stack);
    }

    public BaubleExplosion(World world, @Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionBehavior behavior, double x, double y, double z, float power, boolean createFire, DestructionType destructionType, ItemStack stack) {
        this(world, entity, damageSource, behavior, x, y, z, power, createFire, destructionType, stack, BaubleBlockItem.ExplosionModification.NONE);
    }

    public BaubleExplosion(World world, @Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionBehavior behavior, double x, double y, double z, float power, boolean createFire, DestructionType destructionType, ItemStack stack, BaubleBlockItem.ExplosionModification explosionModification) {
        super(world, entity, damageSource, behavior, x, y, z, power, createFire, destructionType, new BaubleExplosionParticleEffect(stack), new BaubleExplosionEmitterParticleEffect(stack), SoundEvents.ENTITY_GENERIC_EXPLODE);
        this.stack = stack;
        this.explosionModification = explosionModification;
    }


    public static BaubleExplosion create(World world, @Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionBehavior behavior, double x, double y, double z, float power, World.ExplosionSourceType explosionSourceType, ItemStack stack) {
        Explosion.DestructionType var10000;
        switch (explosionSourceType) {
            case NONE:
                var10000 = DestructionType.KEEP;
                break;
            case BLOCK:
                var10000 = ((WorldAccessor) world).festive_frenzy$getDestructionType(GameRules.BLOCK_EXPLOSION_DROP_DECAY);
                break;
            case MOB:
                var10000 = world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? ((WorldAccessor) world).festive_frenzy$getDestructionType(GameRules.MOB_EXPLOSION_DROP_DECAY) : DestructionType.KEEP;
                break;
            case TNT:
                var10000 = ((WorldAccessor) world).festive_frenzy$getDestructionType(GameRules.TNT_EXPLOSION_DROP_DECAY);
                break;
            default:
                throw new IncompatibleClassChangeError();
        }

        BaubleBlockItem.ExplosionModification modification = BaubleBlockItem.ExplosionModification.fromName(stack.getOrCreateNbt().getString(BaubleBlockItem.EXPLOSION_MODIFICATION_KEY));

        boolean hasModification = modification != BaubleBlockItem.ExplosionModification.NONE;

        Explosion.DestructionType destructionType = var10000;
        BaubleExplosion explosion = new BaubleExplosion(world, entity, damageSource, behavior, x, y, z, power, hasModification, destructionType, stack, modification);
        explosion.collectBlocksAndDamageEntities();
        explosion.affectWorld(false);

        if (world instanceof ServerWorld serverWorld) {
            if (!explosion.shouldDestroy()) {
                explosion.clearAffectedBlocks();
            }

            Iterator<ServerPlayerEntity> players = serverWorld.getPlayers().iterator();

            while (players.hasNext()) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)players.next();
                if (serverPlayerEntity.squaredDistanceTo(x, y, z) < 4096.0) {
                    ServerPlayNetworking.send(serverPlayerEntity, new BaubleExplosionS2CPacket(x, y, z, power, explosion.getAffectedBlocks(), (Vec3d)explosion.getAffectedPlayers().get(serverPlayerEntity), stack, modification));
                }
            }
        }
        return explosion;
    }
}
