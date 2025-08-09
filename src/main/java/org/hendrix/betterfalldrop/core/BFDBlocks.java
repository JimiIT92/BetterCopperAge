package org.hendrix.betterfalldrop.core;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.HoneycombItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.hendrix.betterfalldrop.BetterFallDrop;
import org.hendrix.betterfalldrop.block.*;
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

    public static final Block MEDIUM_WEIGHTED_PRESSURE_PLATE = registerCopperPressurePlate(Oxidizable.OxidationLevel.UNAFFECTED, false);
    public static final Block EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = registerCopperPressurePlate(Oxidizable.OxidationLevel.EXPOSED, false);
    public static final Block WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = registerCopperPressurePlate(Oxidizable.OxidationLevel.WEATHERED, false);
    public static final Block OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = registerCopperPressurePlate(Oxidizable.OxidationLevel.OXIDIZED, false);

    public static final Block WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE = registerCopperPressurePlate(Oxidizable.OxidationLevel.UNAFFECTED, true);
    public static final Block WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = registerCopperPressurePlate(Oxidizable.OxidationLevel.EXPOSED, true);
    public static final Block WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = registerCopperPressurePlate(Oxidizable.OxidationLevel.WEATHERED, true);
    public static final Block WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = registerCopperPressurePlate(Oxidizable.OxidationLevel.OXIDIZED, true);

    public static final Block COPPER_FIRE = registerCopperFire();

    public static final Block CHISELED_IRON = registerChiseledBlock("iron", Blocks.IRON_BLOCK);
    public static final Block IRON_GRATE = registerGrate("iron", MapColor.IRON_GRAY, BlockSoundGroup.IRON);
    public static final Block CUT_IRON = registerCutBlock("iron", Blocks.IRON_BLOCK);
    public static final Block CUT_IRON_SLAB = registerCutSlab("iron", Suppliers.memoize(() -> CUT_IRON));
    public static final Block CUT_IRON_STAIRS = registerCutStairs("iron", Suppliers.memoize(() -> CUT_IRON));
    public static final Block IRON_BUTTON = registerButton("iron", BlockSetType.IRON, 15);

    public static final Block CHISELED_GOLD = registerChiseledBlock("gold", Blocks.GOLD_BLOCK);
    public static final Block GOLDEN_GRATE = registerGrate("golden", MapColor.GOLD, BlockSoundGroup.METAL);
    public static final Block CUT_GOLD = registerCutBlock("gold", Blocks.GOLD_BLOCK);
    public static final Block CUT_GOLDEN_SLAB = registerCutSlab("golden", Suppliers.memoize(() -> CUT_GOLD));
    public static final Block CUT_GOLDEN_STAIRS = registerCutStairs("golden", Suppliers.memoize(() -> CUT_GOLD));
    public static final Block GOLD_BUTTON = registerButton("gold", BlockSetType.GOLD, 5);

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
        final AbstractBlock.Settings settings = buttonSettings(name);
        return registerBlock(name, Suppliers.memoize(() -> isWaxed ? new CopperButtonBlock(oxidationLevel, settings) : new OxidizableCopperButtonBlock(oxidationLevel, settings)));
    }

    /**
     * Register a {@link MediumWeightedPressurePlateBlock Copper Pressure Plate}
     *
     * @param oxidationLevel The {@link Oxidizable.OxidationLevel Oxidation Level}
     * @param isWaxed {@link Boolean Whether the button is waxed}
     * @return The {@link Block registered Block}
     */
    private static Block registerCopperPressurePlate(final Oxidizable.OxidationLevel oxidationLevel, final boolean isWaxed) {
        final String name = BlockUtils.oxidizableBlockName(oxidationLevel, isWaxed, "medium_weighted", "pressure_plate");
        final AbstractBlock.Settings settings = AbstractBlock.Settings.create().mapColor(BlockUtils.oxidizableMapColor(oxidationLevel)).solid().noCollision().strength(0.5F).pistonBehavior(PistonBehavior.DESTROY).registryKey(RegistryKeyUtils.block(name));
        return registerBlock(name, Suppliers.memoize(() -> isWaxed ? new MediumWeightedPressurePlateBlock(oxidationLevel, settings) : new OxidizableMediumWeightedPressurePlateBlock(oxidationLevel, settings)));
    }

    /**
     * Register the {@link CopperFireBlock Copper Fire Block}
     *
     * @return The {@link Block registered Block}
     */
    private static Block registerCopperFire() {
        final String name = "copper_fire";
        final AbstractBlock.Settings settings = AbstractBlock.Settings.create().mapColor(MapColor.EMERALD_GREEN).replaceable().noCollision().breakInstantly().luminance((state) -> 13).sounds(BlockSoundGroup.WOOL).pistonBehavior(PistonBehavior.DESTROY).registryKey(RegistryKeyUtils.block(name));
        return registerBlockWithoutBlockItem(name, Suppliers.memoize(() -> new CopperFireBlock(settings)));
    }

    /**
     * Register a {@link Block Button}
     *
     * @param materialName The {@link String Block base material Name}
     * @param blockSetType The {@link BlockSetType Block Set Type}
     * @param pressTicks The {@link Integer Button Press Ticks}
     * @return The {@link Block registered Block}
     */
    private static Block registerButton(final String materialName, final BlockSetType blockSetType, final int pressTicks) {
        final String name = materialName + "_button";
        final AbstractBlock.Settings settings = buttonSettings(name);
        return registerBlock(name, Suppliers.memoize(() -> new ButtonBlock(blockSetType, pressTicks, settings)));
    }

    /**
     * Register a {@link GrateBlock Grate Block}
     *
     * @param materialName The {@link String Block base material Name}
     * @param mapColor The {@link MapColor Block Map Color}
     * @param soundGroup The {@link BlockSoundGroup Block Sound Group}
     * @return The {@link Block registered Block}
     */
    private static Block registerGrate(final String materialName, final MapColor mapColor, final BlockSoundGroup soundGroup) {
        final String name = materialName + "_grate";
        final AbstractBlock.Settings settings = AbstractBlock.Settings.create()
                .strength(3.0F, 6.0F)
                .sounds(soundGroup)
                .mapColor(mapColor)
                .nonOpaque()
                .requiresTool()
                .allowsSpawning(Blocks::never)
                .solidBlock(Blocks::never)
                .suffocates(Blocks::never)
                .blockVision(Blocks::never)
                .registryKey(RegistryKeyUtils.block(name));
        return registerBlock(name, Suppliers.memoize(() -> new GrateBlock(settings)));
    }

    /**
     * Register a {@link Block Cut Block}
     *
     * @param materialName The {@link String Block base material Name}
     * @param sourceBlock The {@link Block Source Block}
     * @return The {@link Block registered Block}
     */
    private static Block registerCutBlock(final String materialName, final Block sourceBlock) {
        final String name = "cut_" + materialName;
        final AbstractBlock.Settings settings = AbstractBlock.Settings.copy(sourceBlock).registryKey(RegistryKeyUtils.block(name));
        return registerBlock(name, Suppliers.memoize(() -> new Block(settings)));
    }

    /**
     * Register a {@link Block Chiseled Block}
     *
     * @param materialName The {@link String Block base material Name}
     * @param sourceBlock The {@link Block Source Block}
     * @return The {@link Block registered Block}
     */
    private static Block registerChiseledBlock(final String materialName, final Block sourceBlock) {
        final String name = "chiseled_" + materialName;
        final AbstractBlock.Settings settings = AbstractBlock.Settings.copy(sourceBlock).registryKey(RegistryKeyUtils.block(name));
        return registerBlock(name, Suppliers.memoize(() -> new Block(settings)));
    }

    /**
     * Register a {@link Block Cut Slab}
     *
     * @param materialName The {@link String Block base material Name}
     * @param sourceBlockSupplier The {@link Supplier<Block> Source Block Supplier}
     * @return The {@link Block registered Block}
     */
    private static Block registerCutSlab(final String materialName, final Supplier<Block> sourceBlockSupplier) {
        final String name = "cut_" + materialName + "_slab";
        final AbstractBlock.Settings settings = AbstractBlock.Settings.copy(sourceBlockSupplier.get()).registryKey(RegistryKeyUtils.block(name));
        return registerBlock(name, Suppliers.memoize(() -> new SlabBlock(settings)));
    }

    /**
     * Register a {@link Block Cut Stairs}
     *
     * @param materialName The {@link String Block base material Name}
     * @param sourceBlockSupplier The {@link Supplier<Block> Source Block Supplier}
     * @return The {@link Block registered Block}
     */
    private static Block registerCutStairs(final String materialName, final Supplier<Block> sourceBlockSupplier) {
        final String name = "cut_" + materialName + "_stairs";
        final AbstractBlock.Settings settings = AbstractBlock.Settings.copy(sourceBlockSupplier.get()).registryKey(RegistryKeyUtils.block(name));
        return registerBlock(name, Suppliers.memoize(() -> new StairsBlock(sourceBlockSupplier.get().getDefaultState(), settings)));
    }

    /**
     * Get the {@link AbstractBlock.Settings Block Settings} for a {@link ButtonBlock Button Block}
     *
     * @param name The {@link String Block Name}
     * @return The {@link AbstractBlock.Settings Block Settings}
     */
    private static AbstractBlock.Settings buttonSettings(final String name) {
        return AbstractBlock.Settings.create().noCollision().strength(0.5F).pistonBehavior(PistonBehavior.DESTROY).registryKey(RegistryKeyUtils.block(name));
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
        unwaxedToWaxedBlocks.put(MEDIUM_WEIGHTED_PRESSURE_PLATE, WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
        unwaxedToWaxedBlocks.put(EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE, WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
        unwaxedToWaxedBlocks.put(WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE, WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
        unwaxedToWaxedBlocks.put(OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE, WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE);
    }

}