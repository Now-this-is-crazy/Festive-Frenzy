package powercyphe.festive_frenzy.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class FrostflakeTrailParticle extends SingleQuadParticle {
    private final SpriteSet sprites;

    public FrostflakeTrailParticle(ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet sprites) {
        super(clientLevel, x, y, z, sprites.first());
        RandomSource random = RandomSource.create();

        this.xd = velocityX * 0.14F;
        this.yd = velocityY * 0.17F;
        this.zd = velocityZ * 0.14F;
        this.gravity = 0.37F + random.nextFloat() * 0.235F;

        this.lifetime = 17 + random.nextInt(14);
        this.quadSize = 0.13F + random.nextFloat() * 0.27F;

        this.sprites = sprites;
        this.setSpriteFromAge(this.sprites);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.age++ > this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.sprites);
        }
    }

    @Override
    protected Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    @Override
    protected int getLightColor(float f) {
        return LightTexture.FULL_BRIGHT;
    }

    public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z,
                                       double velocityX, double velocityY, double velocityZ, RandomSource randomSource) {
            return new FrostflakeTrailParticle(clientLevel, x, y, z, velocityX, velocityY, velocityZ, this.sprites);
        }
    }

    public record FrostburnProvider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z,
                                       double velocityX, double velocityY, double velocityZ, RandomSource randomSource) {
            FrostflakeTrailParticle particle = new FrostflakeTrailParticle(clientLevel, x, y, z, velocityX, velocityY, velocityZ, this.sprites);
            RandomSource random = RandomSource.create();

            particle.xd = 0F;
            particle.yd = 0F;
            particle.zd = 0F;

            particle.friction = 0.96F;
            particle.gravity = -0.17F;
            particle.speedUpWhenYMotionIsBlocked = true;

            particle.quadSize = 0.4F;
            particle.lifetime = 11 + random.nextInt(14);

            return particle;
        }
    }
}
