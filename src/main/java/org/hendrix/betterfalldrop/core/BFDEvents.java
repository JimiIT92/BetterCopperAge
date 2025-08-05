package org.hendrix.betterfalldrop.core;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.hendrix.betterfalldrop.BetterFallDrop;
import org.hendrix.betterfalldrop.block.BFDOxidizable;
import org.hendrix.betterfalldrop.block.CopperButtonBlock;

/**
 * {@link BetterFallDrop Better Fall Drop} Events
 */
public final class BFDEvents {

    /**
     * Scrape a {@link BFDOxidizable modded Oxidizable Block}
     *
     * @param player The {@link PlayerEntity Player that is interacting with the Block}
     * @param world The {@link World World reference}
     * @param hand The {@link Hand Hand that the Player is using to interact with the Block}
     * @param blockHitResult The {@link BlockHitResult Block Hit Result}
     * @return The {@link ActionResult Action Result}
     */
    private static ActionResult scrapeCopperBlocks(final PlayerEntity player, final World world, final Hand hand, final BlockHitResult blockHitResult) {
        final ItemStack itemStack = player.getStackInHand(hand);
        final BlockPos blockPos = blockHitResult.getBlockPos();
        final BlockState blockState = world.getBlockState(blockPos);
        if(itemStack.getItem() instanceof AxeItem && player.isSneaking() && blockState.getBlock() instanceof BFDOxidizable) {
            BFDOxidizable.getDecreasedOxidationState(blockState).map(state -> {
                world.playSound(player, blockPos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.syncWorldEvent(player, 3005, blockPos, 0);

                if (player instanceof ServerPlayerEntity) {
                    Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)player, blockPos, itemStack);
                }

                if(state.getBlock() instanceof CopperButtonBlock) {
                    state = state.with(CopperButtonBlock.POWERED, Boolean.FALSE);
                }

                world.setBlockState(blockPos, state);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(player, state));
                itemStack.damage(1, player, LivingEntity.getSlotForHand(hand));

                if(world.isClient()) {
                    player.swingHand(hand);
                }

                return ActionResult.SUCCESS;
            });
        }
        return ActionResult.PASS;
    }

    /**
     * Register all Events
     */
    public static void register() {
       UseBlockCallback.EVENT.register(BFDEvents::scrapeCopperBlocks);
    }

}