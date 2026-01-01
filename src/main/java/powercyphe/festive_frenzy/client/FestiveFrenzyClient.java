package powercyphe.festive_frenzy.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperties;
import powercyphe.festive_frenzy.client.event.ExplosiveBaubleTooltipEvent;
import powercyphe.festive_frenzy.client.particle.*;
import powercyphe.festive_frenzy.client.render.entity.FrostflakeProjectileRenderer;
import powercyphe.festive_frenzy.client.render.entity.ThrownBaubleProjectileEntityRenderer;
import powercyphe.festive_frenzy.client.render.entity.WreathChakramProjectileRenderer;
import powercyphe.festive_frenzy.client.render.entity.model.FrostflakeProjectileEntityModel;
import powercyphe.festive_frenzy.client.render.item.BaubleExplosionModificationProperty;
import powercyphe.festive_frenzy.client.render.item.BaubleExplosionPowerProperty;
import powercyphe.festive_frenzy.client.screen.PresentScreen;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.payload.EmitterParticlePayload;
import powercyphe.festive_frenzy.common.registry.*;

public class FestiveFrenzyClient implements ClientModInitializer {
    public static final ModelLayerLocation FROSTFLAKE = new ModelLayerLocation(FestiveFrenzy.id("entity/frostflake"), "main");

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), FFBlocks.SHORT_FROZEN_GRASS, FFBlocks.TALL_FROZEN_GRASS, FFBlocks.HOLLY_BUSH);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), FFBlocks.GINGERBREAD_DOOR, FFBlocks.GINGERBREAD_TRAPDOOR);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), FFBlocks.PRESENTS);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), FFBlocks.TINSELS);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), FFBlocks.BAUBLES);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), FFBlocks.FAIRY_LIGHTS, FFBlocks.WREATH, FFBlocks.STAR_DECORATION);

        RangeSelectItemModelProperties.ID_MAPPER.put(BaubleExplosionPowerProperty.ID, BaubleExplosionPowerProperty.CODEC);
        SelectItemModelProperties.ID_MAPPER.put(BaubleExplosionModificationProperty.ID, BaubleExplosionModificationProperty.TYPE);

        EntityModelLayerRegistry.registerModelLayer(FROSTFLAKE, FrostflakeProjectileEntityModel::createBodyLayer);

        EntityRendererRegistry.register(FFEntities.FROSTFLAKE_PROJECTILE, FrostflakeProjectileRenderer::new);
        EntityRendererRegistry.register(FFEntities.THROWN_BAUBLE_PROJECTILE, ThrownBaubleProjectileEntityRenderer::new);
        EntityRendererRegistry.register(FFEntities.WREATH_CHAKRAM_PROJECTILE, WreathChakramProjectileRenderer::new);

        ParticleFactoryRegistry.getInstance().register(FFParticles.CANDY_CRIT, CandyCritParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(FFParticles.CANDY_SWEEP, CandySweepParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(FFParticles.FAIRY_SPARK, FairySparkParticle.Provider::new);

        ParticleFactoryRegistry.getInstance().register(FFParticles.FROSTBURN, FrostflakeTrailParticle.FrostburnProvider::new);
        ParticleFactoryRegistry.getInstance().register(FFParticles.FROSTFLAKE, FrostflakeParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(FFParticles.FROSTFLAKE_TRAIL, FrostflakeTrailParticle.Provider::new);

        ParticleFactoryRegistry.getInstance().register(FFParticles.HOLLY_LEAF, HollyLeafParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(FFParticles.GOLDEN_SPARKLE, GoldenSparkleParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(FFParticles.BAUBLE_EXPLOSION, BaubleExplosionParticle.Provider::new);

        MenuScreens.register(FFMenus.PRESENT_MENU, PresentScreen::new);
        TooltipComponentCallback.EVENT.register(new ExplosiveBaubleTooltipEvent());

        initNetworking();
    }

    public void initNetworking() {
        ClientPlayNetworking.registerGlobalReceiver(EmitterParticlePayload.TYPE, new EmitterParticlePayload.Receiver());
    }
}
