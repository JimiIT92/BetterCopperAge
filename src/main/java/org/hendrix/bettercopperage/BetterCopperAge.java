package org.hendrix.bettercopperage;

import net.fabricmc.api.ModInitializer;
import org.hendrix.bettercopperage.core.*;

/**
 * Hendrix's Better Copper Age
 * Make Copper Golems interact with Copper Buttons, like originally intended, and boost your builds with new decorative blocks!
 */
public final class BetterCopperAge implements ModInitializer {

    /**
     * The {@link String Mod ID}
     */
    public static final String MOD_ID = "bettercopperage";

    /**
     * Initialize the mod
     */
    @Override
    public void onInitialize() {
        BCASounds.register();
        BCAItems.register();
        BCABlocks.register();
        BCABlockEntityTypes.register();
        BCAItemGroups.register();
        BCAEvents.register();
        BCAGameRules.register();
    }

}