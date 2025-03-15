package powercyphe.festive_frenzy.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.particle.SimpleParticleType;

public class FrostflakeParticle extends SpriteBillboardParticle {

    public FrostflakeParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z);
        this.gravityStrength = 0.225F;
        this.velocityMultiplier = 1.0F;
        this.velocityX = velocityX + (Math.random() * 2.0 - 1.0) * 0.05000000074505806;
        this.velocityY = velocityY + (Math.random() * 2.0 - 1.0) * 0.05000000074505806;
        this.velocityZ = velocityZ + (Math.random() * 2.0 - 1.0) * 0.05000000074505806;
        this.scale = 0.2F * (this.random.nextFloat() * this.random.nextFloat() * 1.0F + 1.0F);
        this.maxAge = (int)(15.0 / ((double)this.random.nextFloat() * 0.8 + 0.2)) + 2;
    }

    @Override
    public void tick() {
        super.tick();
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
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            FrostflakeParticle snowflakeParticle = new FrostflakeParticle(clientWorld, d, e, f, g, h, i);
            snowflakeParticle.setColor(0.923F, 0.964F, 0.999F);
            snowflakeParticle.setSprite(this.spriteProvider);
            return snowflakeParticle;
        }
    }
}
