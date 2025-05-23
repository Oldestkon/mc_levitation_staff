package com.levitationstaff.mod.init;

import com.levitationstaff.mod.LevitationStaffMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = 
        DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LevitationStaffMod.MOD_ID);

    public static final RegistryObject<SoundEvent> STAFF_CAST = SOUNDS.register("staff_cast",
        () -> new SoundEvent(new ResourceLocation(LevitationStaffMod.MOD_ID, "staff_cast")));

    public static final RegistryObject<SoundEvent> STAFF_CAST_FAIL = SOUNDS.register("staff_cast_fail",
        () -> new SoundEvent(new ResourceLocation(LevitationStaffMod.MOD_ID, "staff_cast_fail")));

    public static final RegistryObject<SoundEvent> MAGICKA_REPLENISH = SOUNDS.register("magicka_replenish",
        () -> new SoundEvent(new ResourceLocation(LevitationStaffMod.MOD_ID, "magicka_replenish")));

    public static final RegistryObject<SoundEvent> LEVITATION_AMBIENT = SOUNDS.register("levitation_ambient",
        () -> new SoundEvent(new ResourceLocation(LevitationStaffMod.MOD_ID, "levitation_ambient")));

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
} 