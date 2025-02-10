package powercyphe.festive_frenzy.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import powercyphe.festive_frenzy.FestiveFrenzy;
import powercyphe.festive_frenzy.block.entity.PresentBlockEntity;

public class ModBlockEntities {

    public static BlockEntityType<PresentBlockEntity> PRESENT_BLOCK_ENTITY = registerBlockEntity("colored", PresentBlockEntity::new,
            ModBlocks.WHITE_PRESENT, ModBlocks.LIGHT_GRAY_PRESENT, ModBlocks.GRAY_PRESENT, ModBlocks.BLACK_PRESENT, ModBlocks.BROWN_PRESENT,
            ModBlocks.RED_PRESENT, ModBlocks.ORANGE_PRESENT, ModBlocks.YELLOW_PRESENT, ModBlocks.LIME_PRESENT, ModBlocks.GREEN_PRESENT,
            ModBlocks.CYAN_PRESENT, ModBlocks.LIGHT_BLUE_PRESENT, ModBlocks.BLUE_PRESENT, ModBlocks.PURPLE_PRESENT, ModBlocks.MAGENTA_PRESENT,
            ModBlocks.PINK_PRESENT,
            ModBlocks.KELP_PRESENT, ModBlocks.FOLLY_PRESENT, ModBlocks.GOLDEN_PRESENT, ModBlocks.SAND_PRESENT, ModBlocks.BLOOD_PRESENT,
            ModBlocks.SCULK_PRESENT);

    public static void init() {}

    public static <T extends BlockEntity> BlockEntityType registerBlockEntity(String path, FabricBlockEntityTypeBuilder.Factory<? extends T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, FestiveFrenzy.id(path),
                FabricBlockEntityTypeBuilder.create(factory, blocks).build(null));
    }
}


