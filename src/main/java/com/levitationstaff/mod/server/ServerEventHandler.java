package com.levitationstaff.mod.server;

import com.levitationstaff.mod.capability.LevitationCapability;
import com.levitationstaff.mod.init.ModParticles;
import com.levitationstaff.mod.init.ModSounds;
import com.levitationstaff.mod.item.LevitationStaff;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ServerEventHandler {

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<LivingEntity> event) {
        if (event.getObject() instanceof LivingEntity) {
            event.addCapability(
                com.levitationstaff.mod.LevitationStaffMod.resource("levitation"),
                new LevitationCapability.Provider(event.getObject())
            );
        }
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        entity.getCapability(LevitationCapability.LEVITATION_CAP).ifPresent(cap -> {
            cap.tick();
        });
    }

    @SubscribeEvent
    public static void onCauldronInteraction(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        BlockState state = event.getLevel().getBlockState(event.getPos());
        ItemStack heldItem = player.getItemInHand(event.getHand());

        // Check if it's a water cauldron
        if (state.getBlock() == Blocks.WATER_CAULDRON && heldItem.getItem() instanceof LevitationStaff) {
            LevitationStaff staff = (LevitationStaff) heldItem.getItem();
            
            // Check if player has glowstone dust in inventory
            ItemStack glowstone = findGlowstoneDust(player);
            if (glowstone.isEmpty()) {
                player.displayClientMessage(
                    net.minecraft.network.chat.Component.literal("Need glowstone dust to replenish staff!"),
                    true
                );
                return;
            }
            
            // Check if staff needs replenishing
            int currentMagicka = staff.getCurrentMagicka(heldItem);
            int maxMagicka = staff.getStaffType().getMaxMagicka();
            
            if (currentMagicka >= maxMagicka) {
                player.displayClientMessage(
                    net.minecraft.network.chat.Component.literal("Staff is already fully charged!"),
                    true
                );
                return;
            }
            
            // Check if cauldron is hot (fire/lava underneath)
            if (!isCauldronHot(event.getLevel(), event.getPos())) {
                player.displayClientMessage(
                    net.minecraft.network.chat.Component.literal("Cauldron needs to be hot! Place fire or lava underneath."),
                    true
                );
                return;
            }
            
            // Consume glowstone dust and replenish staff
            glowstone.shrink(1);
            staff.setCurrentMagicka(heldItem, maxMagicka);
            
            // Lower water level
            int waterLevel = state.getValue(LayeredCauldronBlock.LEVEL);
            if (waterLevel > 1) {
                event.getLevel().setBlock(event.getPos(), 
                    state.setValue(LayeredCauldronBlock.LEVEL, waterLevel - 1), 3);
            } else {
                event.getLevel().setBlock(event.getPos(), Blocks.CAULDRON.defaultBlockState(), 3);
            }
            
            // Effects
            event.getLevel().playSound(null, event.getPos(), 
                ModSounds.MAGICKA_REPLENISH.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                
            if (event.getLevel() instanceof ServerLevel serverLevel) {
                // Spawn replenish particles
                for (int i = 0; i < 30; i++) {
                    double x = event.getPos().getX() + 0.2 + Math.random() * 0.6;
                    double y = event.getPos().getY() + 0.8 + Math.random() * 0.4;
                    double z = event.getPos().getZ() + 0.2 + Math.random() * 0.6;
                    
                    serverLevel.sendParticles(ModParticles.MAGICKA_REPLENISH.get(),
                        x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                }
            }
            
            player.displayClientMessage(
                net.minecraft.network.chat.Component.literal("Staff magicka replenished!"),
                true
            );
            
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }

    private static ItemStack findGlowstoneDust(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() == Items.GLOWSTONE_DUST) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    private static boolean isCauldronHot(net.minecraft.world.level.Level level, net.minecraft.core.BlockPos pos) {
        BlockState belowState = level.getBlockState(pos.below());
        return belowState.getBlock() == Blocks.FIRE || 
               belowState.getBlock() == Blocks.LAVA ||
               belowState.getBlock() == Blocks.MAGMA_BLOCK ||
               belowState.getBlock() == Blocks.CAMPFIRE ||
               belowState.getBlock() == Blocks.SOUL_CAMPFIRE;
    }
} 