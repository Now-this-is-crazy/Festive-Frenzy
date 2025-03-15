package powercyphe.festive_frenzy.common.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import powercyphe.festive_frenzy.common.payload.PresentClosePayload;
import powercyphe.festive_frenzy.common.payload.BaubleExplosionPayload;

public class ModNetworking {
    public static final int SPARK_CRIT_ANIMATION_ID = 23;
    public static final int ENCHANTED_CANDY_ANIMATION_ID = 24;

    public static void init() {
        PayloadTypeRegistry.playS2C().register(BaubleExplosionPayload.ID, BaubleExplosionPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(PresentClosePayload.ID, PresentClosePayload.CODEC);
    }
}
