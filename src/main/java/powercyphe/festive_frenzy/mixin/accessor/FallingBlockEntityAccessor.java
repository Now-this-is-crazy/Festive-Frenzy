package powercyphe.festive_frenzy.mixin.accessor;

import net.minecraft.block.BlockState;
import net.minecraft.entity.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FallingBlockEntity.class)
public interface FallingBlockEntityAccessor {

    @Accessor("block")
    void setBlock(BlockState state);

    @Accessor
    BlockState getBlock();
}
