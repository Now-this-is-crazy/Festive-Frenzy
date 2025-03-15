package powercyphe.festive_frenzy.client.particle;

import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import powercyphe.festive_frenzy.common.particle.BaubleExplosionParticleEffect;

public class BaubleExplosionEmitterParticle extends NoRenderParticle {
    private int age_;
    private final int maxAge_ = 8;
    private final ItemStack stack;

    public BaubleExplosionEmitterParticle(ClientWorld clientWorld, double d, double e, double f, ItemStack stack) {
        super(clientWorld, d, e, f, 0.0, 0.0, 0.0);
        this.stack = stack;
    }

    public void tick() {
        for(int i = 0; i < 6; ++i) {
            double d = this.x + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
            double e = this.y + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
            double f = this.z + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
            BaubleExplosionParticleEffect particleEffect = new BaubleExplosionParticleEffect(this.stack);
            this.world.addParticle(particleEffect, d, e, f, (double)((float)this.age_ / (float)this.maxAge_), 0.0, 0.0);
        }

        ++this.age_;
        if (this.age_ == this.maxAge_) {
            this.markDead();
        }

    }
}
