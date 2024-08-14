package tech.samgosden.magestones;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import tech.samgosden.magestones.blocks.BlockEntities;
import tech.samgosden.magestones.blocks.ModBlocks;
import tech.samgosden.magestones.item.MageStonesItemGroup;
import tech.samgosden.magestones.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.samgosden.magestones.util.ItemTagger;

public class MageStones implements ModInitializer {
    public static String MOD_ID = "mage_stones";
    public static String MOD_NAME = "Mage Stones";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);


    @Override
    public void onInitialize() {
        LOGGER.info("Initialized! This mod adds... nothing?");
        ModItems.initialize();
        MageStonesItemGroup.initialize();
        ModBlocks.initialize();
        BlockEntities.initialize();
        ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
            ItemTagger itemTagger = new ItemTagger();
            itemTagger.tagIronRelatedItems(server, server.getRegistryManager());
        });
    }
}
