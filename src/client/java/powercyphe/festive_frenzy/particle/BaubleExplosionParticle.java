package powercyphe.festive_frenzy.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ExplosionLargeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import powercyphe.festive_frenzy.registry.ModBlocks;

import java.util.HashMap;

public class BaubleExplosionParticle extends ExplosionLargeParticle {
    public BaubleExplosionParticle(ClientWorld world, double x, double y, double z, double d, SpriteProvider spriteProvider, ItemStack stack) {
        super(world, x, y, z, d, spriteProvider);

        HashMap<Item, DyeItem> baubleDyePairs = new HashMap<>();
        baubleDyePairs.put(ModBlocks.WHITE_BAUBLE.asItem(), (DyeItem) Items.WHITE_DYE);
        baubleDyePairs.put(ModBlocks.LIGHT_BLUE_BAUBLE.asItem(), (DyeItem) Items.LIGHT_GRAY_DYE);
        baubleDyePairs.put(ModBlocks.GRAY_BAUBLE.asItem(), (DyeItem) Items.GRAY_DYE);
        baubleDyePairs.put(ModBlocks.BLACK_BAUBLE.asItem(), (DyeItem) Items.BLACK_DYE);
        baubleDyePairs.put(ModBlocks.BROWN_BAUBLE.asItem(), (DyeItem) Items.BROWN_DYE);
        baubleDyePairs.put(ModBlocks.RED_BAUBLE.asItem(), (DyeItem) Items.RED_DYE);
        baubleDyePairs.put(ModBlocks.ORANGE_BAUBLE.asItem(), (DyeItem) Items.ORANGE_DYE);
        baubleDyePairs.put(ModBlocks.YELLOW_BAUBLE.asItem(), (DyeItem) Items.YELLOW_DYE);
        baubleDyePairs.put(ModBlocks.LIME_BAUBLE.asItem(), (DyeItem) Items.LIME_DYE);
        baubleDyePairs.put(ModBlocks.GREEN_BAUBLE.asItem(), (DyeItem) Items.GREEN_DYE);
        baubleDyePairs.put(ModBlocks.CYAN_BAUBLE.asItem(), (DyeItem) Items.CYAN_DYE);
        baubleDyePairs.put(ModBlocks.LIGHT_BLUE_BAUBLE.asItem(), (DyeItem) Items.LIGHT_BLUE_DYE);
        baubleDyePairs.put(ModBlocks.BLUE_BAUBLE.asItem(), (DyeItem) Items.BLUE_DYE);
        baubleDyePairs.put(ModBlocks.PURPLE_BAUBLE.asItem(), (DyeItem) Items.PURPLE_DYE);
        baubleDyePairs.put(ModBlocks.MAGENTA_BAUBLE.asItem(), (DyeItem) Items.MAGENTA_DYE);
        baubleDyePairs.put(ModBlocks.PINK_BAUBLE.asItem(), (DyeItem) Items.PINK_DYE);

        if (baubleDyePairs.containsKey(stack.getItem())) {
            DyeItem dyeItem = baubleDyePairs.get(stack.getItem());
            float[] color = dyeItem.getColor().getColorComponents();
            this.red = Math.max(Math.min(color[0] + (Random.create().nextBetween(-10, 10) / 255f), 1), 0);
            this.green = Math.max(Math.min(color[1] + (Random.create().nextBetween(-10, 10) / 255f), 1), 0);
            this.blue = Math.max(Math.min(color[2] + (Random.create().nextBetween(-10, 10) / 255f), 1), 0);
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<BaubleExplosionParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(BaubleExplosionParticleEffect defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new BaubleExplosionParticle(clientWorld, d, e, f, g, this.spriteProvider, defaultParticleType.getBauble());
        }
    }
}
