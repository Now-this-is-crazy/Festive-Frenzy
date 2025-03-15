package powercyphe.festive_frenzy.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

public class FairySparkParticle extends SpriteBillboardParticle {
    private final float baseScale;
    private final float angleMultiplier;

    FairySparkParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        super(clientWorld, d, e, f, 0.0, 0.0, 0.0);
        this.velocityMultiplier = 0.7F;
        this.gravityStrength = 0.5F;
        this.velocityX *= 0.10000000149011612;
        this.velocityY *= 0.10000000149011612;
        this.velocityZ *= 0.10000000149011612;
        this.velocityX += g * 0.4;
        this.velocityY += h * 0.4;
        this.velocityZ += i * 0.4;

        this.scale *= 1.5F;
        this.baseScale = this.scale;

        this.angle = Random.create().nextBetween(0, 360);
        this.prevAngle = this.angle;
        this.angleMultiplier = this.random.nextFloat() * (this.random.nextBoolean() ? 1 : -1);

        this.maxAge = Math.max((int)(10.0 / (Math.random() * 0.8 + 0.6)), 1);
        this.collidesWithWorld = false;
        this.tick();
    }

    @Override
    public void tick() {
        super.tick();

        this.angle = this.prevAngle + this.angleMultiplier * (float) (Math.sin(this.age * 14) * 3);
        if (this.age++ < this.maxAge) {
            int faintAge = this.maxAge / 3;
            if (this.age >= faintAge) {
                float scaleAge = this.age - faintAge - 0.75F;
                float scaleAgeMax = this.maxAge - faintAge;
                this.scale = Math.max(this.baseScale * ((scaleAgeMax - scaleAge) / scaleAgeMax), 0.01F);
            }
        }
    }

    @Override
    protected int getBrightness(float tint) {
        return 15728880;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public float getSize(float tickDelta) {
        return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 32.0F, 0.0F, 1.0F);
    }

    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            FairySparkParticle fairySparkParticle = new FairySparkParticle(clientWorld, d, e, f, g, h, i);
            fairySparkParticle.setSprite(this.spriteProvider);
            return fairySparkParticle;
        }
    }
}
