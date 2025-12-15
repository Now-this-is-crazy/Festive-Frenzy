package powercyphe.festive_frenzy.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameRules;
import powercyphe.festive_frenzy.common.event.CandyPouchDropEvent;
import powercyphe.festive_frenzy.common.event.SnowloggablePlaceEvent;
import powercyphe.festive_frenzy.common.payload.EmitterParticlePayload;
import powercyphe.festive_frenzy.common.payload.PresentClosePayload;
import powercyphe.festive_frenzy.common.registry.*;
import powercyphe.festive_frenzy.common.util.FFLootTableModifier;

public class FestiveFrenzy implements ModInitializer {
    public static final String MOD_ID = "festive_frenzy";

    @Override
    public void onInitialize() {
        FFItems.init();
        FFItems.Components.init();
        FFBlocks.init();
        FFBlocks.Entities.init();
        FFEntities.init();
        FFMenus.init();
        FFGamerules.init();
        FFParticles.init();
        FFDamageTypes.init();
        FFSounds.init();
        FFTags.init();
        FFRecipes.init();
        FFLootTables.init();
        FFLootTableModifier.init();
        FFFeatures.init();

        UseBlockCallback.EVENT.register(new SnowloggablePlaceEvent());
        ServerLivingEntityEvents.AFTER_DEATH.register(new CandyPouchDropEvent());

        initNetworking();
    }

    public void initNetworking() {
        PayloadTypeRegistry.playS2C().register(EmitterParticlePayload.TYPE, EmitterParticlePayload.CODEC);
        PayloadTypeRegistry.playC2S().register(PresentClosePayload.TYPE, PresentClosePayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(PresentClosePayload.TYPE, new PresentClosePayload.Receiver());
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.tryBuild(MOD_ID, path);
    }
}
