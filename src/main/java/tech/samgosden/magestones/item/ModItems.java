package tech.samgosden.magestones.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import tech.samgosden.magestones.MageStones;
import tech.samgosden.magestones.blocks.ModBlocks;
import tech.samgosden.magestones.util.ConfigHandler;

public class ModItems {

    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        Identifier itemID = new Identifier(MageStones.MOD_ID, id);

        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        // Return the registered item!
        return registeredItem;
    }


    public static final Item MAGE_STONE = register(
            new MageCrystalItem(new FabricItemSettings()
                    .rarity(Rarity.EPIC)
                    .maxDamage(ConfigHandler.config
                            .getInt("magestones.DefaultCrystalTicksLeft")),
                    ModBlocks.MAGE_STONE_CRYSTAL),
            "mage_stone");

    public static final Item LIGHT_MAGE_STONE = register(
            new MageCrystalItem(new FabricItemSettings()
                    .rarity(Rarity.EPIC)
                    .maxDamage(ConfigHandler.config
                            .getInt("magestones.DefaultCrystalTicksLeft")),
                    ModBlocks.LIGHT_MAGE_STONE_CRYSTAL),
            "light_mage_stone");

    public static final Item COLD_MAGE_STONE = register(
            new MageCrystalItem(new FabricItemSettings()
                    .rarity(Rarity.EPIC)
                    .maxDamage(ConfigHandler.config
                            .getInt("magestones.DefaultCrystalTicksLeft")),
                    ModBlocks.COLD_MAGE_STONE_CRYSTAL),
            "cold_mage_stone");

    public static final Item FORCE_MAGE_STONE = register(
            new MageCrystalItem(new FabricItemSettings()
                    .rarity(Rarity.EPIC)
                    .maxDamage(ConfigHandler.config
                            .getInt("magestones.DefaultCrystalTicksLeft")),
                    ModBlocks.FORCE_MAGE_STONE_CRYSTAL),
            "force_mage_stone");

    public static final Item GRAVITY_MAGE_STONE = register(
            new MageCrystalItem(new FabricItemSettings()
                    .rarity(Rarity.EPIC)
                    .maxDamage(ConfigHandler.config
                            .getInt("magestones.DefaultCrystalTicksLeft")),
                    ModBlocks.GRAVITY_MAGE_STONE_CRYSTAL),
            "gravity_mage_stone");

    public static final Item HEAT_MAGE_STONE = register(
            new MageCrystalItem(new FabricItemSettings()
                    .rarity(Rarity.EPIC)
                    .maxDamage(ConfigHandler.config
                            .getInt("magestones.DefaultCrystalTicksLeft")),
                    ModBlocks.HEAT_MAGE_STONE_CRYSTAL),
            "heat_mage_stone");

    public static final Item GHOUL_MAGE_STONE = register(
            new MageCrystalItem(new FabricItemSettings()
                    .rarity(Rarity.EPIC)
                    .maxDamage(ConfigHandler.config
                            .getInt("magestones.DefaultCrystalTicksLeft")),
                    ModBlocks.GHOUL_MAGE_STONE_CRYSTAL),
            "ghoul_mage_stone");


    public static void initialize(){
    }
}


