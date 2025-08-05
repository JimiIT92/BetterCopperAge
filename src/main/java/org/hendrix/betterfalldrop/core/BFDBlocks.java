package org.hendrix.betterfalldrop.core;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.HoneycombItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.hendrix.betterfalldrop.BetterFallDrop;
import org.hendrix.betterfalldrop.block.CopperButtonBlock;
import org.hendrix.betterfalldrop.utils.BlockUtils;
import org.hendrix.betterfalldrop.utils.IdentifierUtils;
import org.hendrix.betterfalldrop.utils.RegistryKeyUtils;

import java.util.function.Supplier;

/**
 * {@link BetterFallDrop Better Fall Drop} {@link Block Blocks}
 */
public final class BFDBlocks {

    //#region Blocks

    public static final Block COPPER_BUTTON = registerCopperButton(Oxidizable.OxidationLevel.UNAFFECTED, false);
    public static final Block EXPOSED_COPPER_BUTTON = registerCopperButton(Oxidizable.OxidationLevel.EXPOSED, false);
    public static final Block WEATHERED_COPPER_BUTTON = registerCopperButton(Oxidizable.OxidationLevel.WEATHERED, false);
    public static final Block OXIDIZED_COPPER_BUTTON = registerCopperButton(Oxidizable.OxidationLevel.OXIDIZED, false);

    public static final Block WAXED_COPPER_BUTTON = registerCopperButton(Oxidizable.OxidationLevel.UNAFFECTED, true);
    public static final Block WAXED_EXPOSED_COPPER_BUTTON = registerCopperButton(Oxidizable.OxidationLevel.EXPOSED, true);
    public static final Block WAXED_WEATHERED_COPPER_BUTTON = registerCopperButton(Oxidizable.OxidationLevel.WEATHERED, true);
    public static final Block WAXED_OXIDIZED_COPPER_BUTTON = registerCopperButton(Oxidizable.OxidationLevel.OXIDIZED, true);

    //#endregion

    /**
     * Register a {@link CopperButtonBlock Copper Button}
     *
     * @param oxidationLevel The {@link Oxidizable.OxidationLevel Oxidation Level}
     * @param isWaxed {@link Boolean Whether the button is waxed}
     * @return The {@link Block registered Block}
     */
    private static Block registerCopperButton(final Oxidizable.OxidationLevel oxidationLevel, final boolean isWaxed) {
        final String name = BlockUtils.copperBlockName(oxidationLevel, isWaxed, "button");
        final AbstractBlock.Settings settings = AbstractBlock.Settings.create().noCollision().strength(0.5F).pistonBehavior(PistonBehavior.DESTROY).registryKey(RegistryKeyUtils.block(name));
        return registerBlock(name, Suppliers.memoize(() -> new CopperButtonBlock(oxidationLevel, isWaxed, settings)));
    }

    /**
     * Register a {@link Block Block}
     *
     * @param name The {@link String Block name}
     * @param blockSupplier The {@link Supplier <Block> Block Supplier}
     * @return The {@link Block registered Block}
     */
    private static Block registerBlock(final String name, final Supplier<Block> blockSupplier) {
        final Block block = registerBlockWithoutBlockItem(name, blockSupplier);
        BFDItems.registerBlockItem(IdentifierUtils.modIdentifier(name), blockSupplier);
        return block;
    }

    /**
     * Register a {@link Block Block}
     *
     * @param name The {@link String Block name}
     * @param blockSupplier The {@link Supplier<Block> Block Supplier}
     * @return The {@link Block registered Block}
     */
    private static Block registerBlockWithoutBlockItem(final String name, final Supplier<Block> blockSupplier) {
        final Identifier identifier = IdentifierUtils.modIdentifier(name);
        return Registry.register(Registries.BLOCK, identifier, blockSupplier.get());
    }

    /**
     * Register all {@link Block Blocks}
     */
    public static void register() {
        registerWaxableBlocks();
    }

    /**
     * Register all {@link Block Waxable Blocks}
     */
    private static void registerWaxableBlocks() {
        final BiMap<Block, Block> unwaxedToWaxedBlocks = HoneycombItem.UNWAXED_TO_WAXED_BLOCKS.get();
        unwaxedToWaxedBlocks.put(COPPER_BUTTON, WAXED_COPPER_BUTTON);
        unwaxedToWaxedBlocks.put(EXPOSED_COPPER_BUTTON, WAXED_EXPOSED_COPPER_BUTTON);
        unwaxedToWaxedBlocks.put(WEATHERED_COPPER_BUTTON, WAXED_WEATHERED_COPPER_BUTTON);
        unwaxedToWaxedBlocks.put(OXIDIZED_COPPER_BUTTON, WAXED_OXIDIZED_COPPER_BUTTON);
    }

}