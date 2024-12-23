package powercyphe.festive_frenzy.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import powercyphe.festive_frenzy.FestiveFrenzy;
import powercyphe.festive_frenzy.entity.BaubleProjectileEntity;
import powercyphe.festive_frenzy.entity.FrostflakeProjectileEntity;

public class ModEntities {
    public static EntityType<FrostflakeProjectileEntity> FROSTFLAKE_PROJECTILE = register("frostflake", EntityType.Builder.<FrostflakeProjectileEntity>create(
            FrostflakeProjectileEntity::new, SpawnGroup.MISC).allowSpawningInside().setDimensions(0.5f, 0.5f));

    public static EntityType<BaubleProjectileEntity> BAUBLE_PROJECTILE = register("bauble", EntityType.Builder.<BaubleProjectileEntity>create(
            BaubleProjectileEntity::new, SpawnGroup.MISC).allowSpawningInside().setDimensions(1f, 1f));

    public static void init() {}

    public static EntityType register(String path, EntityType.Builder<?> builder) {
        return Registry.register(Registries.ENTITY_TYPE, FestiveFrenzy.id(path), builder.build(path));
    }
}
