package powercyphe.festive_frenzy.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
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
			if (entity.getRandom().nextInt(100) <= entity.getWorld().getGameRules().getInt(FestiveFrenzy.PRESENT_DROP_CHANCE)) {
				Identifier presentLootTable = FestiveFrenzy.id("mob_present");
				LootTable lootTable = entity.getWorld().getServer().getLootManager().getLootTable(presentLootTable);
				LootContextParameterSet.Builder builder = (new LootContextParameterSet.Builder((ServerWorld) entity.getWorld())).add(LootContextParameters.THIS_ENTITY, entity).add(LootContextParameters.ORIGIN, entity.getPos()).add(LootContextParameters.DAMAGE_SOURCE, damageSource).addOptional(LootContextParameters.KILLER_ENTITY, damageSource.getAttacker()).addOptional(LootContextParameters.DIRECT_KILLER_ENTITY, damageSource.getSource());

				SimpleInventory inventory = new SimpleInventory(PresentBlockItem.MAX_STORAGE);
				lootTable.supplyInventory(inventory, builder.build(LootContextTypes.ENTITY), entity.getLootTableSeed());
				ItemStack present = ModBlocks.RED_PRESENT.asItem().getDefaultStack();

				SimpleInventory compressedInv = new SimpleInventory(inventory.size());
				for (int i = 0; i < inventory.size(); i++) {
					if (!inventory.getStack(i).isEmpty()) {
						compressedInv.addStack(inventory.getStack(i));
					}
				}

				DefaultedList<ItemStack> items = DefaultedList.ofSize(PresentBlockItem.MAX_STORAGE);
				for (int i = 0; i < compressedInv.size(); i++) {
					if (!compressedInv.getStack(i).isEmpty()) {
						items.add(0, compressedInv.getStack(i));
					}
				}
				if (!items.isEmpty()) {
					PresentBlockItem.setStoredItems(present, items);
					entity.dropStack(present);
				}
			}
		}
	}
}