package net.enderkitty.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.enderkitty.SimpleCloseButton;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.Identifier;

import java.net.URI;
import java.util.List;

public class SimpleCloseButtonConfig {
    public static final ConfigClassHandler<SimpleCloseButtonConfig> HANDLER = ConfigClassHandler.createBuilder(SimpleCloseButtonConfig.class)
            .id(Identifier.fromNamespaceAndPath(SimpleCloseButton.MOD_ID, "simple_close_button_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("simple_close_button.json"))
                    .setJson5(false)
                    .build())
            .build();
    
    
    @SerialEntry public boolean modEnabled = true;
    @SerialEntry public boolean tooltip = true;
    
    @SerialEntry public boolean chestInventory = true;
    @SerialEntry public int chestInventoryX = 73;
    @SerialEntry public int chestInventoryY1 = 63;
    @SerialEntry public int chestInventoryY2 = 72;
    @SerialEntry public int chestInventoryY3 = 81;
    @SerialEntry public int chestInventoryY4 = 90;
    @SerialEntry public int chestInventoryY5 = 99;
    @SerialEntry public int chestInventoryY6 = 108;
    
    @SerialEntry public List<ScreenEntry> screens = List.of(
            new ScreenEntry(CreativeModeInventoryScreen.class.getCanonicalName(), 97, 63, false, 0, 0),
            new ScreenEntry(InventoryScreen.class.getCanonicalName(), 73, 80, true, 150, 80),
            new ScreenEntry(CraftingScreen.class.getCanonicalName(), 73, 80, true, 150, 80),
            new ScreenEntry(CrafterScreen.class.getCanonicalName(), 73, 80, false, 0, 0),
            new ScreenEntry(ShulkerBoxScreen.class.getCanonicalName(), 73, 81, false, 0, 0),
            new ScreenEntry(FurnaceScreen.class.getCanonicalName(), 73, 80, true, 150, 80),
            new ScreenEntry(SmokerScreen.class.getCanonicalName(), 73, 80, true, 150, 80),
            new ScreenEntry(BlastFurnaceScreen.class.getCanonicalName(), 73, 80, true, 150, 80),
            new ScreenEntry(AnvilScreen.class.getCanonicalName(), 73, 80, false, 0, 0),
            new ScreenEntry(DispenserScreen.class.getCanonicalName(), 73, 80, false, 0, 0),
            new ScreenEntry(HopperScreen.class.getCanonicalName(), 73, 64, false, 0, 0),
            new ScreenEntry(MerchantScreen.class.getCanonicalName(), 123, 80, false, 0, 0),
            new ScreenEntry(HorseInventoryScreen.class.getCanonicalName(), 73, 80, false, 0, 0),
            new ScreenEntry(EnchantmentScreen.class.getCanonicalName(), 88, 78, false, 0, 0),
            new ScreenEntry(StonecutterScreen.class.getCanonicalName(), 73, 80, false, 0, 0),
            new ScreenEntry(CartographyTableScreen.class.getCanonicalName(), 73, 80, false, 0, 0),
            new ScreenEntry(SmithingScreen.class.getCanonicalName(), 73, 80, false, 0, 0),
            new ScreenEntry(GrindstoneScreen.class.getCanonicalName(), 73, 80, false, 0, 0),
            new ScreenEntry(LoomScreen.class.getCanonicalName(), 73, 80, false, 0, 0),
            new ScreenEntry(BrewingStandScreen.class.getCanonicalName(), 73, 80, false, 0, 0),
            new ScreenEntry(BeaconScreen.class.getCanonicalName(), 115, 105, false, 0, 0)
    );
    
    
    public static Screen makeScreen(Screen parent) {
        Component text = Component.translatable("config.list.screens.description.link")
                .withStyle(Style.EMPTY.withColor(ChatFormatting.BLUE).withUnderlined(true).withItalic(true).withClickEvent(new ClickEvent.OpenUrl(URI.create("https://linkie.shedaniel.dev/mappings"))));
        
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> builder
                .title(Component.translatable("config.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.title"))
                        
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.option.modEnabled.name"))
                                .description(OptionDescription.of(Component.translatable("config.option.modEnabled.desc")))
                                .binding(defaults.modEnabled, () -> config.modEnabled, value -> config.modEnabled = value)
                                .controller(TickBoxControllerBuilder::create).build())
                        
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.option.tooltip.name"))
                                .description(OptionDescription.of(Component.translatable("config.option.tooltip.desc")))
                                .binding(defaults.tooltip, () -> config.tooltip, value -> config.tooltip = value)
                                .controller(TickBoxControllerBuilder::create).build())

                        .group(OptionGroup.createBuilder().name(Component.translatable("config.group.chestInventory.name")).collapsed(true)
                                .description(OptionDescription.of(Component.translatable("config.group.chestInventory.desc")))

                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.group.chestInventory.option.toggle.name"))
                                        .description(OptionDescription.of(Component.translatable("config.group.chestInventory.option.toggle.desc")))
                                        .binding(defaults.chestInventory, () -> config.chestInventory, value -> config.chestInventory = value)
                                        .controller(TickBoxControllerBuilder::create).build())
                                
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.translatable("config.group.chestInventory.option.posX.name"))
                                        .description(OptionDescription.of(Component.translatable("config.group.chestInventory.option.posX.desc")))
                                        .binding(defaults.chestInventoryX, () -> config.chestInventoryX, value -> config.chestInventoryX = value)
                                        .controller(IntegerFieldControllerBuilder::create).build())
                                
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.translatable("config.group.chestInventory.option.posY1.name"))
                                        .description(OptionDescription.of(Component.translatable("config.group.chestInventory.option.posY1.desc")))
                                        .binding(defaults.chestInventoryY1, () -> config.chestInventoryY1, value -> config.chestInventoryY1 = value)
                                        .controller(IntegerFieldControllerBuilder::create).build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.translatable("config.group.chestInventory.option.posY2.name"))
                                        .description(OptionDescription.of(Component.translatable("config.group.chestInventory.option.posY2.desc")))
                                        .binding(defaults.chestInventoryY2, () -> config.chestInventoryY2, value -> config.chestInventoryY2 = value)
                                        .controller(IntegerFieldControllerBuilder::create).build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.translatable("config.group.chestInventory.option.posY3.name"))
                                        .description(OptionDescription.of(Component.translatable("config.group.chestInventory.option.posY3.desc")))
                                        .binding(defaults.chestInventoryY3, () -> config.chestInventoryY3, value -> config.chestInventoryY3 = value)
                                        .controller(IntegerFieldControllerBuilder::create).build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.translatable("config.group.chestInventory.option.posY4.name"))
                                        .description(OptionDescription.of(Component.translatable("config.group.chestInventory.option.posY4.desc")))
                                        .binding(defaults.chestInventoryY4, () -> config.chestInventoryY4, value -> config.chestInventoryY4 = value)
                                        .controller(IntegerFieldControllerBuilder::create).build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.translatable("config.group.chestInventory.option.posY5.name"))
                                        .description(OptionDescription.of(Component.translatable("config.group.chestInventory.option.posY5.desc")))
                                        .binding(defaults.chestInventoryY5, () -> config.chestInventoryY5, value -> config.chestInventoryY5 = value)
                                        .controller(IntegerFieldControllerBuilder::create).build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.translatable("config.group.chestInventory.option.posY6.name"))
                                        .description(OptionDescription.of(Component.translatable("config.group.chestInventory.option.posY6.desc")))
                                        .binding(defaults.chestInventoryY6, () -> config.chestInventoryY6, value -> config.chestInventoryY6 = value)
                                        .controller(IntegerFieldControllerBuilder::create).build()).build())
                        .build()
                )
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.category.screens"))
                        .group(ListOption.<ScreenEntry>createBuilder()
                                .name(Component.translatable("config.list.screens"))
                                .description(OptionDescription.of(Component.translatable("config.list.screens.description", text)))
                                .binding(defaults.screens, () -> config.screens, value -> config.screens = value)
                                .controller(ScreenEntryController.Builder::create)
                                .initial(new ScreenEntry("<input screen class directory here>", 0, 0, false, 0, 0))
                                .build()
                        )
                        
                        .build()
                )
                
        ).generateScreen(parent);
    }
}
