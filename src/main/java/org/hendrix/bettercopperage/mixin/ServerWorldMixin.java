package org.hendrix.bettercopperage.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CopperGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.EntityView;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;
import org.hendrix.bettercopperage.core.BCAGameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Mixin class for the {@link ServerWorld Server World class}
 */
@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin implements EntityView, BlockRenderView {
    
    /**
     * Determine where a {@link LightningEntity Lightning Bolt} should strike
     *
     * @param pointOfInterestStorage The {@link PointOfInterestStorage Point of Interest Storage Instance}
     * @param typePredicate The {@link Predicate<PointOfInterestType> Point of Interest Type predicate}
     * @param posPredicate The {@link Predicate<BlockPos> Block Pos predicate}
     * @param pos The {@link BlockPos current Block Pos}
     * @param radius The {@link Integer search radius}
     * @param occupationStatus The {@link PointOfInterestStorage.OccupationStatus Point of Interest Occupation Status}
     * @return The {@link Optional<BlockPos> Block Pos where the Lightning will strike, if any}
     */
    @Redirect(method = "getLightningRodPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/poi/PointOfInterestStorage;getNearestPosition(Ljava/util/function/Predicate;Ljava/util/function/Predicate;Lnet/minecraft/util/math/BlockPos;ILnet/minecraft/world/poi/PointOfInterestStorage$OccupationStatus;)Ljava/util/Optional;"))
    private Optional<BlockPos> getLightningRodPos(final PointOfInterestStorage pointOfInterestStorage, final Predicate<RegistryEntry<PointOfInterestType>> typePredicate, final Predicate<BlockPos> posPredicate, final BlockPos pos, final int radius, final PointOfInterestStorage.OccupationStatus occupationStatus) {
        final Random random = Random.create();
        Optional<BlockPos> lightningRodPos = pointOfInterestStorage.getNearestPosition(
                typePredicate,
                posPredicate,
                pos,
                radius,
                occupationStatus
        );
        if(lightningRodPos.isEmpty() && random.nextBoolean()) {
            final List<LivingEntity> nearbyEntities = this.getEntitiesByClass(LivingEntity.class, Box.enclosing(pos, pos.withY(this.getTopYInclusive() + 1)).expand(radius), this::canBeStruckByLightning);
            if (!nearbyEntities.isEmpty()) {
                lightningRodPos = Optional.of(nearbyEntities.get(random.nextInt(nearbyEntities.size())).getBlockPos());
            }
        }
        return lightningRodPos;
    }

    /**
     * Check if an {@link LivingEntity Entity} can be struck by a {@link LightningEntity Lightning}
     *
     * @param entity The {@link LivingEntity Entity to check}
     * @return {@link Boolean True if the Entity can be struck by a Lightning}
     */
    @Unique
    private boolean canBeStruckByLightning(final LivingEntity entity) {
        return entity != null && entity.isAlive() && this.isSkyVisible(entity.getBlockPos()) && (canStruckCopperGolem(entity) || canStruckEntity(entity));
    }

    /**
     * Check if the {@link LivingEntity provided Entity} is a {@link CopperGolemEntity Copper Golem}
     * and can be struck by a {@link LightningEntity Lightning}
     *
     * @param entity The {@link LivingEntity entity to check}
     * @return  {@link Boolean True} if the {@link LivingEntity provided Entity} is a {@link CopperGolemEntity Copper Golem}
     *          and the {@link BCAGameRules#COPPER_GOLEM_ATTRACTS_LIGHTNING copperGolemAttractsLightning Game Rule is turned on}
     */
    @Unique
    private boolean canStruckCopperGolem(final LivingEntity entity) {
        return getGameRuleValue(BCAGameRules.COPPER_GOLEM_ATTRACTS_LIGHTNING) && entity instanceof CopperGolemEntity;
    }

    /**
     * Check if the {@link LivingEntity provided Entity} is wearing Copper Armor
     * and can be struck by a {@link LightningEntity Lightning}
     *
     * @param entity The {@link LivingEntity entity to check}
     * @return  {@link Boolean True} if the {@link LivingEntity provided Entity} is wearing Copper Armor
     *          and the {@link BCAGameRules#COPPER_ARMOR_ATTRACTS_LIGHTNING copperArmorAttractsLightning Game Rule is turned on}
     */
    @Unique
    private boolean canStruckEntity(final LivingEntity entity) {
        return getGameRuleValue(BCAGameRules.COPPER_ARMOR_ATTRACTS_LIGHTNING) && ((
                entity instanceof PlayerEntity player && GameMode.SURVIVAL.equals(player.getGameMode()) && isWearingCopperArmor(player)
        ) || (!(entity instanceof PlayerEntity) && isWearingCopperArmor(entity)));
    }

    /**
     * Get a {@link GameRules.Key Game Rule value}
     *
     * @param gameRule The {@link GameRules.Key Game Rule Key}
     * @return The {@link Boolean Game Rule value}
     */
    @Unique
    private boolean getGameRuleValue(final GameRules.Key<GameRules.BooleanRule> gameRule) {
        return ((ServerWorld)(Object)this).getGameRules().getBoolean(gameRule);
    }

    /**
     * Check if an {@link LivingEntity Entity} is wearing some Copper Armor
     *
     * @param entity The {@link LivingEntity Entity to check}
     * @return {@link Boolean True if the Entity is wearing some Copper Armor}
     */
    @Unique
    private boolean isWearingCopperArmor(final LivingEntity entity) {
        return entity.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.COPPER_HELMET) ||
                entity.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.COPPER_CHESTPLATE) ||
                entity.getEquippedStack(EquipmentSlot.LEGS).isOf(Items.COPPER_LEGGINGS) ||
                entity.getEquippedStack(EquipmentSlot.FEET).isOf(Items.COPPER_BOOTS);
    }

}