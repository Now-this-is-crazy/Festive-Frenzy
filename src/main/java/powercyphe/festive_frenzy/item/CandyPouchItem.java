package powercyphe.festive_frenzy.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import powercyphe.festive_frenzy.FestiveFrenzy;
import powercyphe.festive_frenzy.registry.ModBlocks;
import powercyphe.festive_frenzy.registry.ModSounds;

public class CandyPouchItem extends Item {
    public CandyPouchItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.POUCH_OPEN, SoundCategory.NEUTRAL, 0.5F, 0.9F + (world.getRandom().nextFloat() * 0.2F));
        if (!world.isClient()) {
            Identifier presentLootTable = FestiveFrenzy.id("mob_present");
            LootTable lootTable = world.getServer().getLootManager().getLootTable(presentLootTable);
            LootContextParameterSet.Builder builder = (new LootContextParameterSet.Builder((ServerWorld) world));

            SimpleInventory inventory = new SimpleInventory(27);
            lootTable.supplyInventory(inventory, builder.build(LootContextTypes.EMPTY), user.getLootTableSeed());

            ItemScatterer.spawn(world, user, inventory);
            stack.decrement(1);
            return TypedActionResult.consume(stack);
        }
        return TypedActionResult.success(stack);
    }
}
