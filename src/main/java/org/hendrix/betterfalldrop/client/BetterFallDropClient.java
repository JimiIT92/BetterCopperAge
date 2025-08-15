package org.hendrix.betterfalldrop.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.CampfireBlockEntityRenderer;
import org.hendrix.betterfalldrop.BetterFallDrop;
import org.hendrix.betterfalldrop.core.BFDBlockEntityTypes;
import org.hendrix.betterfalldrop.core.BFDBlocks;

/**
 * {@link BetterFallDrop Hendrix's Better Fall Drop} {@link ClientModInitializer Client Mod Initializer}
 */
@Environment(EnvType.CLIENT)
public final class BetterFallDropClient implements ClientModInitializer {

    /**
     * Initialize the mod's client stuffs
     */
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlocks(
                BlockRenderLayer.CUTOUT,
                BFDBlocks.COPPER_FIRE,
                BFDBlocks.COPPER_CAMPFIRE,
                BFDBlocks.IRON_GRATE,
                BFDBlocks.GOLDEN_GRATE,
                BFDBlocks.GOLDEN_BARS,
                BFDBlocks.GOLDEN_CHAIN,
                BFDBlocks.GOLDEN_LANTERN
        );
        BlockEntityRendererFactories.register(BFDBlockEntityTypes.CAMPFIRE, CampfireBlockEntityRenderer::new);
    }
}
