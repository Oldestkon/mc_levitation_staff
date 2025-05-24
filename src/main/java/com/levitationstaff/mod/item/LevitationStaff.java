package com.levitationstaff.mod.item;

import com.levitationstaff.mod.capability.LevitationCapability;
import com.levitationstaff.mod.init.ModParticles;
import com.levitationstaff.mod.init.ModSounds;
import com.levitationstaff.mod.network.NetworkHandler;
import com.levitationstaff.mod.network.packet.StaffCastPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class LevitationStaff extends Item {
    private final StaffType staffType;

    public LevitationStaff(StaffType staffType, Properties properties) {
        super(properties);
        this.staffType = staffType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        
        if (!level.isClientSide) {
            HitResult hitResult = player.pick(staffType.getRange(), 0.0F, false);
            
            if (hitResult.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityHit = (EntityHitResult) hitResult;
                if (entityHit.getEntity() instanceof LivingEntity target) {
                    boolean success = castLevitation(level, player, stack, target);
                    
                    // Send packet to client for animation
                    // NetworkHandler.sendToPlayer(new StaffCastPacket(success), player);
                    
                    return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
                }
            } else if (staffType.isAreaOfEffect()) {
                // AOE cast at looked position
                Vec3 lookVec = player.getLookAngle();
                Vec3 castPos = player.getEyePosition().add(lookVec.scale(staffType.getRange() * 0.7));
                
                boolean anySuccess = castAreaLevitation(level, player, stack, castPos);
                //NetworkHandler.sendToPlayer(new StaffCastPacket(anySuccess), player);
                
                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
            }
            
            // Failed cast
            // NetworkHandler.sendToPlayer(new StaffCastPacket(false), player);
        }
        
        return InteractionResultHolder.pass(stack);
    }

    private boolean castLevitation(Level level, Player player, ItemStack stack, LivingEntity target) {
        int currentMagicka = getCurrentMagicka(stack);
        int cost = staffType.getMagickaCost();
        
        if (currentMagicka < cost) {
            player.displayClientMessage(Component.literal("Not enough magicka!").withStyle(ChatFormatting.RED), true);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), 
                SoundEvents.NOTE_BLOCK_BASS, SoundSource.PLAYERS, 0.5F, 0.5F);
            return false;
        }

        // Check if mob is too heavy for this staff
        if (!canLevitate(target)) {
            player.displayClientMessage(Component.literal("Target too heavy for this staff!").withStyle(ChatFormatting.RED), true);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), 
                SoundEvents.NOTE_BLOCK_BASS, SoundSource.PLAYERS, 0.5F, 0.5F);
            return false;
        }

        // Apply levitation effect
        target.getCapability(LevitationCapability.LEVITATION_CAP).ifPresent(cap -> {
            cap.setLevitating(true, 200, staffType.getLevitationStrength(), staffType.getParticleColor());
        });

        // Consume magicka
        setCurrentMagicka(stack, currentMagicka - cost);
        
        // Play success sound and particles
        level.playSound(null, target.getX(), target.getY(), target.getZ(), 
            ModSounds.STAFF_CAST.get(), SoundSource.PLAYERS, 1.0F, 1.0F + (float)(Math.random() * 0.4 - 0.2));
            
        if (level instanceof ServerLevel serverLevel) {
            // Cast particles
            for (int i = 0; i < 20; i++) {
                double offsetX = (Math.random() - 0.5) * 2.0;
                double offsetY = (Math.random() - 0.5) * 2.0;
                double offsetZ = (Math.random() - 0.5) * 2.0;
                
                serverLevel.sendParticles(ModParticles.LEVITATION_CAST.get(),
                    target.getX() + offsetX, target.getY() + 1.0 + offsetY, target.getZ() + offsetZ,
                    1, 0.0, 0.0, 0.0, 0.0);
            }
        }
        
        return true;
    }

    private boolean castAreaLevitation(Level level, Player player, ItemStack stack, Vec3 center) {
        int currentMagicka = getCurrentMagicka(stack);
        float radius = staffType.getAoeRadius();
        
        AABB searchArea = new AABB(center.subtract(radius, radius, radius), center.add(radius, radius, radius));
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, searchArea, 
            entity -> entity != player && entity.distanceToSqr(center) <= radius * radius);
        
        if (targets.isEmpty()) {
            player.displayClientMessage(Component.literal("No targets in range!").withStyle(ChatFormatting.YELLOW), true);
            return false;
        }
        
        int validTargets = 0;
        int totalCost = 0;
        
        // Calculate total cost and count valid targets
        for (LivingEntity target : targets) {
            if (canLevitate(target)) {
                validTargets++;
                totalCost += staffType.getMagickaCost();
            }
        }
        
        if (validTargets == 0) {
            player.displayClientMessage(Component.literal("No valid targets!").withStyle(ChatFormatting.RED), true);
            return false;
        }
        
        if (currentMagicka < totalCost) {
            player.displayClientMessage(Component.literal("Not enough magicka for all targets!").withStyle(ChatFormatting.RED), true);
            return false;
        }
        
        // Apply levitation to all valid targets
        for (LivingEntity target : targets) {
            if (canLevitate(target)) {
                target.getCapability(LevitationCapability.LEVITATION_CAP).ifPresent(cap -> {
                    cap.setLevitating(true, 200, staffType.getLevitationStrength(), staffType.getParticleColor());
                });
                
                if (level instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 15; i++) {
                        double offsetX = (Math.random() - 0.5) * 1.5;
                        double offsetY = (Math.random() - 0.5) * 1.5;
                        double offsetZ = (Math.random() - 0.5) * 1.5;
                        
                        serverLevel.sendParticles(ModParticles.LEVITATION_CAST.get(),
                            target.getX() + offsetX, target.getY() + 1.0 + offsetY, target.getZ() + offsetZ,
                            1, 0.0, 0.0, 0.0, 0.0);
                    }
                }
            }
        }
        
        setCurrentMagicka(stack, currentMagicka - totalCost);
        
        level.playSound(null, center.x, center.y, center.z, 
            ModSounds.STAFF_CAST.get(), SoundSource.PLAYERS, 1.5F, 0.8F);
            
        player.displayClientMessage(Component.literal("Levitated " + validTargets + " targets!").withStyle(ChatFormatting.GREEN), true);
        
        return true;
    }

    private boolean canLevitate(LivingEntity entity) {
        // Bigger mobs need stronger staffs
        float entitySize = entity.getBbWidth() * entity.getBbHeight();
        float maxSize = staffType.getLevitationStrength() * 2.0f; // Each strength level allows 2.0 size units
        return entitySize <= maxSize;
    }

    public int getCurrentMagicka(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt("magicka");
    }

    public void setCurrentMagicka(ItemStack stack, int magicka) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("magicka", Mth.clamp(magicka, 0, staffType.getMaxMagicka()));
    }

    public StaffType getStaffType() {
        return staffType;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F * getCurrentMagicka(stack) / (float)staffType.getMaxMagicka());
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return staffType.getParticleColor();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Type: " + staffType.getName().toUpperCase()).withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.literal("Range: " + (int)staffType.getRange() + " blocks").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.literal("Magicka: " + getCurrentMagicka(stack) + "/" + staffType.getMaxMagicka()).withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.literal("Strength: " + staffType.getLevitationStrength()).withStyle(ChatFormatting.GREEN));
        
        if (staffType.isAreaOfEffect()) {
            tooltip.add(Component.literal("Area of Effect: " + (int)staffType.getAoeRadius() + " blocks").withStyle(ChatFormatting.LIGHT_PURPLE));
        }
        
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.literal(""));
            tooltip.add(Component.literal("Replenish magicka by placing in a hot").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("cauldron with water and adding glowstone dust.").withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.literal("Hold SHIFT for more info").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
} 