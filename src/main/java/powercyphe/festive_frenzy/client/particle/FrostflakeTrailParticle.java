package powercyphe.festive_frenzy.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class FrostflakeTrailParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    public FrostflakeTrailParticle(ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet sprites) {
        super(clientLevel, x, y, z);
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
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    protected int getLightColor(float f) {
        return LightTexture.FULL_BRIGHT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z,
                                       double velocityX, double velocityY, double velocityZ) {
                return new FrostflakeTrailParticle(clientLevel, x, y, z, velocityX, velocityY, velocityZ, this.sprites);
        }
    }

    public static class FrostburnProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public FrostburnProvider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z,
                                       double velocityX, double velocityY, double velocityZ) {
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
