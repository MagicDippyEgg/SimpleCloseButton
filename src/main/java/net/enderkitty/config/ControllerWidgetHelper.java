// Credit: Imajo24I, Mob-Armor-Trims under MIT License
package net.enderkitty.config;

import dev.isxander.yacl3.api.Controller;
import dev.isxander.yacl3.api.utils.Dimension;
import dev.isxander.yacl3.gui.YACLScreen;
import dev.isxander.yacl3.gui.controllers.ControllerWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.CharacterEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Helper class for {@link ControllerWidget}. <br>
 * Overrides many of the methods like {@link ControllerWidget#mouseMoved} to reduce boilerplate code. <br>
 * Make sure to override {@link ControllerWidget#render} and {@link ControllerWidget#setDimension}
 */
public abstract class ControllerWidgetHelper<T extends Controller<?>> extends ControllerWidget<T> {
    protected ControllerWidgetHelper(T control, YACLScreen screen, Dimension<Integer> dim) {
        super(control, screen, dim);
    }
    
    /**
     * Returns all {@link GuiEventListener} used in this Widget
     */
    public abstract List<? extends GuiEventListener> guiEventsListeners();
    
    /**
     * Applies a consumer to all widgets
     */
    public void forWidget(Consumer<GuiEventListener> action) {
        guiEventsListeners().forEach(action);
    }
    
    /**
     * Checks if any widget matches the predicate
     */
    public boolean anyWidgetMatches(Predicate<GuiEventListener> action) {
        return guiEventsListeners().stream().anyMatch(action);
    }
    
    @Override
    protected int getHoveredControlWidth() {
        return getUnhoveredControlWidth();
    }
    
    /**
     * Called when the mouse is moved within the GUI element.
     *
     * @param mouseX the X coordinate of the mouse.
     * @param mouseY the Y coordinate of the mouse.
     */
    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        forWidget(widget -> widget.mouseMoved(mouseX, mouseY));
        super.mouseMoved(mouseX, mouseY);
    }
    
    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        return anyWidgetMatches(widget -> widget.mouseClicked(event, doubleClick)) || super.mouseClicked(event, doubleClick);
    }
    
    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        return anyWidgetMatches(widget -> widget.mouseReleased(event)) || super.mouseReleased(event);
    }
    
    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dragX, double dragY) {
        return anyWidgetMatches(widget -> widget.mouseDragged(event, dragX, dragY)) || super.mouseDragged(event, dragX, dragY);
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        return anyWidgetMatches(widget -> widget.mouseScrolled(mouseX, mouseY, scrollX, scrollY)) || super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }
    
    @Override
    public boolean keyPressed(KeyEvent event) {
        return anyWidgetMatches(widget -> widget.keyPressed(event)) || super.keyPressed(event);
    }
    
    @Override
    public boolean keyReleased(KeyEvent event) {
        return anyWidgetMatches(widget -> widget.keyReleased(event)) || super.keyReleased(event);
    }
    
    @Override
    public boolean charTyped(CharacterEvent event) {
        return anyWidgetMatches(widget -> widget.charTyped(event)) || super.charTyped(event);
    }
    
    @Override
    public boolean isFocused() {
        return anyWidgetMatches(GuiEventListener::isFocused);
    }
    
    @Override
    public void setFocused(boolean focused) {
        forWidget(widget -> widget.setFocused(focused));
    }
    
    /**
     * {@return the narration priority}
     */
    @Override
    public @NotNull NarratableEntry.NarrationPriority narrationPriority() {
        return NarratableEntry.NarrationPriority.HOVERED;
    }
    
    /**
     * Updates the narration output with the current narration information.
     *
     * @param narrationElementOutput the output to update with narration information.
     */
    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {}
}
