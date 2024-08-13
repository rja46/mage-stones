package tech.samgosden.magestones.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import tech.samgosden.magestones.blocks.ModBlocks;

@Environment(EnvType.CLIENT)
public class MageStonesCutout {
    public static void registerCutouts(){
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHARGED_MAGE_STONE_CRYSTAL, RenderLayer.getCutout());    }
}