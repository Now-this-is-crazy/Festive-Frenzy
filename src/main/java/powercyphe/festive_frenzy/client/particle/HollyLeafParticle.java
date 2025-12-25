package powercyphe.festive_frenzy.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

import java.util.Random;

public class HollyLeafParticle extends TextureSheetParticle {
    private final float baseScale;
    private float lastQuadSize;

    private float rollSin;

    private final SpriteSet sprites;

    public HollyLeafParticle(ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet sprites) {
        super(clientLevel, x, y, z);
        RandomSource random = RandomSource.create();

        this.xd = velocityX * 0.07F;
        this.yd = velocityY * 0.03F;
        this.zd = velocityZ * 0.07F;
        this.gravity = 0.13F + random.nextFloat() * 0.07F;

        this.rollSin = random.nextFloat() * 360;
        this.roll = (float) Math.toRadians(11 * this.rollSin);
        this.oRoll = this.roll;

        this.lifetime = 21 + random.nextInt(49);
        this.baseScale = 0.13F + random.nextFloat() * 0.07F;
        this.quadSize = this.baseScale;
        this.lastQuadSize = this.quadSize;

        this.sprites = sprites;
        this.setSprite(sprites.get(random));
        this.tick();
    }

    @Override
    public void tick() {
        super.tick();

        this.rollSin += 0.2F;
        this.oRoll = this.roll;
        this.roll = (float) Math.toRadians(11 * this.rollSin * (this.onGround ? 0F : 1F));

        if (this.age++ > this.lifetime) {
            this.remove();
        } else {
            int remainingTime = this.lifetime - this.age;
            if (remainingTime < 10) {
                this.alpha = 1 * (remainingTime / 10F);

                this.lastQuadSize = this.quadSize;
                this.quadSize = this.baseScale * (remainingTime / 10F);
            }
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public float getQuadSize(float f) {
        return Mth.lerp(f, this.lastQuadSize, this.quadSize);
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z,
                                       double velocityX, double velocityY, double velocityZ) {
                return new HollyLeafParticle(clientLevel, x, y, z, velocityX, velocityY, velocityZ, this.sprites);
            }
        }
}
