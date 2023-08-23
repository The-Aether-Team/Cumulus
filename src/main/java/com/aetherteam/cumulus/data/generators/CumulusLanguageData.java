package com.aetherteam.cumulus.data.generators;

import com.aetherteam.cumulus.Cumulus;
import com.aetherteam.cumulus.data.providers.CumulusLanguageProvider;
import net.minecraft.data.DataGenerator;

public class CumulusLanguageData extends CumulusLanguageProvider {
    public CumulusLanguageData(DataGenerator output) {
        super(output, Cumulus.MODID);
    }

    @Override
    protected void addTranslations() {
        this.addGuiText("button.menu_list", "Menu List");
        this.addGuiText("button.menu_launch", "Launch Menu");
        this.addGuiText("title.menu_selection", "Choose a Main Menu");

        this.addMenuTitle("minecraft", "Minecraft");

        this.addClientConfig("menu", "enable_menu_api", "Determines whether the Menu API is enabled or not");
        this.addClientConfig("menu", "active_menu", "Sets the current active menu title screen");
        this.addClientConfig("menu", "enable_menu_list_button", "Adds a button to the top right of the main menu screen to open a menu selection screen");

        this.addPackDescription("mod", "Cumulus Resources");
    }
}
