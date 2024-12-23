package powercyphe.festive_frenzy.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class FrostflakeTrailParticle extends SpriteBillboardParticle {
    private SpriteProvider spriteProvider;

    public FrostflakeTrailParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.gravityStrength = 0.135F;
        this.scale = 0.3F * (this.random.nextFloat() * this.random.nextFloat() * 1.0F + 1.0F);
        this.maxAge = (int)(27.0 / ((double)this.random.nextFloat() * 0.8 + 0.2)) + 2;
        this.spriteProvider = spriteProvider;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteForAge(this.spriteProvider);
        this.setAlpha((float) (this.maxAge - this.age) / this.maxAge);
        this.velocityX *= 0.949999988079071;
        this.velocityY *= 0.8999999761581421;
        this.velocityZ *= 0.949999988079071;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            FrostflakeTrailParticle frostflakeTrailParticle = new FrostflakeTrailParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
            frostflakeTrailParticle.setColor(0.923F, 0.964F, 0.999F);
            frostflakeTrailParticle.setSpriteForAge(this.spriteProvider);
            return frostflakeTrailParticle;
        }
    }
}
