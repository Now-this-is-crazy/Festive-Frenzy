package powercyphe.festive_frenzy.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.Registries;
import net.minecraft.stat.StatHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.festive_frenzy.common.registry.ModBlocks;
import powercyphe.festive_frenzy.common.util.PresentTypeAccessor;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements PresentTypeAccessor {
    @Unique
    private String present;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void festive_frenzy$present(MinecraftClient client, ClientWorld world, ClientPlayNetworkHandler networkHandler, StatHandler stats, ClientRecipeBook recipeBook, boolean lastSneaking, boolean lastSprinting, CallbackInfo ci) {
        this.present = Registries.BLOCK.getId(ModBlocks.WHITE_PRESENT).getPath();
    }

    @Override
    public String festive_frenzy$getPresent() {
        return this.present;
    }

    @Override
    public void festive_frenzy$setPresent(String id) {
        this.present = id;
    }
}
