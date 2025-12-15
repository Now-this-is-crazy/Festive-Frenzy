package powercyphe.festive_frenzy.common.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import powercyphe.festive_frenzy.common.registry.FFLootTables;
import powercyphe.festive_frenzy.common.registry.FFSounds;

public class CandyPouchItem extends Item {
    public CandyPouchItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);

        if (level instanceof ServerLevel serverLevel) {
            LootTable lootTable = serverLevel.getServer().reloadableRegistries()
                    .getLootTable(FFLootTables.CANDY_POUCH);

            for (ItemStack drop : lootTable.getRandomItems(
                    new LootParams.Builder(serverLevel).create(LootContextParamSets.EMPTY))) {
                if (!stack.isEmpty()) {
                    player.getInventory().placeItemBackInInventory(drop);
                }
            }

            stack.consume(1, player);
            return InteractionResult.CONSUME;
        }

        level.playSound(player, player.getX(), player.getY(), player.getZ(), FFSounds.POUCH_OPEN, SoundSource.PLAYERS,
                1F, 0.9F + RandomSource.create().nextFloat() * 0.2F);
        return InteractionResult.SUCCESS;
    }
}
