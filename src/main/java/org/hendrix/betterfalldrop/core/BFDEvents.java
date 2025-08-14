package org.hendrix.betterfalldrop.core;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShelfBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.CopperGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.hendrix.betterfalldrop.BetterFallDrop;
import org.jetbrains.annotations.Nullable;

/**
 * {@link BetterFallDrop Better Fall Drop} Events
 */
public final class BFDEvents {

    /**
     * Toggle the {@link ShelfBlock Shelf Item Display Block State}
     *
     * @param player The {@link PlayerEntity Player that is interacting with the Block}
     * @param world The {@link World World reference}
     * @param hand The {@link Hand Hand that the Player is using to interact with the Block}
     * @param blockHitResult The {@link BlockHitResult Block Hit Result}
     * @return The {@link ActionResult Action Result}
     */
    private static ActionResult toggleShelfState(final PlayerEntity player, final World world, final Hand hand, final BlockHitResult blockHitResult) {
        final BlockPos blockPos = blockHitResult.getBlockPos();
        final BlockState blockState = world.getBlockState(blockPos);
        if(player.isSneaking() && blockState.getBlock() instanceof ShelfBlock && blockState.contains(ShelfBlock.field_61997)) {
            world.setBlockState(blockPos, blockState.with(ShelfBlock.field_61997, !blockState.get(ShelfBlock.field_61997)));
            world.playSound(player, blockPos, SoundEvents.BLOCK_SHELF_PLACE_ITEM, SoundCategory.BLOCKS);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    /**
     * Place an {@link Item Item} on top of the {@link CopperGolemEntity Copper Golem} when interacted
     *
     * @param player The {@link PlayerEntity Player that is interacting with the Block}
     * @param world The {@link World World reference}
     * @param hand The {@link Hand Hand that the Player is using to interact with the Block}
     * @param entity The {@link Entity Entity that has been interacted}
     * @param entityHitResult The {@link EntityHitResult Entity Hit Result}
     * @return The {@link ActionResult Action Result}
     */
    private static ActionResult placeItemOnCopperGolem(final PlayerEntity player, final World world, final Hand hand, final Entity entity, final @Nullable EntityHitResult entityHitResult) {
        if(entity instanceof CopperGolemEntity copperGolemEntity) {
            final ItemStack itemStack = player.getStackInHand(hand);
            final ItemStack equippedStack = copperGolemEntity.getEquippedStack(EquipmentSlot.HEAD);

            if(itemStack.isIn(ItemTags.SHEARABLE_FROM_COPPER_GOLEM) && equippedStack.isEmpty()) {
                copperGolemEntity.equipStack(EquipmentSlot.HEAD, itemStack);
                copperGolemEntity.setEquipmentDropChance(EquipmentSlot.HEAD, 1.0F);
                player.setStackInHand(hand, ItemStack.EMPTY);
                world.playSound(player, copperGolemEntity.getBlockPos(), SoundEvents.BLOCK_COPPER_GOLEM_STATUE_PLACE, SoundCategory.NEUTRAL);
                return ActionResult.SUCCESS;
            }

        }

        return ActionResult.PASS;
    }

    /**
     * Register all Events
     */
    public static void register() {
        UseBlockCallback.EVENT.register(BFDEvents::toggleShelfState);
        UseEntityCallback.EVENT.register(BFDEvents::placeItemOnCopperGolem);
    }

}