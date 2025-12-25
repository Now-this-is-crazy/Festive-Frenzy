package powercyphe.festive_frenzy.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.Nullable;
import powercyphe.festive_frenzy.common.particle.BaubleExplosionParticleOption;

public class BaubleExplosionParticle extends HugeExplosionParticle {
    private final int color;
    private final boolean isEmitter;

    public BaubleExplosionParticle(ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int color, boolean isEmitter, SpriteSet spriteSet) {
        super(clientLevel, x, y, z, velocityX, spriteSet);
        this.color = color;
        this.isEmitter = isEmitter;
        if (this.isEmitter) {
            this.lifetime = 8;
        }

        this.rCol = ((color & 16711680) >> 16) / 255F;
        this.gCol = ((color & '\uff00') >> 8) / 255F;
        this.bCol = (color & 255) / 255F;
    }

    @Override
    public void tick() {
        if (!this.isEmitter) {
            super.tick();
        } else {
            for (int i = 0; i < 6; i++) {
                double x = this.x + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
                double y = this.y + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
                double z = this.z + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
                this.level.addParticle(new BaubleExplosionParticleOption(this.color, false), x, y, z, (float)this.age / this.lifetime, 0.0, 0.0);
            }

            this.age++;
            if (this.age == this.lifetime) {
                this.remove();
            }
        }
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float f) {
        if (!this.isEmitter) {
            super.render(vertexConsumer, camera, f);
        }
    }

    public static class Provider implements ParticleProvider<BaubleExplosionParticleOption> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(BaubleExplosionParticleOption options, ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            int color = options.color();
            boolean isEmitter = options.isEmitter();

            return new BaubleExplosionParticle(clientLevel, x, y, z, velocityX, velocityY, velocityZ, color, isEmitter, this.sprites);
        }
    }
}
