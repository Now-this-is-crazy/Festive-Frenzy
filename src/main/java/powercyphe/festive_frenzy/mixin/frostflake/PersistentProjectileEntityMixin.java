package powercyphe.festive_frenzy.mixin.frostflake;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.festive_frenzy.common.entity.FrostflakeProjectileEntity;
import powercyphe.festive_frenzy.common.registry.ModDamageTypes;

import java.util.Optional;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin {

    @Shadow private ItemStack stack;

    @Redirect(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setStuckArrowCount(I)V"))
    private void festive_frenzy$arrowFix(LivingEntity instance, int stuckArrowCount) {
        PersistentProjectileEntity projectile = (PersistentProjectileEntity) (Object) this;
        if ((projectile instanceof ArrowEntity)) {
            instance.setStuckArrowCount(stuckArrowCount);
        }
    }

    @ModifyExpressionValue(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSources;arrow(Lnet/minecraft/entity/projectile/PersistentProjectileEntity;Lnet/minecraft/entity/Entity;)Lnet/minecraft/entity/damage/DamageSource;"))
    private DamageSource festive_frenzy$frostflake(DamageSource original) {
        PersistentProjectileEntity projectile = (PersistentProjectileEntity) (Object) this;
        if (projectile instanceof FrostflakeProjectileEntity) {
            return ModDamageTypes.create(projectile.getWorld(), ModDamageTypes.FROSTFLAKE, projectile, projectile.getOwner());
        }
        return original;
    }

    @ModifyExpressionValue(method = "readCustomDataFromNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;fromNbt(Lnet/minecraft/registry/RegistryWrapper$WrapperLookup;Lnet/minecraft/nbt/NbtElement;)Ljava/util/Optional;"))
    private Optional<ItemStack> festive_frenzy$frostflake(Optional<ItemStack> original) {
        if (((Object) this) instanceof FrostflakeProjectileEntity) {
            return Optional.ofNullable(ItemStack.EMPTY);
        }
        return original;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void festive_frenzy$frostflake(NbtCompound nbt, CallbackInfo ci) {
        if (((Object) this) instanceof FrostflakeProjectileEntity) {
            this.stack = Items.BEDROCK.getDefaultStack();
        }
    }
}
