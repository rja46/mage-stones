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

    public static final Block MAGE_STONE_CRYSTAL = register(
            new MageCrystalBlock(7, 3, AbstractBlock.Settings.create().nonOpaque()),
            "mage_stone_crystal",
            false);


    public static final Block LIGHT_MAGE_STONE_CRYSTAL = register(
            new LightCrystalBlock(7, 3, AbstractBlock.Settings.create().nonOpaque().luminance(state -> state.get(MageCrystalBlock.ISACTIVE) ? 15:0)),
            "light_mage_stone_crystal",
            false);

    public static final Block COLD_MAGE_STONE_CRYSTAL = register(
            new ColdCrystalBlock(7, 3, AbstractBlock.Settings.create().nonOpaque()),
            "cold_mage_stone_crystal",
            false);

    public static final Block FORCE_MAGE_STONE_CRYSTAL = register(
            new ForceCrystalBlock(7, 3, AbstractBlock.Settings.create().nonOpaque()),
            "force_mage_stone_crystal",
            false);

    public static final Block GRAVITY_MAGE_STONE_CRYSTAL = register(
            new GravityCrystalBlock(7, 3, AbstractBlock.Settings.create().nonOpaque()),
            "gravity_mage_stone_crystal",
            false);

    public static final Block HEAT_MAGE_STONE_CRYSTAL = register(
            new HeatCrystalBlock(7, 3, AbstractBlock.Settings.create().nonOpaque()),
            "heat_mage_stone_crystal",
            false);

    public  static final Block CHARGER_BLOCK = register(
            new ChargerBlock(AbstractBlock.Settings.create().nonOpaque()),
            "charger_block",
            true);

    public static void initialize(){
    }
}
