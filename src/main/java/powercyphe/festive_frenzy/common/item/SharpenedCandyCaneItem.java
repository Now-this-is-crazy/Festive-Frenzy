package powercyphe.festive_frenzy.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;

import java.util.List;
import java.util.UUID;

public class SharpenedCandyCaneItem extends SwordItem {
    protected static final UUID REACH_MODIFIER_ID = UUID.fromString("7ce0388d-edbe-4059-9cfd-a6ac374eb725");
    protected static final UUID ATTACK_RANGE_MODIFIER_ID = UUID.fromString("d3848306-a3c0-4762-a3ae-71730be458f3");

    public SharpenedCandyCaneItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, int attackDamage, float attackSpeed, float reach) {
        return AttributeModifiersComponent.builder().add(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double)((float)attackDamage + material.getAttackDamage()),
                        EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).add(EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", (double)attackSpeed,
                        EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).add(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE,
                new EntityAttributeModifier(REACH_MODIFIER_ID, "Weapon modifier", reach,
                        EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                new EntityAttributeModifier(ATTACK_RANGE_MODIFIER_ID, "Weapon modifier", reach,
                        EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    @Override
    public AttributeModifiersComponent getAttributeModifiers(ItemStack stack) {
        return super.getAttributeModifiers(stack);
    }
}
