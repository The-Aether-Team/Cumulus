package com.aetherteam.cumulus.data.providers;

import com.aetherteam.cumulus.client.LanguageProviderBase;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class CumulusLanguageProvider extends LanguageProvider implements LanguageProviderBase {
    protected final String id;

    public CumulusLanguageProvider(PackOutput output, String id) {
        super(output, id, "en_us");
        this.id = id;
    }

    @Override
    public void addTranslations() {
        LanguageProviderBase.super.addTranslations();
    }

    @Override
    public String id() {
        return this.id;
    }
}
