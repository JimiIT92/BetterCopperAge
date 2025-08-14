package org.hendrix.betterfalldrop;

import net.fabricmc.api.ModInitializer;
import org.hendrix.betterfalldrop.core.*;

/**
 * Hendrix's Better Fall Drop
 * Make Copper Golems interact with Copper Buttons, like originally intended, and boost your builds with new decorative blocks!
 */
public final class BetterFallDrop implements ModInitializer {

    /**
     * The {@link String Mod ID}
     */
    public static final String MOD_ID = "betterfalldrop";

    /**
     * Initialize the mod
     */
    @Override
    public void onInitialize() {
        BFDSounds.register();
        BFDItemGroups.register();
        BFDItems.register();
        BFDBlocks.register();

        BFDEvents.register();
    }

}