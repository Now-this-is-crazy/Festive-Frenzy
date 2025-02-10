package powercyphe.festive_frenzy.mixin.snowglobe;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import powercyphe.festive_frenzy.registry.ModComponents;
import powercyphe.festive_frenzy.util.BiomeWorldAccessor;

@Mixin(Biome.class)
public class BiomeMixin implements BiomeWorldAccessor {

    @Unique
    private World world;

    /*
    @ModifyReturnValue(method = "computeTemperature", at = @At("RETURN"))
    private float festive_frenzy$snowGlobe(float original, BlockPos blockPos) {
        if (this.world != null) {
            boolean bl = false;
            for (BlockPos bP : ModComponents.SNOWGLOBE.get(world).snowGlobeList) {
                if (bP.withY(blockPos.getY()).isWithinDistance(blockPos.toCenterPos(), 60)) {
                    bl = true;
                    break;
                }
            }
            if (bl) {
                return 0;
            }
        }
        return original;
    }
     */

    @Override
    public void festive_frenzy$setWorld(World world) {
        this.world = world;
    }

    @Override
    public World festive_frenzy$getWorld() {
        return this.world;
    }
}
