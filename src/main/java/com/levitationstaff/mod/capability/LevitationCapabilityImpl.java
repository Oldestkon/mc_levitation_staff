package com.levitationstaff.mod.capability;

import com.levitationstaff.mod.init.ModParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.common.util.INBTSerializable;

public class LevitationCapabilityImpl implements ILevitationCapability, INBTSerializable<CompoundTag> {
    private boolean isLevitating = false;
    private int remainingDuration = 0;
    private float strength = 1.0f;
    private int particleColor = 0x3399FF;
    private float limbRotation = 0.0f;
    private final LivingEntity entity;

    public LevitationCapabilityImpl(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public boolean isLevitating() {
        return isLevitating;
    }

    @Override
    public void setLevitating(boolean levitating, int duration, float strength, int particleColor) {
        this.isLevitating = levitating;
        this.remainingDuration = levitating ? duration : 0;
        this.strength = strength;
        this.particleColor = particleColor;
        
        if (levitating) {
            // Apply levitation effect
            entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, duration, 
                Math.max(0, (int)(strength - 1.0f)), false, false));
            
            // Disable AI temporarily
            if (entity.getNavigation() != null) {
                entity.getNavigation().stop();
            }
        } else {
            // Remove levitation effect
            entity.removeEffect(MobEffects.LEVITATION);
        }
    }

    @Override
    public int getRemainingDuration() {
        return remainingDuration;
    }

    @Override
    public float getStrength() {
        return strength;
    }

    @Override
    public int getParticleColor() {
        return particleColor;
    }

    @Override
    public void tick() {
        if (isLevitating) {
            remainingDuration--;
            
            if (remainingDuration <= 0) {
                setLevitating(false, 0, 0, 0);
                return;
            }
            
            // Update limb rotation for visual effect
            limbRotation += 3.0f; // Rotate limbs in circles
            if (limbRotation >= 360.0f) {
                limbRotation -= 360.0f;
            }
            
            // Spawn idle particles
            if (entity.level instanceof ServerLevel serverLevel && entity.tickCount % 3 == 0) {
                double x = entity.getX() + (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth();
                double y = entity.getY() + entity.getRandom().nextDouble() * entity.getBbHeight();
                double z = entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth();
                
                serverLevel.sendParticles(ModParticles.LEVITATION_IDLE.get(),
                    x, y, z, 1, 0.0, 0.1, 0.0, 0.0);
            }
            
            // Keep entity floating (add slight upward movement if falling)
            if (entity.getDeltaMovement().y < -0.1) {
                entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0, 0.5, 1.0));
            }
        }
    }

    @Override
    public float getLimbRotation() {
        return limbRotation;
    }

    @Override
    public void setLimbRotation(float rotation) {
        this.limbRotation = rotation;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("isLevitating", isLevitating);
        tag.putInt("remainingDuration", remainingDuration);
        tag.putFloat("strength", strength);
        tag.putInt("particleColor", particleColor);
        tag.putFloat("limbRotation", limbRotation);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        isLevitating = nbt.getBoolean("isLevitating");
        remainingDuration = nbt.getInt("remainingDuration");
        strength = nbt.getFloat("strength");
        particleColor = nbt.getInt("particleColor");
        limbRotation = nbt.getFloat("limbRotation");
    }
} 