package com.levitationstaff.mod.item;

public enum StaffType {
    NOVICE("novice", 8.0f, false, 100, 1.0f, 0x3399FF),
    APPRENTICE("apprentice", 12.0f, false, 200, 1.5f, 0x6633FF),
    ADEPT("adept", 16.0f, true, 300, 2.0f, 0x9933FF),
    EXPERT("expert", 20.0f, true, 400, 2.5f, 0xCC33FF),
    MASTER("master", 25.0f, true, 500, 3.0f, 0xFF33CC);

    private final String name;
    private final float range;
    private final boolean isAreaOfEffect;
    private final int maxMagicka;
    private final float levitationStrength;
    private final int particleColor;

    StaffType(String name, float range, boolean isAreaOfEffect, int maxMagicka, float levitationStrength, int particleColor) {
        this.name = name;
        this.range = range;
        this.isAreaOfEffect = isAreaOfEffect;
        this.maxMagicka = maxMagicka;
        this.levitationStrength = levitationStrength;
        this.particleColor = particleColor;
    }

    public String getName() {
        return name;
    }

    public float getRange() {
        return range;
    }

    public boolean isAreaOfEffect() {
        return isAreaOfEffect;
    }

    public int getMaxMagicka() {
        return maxMagicka;
    }

    public float getLevitationStrength() {
        return levitationStrength;
    }

    public int getParticleColor() {
        return particleColor;
    }

    public int getMagickaCost() {
        return (int)(20 * levitationStrength);
    }

    public float getAoeRadius() {
        return isAreaOfEffect ? 4.0f : 0.0f;
    }
} 