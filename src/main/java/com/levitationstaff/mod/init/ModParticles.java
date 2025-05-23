package com.levitationstaff.mod.init;

import com.levitationstaff.mod.LevitationStaffMod;
import com.levitationstaff.mod.client.particle.LevitationCastParticle;
import com.levitationstaff.mod.client.particle.LevitationIdleParticle;
import com.levitationstaff.mod.client.particle.MagickaReplenishParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = 
        DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, LevitationStaffMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> LEVITATION_CAST = PARTICLES.register("levitation_cast",
        () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> LEVITATION_IDLE = PARTICLES.register("levitation_idle",
        () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> MAGICKA_REPLENISH = PARTICLES.register("magicka_replenish",
        () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = LevitationStaffMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientParticleEvents {
        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
            event.register(LEVITATION_CAST.get(), LevitationCastParticle.Provider::new);
            event.register(LEVITATION_IDLE.get(), LevitationIdleParticle.Provider::new);
            event.register(MAGICKA_REPLENISH.get(), MagickaReplenishParticle.Provider::new);
        }
    }
} 