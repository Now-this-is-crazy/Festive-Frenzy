package powercyphe.festive_frenzy;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.particle.SweepAttackParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FallingBlockEntityRenderer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import powercyphe.festive_frenzy.network.ClientNetworking;
import powercyphe.festive_frenzy.particle.*;
import powercyphe.festive_frenzy.registry.*;
import powercyphe.festive_frenzy.render.entity.BaubleProjectileEntityRenderer;
import powercyphe.festive_frenzy.screen.PresentScreen;

public class FestiveFrenzyClient implements ClientModInitializer {
	public static DefaultedList<Pair<Item, ModelIdentifier>> GUI_MODELS = DefaultedList.of();

	@Override
	public void onInitializeClient() {
		ClientNetworking.init();

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
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SNOW_GLOBE, RenderLayer.getCutout());

		EntityRendererRegistry.register(ModEntities.FROSTFLAKE_PROJECTILE, EmptyEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.BAUBLE_PROJECTILE, BaubleProjectileEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.FALLING_BAUBLE_BLOCK_ENTITY, FallingBlockEntityRenderer::new);

		ParticleFactoryRegistry.getInstance().register(ModParticles.CANDY_SWEEP, SweepAttackParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.CANDY_CRIT, CandyCritParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.FAIRY_SPARK, FairySparkParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.FROSTFLAKE, FrostflakeParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.FROSTFLAKE_TRAIL, FrostflakeTrailParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.BAUBLE_EXPLOSION, BaubleExplosionParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.BAUBLE_EXPLOSION_EMITTER, ((parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> new BaubleExplosionEmitterParticle(world, x, y, z, parameters.getBauble())));

		HandledScreens.register(ModScreenHandlers.PRESENT_SCREEN_HANDLER, PresentScreen::new);

		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			return tintIndex > 0 ? -1 : ((DyeableItem)stack.getItem()).getColor(stack);
		}, ModItems.FESTIVE_HAT);

		for (Item item : ModItems.SHARPENED_CANDY_CANES) {
			addGuiModel(item);
		}
		addGuiModel(ModItems.FESTIVE_HAT);
	}

	public static void addGuiModel(Item item) {
		Pair<Item, ModelIdentifier> pair = new Pair<>(item, new ModelIdentifier(
				FestiveFrenzy.id(Registries.ITEM.getId(item).getPath() + "_2d"), "inventory"));
		GUI_MODELS.add(pair);
	}
}