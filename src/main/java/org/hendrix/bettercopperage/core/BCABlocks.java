package org.hendrix.bettercopperage.core;

import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.BlockItemId;
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

    public static final WeatheringCopperCollection<Block> COPPER_BUTTON = registerCopperButton();

    public static final WeatheringCopperCollection<Block> MEDIUM_WEIGHTED_PRESSURE_PLATE = registerMediumWeightedPressurePlate();

    public static final WeatheringCopperCollection<Block> COPPER_RAIL = registerCopperRail();

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
     * @return The registered {@link Block}
     */
    private static WeatheringCopperCollection<Block> registerCopperButton() {
        return WeatheringCopperCollection.registerBlocks(
                WeatheringCopperCollection.prefixWithState(WeatheringCopperCollection.create("copper_button")).map(BlockItemId::create),
                (blockItemId, blockFactory, properties) -> register(blockItemId.block().identifier().getPath(), blockFactory, properties),
                CopperButtonBlock::new,
                OxidizableCopperButtonBlock::new,
                _ -> buttonProperties()
        );
    }

    /**
     * Register a medium weighted pressure plate
     *
     * @return The registered {@link Block}
     */
    private static WeatheringCopperCollection<Block> registerMediumWeightedPressurePlate() {
        return WeatheringCopperCollection.registerBlocks(
                WeatheringCopperCollection.prefixWithState(WeatheringCopperCollection.create("medium_weighted_pressure_plate")).map(BlockItemId::create),
                (blockItemId, blockFactory, properties) -> register(blockItemId.block().identifier().getPath(), blockFactory, properties),
                MediumWeightedPressurePlateBlock::new,
                OxidizableMediumWeightedPressurePlateBlock::new,
                weatherState -> BlockBehaviour.Properties.of()
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
     * @return The registered {@link Block}
     */
    private static WeatheringCopperCollection<Block> registerCopperRail() {
        return WeatheringCopperCollection.registerBlocks(
                WeatheringCopperCollection.prefixWithState(WeatheringCopperCollection.create("copper_rail")).map(BlockItemId::create),
                (blockItemId, blockFactory, properties) -> register(blockItemId.block().identifier().getPath(), blockFactory, properties),
                CopperRailBlock::new,
                OxidizableCopperRailBlock::new,
                _ -> BlockBehaviour.Properties.of()
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
                BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE.asList().getFirst())
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
        OxidizableBlocksRegistry.registerWeatheringCopperBlocks(COPPER_BUTTON);
        OxidizableBlocksRegistry.registerWeatheringCopperBlocks(MEDIUM_WEIGHTED_PRESSURE_PLATE);
        OxidizableBlocksRegistry.registerWeatheringCopperBlocks(COPPER_RAIL);
    }

    /**
     * Register all {@link Block Blocks}
     */
    public static void register() {
        registerOxidizableBlocks();
    }

}