package tech.samgosden.magestones.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import tech.samgosden.magestones.MageStones;

public class ModBlocks {
    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        Identifier id = Identifier.of(MageStones.MOD_ID, name);
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }
        return Registry.register(Registries.BLOCK, id, block);
    }

    public static final Block CHARGED_MAGE_STONE_CRYSTAL = register(
            new MageCrystalBlock(7, 3, AbstractBlock.Settings.create().nonOpaque()),
            "charged_mage_stone_crystal",
            false);

    public static final Block DULL_MAGE_STONE_CRYSTAL = register(
            new MageCrystalBlock(7, 3, AbstractBlock.Settings.create().nonOpaque()),
            "dull_mage_stone_crystal",
            false);

    public  static final Block CHARGER_BLOCK = register(
            new ChargerBlock(AbstractBlock.Settings.create()),
            "charger_block",
            true);

    public static void initialize(){
    }
}
