package powercyphe.festive_frenzy.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class CandyCritParticle extends SingleQuadParticle {
    public CandyCritParticle(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i, TextureAtlasSprite sprite) {
        super(clientLevel, d, e, f, 0.0, 0.0, 0.0, sprite);
        this.friction = 0.7F;
        this.gravity = 0.5F;
        this.xd *= 0.1F;
        this.yd *= 0.1F;
        this.zd *= 0.1F;
        this.xd += g * 0.4;
        this.yd += h * 0.4;
        this.zd += i * 0.4;
        this.quadSize *= 0.75F;
        this.lifetime = Math.max((int)(6.0 / (Math.random() * 0.8 + 0.6)), 1);
        this.hasPhysics = false;
        this.tick();
    }

    @Override
    public float getQuadSize(float f) {
        return this.quadSize * Mth.clamp((this.age + f) / this.lifetime * 32.0F, 0.0F, 1.0F);
    }

    @Override
    protected Layer getLayer() {
        return Layer.OPAQUE;
    }

    @Environment(EnvType.CLIENT)
        public record Provider(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z,
                                       double velocityX, double velocityY, double velocityZ, RandomSource randomSource) {
                return new CandyCritParticle(clientLevel, x, y, z, velocityX, velocityY, velocityZ, this.sprite.get(randomSource));
            }
        }
}
