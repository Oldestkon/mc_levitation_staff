package com.levitationstaff.mod.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LevitationCapability {
    public static final Capability<ILevitationCapability> LEVITATION_CAP = 
        CapabilityManager.get(new CapabilityToken<>() {});

    public static class Provider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
        private final ILevitationCapability capability;
        private final LazyOptional<ILevitationCapability> optional;

        public Provider(LivingEntity entity) {
            this.capability = new LevitationCapabilityImpl(entity);
            this.optional = LazyOptional.of(() -> capability);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return cap == LEVITATION_CAP ? optional.cast() : LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            return ((LevitationCapabilityImpl) capability).serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            ((LevitationCapabilityImpl) capability).deserializeNBT(nbt);
        }
    }
} 