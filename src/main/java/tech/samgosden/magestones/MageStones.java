package tech.samgosden.magestones;

import net.fabricmc.api.ModInitializer;
import tech.samgosden.magestones.blocks.ModBlocks;
import tech.samgosden.magestones.item.MageStonesItemGroup;
import tech.samgosden.magestones.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MageStones implements ModInitializer {
    public static String MOD_ID = "mage_stones";
    public static String MOD_NAME = "Mage Stones";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitialize() {
        LOGGER.info("[Mage_Stones] Initialized! This mod adds... nothing?");
        ModItems.initialize();
        MageStonesItemGroup.initialize();
        ModBlocks.initialize();
    }
}
