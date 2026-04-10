package org.hendrix.bettercopperage.core;

import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.block.*;
import org.hendrix.bettercopperage.utils.BlockUtils;
import org.hendrix.bettercopperage.utils.IdentifierUtils;

import java.util.function.Function;

/**
 * {@link BetterCopperAge} {@link Block Blocks}
 */
public final class BCABlocks {

    //#region Blocks

    public static final Block COPPER_BUTTON = registerCopperButton(WeatheringCopper.WeatherState.UNAFFECTED, false);
    public static final Block EXPOSED_COPPER_BUTTON = registerCopperButton(WeatheringCopper.WeatherState.EXPOSED, false);
    public static final Block WEATHERED_COPPER_BUTTON = registerCopperButton(WeatheringCopper.WeatherState.WEATHERED, false);
    public static final Block OXIDIZED_COPPER_BUTTON = registerCopperButton(WeatheringCopper.WeatherState.OXIDIZED, false);

    public static final Block WAXED_COPPER_BUTTON = registerCopperButton(WeatheringCopper.WeatherState.UNAFFECTED, true);
    public static final Block WAXED_EXPOSED_COPPER_BUTTON = registerCopperButton(WeatheringCopper.WeatherState.EXPOSED, true);
    public static final Block WAXED_WEATHERED_COPPER_BUTTON = registerCopperButton(WeatheringCopper.WeatherState.WEATHERED, true);
    public static final Block WAXED_OXIDIZED_COPPER_BUTTON = registerCopperButton(WeatheringCopper.WeatherState.OXIDIZED, true);

    public static final Block MEDIUM_WEIGHTED_PRESSURE_PLATE = registerMediumWeightedPressurePlate(WeatheringCopper.WeatherState.UNAFFECTED, false);
    public static final Block EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = registerMediumWeightedPressurePlate(WeatheringCopper.WeatherState.EXPOSED, false);
    public static final Block WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = registerMediumWeightedPressurePlate(WeatheringCopper.WeatherState.WEATHERED, false);
    public static final Block OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = registerMediumWeightedPressurePlate(WeatheringCopper.WeatherState.OXIDIZED, false);

    public static final Block WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE = registerMediumWeightedPressurePlate(WeatheringCopper.WeatherState.UNAFFECTED, true);
    public static final Block WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE = registerMediumWeightedPressurePlate(WeatheringCopper.WeatherState.EXPOSED, true);
    public static final Block WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE = registerMediumWeightedPressurePlate(WeatheringCopper.WeatherState.WEATHERED, true);
    public static final Block WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE = registerMediumWeightedPressurePlate(WeatheringCopper.WeatherState.OXIDIZED, true);

    public static final Block COPPER_RAIL = registerCopperRail(WeatheringCopper.WeatherState.UNAFFECTED, false);
    public static final Block EXPOSED_COPPER_RAIL = registerCopperRail(WeatheringCopper.WeatherState.EXPOSED, false);
    public static final Block WEATHERED_COPPER_RAIL = registerCopperRail(WeatheringCopper.WeatherState.WEATHERED, false);
    public static final Block OXIDIZED_COPPER_RAIL = registerCopperRail(WeatheringCopper.WeatherState.OXIDIZED, false);

    public static final Block WAXED_COPPER_RAIL = registerCopperRail(WeatheringCopper.WeatherState.UNAFFECTED, true);
    public static final Block WAXED_EXPOSED_COPPER_RAIL = registerCopperRail(WeatheringCopper.WeatherState.EXPOSED, true);
    public static final Block WAXED_WEATHERED_COPPER_RAIL = registerCopperRail(WeatheringCopper.WeatherState.WEATHERED, true);
    public static final Block WAXED_OXIDIZED_COPPER_RAIL = registerCopperRail(WeatheringCopper.WeatherState.OXIDIZED, true);

    public static final Block COPPER_FIRE = registerBlockWithoutBlockItem(
            "copper_fire",
            CopperFireBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.FIRE)
                    .mapColor(MapColor.EMERALD)
                    .lightLevel(_ -> 13)
    );

    public static final Block COPPER_CAMPFIRE = register(
            "copper_campfire",
            CopperCampfireBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.CAMPFIRE)
                    .lightLevel(_ -> 13)
    );

    public static final Block CHISELED_IRON = registerChiseledBlock("iron", Blocks.IRON_BLOCK);
    public static final Block IRON_GRATE = registerGrateBlock("iron", Blocks.IRON_BLOCK);
    public static final Block CUT_IRON = registerCutBlock("iron", Blocks.IRON_BLOCK);
    public static final Block CUT_IRON_SLAB = registerCutSlab("iron", CUT_IRON);
    public static final Block CUT_IRON_STAIRS = registerCutStairs("iron", CUT_IRON);
    public static final Block IRON_BUTTON = registerButton("iron", BlockSetType.IRON, 15);

    public static final Block CHISELED_GOLD = registerChiseledBlock("gold", Blocks.GOLD_BLOCK);
    public static final Block GOLDEN_GRATE = registerGrateBlock("golden", Blocks.GOLD_BLOCK);
    public static final Block CUT_GOLD = registerCutBlock("gold", Blocks.GOLD_BLOCK);
    public static final Block CUT_GOLDEN_SLAB = registerCutSlab("golden", CUT_GOLD);
    public static final Block CUT_GOLDEN_STAIRS = registerCutStairs("golden", CUT_GOLD);
    public static final Block GOLD_BUTTON = registerButton("gold", BlockSetType.GOLD, 5);
    public static final Block GOLDEN_BARS = register(
            "golden_bars",
            IronBarsBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS)
                    .sound(SoundType.METAL)
    );
    public static final Block GOLDEN_CHAIN = register(
            "golden_chain",
            ChainBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_CHAIN)
    );
    public static final Block GOLDEN_DOOR = register(
            "golden_door",
            properties -> new DoorBlock(BlockSetType.GOLD, properties),
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_DOOR)
                    .mapColor(MapColor.GOLD)
    );
    public static final Block GOLDEN_TRAPDOOR = register(
            "golden_trapdoor",
            properties -> new TrapDoorBlock(BlockSetType.GOLD, properties),
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_TRAPDOOR)
                    .mapColor(MapColor.GOLD)
    );
    public static final Block GOLDEN_LANTERN = register(
            "golden_lantern",
            LanternBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)
                    .mapColor(MapColor.GOLD)
                    .strength(5F)
    );

    //#endregion

    /**
     * Register a copper button
     *
     * @param weatherState The {@link WeatheringCopper.WeatherState}
     * @param isWaxed Whether the block is waxed or not
     * @return The registered {@link Block}
     */
    private static Block registerCopperButton(final WeatheringCopper.WeatherState weatherState, final boolean isWaxed) {
        return register(
                BlockUtils.copperBlockName(weatherState, isWaxed, "button"),
                properties -> isWaxed ? new CopperButtonBlock(weatherState, properties) : new OxidizableCopperButtonBlock(weatherState, properties),
                buttonProperties()
        );
    }

    /**
     * Register a medium weighted pressure plate
     *
     * @param weatherState The {@link WeatheringCopper.WeatherState}
     * @param isWaxed Whether the block is waxed or not
     * @return The registered {@link Block}
     */
    private static Block registerMediumWeightedPressurePlate(final WeatheringCopper.WeatherState weatherState, final boolean isWaxed) {
        return register(
                BlockUtils.oxidizableBlockName(weatherState, isWaxed, "medium_weighted", "pressure_plate"),
                properties -> isWaxed ? new MediumWeightedPressurePlateBlock(weatherState, properties) : new OxidizableMediumWeightedPressurePlateBlock(weatherState, properties),
                BlockBehaviour.Properties.of()
                        .mapColor(BlockUtils.weatherStateMapColor(weatherState))
                        .forceSolidOn()
                        .noCollision()
                        .strength(0.5F)
                        .pushReaction(PushReaction.DESTROY)
        );
    }

    /**
     * Register a copper rail
     *
     * @param weatherState The {@link WeatheringCopper.WeatherState}
     * @param isWaxed Whether the block is waxed or not
     * @return The registered {@link Block}
     */
    private static Block registerCopperRail(final WeatheringCopper.WeatherState weatherState, final boolean isWaxed) {
        return register(
                BlockUtils.copperBlockName(weatherState, isWaxed, "rail"),
                properties -> isWaxed ? new CopperRailBlock(weatherState, properties) : new OxidizableCopperRailBlock(weatherState, properties),
                BlockBehaviour.Properties.of()
                        .noCollision()
                        .strength(0.7F)
                        .sound(SoundType.METAL)
        );
    }

    /**
     * Register a chiseled block
     *
     * @param materialName The name of the chiseled block material
     * @param sourceBlock The source {@link Block}
     * @return The registered {@link Block}
     */
    private static Block registerChiseledBlock(final String materialName, final Block sourceBlock) {
        return register(
                "chiseled_" + materialName,
                Block::new,
                BlockBehaviour.Properties.ofFullCopy(sourceBlock)
        );
    }

    /**
     * Register a grate block
     *
     * @param materialName The name of the grate block material
     * @param sourceBlock The source {@link Block}
     * @return The registered {@link Block}
     */
    private static Block registerGrateBlock(final String materialName, final Block sourceBlock) {
        return register(
                materialName + "_grate",
                WaterloggedTransparentBlock::new,
                BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE)
                        .mapColor(sourceBlock.defaultMapColor())
                        .sound(sourceBlock.defaultBlockState().getSoundType())
        );
    }

    /**
     * Register a cut block
     *
     * @param materialName The name of the cut block material
     * @param sourceBlock The source {@link Block}
     * @return The registered {@link Block}
     */
    private static Block registerCutBlock(final String materialName, final Block sourceBlock) {
        return register(
                "cut_" + materialName,
                Block::new,
                BlockBehaviour.Properties.ofFullCopy(sourceBlock)
        );
    }

    /**
     * Register a cut slab
     *
     * @param materialName The name of the cut slab material
     * @param sourceBlock The source {@link Block}
     * @return The registered {@link Block}
     */
    private static Block registerCutSlab(final String materialName, final Block sourceBlock) {
        return register(
                "cut_" + materialName + "_slab",
                SlabBlock::new,
                BlockBehaviour.Properties.ofFullCopy(sourceBlock)
        );
    }

    /**
     * Register some cut stairs
     *
     * @param materialName The name of the cut stairs material
     * @param sourceBlock The source {@link Block}
     * @return The registered {@link Block}
     */
    private static Block registerCutStairs(final String materialName, final Block sourceBlock) {
        return register(
                "cut_" + materialName + "_stairs",
                properties -> new StairBlock(sourceBlock.defaultBlockState(), properties),
                BlockBehaviour.Properties.ofFullCopy(sourceBlock)
        );
    }

    /**
     * Register a button
     *
     * @param materialName The name of the button material
     * @param blockSetType The {@link BlockSetType}
     * @param pressTicks The button press ticks
     * @return The registered {@link Block}
     */
    private static Block registerButton(final String materialName, final BlockSetType blockSetType, final int pressTicks) {
        return register(
                materialName + "_button",
                properties -> new ButtonBlock(blockSetType, pressTicks, properties),
                buttonProperties()
        );
    }

    /**
     * Get the {@link BlockBehaviour.Properties} for a button
     *
     * @return The {@link BlockBehaviour.Properties}
     */
    private static BlockBehaviour.Properties buttonProperties() {
        return BlockBehaviour.Properties.of()
                .noCollision()
                .strength(0.5F)
                .pushReaction(PushReaction.DESTROY);
    }

    /**
     * Register a {@link Block} without registering a {@link BlockItem}
     *
     * @param name The block name
     * @param blockFactory The block factory
     * @param properties The {@link BlockBehaviour.Properties block properties}
     * @return The registered {@link Block}
     */
    private static Block registerBlockWithoutBlockItem(final String name, final Function<BlockBehaviour.Properties, Block> blockFactory, final BlockBehaviour.Properties properties) {
        final ResourceKey<Block> blockResourceKey = ResourceKey.create(Registries.BLOCK, IdentifierUtils.modded(name));
        final Block block = blockFactory.apply(properties.setId(blockResourceKey));
        return Registry.register(BuiltInRegistries.BLOCK, blockResourceKey, block);
    }

    /**
     * Register a {@link Block}
     *
     * @param name The block name
     * @param blockFactory The block factory
     * @param properties The {@link BlockBehaviour.Properties block properties}
     * @return The registered {@link Block}
     */
    private static Block register(final String name, final Function<BlockBehaviour.Properties, Block> blockFactory, final BlockBehaviour.Properties properties) {
        final Block block = registerBlockWithoutBlockItem(name, blockFactory, properties);
        final ResourceKey<Item> blockItemResourceKey = ResourceKey.create(Registries.ITEM, IdentifierUtils.modded(name));
        final BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(blockItemResourceKey).useBlockDescriptionPrefix());
        Registry.register(BuiltInRegistries.ITEM, blockItemResourceKey, blockItem);
        return block;
    }

    /**
     * Register all oxidizable blocks
     */
    private static void registerOxidizableBlocks() {
        OxidizableBlocksRegistry.registerWeatheringCopperBlocks(new WeatheringCopperBlocks(
                COPPER_BUTTON,
                EXPOSED_COPPER_BUTTON,
                WEATHERED_COPPER_BUTTON,
                OXIDIZED_COPPER_BUTTON,
                WAXED_COPPER_BUTTON,
                WAXED_EXPOSED_COPPER_BUTTON,
                WAXED_WEATHERED_COPPER_BUTTON,
                WAXED_OXIDIZED_COPPER_BUTTON
        ));
        OxidizableBlocksRegistry.registerWeatheringCopperBlocks(new WeatheringCopperBlocks(
                MEDIUM_WEIGHTED_PRESSURE_PLATE,
                EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                WAXED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                WAXED_EXPOSED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                WAXED_WEATHERED_MEDIUM_WEIGHTED_PRESSURE_PLATE,
                WAXED_OXIDIZED_MEDIUM_WEIGHTED_PRESSURE_PLATE
        ));
        OxidizableBlocksRegistry.registerWeatheringCopperBlocks(new WeatheringCopperBlocks(
                COPPER_RAIL,
                EXPOSED_COPPER_RAIL,
                WEATHERED_COPPER_RAIL,
                OXIDIZED_COPPER_RAIL,
                WAXED_COPPER_RAIL,
                WAXED_EXPOSED_COPPER_RAIL,
                WAXED_WEATHERED_COPPER_RAIL,
                WAXED_OXIDIZED_COPPER_RAIL
        ));
    }

    /**
     * Register all {@link Block Blocks}
     */
    public static void register() {
        registerOxidizableBlocks();
    }

}