package com.levitationstaff.mod;

import com.levitationstaff.mod.init.ModItems;
import com.levitationstaff.mod.init.ModParticles;
import com.levitationstaff.mod.init.ModSounds;
import com.levitationstaff.mod.network.NetworkHandler;
import com.levitationstaff.mod.client.ClientEventHandler;
import com.levitationstaff.mod.server.ServerEventHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("levitationstaff")
public class LevitationStaffMod {
    public static final String MOD_ID = "levitationstaff";
    public static final Logger LOGGER = LogManager.getLogger();

    public LevitationStaffMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        // Register mod components
        ModItems.register(modEventBus);
        ModParticles.register(modEventBus);
        ModSounds.register(modEventBus);
        
        // Setup events
        modEventBus.addListener(this::setup);
        
        // Register event handlers
        MinecraftForge.EVENT_BUS.register(new ServerEventHandler());
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> 
            MinecraftForge.EVENT_BUS.register(new ClientEventHandler()));
    }

    private void setup(final FMLCommonSetupEvent event) {
        NetworkHandler.register();
        LOGGER.info("Levitation Staff mod setup complete!");
    }
    
    public static ResourceLocation resource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
} 