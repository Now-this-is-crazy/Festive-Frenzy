package powercyphe.festive_frenzy.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class HollyLeafParticle extends SingleQuadParticle {
    private final float baseScale;
    private float lastQuadSize;

    private float rollSin;

    public HollyLeafParticle(ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random, SpriteSet sprites) {
        super(clientLevel, x, y, z, sprites.get(random));

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
    protected Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    @Override
    public float getQuadSize(float f) {
        return Mth.lerp(f, this.lastQuadSize, this.quadSize);
    }

    public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z,
                                       double velocityX, double velocityY, double velocityZ, RandomSource randomSource) {
            return new HollyLeafParticle(clientLevel, x, y, z, velocityX, velocityY, velocityZ, randomSource, this.sprites);
        }
    }
}
