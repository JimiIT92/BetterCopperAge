package org.hendrix.bettercopperage.mixin;

import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.TransportItemsBetweenContainers;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import org.hendrix.bettercopperage.core.BCATags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Mixin for the {@link TransportItemsBetweenContainers} class
 */
@Mixin(TransportItemsBetweenContainers.class)
public final class TransportItemsBetweenContainersMixin {

    /**
     * Make Copper Golem wait to put items inside a container until no one is looking into
     *
     * @param callbackInfoReturnable The {@link CallbackInfoReturnable}
     */
    @Inject(at = @At(value = "HEAD"), method = "getTransportTarget", cancellable = true)
    private void getTransportTarget(final ServerLevel level, final PathfinderMob body, final CallbackInfoReturnable<Optional<TransportItemsBetweenContainers.TransportItemTarget>> callbackInfoReturnable) {
        if(!TransportItemsBetweenContainers.isPickingUpItems(body)) {
            final TransportItemsBetweenContainers behavior = (TransportItemsBetweenContainers) (Object) this;
            final AABB targetBlockSearchArea = behavior.getTargetSearchArea(body);
            final Set<GlobalPos> visitedPositions = TransportItemsBetweenContainers.getVisitedPositions(body);
            final Set<GlobalPos> unreachablePositions = TransportItemsBetweenContainers.getUnreachablePositions(body);
            final List<ChunkPos> chunkPosList = ChunkPos.rangeClosed(ChunkPos.containing(body.blockPosition()), Math.floorDiv(behavior.getHorizontalSearchDistance(body), 16) + 1).toList();
            TransportItemsBetweenContainers.TransportItemTarget target = null;
            double closestDistance = Float.MAX_VALUE;

            for(ChunkPos chunkPos : chunkPosList) {
                LevelChunk levelChunk = level.getChunkSource().getChunkNow(chunkPos.x(), chunkPos.z());
                if (levelChunk != null) {
                    var potentialTargets = levelChunk.getBlockEntities()
                            .values()
                            .stream()
                            .filter(x -> level.getBlockState(x.getBlockPos()).is(BCATags.BlockTags.COPPER_GOLEM_OUTPUT_CONTAINERS) && x instanceof RandomizableContainerBlockEntity)
                            .toList();
                    for(final BlockEntity potentialTarget : potentialTargets) {
                        RandomizableContainerBlockEntity chestBlockEntity = (RandomizableContainerBlockEntity)potentialTarget;
                        double distance = chestBlockEntity.getBlockPos().distToCenterSqr(body.position());
                        if (distance < closestDistance) {
                            TransportItemsBetweenContainers.TransportItemTarget targetValidToPick = behavior.isTargetValidToPick(body, level, chestBlockEntity, visitedPositions, unreachablePositions, targetBlockSearchArea);
                            if (targetValidToPick != null) {
                                target = targetValidToPick;
                                closestDistance = distance;
                            }
                        }
                    }
                }
            }

            callbackInfoReturnable.setReturnValue(target == null ? Optional.empty() : Optional.of(target));
        }

    }

}