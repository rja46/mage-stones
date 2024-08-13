package tech.samgosden.magestones.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import tech.samgosden.magestones.MageStones;

public class BlockEntites {
    public static BlockEntityType<MageCrystalBlockEntity> MAGE_CRYSTAL_BLOCK_ENTITY;
    public static void initialize() {
        MAGE_CRYSTAL_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(MageStones.MOD_ID, "MageCrystalBlockEntity"),
                FabricBlockEntityTypeBuilder.create(MageCrystalBlockEntity::new, ModBlocks.CHARGED_MAGE_STONE_CRYSTAL).build(null)
        );
    }
}
