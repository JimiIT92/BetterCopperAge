package org.hendrix.bettercopperage.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.CampfireRenderer;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.core.BCABlockEntityTypes;

/**
 * {@link BetterCopperAge} {@link ClientModInitializer}
 */
@Environment(EnvType.CLIENT)
public final class BetterCopperAgeClient implements ClientModInitializer {

    /**
     * Initialize the mod's client stuffs
     */
    @Override
    public void onInitializeClient() {
        BlockEntityRenderers.register(BCABlockEntityTypes.COPPER_CAMPFIRE, CampfireRenderer::new);
    }

}