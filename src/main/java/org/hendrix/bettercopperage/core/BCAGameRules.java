package org.hendrix.bettercopperage.core;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;
import org.hendrix.bettercopperage.BetterCopperAge;

/**
 * {@link BetterCopperAge Better Copper Age} {@link GameRules Game Rules}
 */
public final class BCAGameRules {

    //#region Game Rules

    public static final GameRules.Key<GameRules.BooleanRule> COPPER_GOLEM_ATTRACTS_LIGHTNING = registerGameRule("copperGolemAttractsLightning");
    public static final GameRules.Key<GameRules.BooleanRule> COPPER_ARMOR_ATTRACTS_LIGHTNING = registerGameRule("copperArmorAttractsLightning");

    //#endregion

    /**
     * Register a {@link GameRules.Rule Game Rule}
     *
     * @param name The {@link String Game Rule name}
     * @return The {@link GameRules.Key registered Game Rule Key}
     */
    public static GameRules.Key<GameRules.BooleanRule> registerGameRule(final String name) {
        return GameRuleRegistry.register(BetterCopperAge.MOD_ID + "." + name, GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
    }

    /**
     * Register all {@link GameRules Game Rules}
     */
    public static void register() {

    }

}