package com.levitationstaff.mod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class LevitationCastParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    public LevitationCastParticle(ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet sprites) {
        super(level, x, y, z, velocityX, velocityY, velocityZ);
        this.sprites = sprites;
        
        this.lifetime = 20 + this.random.nextInt(10);
        this.gravity = -0.1F;
        this.friction = 0.9F;
        
        this.rCol = 0.4F + this.random.nextFloat() * 0.6F;
        this.gCol = 0.2F + this.random.nextFloat() * 0.8F;
        this.bCol = 1.0F;
        this.alpha = 1.0F;
        
        this.quadSize = 0.1F + this.random.nextFloat() * 0.3F;
        
        this.xd = (this.random.nextDouble() - 0.5) * 0.2;
        this.yd = this.random.nextDouble() * 0.1 + 0.05;
        this.zd = (this.random.nextDouble() - 0.5) * 0.2;
        
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        
        // Fade out over time
        this.alpha = 1.0F - (float)this.age / (float)this.lifetime;
        
        // Spiral motion
        double spiralRadius = 0.5 * Math.sin(this.age * 0.3);
        this.xd += Math.cos(this.age * 0.2) * spiralRadius * 0.01;
        this.zd += Math.sin(this.age * 0.2) * spiralRadius * 0.01;
        
        this.setSpriteFromAge(sprites);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new LevitationCastParticle(level, x, y, z, velocityX, velocityY, velocityZ, sprites);
        }
    }
} 