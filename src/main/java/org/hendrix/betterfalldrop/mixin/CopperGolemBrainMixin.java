package org.hendrix.betterfalldrop.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.*;
import net.minecraft.entity.ai.brain.task.MoveItemsTask;
import net.minecraft.entity.passive.CopperGolemBrain;
import net.minecraft.entity.passive.CopperGolemEntity;
import net.minecraft.entity.passive.CopperGolemState;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import org.hendrix.betterfalldrop.core.BFDTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

/**
 * Mixin class for the {@link CopperGolemBrain Copper Golem Brain}
 */
@Mixin(CopperGolemBrain.class)
public final class CopperGolemBrainMixin {

    /**
     * Make the {@link CopperGolemEntity Copper Golem} leave items in all {@link Block Blocks} that are in the {@link BFDTags.BlockTags#COPPER_GOLEM_OUTPUT_CONTAINERS Copper Golem Output Containers Block Tag}
     */
    @Shadow
    private static final Predicate<BlockState> OUTPUT_CHEST_PREDICATE = state -> state.isIn(BFDTags.BlockTags.COPPER_GOLEM_OUTPUT_CONTAINERS);

    /**
     * Make the {@link CopperGolemEntity Copper Golem} wait to put items in Containers until no one is looking
     *
     * @param callbackInfoReturnable The {@link CallbackInfoReturnable<MoveItemsTask.Storage> Move Items Task Storage Predicate Callback Info Returnable}
     */
    @Inject(method = "createStoragePredicate", at = @At(value = "RETURN"), cancellable = true)
    private static void createStoragePredicate(final CallbackInfoReturnable<Predicate<MoveItemsTask.Storage>> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue(storage -> storage.blockEntity() instanceof LootableContainerBlockEntity lootableContainerBlockEntity && !lootableContainerBlockEntity.getViewingUsers().isEmpty());
    }

    /**
     * Make the container open/close when the {@link CopperGolemEntity Copper Golem} interacts with them
     *
     * @param state The {@link CopperGolemState Copper Golem State}
     * @param soundEvent The {@link SoundEvent Sound Event to play}
     * @param callbackInfoReturnable The {@link CallbackInfoReturnable<MoveItemsTask.InteractionCallback> Move Items Task Interaction Callback Info Returnable}
     */
    @Inject(method = "createInteractionCallback", at = @At(value = "RETURN"), cancellable = true)
    private static void createInteractionCallback(final CopperGolemState state, final SoundEvent soundEvent, final CallbackInfoReturnable<MoveItemsTask.InteractionCallback> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue((entity, storage, interactionTicks) -> {
            if(entity instanceof CopperGolemEntity copperGolemEntity) {
                if(interactionTicks == 1) {
                    if(storage.blockEntity() instanceof LootableContainerBlockEntity lootableContainerBlockEntity) {
                        lootableContainerBlockEntity.onOpen(copperGolemEntity);
                    }
                    copperGolemEntity.setTargetContainerPos(storage.pos());
                    copperGolemEntity.setState(state);
                }

                if (interactionTicks == 9 && soundEvent != null) {
                    copperGolemEntity.getEntityWorld().playSound(null, copperGolemEntity.getBlockPos(), soundEvent, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                }

                if (interactionTicks == 60) {
                    if(storage.blockEntity() instanceof LootableContainerBlockEntity lootableContainerBlockEntity) {
                        lootableContainerBlockEntity.onClose(copperGolemEntity);
                    }
                    copperGolemEntity.resetTargetContainerPos();
                }
            }
        });
    }

}