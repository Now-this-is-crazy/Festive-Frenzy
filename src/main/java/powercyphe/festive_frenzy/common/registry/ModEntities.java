package powercyphe.festive_frenzy.common.registry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import powercyphe.festive_frenzy.FestiveFrenzy;
import powercyphe.festive_frenzy.common.entity.BaubleProjectileEntity;
import powercyphe.festive_frenzy.common.entity.FallingBaubleBlockEntity;
import powercyphe.festive_frenzy.common.entity.FrostflakeProjectileEntity;
import powercyphe.festive_frenzy.common.item.BaubleBlockItem;
import powercyphe.festive_frenzy.common.world.BaubleExplosion;

public class ModEntities {

    public static class TrackedData {
        public static final TrackedDataHandler<BaubleExplosion.ExplosionModification> EXPLOSION_MODIFICATION = TrackedDataHandler.create(PacketCodecs.codec(BaubleExplosion.ExplosionModification.CODEC));

        public static void init() {
            TrackedDataHandlerRegistry.register(EXPLOSION_MODIFICATION);
        }
    }

    public static EntityType<FrostflakeProjectileEntity> FROSTFLAKE_PROJECTILE = register("frostflake", EntityType.Builder.<FrostflakeProjectileEntity>create(
            FrostflakeProjectileEntity::new, SpawnGroup.MISC).allowSpawningInside().dimensions(0.4f, 0.4f));

    public static EntityType<BaubleProjectileEntity> BAUBLE_PROJECTILE = register("bauble", EntityType.Builder.<BaubleProjectileEntity>create(
            BaubleProjectileEntity::new, SpawnGroup.MISC).allowSpawningInside().dimensions(0.5f, 0.5f));

    public static EntityType<FallingBaubleBlockEntity> FALLING_BAUBLE_BLOCK_ENTITY = register("falling_bauble_block_entity", EntityType.Builder.<FallingBaubleBlockEntity>create(
            FallingBaubleBlockEntity::new, SpawnGroup.MISC).allowSpawningInside().dimensions(1f, 1f));

    public static void init() {
    }

    public static EntityType register(String path, EntityType.Builder<?> builder) {
        return Registry.register(Registries.ENTITY_TYPE, FestiveFrenzy.id(path), builder.build(path));
    }
}
