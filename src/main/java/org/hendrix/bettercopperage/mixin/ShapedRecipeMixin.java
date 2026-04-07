package org.hendrix.bettercopperage.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.InstrumentComponent;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.hendrix.bettercopperage.core.BCAItems;
import org.hendrix.bettercopperage.item.CopperHornItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin for the {@link ShapedRecipe class}
 */
@Mixin(ShapedRecipe.class)
public final class ShapedRecipeMixin {

    /**
     * Craft a copper horn based on the goat horn used in the recipe
     *
     * @param input The {@link CraftingInput}
     * @param callbackInfoReturnable The {@link CallbackInfoReturnable}
     */
    @Inject(at = @At("RETURN"), method = "assemble(Lnet/minecraft/world/item/crafting/CraftingInput;)Lnet/minecraft/world/item/ItemStack;", cancellable = true)
    public void assemble(final CraftingInput input, final CallbackInfoReturnable<ItemStack> callbackInfoReturnable) {
        final ItemStack recipeResult = callbackInfoReturnable.getReturnValue();
        if(recipeResult.is(BCAItems.COPPER_HORN)) {
            input.items().stream()
                    .filter(itemStack -> itemStack.is(Items.GOAT_HORN))
                    .findFirst()
                    .ifPresent(goatHorn -> {
                        final InstrumentComponent instrument = goatHorn.get(DataComponents.INSTRUMENT);
                        if(instrument != null) {
                            assert Minecraft.getInstance().player != null;
                            CopperHornItem.upgradeInstrument(recipeResult, Minecraft.getInstance().player.registryAccess(), instrument);
                        }
                        callbackInfoReturnable.setReturnValue(recipeResult);
                    });
        }
    }

}