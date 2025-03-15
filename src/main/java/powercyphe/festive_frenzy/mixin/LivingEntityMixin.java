package powercyphe.festive_frenzy.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.festive_frenzy.FestiveFrenzy;
import powercyphe.festive_frenzy.common.registry.ModItems;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Inject(method = "dropLoot", at = @At(value = "TAIL"))
	private void festive_frenzy$dropPresents(DamageSource damageSource, boolean causedByPlayer, CallbackInfo ci) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if (causedByPlayer && entity instanceof HostileEntity) {
			int pouchDropChance = entity.getWorld().getGameRules().getInt(FestiveFrenzy.POUCH_DROP_CHANCE);
			if (pouchDropChance != 0 && entity.getRandom().nextInt(100) <= pouchDropChance) {
				World world = entity.getWorld();

				ItemEntity item = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), ModItems.CANDY_POUCH.getDefaultStack());
				entity.getWorld().spawnEntity(item);
			}
		}
	}
}