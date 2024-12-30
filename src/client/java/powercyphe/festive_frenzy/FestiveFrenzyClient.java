package powercyphe.festive_frenzy;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.particle.SweepAttackParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import powercyphe.festive_frenzy.particle.CandyCritParticle;
import powercyphe.festive_frenzy.particle.FairySparkParticle;
import powercyphe.festive_frenzy.particle.FrostflakeParticle;
import powercyphe.festive_frenzy.particle.FrostflakeTrailParticle;
import powercyphe.festive_frenzy.registry.ModBlocks;
import powercyphe.festive_frenzy.registry.ModEntities;
import powercyphe.festive_frenzy.registry.ModItems;
import powercyphe.festive_frenzy.registry.ModParticles;

public class FestiveFrenzyClient implements ClientModInitializer {
	public static DefaultedList<Pair<Item, ModelIdentifier>> GUI_MODELS = DefaultedList.of();

	@Override
	public void onInitializeClient() {
		for (Block present : ModBlocks.PRESENTS) {
			BlockRenderLayerMap.INSTANCE.putBlock(present, RenderLayer.getCutout());
		}

		for (Block bauble : ModBlocks.BAUBLES) {
			BlockRenderLayerMap.INSTANCE.putBlock(bauble, RenderLayer.getCutout());
		}

		for (Block tinsel : ModBlocks.TINSELS) {
			BlockRenderLayerMap.INSTANCE.putBlock(tinsel, RenderLayer.getCutout());
		}

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FAIRY_LIGHTS, RenderLayer.getCutout());

		EntityRendererRegistry.register(ModEntities.FROSTFLAKE_PROJECTILE, EmptyEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.BAUBLE_PROJECTILE, FlyingItemEntityRenderer::new);

		ParticleFactoryRegistry.getInstance().register(ModParticles.CANDY_SWEEP, SweepAttackParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.CANDY_CRIT, CandyCritParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.FAIRY_SPARK, FairySparkParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.FROSTFLAKE, FrostflakeParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.FROSTFLAKE_TRAIL, FrostflakeTrailParticle.Factory::new);

		ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
				0xd41c1c, ModItems.FESTIVE_HAT);
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			return tintIndex > 0 ? -1 : ((DyeableItem)stack.getItem()).getColor(stack);
		}, ModItems.FESTIVE_HAT);


		addGuiModel(ModItems.SHARPENED_CANDY_CANE, FestiveFrenzy.id("sharpened_candy_cane_2d"));
		addGuiModel(ModItems.FESTIVE_HAT, FestiveFrenzy.id("festive_hat_2d"));
	}

	public static void addGuiModel(Item item, Identifier identifier) {
		Pair<Item, ModelIdentifier> pair = new Pair<>(item, new ModelIdentifier(identifier, "inventory"));
		GUI_MODELS.add(pair);
	}
}