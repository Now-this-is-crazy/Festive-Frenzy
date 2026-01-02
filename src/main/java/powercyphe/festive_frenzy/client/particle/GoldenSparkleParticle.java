package powercyphe.festive_frenzy.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.state.QuadParticleRenderState;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.joml.Quaternionf;

public class GoldenSparkleParticle extends SingleQuadParticle {
    private int delay;
    private final SpriteSet sprites;

    public GoldenSparkleParticle(ClientLevel clientLevel, double x, double y, double z, SpriteSet sprites) {
        super(clientLevel, x, y, z, sprites.first());
        this.delay = RandomSource.create().nextInt(10);
        this.lifetime = 7;

        this.sprites = sprites;
        this.setSpriteFromAge(this.sprites);
    }

    @Override
    public void tick() {
        if (this.delay == 0) {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;

            if (this.age++ >= this.lifetime) {
                this.remove();
            } else {
                this.setSpriteFromAge(this.sprites);
            }
        } else {
            this.delay--;
        }
    }

    @Override
    protected void extractRotatedQuad(QuadParticleRenderState quadParticleRenderState, Quaternionf quaternionf, float f, float g, float h, float i) {
        if (this.delay == 0) {
            super.extractRotatedQuad(quadParticleRenderState, quaternionf, f, g, h, i);
        }
    }

    @Override
    protected Layer getLayer() {
        return Layer.OPAQUE;
    }

    @Override
    public int getLightColor(float f) {
        return LightTexture.FULL_BRIGHT;
    }

    public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z,
                                       double velocityX, double velocityY, double velocityZ, RandomSource randomSource) {
            return new GoldenSparkleParticle(clientLevel, x, y, z, this.sprites);
        }
    }
}
