package tech.samgosden.magestones.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BlockEntites {
    public static BlockEntityType<MageCrystalBlockEntity> MAGE_CRYSTAL_BLOCK_ENTITY;
    public static BlockEntityType<LightCrystalBlockEntity> LIGHT_CRYSTAL_BLOCK_ENTITY;
    public static BlockEntityType<LightCrystalBlockEntity> COLD_CRYSTAL_BLOCK_ENTITY;
    public static void initialize() {
        LIGHT_CRYSTAL_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE, "mage_stones:light_crystal_block_entity", FabricBlockEntityTypeBuilder.create(LightCrystalBlockEntity::new, ModBlocks.LIGHT_MAGE_STONE_CRYSTAL).build(null)
        );
        COLD_CRYSTAL_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE, "mage_stones:cold_crystal_block_entity", FabricBlockEntityTypeBuilder.create(LightCrystalBlockEntity::new, ModBlocks.COLD_MAGE_STONE_CRYSTAL).build(null));
    }
}
