package org.hendrix.bettercopperage.core;


import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.world.rule.*;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.utils.IdentifierUtils;

/**
 * {@link BetterCopperAge Better Copper Age} {@link GameRules Game Rules}
 */
public final class BCAGameRules {

    //#region Game Rules

    public static final GameRule<Boolean> COPPER_GOLEM_ATTRACTS_LIGHTNING = registerGameRule("copper_golem_attracts_lightning");
    public static final GameRule<Boolean> COPPER_ARMOR_ATTRACTS_LIGHTNING = registerGameRule("copper_armor_attracts_lightning");

    //#endregion

    /**
     * Register a {@link GameRule Game Rule}
     *
     * @param name The {@link String Game Rule name}
     * @return The {@link GameRule registered Game Rule Key}
     */
    public static GameRule<Boolean> registerGameRule(final String name) {
        GameRule<Boolean> gameRule = new GameRule<>(
                GameRuleCategory.MISC,
                GameRuleType.BOOL,
                BoolArgumentType.bool(),
                GameRuleVisitor::visitBoolean,
                Codec.BOOL,
                (value) -> value ? 1 : 0,
                true,
                FeatureSet.empty()
        );
        return Registry.register(Registries.GAME_RULE, IdentifierUtils.modIdentifier(name), gameRule);
    }

    /**
     * Register all {@link GameRules Game Rules}
     */
    public static void register() {

    }

}