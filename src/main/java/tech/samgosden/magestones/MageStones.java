package tech.samgosden.magestones;

import net.fabricmc.api.ModInitializer;
import tech.samgosden.magestones.blocks.ModBlocks;
import tech.samgosden.magestones.items.ModItems;

public class MageStones implements ModInitializer {
    public static String MOD_ID = "mage_stones";
    public static String MOD_NAME = "Mage Stones";


    @Override
    public void onInitialize() {
        System.out.println("[Mage_Stones] Initialized! This mod adds... nothing?");
        ModItems.initialize();
        MageStonesItemGroup.initialize();
        ModBlocks.initialize();
    }
}
