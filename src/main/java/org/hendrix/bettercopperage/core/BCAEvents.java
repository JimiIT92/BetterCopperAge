package org.hendrix.bettercopperage.core;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.EntityHitResult;
import org.hendrix.bettercopperage.BetterCopperAge;
import org.hendrix.bettercopperage.item.WrenchItem;
import org.jspecify.annotations.Nullable;

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
     * Register all events
     */
    public static void register() {
        AttackBlockCallback.EVENT.register(BCAEvents::wrenchBlockInteract);
        AttackEntityCallback.EVENT.register(BCAEvents::wrenchBlockEntityInteract);
    }

}