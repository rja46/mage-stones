package tech.samgosden.magestones.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import tech.samgosden.magestones.MageCrystalItem;
import tech.samgosden.magestones.MageStones;

public class ModItems {

    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        Identifier itemID = new Identifier(MageStones.MOD_ID, id);

        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        // Return the registered item!
        return registeredItem;
    }

    public static final Item DULL_MAGE_STONE = register(
            new Item(new FabricItemSettings()),
            "dull_mage_stone");

    public static final Item CHARGED_MAGE_STONE = register(
            new MageCrystalItem(new FabricItemSettings().rarity(Rarity.EPIC)),
            "charged_mage_stone");


    public static void initialize(){
    }
}


