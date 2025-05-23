package com.levitationstaff.mod.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class MagickaReplenishParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    public MagickaReplenishParticle(ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet sprites) {
        super(level, x, y, z, velocityX, velocityY, velocityZ);
        this.sprites = sprites;
        
        this.lifetime = 40 + this.random.nextInt(20);
        this.gravity = -0.02F;
        this.friction = 0.92F;
        
        // Golden magical colors
        this.rCol = 1.0F;
        this.gCol = 0.8F + this.random.nextFloat() * 0.2F;
        this.bCol = 0.2F + this.random.nextFloat() * 0.3F;
        this.alpha = 0.9F;
        
        this.quadSize = 0.08F + this.random.nextFloat() * 0.2F;
        
        // Rise up from cauldron
        this.xd = (this.random.nextDouble() - 0.5) * 0.1;
        this.yd = this.random.nextDouble() * 0.15 + 0.05;
        this.zd = (this.random.nextDouble() - 0.5) * 0.1;
        
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        
        // Bright fade with pulsing
        float pulseEffect = 1.0F + 0.3F * (float)Math.sin(this.age * 0.5);
        this.alpha = (0.9F * pulseEffect) * (1.0F - (float)this.age / (float)this.lifetime);
        
        // Magical swirling motion
        double swirl = this.age * 0.1;
        this.xd += Math.cos(swirl) * 0.002;
        this.zd += Math.sin(swirl) * 0.002;
        
        // Sparkle size variation
        this.quadSize = (0.08F + this.random.nextFloat() * 0.2F) * pulseEffect;
        
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
            return new MagickaReplenishParticle(level, x, y, z, velocityX, velocityY, velocityZ, sprites);
        }
    }
} 