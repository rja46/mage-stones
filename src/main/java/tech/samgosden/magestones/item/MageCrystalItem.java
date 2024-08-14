package tech.samgosden.magestones.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;



public class MageCrystalItem extends BlockItem {
    public MageCrystalItem(Settings settings, Block block) {
        super(block, settings);
    }

    @Override
    public boolean isDamageable() {
        return true; // This makes the item damageable
    }


}
