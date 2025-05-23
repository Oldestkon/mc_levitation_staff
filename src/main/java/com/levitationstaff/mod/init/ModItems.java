package com.levitationstaff.mod.init;

import com.levitationstaff.mod.LevitationStaffMod;
import com.levitationstaff.mod.item.LevitationStaff;
import com.levitationstaff.mod.item.StaffType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = 
        DeferredRegister.create(ForgeRegistries.ITEMS, LevitationStaffMod.MOD_ID);

    // Staff items for each type
    public static final RegistryObject<LevitationStaff> NOVICE_STAFF = ITEMS.register("novice_levitation_staff",
        () -> new LevitationStaff(StaffType.NOVICE, 
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1).rarity(Rarity.COMMON)) {
                @Override
                public void onCraftedBy(net.minecraft.world.item.ItemStack stack, net.minecraft.world.level.Level level, net.minecraft.world.entity.player.Player player) {
                    setCurrentMagicka(stack, getStaffType().getMaxMagicka());
                }
            });

    public static final RegistryObject<LevitationStaff> APPRENTICE_STAFF = ITEMS.register("apprentice_levitation_staff",
        () -> new LevitationStaff(StaffType.APPRENTICE, 
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1).rarity(Rarity.UNCOMMON)) {
                @Override
                public void onCraftedBy(net.minecraft.world.item.ItemStack stack, net.minecraft.world.level.Level level, net.minecraft.world.entity.player.Player player) {
                    setCurrentMagicka(stack, getStaffType().getMaxMagicka());
                }
            });

    public static final RegistryObject<LevitationStaff> ADEPT_STAFF = ITEMS.register("adept_levitation_staff",
        () -> new LevitationStaff(StaffType.ADEPT, 
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1).rarity(Rarity.RARE)) {
                @Override
                public void onCraftedBy(net.minecraft.world.item.ItemStack stack, net.minecraft.world.level.Level level, net.minecraft.world.entity.player.Player player) {
                    setCurrentMagicka(stack, getStaffType().getMaxMagicka());
                }
            });

    public static final RegistryObject<LevitationStaff> EXPERT_STAFF = ITEMS.register("expert_levitation_staff",
        () -> new LevitationStaff(StaffType.EXPERT, 
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1).rarity(Rarity.EPIC)) {
                @Override
                public void onCraftedBy(net.minecraft.world.item.ItemStack stack, net.minecraft.world.level.Level level, net.minecraft.world.entity.player.Player player) {
                    setCurrentMagicka(stack, getStaffType().getMaxMagicka());
                }
            });

    public static final RegistryObject<LevitationStaff> MASTER_STAFF = ITEMS.register("master_levitation_staff",
        () -> new LevitationStaff(StaffType.MASTER, 
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1).rarity(Rarity.EPIC)) {
                @Override
                public void onCraftedBy(net.minecraft.world.item.ItemStack stack, net.minecraft.world.level.Level level, net.minecraft.world.entity.player.Player player) {
                    setCurrentMagicka(stack, getStaffType().getMaxMagicka());
                }
            });

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
} 