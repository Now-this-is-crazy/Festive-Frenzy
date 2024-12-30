package powercyphe.festive_frenzy.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

import java.util.UUID;

public class SharpenedCandyCaneItem extends SwordItem {
    protected static final UUID REACH_MODIFIER_ID = UUID.fromString("7ce0388d-edbe-4059-9cfd-a6ac374eb725");
    protected static final UUID ATTACK_RANGE_MODIFIER_ID = UUID.fromString("d3848306-a3c0-4762-a3ae-71730be458f3");
    private final float attackDamage;
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public SharpenedCandyCaneItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, float reach, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        this.attackDamage = (float)attackDamage + toolMaterial.getAttackDamage();
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double)this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", (double)attackSpeed, EntityAttributeModifier.Operation.ADDITION));
        builder.put(ReachEntityAttributes.REACH, new EntityAttributeModifier(REACH_MODIFIER_ID, "Weapon modifier", (double)reach, EntityAttributeModifier.Operation.ADDITION));
        builder.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier(ATTACK_RANGE_MODIFIER_ID, "Weapon modifier", (double)reach, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }
}
