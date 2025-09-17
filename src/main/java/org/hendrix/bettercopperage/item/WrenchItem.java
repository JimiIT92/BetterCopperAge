package org.hendrix.bettercopperage.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.core.BCATags;

import java.util.Arrays;
import java.util.List;

/**
 * {@link BetterCopperAge Better Copper Age} {@link Item Wrench Item}
 */
public final class WrenchItem extends Item {

    /**
     * Constructor. Set the {@link Settings Item Settings}
     *
     * @param settings The {@link Settings Item Settings}
     */
    public WrenchItem(final Settings settings) {
        super(settings);
    }

    /**
     * Prevent the {@link Item Item} from mining {@link Block Blocks}
     *
     * @param world The {@link World World reference}
     * @param blockState The {@link BlockState current Block State}
     * @param blockPos The {@link BlockPos current Block Pos}
     * @param livingEntity The {@link LivingEntity Entity interacting with the Block}
     * @param itemStack The {@link ItemStack ItemStack used to interact with the Block}
     * @return The {@link ActionResult Action Result}
     */
    @Override
    public boolean canMine(final ItemStack itemStack, final BlockState blockState, final World world, final BlockPos blockPos, final LivingEntity livingEntity) {
        if (!world.isClient() && livingEntity instanceof PlayerEntity playerEntity) {
            this.use(playerEntity, blockState, world, blockPos, itemStack, playerEntity.getActiveHand());
        }
        return false;
    }

    /**
     * Try cycle some {@link Property Properties} on a {@link Block Block}
     *
     * @param world      The {@link World World reference}
     * @param blockState The {@link BlockState current Block State}
     * @param blockPos   The {@link BlockPos current Block Pos}
     * @param player     The {@link PlayerEntity Player interacting with the Block}
     * @param itemStack  The {@link ItemStack ItemStack used to interact with the Block}
     * @param hand       The {@link Hand Hand used to interact with the Block}
     */
    private void use(final PlayerEntity player, final BlockState blockState, final World world, final BlockPos blockPos, final ItemStack itemStack, final Hand hand) {
        final boolean isSneaking = player != null && player.isSneaking();
        final List<Property<?>> properties = Arrays.asList(
                Properties.AXIS,
                Properties.FACING,
                Properties.ROTATION,
                Properties.HOPPER_FACING,
                Properties.HORIZONTAL_AXIS,
                Properties.HORIZONTAL_FACING,
                Properties.VERTICAL_DIRECTION,
                Properties.DOOR_HINGE,
                Properties.ORIENTATION
        );
        if(!blockState.isIn(BCATags.BlockTags.INVALID_FOR_WRENCH)) {
            for (Property<?> property : properties) {
                if(blockState.contains(property)) {
                    cycleBlockState(world, blockState, blockPos, player, itemStack, property, hand, isSneaking);
                    return;
                }
            }
        }
    }

    /**
     * Cycle a {@link Property Property} on a {@link Block Block}
     *
     * @param world The {@link World World reference}
     * @param blockState The {@link BlockState current Block State}
     * @param blockPos The {@link BlockPos current Block Pos}
     * @param player The {@link PlayerEntity Player interacting with the Block}
     * @param itemStack The {@link ItemStack ItemStack used to interact with the Block}
     * @param property The {@link Property<T> Property to cycle}
     * @param hand The {@link Hand Hand used to interact with the Block}
     * @param isSneaking {@link Boolean Whether the Player is sneaking}
     * @param <T> The {@link T Property Type}
     */
    private <T extends Comparable<T>> void cycleBlockState(final World world, final BlockState blockState, final BlockPos blockPos, final PlayerEntity player, final ItemStack itemStack, final Property<T> property, final Hand hand, final boolean isSneaking) {
        if (player instanceof ServerPlayerEntity) {
            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)player, blockPos, itemStack);
        }
        final List<T> propertyValues = property.getValues();
        final T currentPropertyValue = blockState.get(property);
        final T nextPropertyValue = isSneaking ? Util.previous(propertyValues, currentPropertyValue) : Util.next(propertyValues, currentPropertyValue);
        world.setBlockState(blockPos, blockState.with(property, nextPropertyValue), Block.NOTIFY_ALL_AND_REDRAW);
        playInteractSound(world, player, blockPos, blockState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(player, blockState));
        damageItemStack(itemStack, player, hand);
    }

    /**
     * Toggle the {@link ArmorStandEntity Armor Stand} arms when interacted with the {@link WrenchItem Wrench}
     *
     * @param itemStack The {@link ItemStack Wrench Item Stack}
     * @param player The {@link PlayerEntity Player} that is interacting with the {@link ArmorStandEntity Armor Stand}
     * @param world The {@link World World reference}
     * @param hand The {@link Hand Hand used to interact with the Armor Stand}
     * @param entity The {@link ArmorStandEntity Armor Stand Entity}
     * @return The {@link ActionResult#SUCCESS Success Action Result}
     */
    public ActionResult toggleArmorStandArms(final ItemStack itemStack, final PlayerEntity player, final World world, final Hand hand, final ArmorStandEntity entity) {
        if(entity.getStackInArm(Arm.RIGHT).isEmpty() && entity.getStackInArm(Arm.LEFT).isEmpty()) {
            entity.setShowArms(!entity.shouldShowArms());
            playInteractSound(world, player, entity, SoundEvents.ENTITY_ARMOR_STAND_PLACE);
            damageItemStack(itemStack, player, hand);
        }
        return ActionResult.SUCCESS;
    }

    /**
     * Toggle the {@link ItemFrameEntity Item Frame} invisibility when interacted with the {@link WrenchItem Wrench}
     *
     * @param itemStack The {@link ItemStack Wrench Item Stack}
     * @param player The {@link PlayerEntity Player} that is interacting with the {@link ItemFrameEntity Item Frame}
     * @param world The {@link World World reference}
     * @param hand The {@link Hand Hand used to interact with the Item Frame}
     * @param entity The {@link ItemFrameEntity Item Frame Entity}
     * @param isGlowItemFrame {@link Boolean Whether the Item Frame is a Glow Item Frame}
     * @return The {@link ActionResult#SUCCESS Success Action Result}
     */
    public ActionResult toggleItemFrameVisibility(final ItemStack itemStack, final PlayerEntity player, final World world, final Hand hand, final ItemFrameEntity entity, final boolean isGlowItemFrame) {
        entity.setInvisible(!entity.isInvisible());
        playInteractSound(world, player, entity, isGlowItemFrame ? SoundEvents.ENTITY_GLOW_ITEM_FRAME_PLACE : SoundEvents.ENTITY_ITEM_FRAME_PLACE);
        damageItemStack(itemStack, player, hand);
        return ActionResult.SUCCESS;
    }

    /**
     * Play the {@link SoundEvent interact Sound} when a {@link PlayerEntity Player} interact
     * with the {@link WrenchItem Wrench} on an {@link Entity Entity}
     *
     * @param world The {@link World World reference}
     * @param player The {@link PlayerEntity Player interacting with the Block or the Entity}
     * @param entity The {@link Entity Entity} that the {@link PlayerEntity Player} is interacting with
     * @param soundEvent The {@link SoundEvent Sound to play}
     */
    private void playInteractSound(final World world, final PlayerEntity player, final Entity entity, final SoundEvent soundEvent) {
        world.playSound(player, entity.getBlockPos(), soundEvent, entity.getSoundCategory());
    }

    /**
     * Play the {@link SoundEvent interact Sound} when a {@link PlayerEntity Player} interact
     * with the {@link WrenchItem Wrench} on a {@link Block Block} or {@link Entity Entity}
     *
     * @param world The {@link World World reference}
     * @param player The {@link PlayerEntity Player interacting with the Block or the Entity}
     * @param blockPos The {@link BlockPos Block or Entity Block Pos}
     * @param soundEvent The {@link SoundEvent Sound to play}
     * @param soundCategory The {@link SoundCategory Sound category}
     */
    private void playInteractSound(final World world, final PlayerEntity player, final BlockPos blockPos, final SoundEvent soundEvent, final SoundCategory soundCategory) {
        world.playSound(player, blockPos, soundEvent, soundCategory);
    }

    /**
     * Damage the {@link WrenchItem Wrench} {@link ItemStack Item Stack}
     *
     * @param itemStack The {@link ItemStack Wrench Item Stack}
     * @param player The {@link PlayerEntity Player interacting with the Block or the Entity}
     * @param hand The {@link Hand Hand used to interact with the Block or the Entity}
     */
    private void damageItemStack(final ItemStack itemStack, final PlayerEntity player, final Hand hand) {
        itemStack.damage(1, player, hand);
    }

}