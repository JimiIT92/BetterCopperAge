package org.hendrix.bettercopperage.core;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.golem.CopperGolem;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShelfBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.item.WrenchItem;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * {@link BetterCopperAge} events
 */
public final class BCAEvents {

    /**
     * Make the {@link Player} interact with some {@link BlockEntity Block entities} with the wrench item
     *
     * @param player The {@link Player} interacting with the {@link BlockEntity}
     * @param level The {@link Level} reference
     * @param interactionHand The {@link InteractionHand} used to interact with the {@link BlockEntity}
     * @param blockPos The clicked {@link BlockPos}
     * @param direction The clicked {@link Block} {@link Direction}
     * @return The {@link InteractionResult.Pass}
     */
    private static InteractionResult wrenchBlockInteract(final Player player, final Level level, final InteractionHand interactionHand, final BlockPos blockPos, final Direction direction) {
        if(!level.isClientSide() && !player.isCreative()) {
            final ItemStack itemStack = player.getItemInHand(interactionHand);
            if(itemStack.getItem() instanceof WrenchItem wrench) {
                wrench.use(player, level, level.getBlockState(blockPos), blockPos, itemStack);
            }
        }
        return InteractionResult.PASS;
    }

    /**
     * Make the {@link Player} interact with some {@link BlockEntity Block entities} with the wrench item
     *
     * @param player The {@link Player} interacting with the {@link BlockEntity}
     * @param level The {@link Level} reference
     * @param interactionHand The {@link InteractionHand} used to interact with the {@link BlockEntity}
     * @param entity The {@link Entity} interacted
     * @param entityHitResult The {@link EntityHitResult}
     * @return The {@link InteractionResult}
     */
    private static InteractionResult wrenchBlockEntityInteract(final Player player, final Level level, final InteractionHand interactionHand, final Entity entity, final @Nullable EntityHitResult entityHitResult) {
        final ItemStack itemStack = player.getItemInHand(interactionHand);
        if(itemStack.getItem() instanceof WrenchItem wrench) {
            if(entity instanceof  ArmorStand armorStand) {
                return wrench.toggleArmorStandArms(itemStack, player, level, interactionHand, armorStand);
            }
            if(entity instanceof GlowItemFrame glowItemFrame) {
                return wrench.toggleItemFrameVisibility(itemStack, player, level, interactionHand, glowItemFrame, true);
            }
            if(entity instanceof ItemFrame itemFrame) {
                return wrench.toggleItemFrameVisibility(itemStack, player, level, interactionHand, itemFrame, false);
            }
        }
        return InteractionResult.PASS;
    }

    /**
     * Toggle the shelf item display block state
     *
     * @param player The {@link Player} interacting with the {@link Block}
     * @param level The {@link Level} reference
     * @param interactionHand The {@link InteractionHand} used to interact with the {@link Block}
     * @param blockHitResult The {@link BlockHitResult}
     * @return The {@link InteractionResult}
     */
    private static InteractionResult toggleShelfState(final Player player, final Level level, final InteractionHand interactionHand, final BlockHitResult blockHitResult) {
        final BlockPos pos = blockHitResult.getBlockPos();
        final BlockState state = level.getBlockState(pos);
        if(player.isShiftKeyDown() && player.getItemInHand(interactionHand).isEmpty() && state.getBlock() instanceof ShelfBlock) {
            final Optional<ShelfBlockEntity> optionalShelfBlockEntity = level.getBlockEntity(pos, BlockEntityType.SHELF);
            if(optionalShelfBlockEntity.isPresent()) {
                final ShelfBlockEntity shelfBlockEntity = optionalShelfBlockEntity.get();
                if(shelfBlockEntity.stillValid(player)) {
                    shelfBlockEntity.alignItemsToBottom = !shelfBlockEntity.alignItemsToBottom;
                    shelfBlockEntity.setChanged();
                    level.playSound(player, pos, SoundEvents.SHELF_PLACE_ITEM, SoundSource.BLOCKS);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    /**
     * Place an item on top of a Copper Golem
     *
     * @param player The {@link Player} interacting with the Copper Golem
     * @param level The {@link Level} reference
     * @param interactionHand The {@link InteractionHand} used to interact with the Copper Golem
     * @param entity The {@link Entity} interacted
     * @param entityHitResult The {@link EntityHitResult}
     * @return The {@link InteractionResult}
     */
    private static InteractionResult placeItemOnCopperGolem(Player player, Level level, InteractionHand interactionHand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        if(entity instanceof CopperGolem copperGolem) {
            final ItemStack itemStack = player.getItemInHand(interactionHand);
            final ItemStack equippedStack = copperGolem.getItemBySlot(EquipmentSlot.SADDLE);

            if(itemStack.is(ItemTags.SHEARABLE_FROM_COPPER_GOLEM) && equippedStack.isEmpty()) {
                copperGolem.setItemSlot(EquipmentSlot.SADDLE, itemStack);
                copperGolem.setDropChance(EquipmentSlot.SADDLE, 1F);
                if(!player.isCreative()) {
                    player.setItemInHand(interactionHand, ItemStack.EMPTY);
                }
                level.playSound(player, copperGolem.blockPosition(), SoundEvents.COPPER_GOLEM_STATUE_PLACE, SoundSource.NEUTRAL);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    /**
     * Register all events
     */
    public static void register() {
        AttackBlockCallback.EVENT.register(BCAEvents::wrenchBlockInteract);
        AttackEntityCallback.EVENT.register(BCAEvents::wrenchBlockEntityInteract);
        UseBlockCallback.EVENT.register(BCAEvents::toggleShelfState);
        UseEntityCallback.EVENT.register(BCAEvents::placeItemOnCopperGolem);
    }


}