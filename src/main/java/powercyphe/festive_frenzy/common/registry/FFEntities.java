package powercyphe.festive_frenzy.common.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.entity.FrostflakeProjectileEntity;
import powercyphe.festive_frenzy.common.entity.ThrownBaubleProjectileEntity;

public class FFEntities {

    public static final EntityType<FrostflakeProjectileEntity> FROSTFLAKE_PROJECTILE = register("frostflake_projectile",
            EntityType.Builder.<FrostflakeProjectileEntity>of(FrostflakeProjectileEntity::new, MobCategory.MISC)
                    .sized(0.4F, 0.4F).eyeHeight(0.2F));

    public static final EntityType<ThrownBaubleProjectileEntity> THROWN_BAUBLE_PROJECTILE = register("thrown_bauble_projectile",
            EntityType.Builder.<ThrownBaubleProjectileEntity>of(ThrownBaubleProjectileEntity::new, MobCategory.MISC)
                    .sized(0.4F, 0.4F).eyeHeight(0.2F));

    public static void init() {}

    public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> entityKey = ResourceKey.create(Registries.ENTITY_TYPE, FestiveFrenzy.id(id));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, entityKey, builder.build(entityKey));
    }
}
