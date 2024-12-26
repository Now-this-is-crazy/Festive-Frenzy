package powercyphe.festive_frenzy.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import powercyphe.festive_frenzy.FestiveFrenzy;
import powercyphe.festive_frenzy.block.PresentBlock;
import powercyphe.festive_frenzy.item.PresentBlockItem;
import powercyphe.festive_frenzy.registry.ModBlocks;
import powercyphe.festive_frenzy.registry.ModItems;

import java.util.function.Predicate;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Inject(method = "dropLoot", at = @At(value = "TAIL"))
	private void festive_frenzy$dropPresents(DamageSource damageSource, boolean causedByPlayer, CallbackInfo ci) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if (causedByPlayer && entity instanceof HostileEntity) {
			if (entity.getRandom().nextInt(100) <= entity.getWorld().getGameRules().getInt(FestiveFrenzy.POUCH_DROP_CHANCE)) {
				World world = entity.getWorld();

				ItemEntity item = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), ModItems.CANDY_POUCH.getDefaultStack());
				entity.getWorld().spawnEntity(item);
			}
		}
	}
}