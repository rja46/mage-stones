package tech.samgosden.mage_stones;

import net.fabricmc.api.ModInitializer;

public class Mage_stones implements ModInitializer {

    @Override
    public void onInitialize() {
        System.out.println("[Mage_Stones] Initialized! This mod adds... nothing?");
        Mage_stonesItems.initialize();
    }

    public static String MOD_ID = "mage_stones";
}
