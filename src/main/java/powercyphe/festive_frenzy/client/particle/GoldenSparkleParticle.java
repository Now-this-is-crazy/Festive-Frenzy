package powercyphe.festive_frenzy.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class GoldenSparkleParticle extends TextureSheetParticle {
    private int delay;
    private final SpriteSet sprites;

    public GoldenSparkleParticle(ClientLevel clientLevel, double x, double y, double z, SpriteSet sprites) {
        super(clientLevel, x, y, z);
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
    public void render(VertexConsumer vertexConsumer, Camera camera, float f) {
        if (this.delay == 0) {
            super.render(vertexConsumer, camera, f);
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getLightColor(float f) {
        return LightTexture.FULL_BRIGHT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z,
                                       double velocityX, double velocityY, double velocityZ) {
            return new GoldenSparkleParticle(clientLevel, x, y, z, this.sprites);
        }
    }
}
