package org.hendrix.bettercopperage.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.golem.CopperGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import org.hendrix.bettercopperage.core.BCAGameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Mixin for the {@link ServerLevel} class
 */
@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {

    /**
     * Shadow declaration for the getEntities method
     *
     * @param type The {@link EntityTypeTest}
     * @param selector The entity selector {@link Predicate}
     * @return The filtered entities
     * @param <T> The entity type
     */
    @Shadow
    public abstract <T extends Entity> List<? extends T> getEntities(final EntityTypeTest<Entity, T> type, final Predicate<? super T> selector);

    /**
     * Shadow declaration for the getGameRules method
     *
     * @return The world's game rules
     */
    @Shadow
    public abstract GameRules getGameRules();

    /**
     * Make Copper Golems and entities be able to be struck by lightning
     *
     * @param center The current {@link BlockPos}
     * @param callbackInfoReturnable The {@link CallbackInfoReturnable}
     */
    @Inject(at = @At(value = "RETURN"), method = "findLightningRod", cancellable = true)
    private void findLightningRod(final BlockPos center, final CallbackInfoReturnable<Optional<BlockPos>> callbackInfoReturnable) {
        Optional<BlockPos> lightningRodPos = callbackInfoReturnable.getReturnValue();
        if(lightningRodPos.isEmpty()) {
            final List<? extends LivingEntity> nearbyEntities = this.getEntities(EntityTypeTest.forClass(LivingEntity.class), this::canBeStruckByLightning);
            if(!nearbyEntities.isEmpty()) {
                callbackInfoReturnable.setReturnValue(Optional.of(nearbyEntities.get(RandomSource.create().nextInt(nearbyEntities.size())).blockPosition()));
            }
        }
    }

    /**
     * Check whether an entity can be struck by a lightning
     *
     * @param entity The {@link LivingEntity} reference
     * @return {@link Boolean True} if the entity can be struck by a lightning
     */
    @Unique
    private boolean canBeStruckByLightning(final LivingEntity entity) {
        return entity != null && entity.isAlive() && entity.level().canSeeSky(entity.blockPosition()) && (canStruckCopperGolem(entity) || canStruckEntity(entity));
    }

    /**
     * Check whether a Copper Golem can be struck by a lightning
     *
     * @param entity The {@link LivingEntity} reference
     * @return {@link Boolean True} if the entity is a Copper Golem and the {@link BCAGameRules#COPPER_GOLEM_ATTRACTS_LIGHTNING} game rule is set to true
     */
    @Unique
    private boolean canStruckCopperGolem(final LivingEntity entity) {
        return entity instanceof CopperGolem && getGameRuleValue(BCAGameRules.COPPER_GOLEM_ATTRACTS_LIGHTNING);
    }

    /**
     * Check whether an entity can be struck by a lightning
     *
     * @param entity The {@link LivingEntity} reference
     * @return {@link Boolean True} if the entity is wearing a piece of copper armor and the {@link BCAGameRules#COPPER_GOLEM_ATTRACTS_LIGHTNING} game rule is set to true
     */
    @Unique
    private boolean canStruckEntity(final LivingEntity entity) {
        return getGameRuleValue(BCAGameRules.COPPER_ARMOR_ATTRACTS_LIGHTNING) && (
                (entity instanceof Player player && Objects.requireNonNull(player.gameMode()).isSurvival() && isWearingCopperArmor(player)) ||
                (!(entity instanceof Player) && isWearingCopperArmor(entity))
        );
    }

    /**
     * Check whether an entity is wearing a piece of copper armor
     *
     * @param entity The {@link LivingEntity} reference
     * @return {@link Boolean True} if the entity is wearing a piece of copper armor
     */
    @Unique
    private boolean isWearingCopperArmor(final LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(Items.COPPER_HELMET) ||
                entity.getItemBySlot(EquipmentSlot.CHEST).is(Items.COPPER_CHESTPLATE) ||
                entity.getItemBySlot(EquipmentSlot.LEGS).is(Items.COPPER_LEGGINGS) ||
                entity.getItemBySlot(EquipmentSlot.FEET).is(Items.COPPER_BOOTS) ||
                entity.getItemBySlot(EquipmentSlot.BODY).is(Items.COPPER_HORSE_ARMOR) ||
                entity.getItemBySlot(EquipmentSlot.BODY).is(Items.COPPER_NAUTILUS_ARMOR);
    }

    /**
     * Get the value of a {@link GameRule<Boolean>}
     *
     * @param gameRule The {@link GameRule<Boolean>}
     * @return The game rule value
     */
    @Unique
    private boolean getGameRuleValue(final GameRule<Boolean> gameRule) {
        return this.getGameRules().get(gameRule);
    }

}