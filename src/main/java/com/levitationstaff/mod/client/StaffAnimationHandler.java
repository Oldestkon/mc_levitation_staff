package com.levitationstaff.mod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StaffAnimationHandler {
    private static boolean isAnimating = false;
    private static boolean lastCastSuccessful = false;
    private static int animationTicks = 0;
    private static final int ANIMATION_DURATION = 30; // 1.5 seconds

    public static void handleCastAnimation(boolean successful) {
        isAnimating = true;
        lastCastSuccessful = successful;
        animationTicks = 0;
    }

    public static void tick() {
        if (isAnimating) {
            animationTicks++;
            if (animationTicks >= ANIMATION_DURATION) {
                isAnimating = false;
                animationTicks = 0;
            }
        }
    }

    public static boolean isAnimating() {
        return isAnimating;
    }

    public static boolean wasLastCastSuccessful() {
        return lastCastSuccessful;
    }

    public static float getAnimationProgress() {
        if (!isAnimating) return 0.0f;
        return (float) animationTicks / ANIMATION_DURATION;
    }

    public static float getArmRotation(HumanoidArm arm, boolean isMainHand) {
        if (!isAnimating || (isMainHand && arm != getMainHandArm())) {
            return 0.0f;
        }

        float progress = getAnimationProgress();
        
        if (lastCastSuccessful) {
            // Successful cast: smooth upward then downward motion
            if (progress < 0.3f) {
                // Raise arm
                return -60.0f * (progress / 0.3f);
            } else if (progress < 0.7f) {
                // Hold position with slight magical tremor
                float tremor = 5.0f * (float)Math.sin((progress - 0.3f) * 50.0f);
                return -60.0f + tremor;
            } else {
                // Lower arm
                float lowerProgress = (progress - 0.7f) / 0.3f;
                return -60.0f * (1.0f - lowerProgress);
            }
        } else {
            // Failed cast: quick jerky motion
            if (progress < 0.5f) {
                return -30.0f * (progress / 0.5f);
            } else {
                float lowerProgress = (progress - 0.5f) / 0.5f;
                return -30.0f * (1.0f - lowerProgress);
            }
        }
    }

    private static HumanoidArm getMainHandArm() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return HumanoidArm.RIGHT;
        return player.getMainArm();
    }

    public static float getStaffGlow() {
        if (!isAnimating) return 0.0f;
        
        float progress = getAnimationProgress();
        if (lastCastSuccessful) {
            // Bright glow that fades
            return Math.max(0.0f, 1.0f - progress);
        } else {
            // Quick flash
            if (progress < 0.2f) {
                return 0.5f;
            } else {
                return 0.0f;
            }
        }
    }
} 