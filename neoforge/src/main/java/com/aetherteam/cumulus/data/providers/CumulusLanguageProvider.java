package com.aetherteam.cumulus.data.providers;

import com.aetherteam.cumulus.client.LanguageProviderBase;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.function.Supplier;

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

    @Override
    public void addItemStack(Supplier<ItemStack> key, String name) { }

    @Override
    public void add(ItemStack key, String name) { }
}
