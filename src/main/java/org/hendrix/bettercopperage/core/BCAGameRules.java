package org.hendrix.bettercopperage.core;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.utils.IdentifierUtils;

/**
 * {@link BetterCopperAge} {@link GameRule Game Rules}
 */
public final class BCAGameRules {

    //#region Game Rules

    public static final GameRule<Boolean> COPPER_GOLEM_ATTRACTS_LIGHTNING = register("copper_golem_attracts_lightning");
    public static final GameRule<Boolean> COPPER_ARMOR_ATTRACTS_LIGHTNING = register("copper_armor_attracts_lightning");

    //#endregion

    /**
     * Register a game rule
     *
     * @param name The game rule name
     * @return The registered {@link GameRule}
     */
    private static GameRule<Boolean> register(final String name) {
        return GameRuleBuilder
                .forBoolean(true)
                .category(GameRuleCategory.MISC)
                .buildAndRegister(IdentifierUtils.modded(name));
    }

    /**
     * Register all game rules
     */
    public static void register() {

    }

}