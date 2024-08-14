package tech.samgosden.magestones.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import tech.samgosden.magestones.blocks.ModBlocks;

public final class MageStonesItemGroup {
    public static final ItemGroup MAGE_STONES = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.CHARGED_MAGE_STONE))
            .displayName(Text.translatable("itemGroup.magestones.magestones"))
            .entries(((displayContext, entries) -> {
                entries.add(ModItems.CHARGED_MAGE_STONE);
                entries.add(ModItems.DULL_MAGE_STONE);
                entries.add(ModItems.LIGHT_MAGE_STONE);
                entries.add(ModItems.COLD_MAGE_STONE);
                entries.add(ModItems.FORCE_MAGE_STONE);

                entries.add(ModBlocks.CHARGER_BLOCK);
            })).build();

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, new Identifier("magestones", "magestones"), MAGE_STONES);
    }
}
