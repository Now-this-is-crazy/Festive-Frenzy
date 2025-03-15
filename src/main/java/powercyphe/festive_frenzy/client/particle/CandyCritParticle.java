package powercyphe.festive_frenzy.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;

public class CandyCritParticle extends SpriteBillboardParticle {
    CandyCritParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        super(clientWorld, d, e, f, 0.0, 0.0, 0.0);
        this.velocityMultiplier = 0.7F;
        this.gravityStrength = 0.5F;
        this.velocityX *= 0.10000000149011612;
        this.velocityY *= 0.10000000149011612;
        this.velocityZ *= 0.10000000149011612;
        this.velocityX += g * 0.4;
        this.velocityY += h * 0.4;
        this.velocityZ += i * 0.4;
        this.scale *= 0.75F;
        this.maxAge = Math.max((int)(6.0 / (Math.random() * 0.8 + 0.6)), 1);
        this.collidesWithWorld = false;
        this.tick();
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
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
            CandyCritParticle candyCritParticle = new CandyCritParticle(clientWorld, d, e, f, g, h, i);
            candyCritParticle.setSprite(this.spriteProvider);
            return candyCritParticle;
        }
    }
}
