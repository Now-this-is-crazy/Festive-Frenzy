package powercyphe.festive_frenzy.common.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.entity.WreathChakramProjectileEntity;
import powercyphe.festive_frenzy.common.registry.FFSounds;
import powercyphe.festive_frenzy.common.util.FFUtil;

public class WreathChakramItem extends Item {
    public WreathChakramItem(Properties properties, ToolMaterial toolMaterial, float attackDamage, float attackSpeed) {
        super(properties.attributes(createAttributeModifiers(toolMaterial, attackDamage, attackSpeed))
                .durability(toolMaterial.durability()).repairable(toolMaterial.repairItems()).enchantable(toolMaterial.enchantmentValue()));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemCooldowns cooldowns = player.getCooldowns();

        if (!cooldowns.isOnCooldown(stack)) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), FFSounds.WREATH_CHAKRAM_THROW, SoundSource.NEUTRAL,
                    1F, 0.875F + (level.getRandom().nextFloat() * 0.3F));

            if (level instanceof ServerLevel serverLevel) {
                WreathChakramProjectileEntity projectile = Projectile.spawnProjectileFromRotation(
                        WreathChakramProjectileEntity::new, serverLevel, stack, player, 0.0F, 2.5F, 0.5F);

                projectile.setSavedSlot(FFUtil.getSlotForItem(player.getInventory(), stack));
            }

            stack.consume(1, player);
            if (!player.isCreative()) {
                cooldowns.addCooldown(stack, 30);
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(level, player, hand);
    }

    public static ItemAttributeModifiers createAttributeModifiers(ToolMaterial material, float attackDamage, float attackSpeed) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, attackDamage + material.attackDamageBonus(), AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }
}
