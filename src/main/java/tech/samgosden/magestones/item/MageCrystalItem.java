package tech.samgosden.magestones.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;


public class MageCrystalItem extends BlockItem {
    public MageCrystalItem(Settings settings, Block block) {
        super(block, settings);
    }

    @Override
    public boolean isDamageable() {
        return true; // This makes the item damageable
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return stack.hasNbt() && stack.getNbt().contains("durability");
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        if (stack.hasNbt() && stack.getNbt().contains("durability")) {
            int durability = stack.getNbt().getInt("durability");
            int maxDurability = stack.getMaxDamage(); // Replace with your max durability
            return Math.round(13.0F - (float) (maxDurability-durability) * 13.0F / (float) maxDurability);
        }
        return super.getItemBarStep(stack);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return MathHelper.hsvToRgb(Math.max(0.0F, (float) stack.getDamage() / (float) stack.getMaxDamage()) / 3.0F, 1.0F, 1.0F);
    }

}
