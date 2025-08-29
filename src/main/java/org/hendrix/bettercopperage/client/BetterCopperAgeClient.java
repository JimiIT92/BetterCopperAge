package org.hendrix.bettercopperage.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.CampfireBlockEntityRenderer;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.core.BCABlockEntityTypes;
import org.hendrix.bettercopperage.core.BCABlocks;

/**
 * {@link BetterCopperAge Hendrix's Better Copper Age} {@link ClientModInitializer Client Mod Initializer}
 */
@Environment(EnvType.CLIENT)
public final class BetterCopperAgeClient implements ClientModInitializer {

    /**
     * Initialize the mod's client stuffs
     */
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlocks(
                BlockRenderLayer.CUTOUT,
                BCABlocks.COPPER_FIRE,
                BCABlocks.COPPER_CAMPFIRE,
                BCABlocks.IRON_GRATE,
                BCABlocks.GOLDEN_GRATE,
                BCABlocks.GOLDEN_BARS,
                BCABlocks.GOLDEN_CHAIN,
                BCABlocks.GOLDEN_LANTERN,
                BCABlocks.COPPER_RAIL,
                BCABlocks.EXPOSED_COPPER_RAIL,
                BCABlocks.WEATHERED_COPPER_RAIL,
                BCABlocks.OXIDIZED_COPPER_RAIL,
                BCABlocks.WAXED_COPPER_RAIL,
                BCABlocks.WAXED_EXPOSED_COPPER_RAIL,
                BCABlocks.WAXED_WEATHERED_COPPER_RAIL,
                BCABlocks.WAXED_OXIDIZED_COPPER_RAIL
        );
        BlockEntityRendererFactories.register(BCABlockEntityTypes.CAMPFIRE, CampfireBlockEntityRenderer::new);
    }
}
