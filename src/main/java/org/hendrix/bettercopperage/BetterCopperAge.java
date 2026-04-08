package org.hendrix.bettercopperage;

import net.fabricmc.api.ModInitializer;
import org.hendrix.bettercopperage.core.BCABlocks;
import org.hendrix.bettercopperage.core.BCAEvents;
import org.hendrix.bettercopperage.core.BCAItems;
import org.hendrix.bettercopperage.core.BCASounds;

/**
 * Hendrix's Better Copper Age.<br/>
 * Make Copper Golems interact with Copper Buttons, like originally intended,
 * and boost your builds with new decorative blocks!
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

        BCAEvents.register();
    }

}