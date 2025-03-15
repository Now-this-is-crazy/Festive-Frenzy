package powercyphe.festive_frenzy.common.world;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.IceBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.particle.BaubleExplosionEmitterParticleEffect;
import powercyphe.festive_frenzy.common.particle.BaubleExplosionParticleEffect;
import powercyphe.festive_frenzy.common.registry.ModItems;
import powercyphe.festive_frenzy.mixin.accessor.WorldAccessor;
import powercyphe.festive_frenzy.common.payload.BaubleExplosionPayload;

import java.util.Iterator;
import java.util.List;

public class BaubleExplosion extends Explosion {
    public ItemStack stack;
    public BaubleExplosion.ExplosionModification explosionModification;

    public BaubleExplosion(World world, @Nullable Entity entity, double x, double y, double z, float power, List<BlockPos> affectedBlocks, ItemStack stack) {
        this(world, entity, x, y, z, power, Explosion.DestructionType.DESTROY_WITH_DECAY, affectedBlocks, stack);
    }

    public BaubleExplosion(World world, @Nullable Entity entity, double x, double y, double z, float power, DestructionType destructionType, List<BlockPos> affectedBlocks, ItemStack stack) {
        this(world, entity, x, y, z, power, destructionType, stack);
        this.getAffectedBlocks().addAll(affectedBlocks);
    }

    public BaubleExplosion(World world, @Nullable Entity entity, double x, double y, double z, float power, DestructionType destructionType, ItemStack stack) {
        this(world, entity, (DamageSource)null, (ExplosionBehavior)null, x, y, z, power, destructionType, stack);
    }

    public BaubleExplosion(World world, @Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionBehavior behavior, double x, double y, double z, float power, DestructionType destructionType, ItemStack stack) {
        this(world, entity, damageSource, behavior, x, y, z, power, destructionType, stack, BaubleExplosion.ExplosionModification.NONE);
    }

    public BaubleExplosion(World world, @Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionBehavior behavior, double x, double y, double z, float power, DestructionType destructionType, ItemStack stack, BaubleExplosion.ExplosionModification explosionModification) {
        super(world, entity, damageSource, behavior, x, y, z, power, false, destructionType, new BaubleExplosionParticleEffect(stack), new BaubleExplosionEmitterParticleEffect(stack), SoundEvents.ENTITY_GENERIC_EXPLODE);
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

        BaubleExplosion.ExplosionModification modification = stack.getOrDefault(ModItems.Components.EXPLOSION_MODIFICATION, BaubleExplosion.ExplosionModification.NONE);

        Explosion.DestructionType destructionType = var10000;
        BaubleExplosion explosion = new BaubleExplosion(world, entity, damageSource, behavior, x, y, z, power, destructionType, stack, modification);
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
                    ServerPlayNetworking.send(serverPlayerEntity, new BaubleExplosionPayload(new Vec3d(x, y, z), power, explosion.getAffectedBlocks(), (Vec3d)explosion.getAffectedPlayers().get(serverPlayerEntity), stack));
                }
            }
        }
        return explosion;
    }

    public enum ExplosionModification implements StringIdentifiable {
        NONE("none", 0xffffff, Ingredient.EMPTY, (pos, entity, explosionStrength) -> {}, ((pos, world, blocks, explosionStrength) -> {})),
        FIRE("fire", 0xcf8731, Ingredient.ofItems(Items.FIRE_CHARGE),
                (pos, entity, explosionStrength) -> {
                    double dis = Math.max(entity.getPos().distanceTo(pos), 1);
                    int ticks = (int) ((120 + (30 * explosionStrength)) / dis);
                    entity.setFireTicks(ticks);
                },
                ((pos, world, blockPos, explosionStrength) -> {
                        for (Direction direction : Direction.values()) {
                            if (AbstractFireBlock.canPlaceAt(world, blockPos, direction) &&
                                    Random.create().nextInt((int) Math.abs(blockPos.getSquaredDistance(pos)) + 1) == 0) {
                                world.setBlockState(blockPos, AbstractFireBlock.getState(world, blockPos));
                            }
                        }
                })),
        ICE("ice", 0xaefffa, Ingredient.ofItems(Items.BLUE_ICE),
                (pos, entity, explosionStrength) -> {
                    double dis = Math.max(entity.getPos().distanceTo(pos), 1);
                    int ticks = (int) ((360 + (60 * explosionStrength)) / dis);
                    entity.setFrozenTicks(ticks);
                },
                ((pos, world, blockPos, explosionStrength) -> {
                    if (world.isAir(blockPos) && Random.create().nextInt((int) Math.abs(blockPos.getSquaredDistance(pos)) + 1) == 0) {
                        world.setBlockState(blockPos, Blocks.ICE.getDefaultState());
                    }
                }))
        ;

        private final String name;
        private final int color;
        private final Ingredient ingredient;
        private final EntityHandler entityHandler;
        private final BlockHandler blockHandler;

        public static final Codec<ExplosionModification> CODEC = StringIdentifiable.createBasicCodec(ExplosionModification::values);
        public static final PacketCodec<ByteBuf, ExplosionModification> PACKET_CODEC = PacketCodecs.codec(CODEC);

        ExplosionModification(String name, int color, Ingredient item, EntityHandler entityHandler, BlockHandler blockHandler) {
            this.name = name;
            this.color = color;
            this.ingredient = item;
            this.entityHandler = entityHandler;
            this.blockHandler = blockHandler;
        }

        public String getName() {
            return this.name;
        }

        public int getColor() {
            return this.color;
        }

        public Ingredient getIngredient() {
            return this.ingredient;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public String asString() {
            return this.name;
        }


        public static ExplosionModification fromName(@NotNull String name) {
            for (ExplosionModification modification : ExplosionModification.values()) {
                if (modification.getName().equals(name)) {
                    return modification;
                }
            }
            return ExplosionModification.NONE;
        }

        public static ExplosionModification fromItem(@NotNull ItemConvertible item) {
            for (ExplosionModification modification : ExplosionModification.values()) {
                if (!modification.getIngredient().isEmpty() && modification.getIngredient().test(item.asItem().getDefaultStack())) {
                    return modification;
                }
            }
            return null;
        }

        public static ExplosionModification fromItemStack(ItemStack stack) {
            return fromItem(stack.getItem());
        }

        public void affectEntity(Vec3d pos, Entity entity, float explosionStrength) {
            entityHandler.affectEntity(pos, entity, explosionStrength);
        }

        public void affectBlock(Vec3d pos, World world, BlockPos blockPos, float explosionStrength) {
            blockHandler.affectBlock(pos, world, blockPos, explosionStrength);
        }

        public interface EntityHandler {
            void affectEntity(Vec3d pos, Entity entity, float explosionStrength);
        }

        public interface BlockHandler {
            void affectBlock(Vec3d pos, World world, BlockPos blockPos, float explosionStrength);
        }
    }
}
