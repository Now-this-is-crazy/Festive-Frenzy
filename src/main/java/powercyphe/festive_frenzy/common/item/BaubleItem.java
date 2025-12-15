package powercyphe.festive_frenzy.common.item;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import powercyphe.festive_frenzy.client.gui.tooltip.ExplosiveBaubleTooltipData;
import powercyphe.festive_frenzy.common.entity.ThrownBaubleProjectileEntity;
import powercyphe.festive_frenzy.common.item.component.ExplosiveBaubleComponent;
import powercyphe.festive_frenzy.common.registry.FFSounds;

import java.util.Optional;

public class BaubleItem extends BlockItem {
    public BaubleItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!player.getCooldowns().isOnCooldown(stack)) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), FFSounds.BAUBLE_THROW, SoundSource.NEUTRAL,
                    1F, 0.875F + (level.getRandom().nextFloat() * 0.3F));

            if (level instanceof ServerLevel serverLevel) {
                Projectile.spawnProjectileFromRotation(ThrownBaubleProjectileEntity::new, serverLevel, stack, player, 0.0F, 1.5F, 0.5F);
            }

            stack.consume(1, player);
            if (!player.isCreative()) {
                player.getCooldowns().addCooldown(stack, 10);
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(level, player, hand);
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
            return !stack.has(DataComponents.HIDE_TOOLTIP) && !stack.has(DataComponents.HIDE_ADDITIONAL_TOOLTIP) ?
                    Optional.of(ExplosiveBaubleComponent.get(stack)).map(ExplosiveBaubleTooltipData::new) : Optional.empty();
        }
}
