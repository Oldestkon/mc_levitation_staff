package com.levitationstaff.mod.capability;

public interface ILevitationCapability {
    boolean isLevitating();
    void setLevitating(boolean levitating, int duration, float strength, int particleColor);
    int getRemainingDuration();
    float getStrength();
    int getParticleColor();
    void tick();
    float getLimbRotation();
    void setLimbRotation(float rotation);
} 