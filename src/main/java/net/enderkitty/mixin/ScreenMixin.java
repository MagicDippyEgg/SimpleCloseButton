package net.enderkitty.mixin;

import net.enderkitty.SimpleCloseButton;
import net.enderkitty.config.ScreenEntry;
import net.enderkitty.config.SimpleCloseButtonConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.ChestMenu;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value= EnvType.CLIENT)
@Mixin(Screen.class)
public abstract class ScreenMixin extends AbstractContainerEventHandler implements Renderable {
    @Unique private static final WidgetSprites TEXTURES = new WidgetSprites(
            Identifier.fromNamespaceAndPath(SimpleCloseButton.MOD_ID, "widget/close_button"),
            Identifier.fromNamespaceAndPath(SimpleCloseButton.MOD_ID, "widget/close_button_highlighted")
    );
    @Unique Button closeButton = new ImageButton(0, 0, 12, 12, TEXTURES, button -> {
        this.onClose();
    });
    @Unique SimpleCloseButtonConfig config = SimpleCloseButtonConfig.HANDLER.instance();
    
    
    @Shadow public abstract void onClose();
    @Shadow protected abstract <T extends GuiEventListener & Renderable & net.minecraft.client.gui.narration.NarratableEntry> T addRenderableWidget(T drawableElement);
    @Shadow public int width;
    @Shadow public int height;
    @Shadow @Nullable protected Minecraft minecraft;
    
    
    @Inject(method = "init(II)V", at = @At("TAIL"))
    public final void initCloseButtons(int width, int height, CallbackInfo ci) {
        if (config.modEnabled && minecraft != null) {
            Screen currentScreen = (Screen) (Object) this;
            if (currentScreen instanceof ContainerScreen && config.chestInventory && minecraft.player != null) {
                ChestMenu handler = (ChestMenu) minecraft.player.containerMenu;
                switch (handler.getRowCount()) {
                    case 1 -> closeButtonWidget(config.chestInventoryX, config.chestInventoryY1);
                    case 2 -> closeButtonWidget(config.chestInventoryX, config.chestInventoryY2);
                    case 3 -> closeButtonWidget(config.chestInventoryX, config.chestInventoryY3);
                    case 4 -> closeButtonWidget(config.chestInventoryX, config.chestInventoryY4);
                    case 5 -> closeButtonWidget(config.chestInventoryX, config.chestInventoryY5);
                    case 6 -> closeButtonWidget(config.chestInventoryX, config.chestInventoryY6);
                }
            }
            
            for (ScreenEntry screenEntry : config.screens) {
                if (currentScreen.getClass().getCanonicalName() != null &&
                        currentScreen.getClass().getCanonicalName().equals(screenEntry.screen())) {
                    if (screenEntry.recipeBook() && currentScreen instanceof AbstractRecipeBookScreen<?> screenWithBook && ((RecipeBookAccessor) screenWithBook).getRecipeBookComponent().isVisible()) {
                        closeButtonWidget(screenEntry.bookX(), screenEntry.bookY());
                    } else {
                        closeButtonWidget(screenEntry.x(), screenEntry.y());
                    }
                }
            }
        }
    }
    
    @Inject(method = "tick()V", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (config.modEnabled) {
            Screen currentScreen = (Screen) (Object) this;
            for (ScreenEntry screenEntry : config.screens) {
                if (currentScreen.getClass().getCanonicalName() != null &&
                        currentScreen.getClass().getCanonicalName().equals(screenEntry.screen())
                        && screenEntry.recipeBook() && currentScreen instanceof AbstractRecipeBookScreen<?> screenWithBook) {
                    if (((RecipeBookAccessor) screenWithBook).getRecipeBookComponent().isVisible()) {
                        closeButton.setX(this.width / 2 + screenEntry.bookX());
                        closeButton.setY(this.height / 2 - screenEntry.bookY());
                    } else {
                        closeButton.setX(this.width / 2 + screenEntry.x());
                        closeButton.setY(this.height / 2 - screenEntry.y());
                    }
                }
            }
        }
    }
    
    @Unique
    public void closeButtonWidget(int x, int y) {
        closeButton.setX(this.width / 2 + x);
        closeButton.setY(this.height / 2 - y);
        if (config.tooltip) closeButton.setTooltip(Tooltip.create(Component.translatable("simple-close-button.button.tooltip")));
        addRenderableWidget(closeButton);
    }
}
