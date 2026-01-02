package powercyphe.festive_frenzy.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class FrostflakeParticle extends SingleQuadParticle {
    private final float baseScale;
    private float lastQuadSize;

    private final float rollMultiplier;

    public FrostflakeParticle(ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ, TextureAtlasSprite sprite) {
        super(clientLevel, x, y, z, sprite);
        RandomSource random = RandomSource.create();

        this.xd = velocityX * 0.21F;
        this.yd = velocityY * 0.47F;
        this.zd = velocityZ * 0.21F;
        this.gravity = 0.915F + random.nextFloat() * 0.427F;

        this.roll = random.nextFloat() * (random.nextBoolean() ? 36 : -36);
        this.oRoll = this.roll;
        this.rollMultiplier = (0.3F + random.nextFloat() * 1.57F) * (random.nextBoolean() ? 1 : -1);

        this.lifetime = 17 + random.nextInt(14);
        this.baseScale = 0.13F + random.nextFloat() * 0.27F;
        this.quadSize = this.baseScale;
        this.lastQuadSize = this.quadSize;

        this.tick();
    }

    @Override
    public void tick() {
        super.tick();

        this.oRoll = this.roll;
        this.roll += (float) (Math.abs(this.xd) + Math.abs(this.yd) + Math.abs(this.zd)) / 3F * this.rollMultiplier
                * (this.onGround ? 0.125F : 1F);

        if (this.age++ > this.lifetime) {
            this.remove();
        } else {
            int remainingTime = this.lifetime - this.age;
            if (remainingTime < 4) {
                this.lastQuadSize = this.quadSize;
                this.quadSize = this.baseScale * (remainingTime / 4F);
            }
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

    @Override
    public float getQuadSize(float f) {
        return Mth.lerp(f, this.lastQuadSize, this.quadSize);
    }

    public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z,
                                           double velocityX, double velocityY, double velocityZ, RandomSource randomSource) {
            return new FrostflakeParticle(clientLevel, x, y, z, velocityX, velocityY, velocityZ, this.sprites.get(randomSource));
        }
    }
}
