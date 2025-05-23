package com.levitationstaff.mod.client;

import com.levitationstaff.mod.capability.LevitationCapability;
import com.levitationstaff.mod.item.LevitationStaff;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            StaffAnimationHandler.tick();
        }
    }

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() == VanillaGuiOverlay.CROSSHAIR.type()) {
            renderStaffTargeting(event.getPoseStack());
        }
    }

    private static void renderStaffTargeting(PoseStack poseStack) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        
        if (player == null) return;
        
        ItemStack heldItem = player.getMainHandItem();
        if (!(heldItem.getItem() instanceof LevitationStaff)) {
            heldItem = player.getOffhandItem();
            if (!(heldItem.getItem() instanceof LevitationStaff)) {
                return;
            }
        }
        
        LevitationStaff staff = (LevitationStaff) heldItem.getItem();
        HitResult hitResult = mc.hitResult;
        
        if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHit = (EntityHitResult) hitResult;
            if (entityHit.getEntity() instanceof LivingEntity target) {
                double distance = player.distanceTo(target);
                boolean inRange = distance <= staff.getStaffType().getRange();
                boolean canLevitate = canStaffLevitate(staff, heldItem, target);
                boolean hasEnoughMagicka = staff.getCurrentMagicka(heldItem) >= staff.getStaffType().getMagickaCost();
                
                String statusText;
                ChatFormatting color;
                
                if (!inRange) {
                    statusText = "OUT OF RANGE";
                    color = ChatFormatting.RED;
                } else if (!hasEnoughMagicka) {
                    statusText = "NOT ENOUGH MAGICKA";
                    color = ChatFormatting.RED;
                } else if (!canLevitate) {
                    statusText = "TARGET TOO HEAVY";
                    color = ChatFormatting.YELLOW;
                } else {
                    statusText = "READY TO CAST";
                    color = ChatFormatting.GREEN;
                    
                    // Check if already levitating
                    target.getCapability(LevitationCapability.LEVITATION_CAP).ifPresent(cap -> {
                        // This would need to be handled differently due to client/server separation
                        // For now, we'll assume it's available for casting
                    });
                }
                
                int screenWidth = mc.getWindow().getGuiScaledWidth();
                int screenHeight = mc.getWindow().getGuiScaledHeight();
                
                // Render status text above crosshair
                Component text = Component.literal(statusText).withStyle(color);
                int textWidth = mc.font.width(text);
                
                mc.font.drawShadow(poseStack, text, 
                    (screenWidth - textWidth) / 2f, 
                    (screenHeight / 2f) - 20, 
                    color.getColor() != null ? color.getColor() : 0xFFFFFF);
                
                // Render target info
                String targetInfo = target.getDisplayName().getString();
                Component targetText = Component.literal(targetInfo).withStyle(ChatFormatting.WHITE);
                int targetTextWidth = mc.font.width(targetText);
                
                mc.font.drawShadow(poseStack, targetText,
                    (screenWidth - targetTextWidth) / 2f,
                    (screenHeight / 2f) - 32,
                    0xFFFFFF);
            }
        }
        
        // Show staff info
        Component magickaText = Component.literal(
            "Magicka: " + staff.getCurrentMagicka(heldItem) + "/" + staff.getStaffType().getMaxMagicka()
        ).withStyle(ChatFormatting.AQUA);
        
        mc.font.drawShadow(poseStack, magickaText, 10, 10, 0x00FFFF);
    }

    private static boolean canStaffLevitate(LevitationStaff staff, ItemStack stack, LivingEntity entity) {
        // This mirrors the logic from the staff item
        float entitySize = entity.getBbWidth() * entity.getBbHeight();
        float maxSize = staff.getStaffType().getLevitationStrength() * 2.0f;
        return entitySize <= maxSize;
    }
} 