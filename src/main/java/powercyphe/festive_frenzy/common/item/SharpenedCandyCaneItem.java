package powercyphe.festive_frenzy.common.item;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import powercyphe.festive_frenzy.common.FestiveFrenzy;

public class SharpenedCandyCaneItem extends Item {
    public static final Identifier BASE_ENTITY_INTERACTION_RANGE_ID = FestiveFrenzy.id("base_entity_interaction_range");
    public static final Identifier BASE_BLOCK_INTERACTION_RANGE_ID = FestiveFrenzy.id("base_block_interaction_range");

    public SharpenedCandyCaneItem(Properties properties, ToolMaterial toolMaterial, float attackDamage, float attackSpeed, float range) {
        super(toolMaterial.applySwordProperties(properties, attackDamage, attackSpeed)
                .attributes(createAttributeModifiers(toolMaterial, attackDamage, attackSpeed, range)));
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
        super.postHurtEnemy(stack, target, attacker);
    }

    public static ItemAttributeModifiers createAttributeModifiers(ToolMaterial material, float attackDamage, float attackSpeed, float range) {
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
                .add(
                        Attributes.ENTITY_INTERACTION_RANGE,
                        new AttributeModifier(BASE_ENTITY_INTERACTION_RANGE_ID, range, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(
                        Attributes.BLOCK_INTERACTION_RANGE,
                        new AttributeModifier(BASE_BLOCK_INTERACTION_RANGE_ID, range, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }
}
