package powercyphe.festive_frenzy.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import powercyphe.festive_frenzy.client.event.ExplosiveBaubleTooltipEvent;
import powercyphe.festive_frenzy.client.particle.*;
import powercyphe.festive_frenzy.client.render.entity.FrostflakeProjectileRenderer;
import powercyphe.festive_frenzy.client.render.entity.ThrownBaubleProjectileEntityRenderer;
import powercyphe.festive_frenzy.client.render.entity.model.FrostflakeProjectileEntityModel;
import powercyphe.festive_frenzy.client.render.item.BaubleExplosionModificationProperty;
import powercyphe.festive_frenzy.client.render.item.BaubleExplosionProperty;
import powercyphe.festive_frenzy.client.screen.PresentScreen;
import powercyphe.festive_frenzy.common.FestiveFrenzy;
import powercyphe.festive_frenzy.common.payload.EmitterParticlePayload;
import powercyphe.festive_frenzy.common.registry.*;
import powercyphe.festive_frenzy.mixin.accessor.ItemPropertiesAccessor;

import java.util.ArrayList;
import java.util.List;

public class FestiveFrenzyClient implements ClientModInitializer {

    public static final ModelLayerLocation FROSTFLAKE = new ModelLayerLocation(FestiveFrenzy.id("entity/frostflake"), "main");
    public static List<Tuple<Item, ModelResourceLocation>> GUI_MODELS = new ArrayList<>();

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), FFBlocks.SHORT_FROZEN_GRASS, FFBlocks.TALL_FROZEN_GRASS);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), FFBlocks.GINGERBREAD_DOOR, FFBlocks.GINGERBREAD_TRAPDOOR);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), FFBlocks.PRESENTS);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), FFBlocks.TINSEL);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), FFBlocks.BAUBLES);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), FFBlocks.FAIRY_LIGHTS, FFBlocks.STAR_DECORATION);

        ItemPropertiesAccessor.festive_frenzy$getGenericProperties().put(BaubleExplosionModificationProperty.ID, new BaubleExplosionModificationProperty());
        ItemProperties.registerGeneric(BaubleExplosionProperty.ID, new BaubleExplosionProperty());

        EntityModelLayerRegistry.registerModelLayer(FROSTFLAKE, FrostflakeProjectileEntityModel::createBodyLayer);

        EntityRendererRegistry.register(FFEntities.FROSTFLAKE_PROJECTILE, FrostflakeProjectileRenderer::new);
        EntityRendererRegistry.register(FFEntities.THROWN_BAUBLE_PROJECTILE, ThrownBaubleProjectileEntityRenderer::new);

        ParticleFactoryRegistry.getInstance().register(FFParticles.CANDY_CRIT, CandyCritParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(FFParticles.CANDY_SWEEP, CandySweepParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(FFParticles.FAIRY_SPARK, FairySparkParticle.Provider::new);

        ParticleFactoryRegistry.getInstance().register(FFParticles.FROSTFLAKE, FrostflakeParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(FFParticles.FROSTFLAKE_TRAIL, FrostflakeTrailParticle.Provider::new);

        ParticleFactoryRegistry.getInstance().register(FFParticles.GOLDEN_SPARKLE, GoldenSparkleParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(FFParticles.BAUBLE_EXPLOSION, BaubleExplosionParticle.Provider::new);

        MenuScreens.register(FFMenus.PRESENT_MENU, PresentScreen::new);

        TooltipComponentCallback.EVENT.register(new ExplosiveBaubleTooltipEvent());

        addGuiModel(FFItems.SHARPENED_CANDY_CANE);
        initNetworking();
    }

    public void initNetworking() {
        ClientPlayNetworking.registerGlobalReceiver(EmitterParticlePayload.TYPE, new EmitterParticlePayload.Receiver());
    }

    public void addGuiModel(Item item) {
        GUI_MODELS.add(new Tuple<>(item, ModelResourceLocation.inventory(
                BuiltInRegistries.ITEM.getKey(item).withSuffix("_gui"))));
    }
}
