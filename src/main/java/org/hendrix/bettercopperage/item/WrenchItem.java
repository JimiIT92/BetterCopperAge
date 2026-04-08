package org.hendrix.bettercopperage.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import org.hendrix.bettercopperage.core.BCATags;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;
import java.util.List;

/**
 * Implementation class for a wrench item
 */
public final class WrenchItem extends Item {

    /**
     * Constructor. Set the item {@link Properties}
     *
     * @param properties The item {@link Properties}
     */
    public WrenchItem(final Properties properties) {
        super(properties);
    }

    /**
     * Prevent the item from mining blocks
     *
     * @param itemStack The current {@link ItemStack}
     * @param state The clicked {@link BlockState}
     * @param level The {@link Level} reference
     * @param pos The clicked {@link BlockPos}
     * @param user The {@link LivingEntity} using the item
     * @return {@link Boolean False}
     */
    @Override
    public boolean canDestroyBlock(final @NonNull ItemStack itemStack, final @NonNull BlockState state, final Level level, final @NonNull BlockPos pos, final @NonNull LivingEntity user) {
        if(!level.isClientSide() && user instanceof Player player) {
            this.use(player, level, state, pos, itemStack);
        }
        return false;
    }

    /**
     * Try cycle some properties on a {@link Block}
     *
     * @param player The {@link Player} using the item
     * @param state The clicked {@link BlockState}
     * @param level The {@link Level} reference
     * @param pos The clicked {@link BlockPos}
     * @param itemStack The current {@link ItemStack}
     */
    public void use(final Player player, final Level level, final BlockState state, final BlockPos pos, final ItemStack itemStack) {
        final Boolean isSneaking = player.isShiftKeyDown();
        final InteractionHand hand = player.getUsedItemHand();
        final List<Property<?>> properties = Arrays.asList(
                BlockStateProperties.AXIS,
                BlockStateProperties.FACING,
                BlockStateProperties.ROTATION_16,
                BlockStateProperties.FACING_HOPPER,
                BlockStateProperties.HORIZONTAL_AXIS,
                BlockStateProperties.HORIZONTAL_FACING,
                BlockStateProperties.VERTICAL_DIRECTION,
                BlockStateProperties.DOOR_HINGE,
                BlockStateProperties.ORIENTATION
        );
        if(!state.is(BCATags.BlockTags.INVALID_FOR_WRENCH)) {
            for (Property<?> property : properties) {
                if(state.hasProperty(property)) {
                    cycleBlockState(level, state, pos, player, itemStack, property, hand, isSneaking);
                    return;
                }
            }
        }
    }

    /**
     * Cycle a {@link Property} on a {@link Block}
     *
     * @param level The {@link Level} reference
     * @param state The clicked {@link BlockState}
     * @param pos The clicked {@link BlockPos}
     * @param player The {@link Player} using the item
     * @param itemStack The current {@link ItemStack}
     * @param property The {@link Property} to cycle
     * @param hand The {@link InteractionHand} used to interact with the {@link Block}
     * @param isSneaking Whether the {@link Player} is sneaking or not
     */
    private <T extends Comparable<T>> void cycleBlockState(final Level level, final BlockState state, final BlockPos pos, final Player player, final ItemStack itemStack, final Property<T> property, final InteractionHand hand, final Boolean isSneaking) {
        if(player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, itemStack);
        }
        final List<T> propertyValues = property.getPossibleValues();
        final T currentPropertyValue = state.getValue(property);
        final T nextPropertyValue = isSneaking ? Util.findPreviousInIterable(propertyValues, currentPropertyValue) : Util.findNextInIterable(propertyValues, currentPropertyValue);
        level.setBlockAndUpdate(pos, state.setValue(property, nextPropertyValue));
        playInteractSound(level, player, pos, state.getSoundType().getPlaceSound(), SoundSource.BLOCKS);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
        damageItemStack(itemStack, player, hand);
    }

    /**
     * Toggle the {@link ArmorStand} arms
     *
     * @param itemStack The current {@link ItemStack}
     * @param player The {@link Player} using the item
     * @param level The {@link Level} reference
     * @param hand The {@link InteractionHand} used to interact with the {@link ArmorStand}
     * @param armorStand The {@link ArmorStand} being interacted with the wrench
     * @return {@link InteractionResult.Success}
     */
    public InteractionResult toggleArmorStandArms(final ItemStack itemStack, final Player player, final Level level, final InteractionHand hand, final ArmorStand armorStand) {
        if(!armorStand.hasItemInSlot(EquipmentSlot.MAINHAND) && !armorStand.hasItemInSlot(EquipmentSlot.OFFHAND)) {
            armorStand.setShowArms(!armorStand.showArms());
            playInteractSound(level, player, armorStand, SoundEvents.ARMOR_STAND_PLACE);
            damageItemStack(itemStack, player, hand);
        }
        return InteractionResult.SUCCESS;
    }

    /**
     * Toggle the {@link ItemFrame} visibility
     *
     * @param itemStack The current {@link ItemStack}
     * @param player The {@link Player} using the item
     * @param level The {@link Level} reference
     * @param hand The {@link InteractionHand} used to interact with the {@link ItemFrame}
     * @param itemFrame The {@link ItemFrame} being interacted with the wrench
     * @param isGlowItemFrame Whether the {@link ItemFrame} is a glow item frame
     * @return {@link InteractionResult.Success}
     */
    public InteractionResult toggleItemFrameVisibility(final ItemStack itemStack, final Player player, final Level level, final InteractionHand hand, final ItemFrame itemFrame, final Boolean isGlowItemFrame) {
        itemFrame.setInvisible(!itemFrame.isInvisible());
        playInteractSound(level, player, itemFrame, isGlowItemFrame ? SoundEvents.GLOW_ITEM_FRAME_PLACE : SoundEvents.ITEM_FRAME_PLACE);
        damageItemStack(itemStack, player, hand);
        return InteractionResult.SUCCESS;
    }

    /**
     * Play a {@link SoundEvent sound} when interacting with the wrench
     * at an {@link Entity} position
     *
     * @param level The {@link Level} reference
     * @param player The {@link Player} using the item
     * @param entity The {@link Entity} at which the sound should be played
     * @param sound The {@link SoundEvent} to play
     */
    private void playInteractSound(final Level level, final Player player, final Entity entity, final SoundEvent sound) {
        playInteractSound(level, player, entity.blockPosition(), sound, entity.getSoundSource());
    }

    /**
     * Play a {@link SoundEvent sound} when interacting with the wrench
     *
     * @param level The {@link Level} reference
     * @param player The {@link Player} using the item
     * @param pos The clicked {@link BlockPos}
     * @param sound The {@link SoundEvent} to play
     * @param source The {@link SoundSource}
     */
    private void playInteractSound(final Level level, final Player player, final BlockPos pos, final SoundEvent sound, final SoundSource source) {
        level.playSound(player, pos, sound, source);
    }

    /**
     * Damage the wrench item
     *
     * @param itemStack The current {@link ItemStack}
     * @param player The {@link Player} using the item
     * @param hand The {@link InteractionHand} used to interact with the {@link Block}
     */
    private void damageItemStack(final ItemStack itemStack, final Player player, final InteractionHand hand) {
        itemStack.hurtAndBreak(1, player, hand);
    }

}