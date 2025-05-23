package com.levitationstaff.mod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class LevitationIdleParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    public LevitationIdleParticle(ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet sprites) {
        super(level, x, y, z, velocityX, velocityY, velocityZ);
        this.sprites = sprites;
        
        this.lifetime = 30 + this.random.nextInt(20);
        this.gravity = -0.05F;
        this.friction = 0.95F;
        
        this.rCol = 0.6F + this.random.nextFloat() * 0.4F;
        this.gCol = 0.4F + this.random.nextFloat() * 0.6F;
        this.bCol = 1.0F;
        this.alpha = 0.7F;
        
        this.quadSize = 0.05F + this.random.nextFloat() * 0.15F;
        
        this.xd = (this.random.nextDouble() - 0.5) * 0.05;
        this.yd = this.random.nextDouble() * 0.05 + 0.02;
        this.zd = (this.random.nextDouble() - 0.5) * 0.05;
        
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        
        // Gentle fade
        this.alpha = 0.7F * (1.0F - (float)this.age / (float)this.lifetime);
        
        // Floating motion with slight twinkle
        this.yd *= 0.98; // Slow down vertical movement
        this.xd += (this.random.nextDouble() - 0.5) * 0.001;
        this.zd += (this.random.nextDouble() - 0.5) * 0.001;
        
        // Twinkle effect
        if (this.age % 10 == 0) {
            this.quadSize *= 1.2F + this.random.nextFloat() * 0.3F;
        } else if (this.age % 10 == 5) {
            this.quadSize *= 0.8F;
        }
        
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
            return new LevitationIdleParticle(level, x, y, z, velocityX, velocityY, velocityZ, sprites);
        }
    }
} 