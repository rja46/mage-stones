package tech.samgosden.magestones.blocks;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import tech.samgosden.magestones.MageStones;

public class BlockEntities {
    public static final BlockEntityType<LightCrystalBlockEntity> LIGHT_CRYSTAL_BLOCK_ENTITY =
            BlockEntityType.Builder.create(
                            LightCrystalBlockEntity::new,
                            ModBlocks.LIGHT_MAGE_STONE_CRYSTAL)
                    .build(null);

    public static final BlockEntityType<ColdCrystalBlockEntity> COLD_CRYSTAL_BLOCK_ENTITY
            = BlockEntityType.Builder.create(
                            ColdCrystalBlockEntity::new, // Corrected here
                            ModBlocks.COLD_MAGE_STONE_CRYSTAL)
                    .build(null);

    public static final BlockEntityType<ForceCrystalBlockEntity> FORCE_CRYSTAL_BLOCK_ENTITY
            = BlockEntityType.Builder.create(
                    ForceCrystalBlockEntity::new, // Corrected here
                    ModBlocks.FORCE_MAGE_STONE_CRYSTAL)
            .build(null);

    public static final BlockEntityType<GravityCrystalBlockEntity> GRAVITY_CRYSTAL_BLOCK_ENTITY
            = BlockEntityType.Builder.create(
                    GravityCrystalBlockEntity::new, // Corrected here
                    ModBlocks.GRAVITY_MAGE_STONE_CRYSTAL)
            .build(null);

    public static void initialize() {
        Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MageStones.MOD_ID, "light_crystal_block_entity"),LIGHT_CRYSTAL_BLOCK_ENTITY
        );

        Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MageStones.MOD_ID, "cold_crystal_block_entity"),COLD_CRYSTAL_BLOCK_ENTITY
        );

        Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MageStones.MOD_ID, "force_crystal_block_entity"),FORCE_CRYSTAL_BLOCK_ENTITY
        );

        Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MageStones.MOD_ID, "gravity_crystal_block_entity"),GRAVITY_CRYSTAL_BLOCK_ENTITY
        );
    }
}
