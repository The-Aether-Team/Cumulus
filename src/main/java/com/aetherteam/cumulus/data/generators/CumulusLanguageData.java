package com.aetherteam.cumulus.data.generators;

import com.aetherteam.cumulus.Cumulus;
import com.aetherteam.cumulus.data.providers.CumulusLanguageProvider;
import net.minecraft.data.PackOutput;

public class CumulusLanguageData extends CumulusLanguageProvider {
    public CumulusLanguageData(PackOutput output) {
        super(output, Cumulus.MODID);
    }

    @Override
    protected void addTranslations() {
        this.addGuiText("button.menu_list", "Menu List");
        this.addGuiText("button.menu_launch", "Launch Menu");
        this.addGuiText("title.menu_selection", "Choose a Main Menu");

        this.addMenuTitle("minecraft", "Minecraft");

        this.addPackDescription("mod", "Cumulus Resources");
    }
}
