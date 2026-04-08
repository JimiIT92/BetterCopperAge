package org.hendrix.bettercopperage.core;

import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeatheringCopperBlocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.block.CopperButtonBlock;
import org.hendrix.bettercopperage.block.OxidizableCopperButtonBlock;
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

    //#endregion

    /**
     * Register a copper button
     *
     * @param weatherState The {@link WeatheringCopper.WeatherState}
     * @param isWaxed Whether the block is waxed or not
     * @return The registered {@link Block}
     */
    private static Block registerCopperButton(final WeatheringCopper.WeatherState weatherState, final boolean isWaxed) {
        final String name = BlockUtils.copperBlockName(weatherState, isWaxed, "button");
        final BlockBehaviour.Properties properties = buttonProperties();
        return register(name, _ -> isWaxed ? new CopperButtonBlock(weatherState, properties) : new OxidizableCopperButtonBlock(weatherState, properties), properties);
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
    }

    /**
     * Register all {@link Block Blocks}
     */
    public static void register() {
        registerOxidizableBlocks();
    }

}