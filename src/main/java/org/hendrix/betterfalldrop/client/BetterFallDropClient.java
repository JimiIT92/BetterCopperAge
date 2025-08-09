package org.hendrix.betterfalldrop.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;
import org.hendrix.betterfalldrop.BetterFallDrop;
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
                BFDBlocks.IRON_GRATE,
                BFDBlocks.GOLDEN_GRATE
        );
    }
}
