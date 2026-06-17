package net.enderkitty.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(value = EnvType.CLIENT)
@Mixin(AbstractRecipeBookScreen.class)
public interface RecipeBookAccessor {
    @Accessor("recipeBookComponent") RecipeBookComponent<?> getRecipeBookComponent();
}
