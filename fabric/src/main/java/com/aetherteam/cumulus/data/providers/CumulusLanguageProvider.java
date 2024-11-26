package com.aetherteam.cumulus.data.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class CumulusLanguageProvider extends FabricLanguageProvider {
    protected final String id;

    public CumulusLanguageProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
        this.id = output.getModId();
    }

    @Nullable
    protected TranslationBuilder builderInstance = null;

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        this.builderInstance = translationBuilder;
    }

    protected abstract void addTranslations();

    //--

    public void addBlock(Supplier<? extends Block> key, String name) {
        builderInstance.add(key.get(), name);
    }

    public void add(Block key, String name) {
        builderInstance.add(key, name);
    }

    public void addItem(Supplier<? extends Item> key, String name) {
        builderInstance.add(key.get(), name);
    }

    public void add(Item key, String name) {
        builderInstance.add(key, name);
    }

    public void addItemStack(Supplier<ItemStack> key, String name) {
        builderInstance.add(key.get().getItem(), name);
    }

    public void add(ItemStack key, String name) {
        builderInstance.add(key.getDescriptionId(), name);
    }

    public void addEffect(Supplier<? extends MobEffect> key, String name) {
        builderInstance.add(key.get(), name);
    }

    public void add(MobEffect key, String name) {
        builderInstance.add(key, name);
    }

    public void addEntityType(Supplier<? extends EntityType<?>> key, String name) {
        builderInstance.add(key.get(), name);
    }

    public void add(EntityType<?> key, String name) {
        builderInstance.add(key, name);
    }

    public void addTag(Supplier<? extends TagKey<?>> key, String name) {
        builderInstance.add(key.get(), name);
    }

    public void add(TagKey<?> tagKey, String name) {
        builderInstance.add(tagKey, name);
    }

    //--

    public void add(String key, String value) {
        if (this.builderInstance == null) throw new IllegalStateException("TranslationBuilder was null!");

        this.builderInstance.add(key, value);
    }

    public void addGuiText(String key, String name) {
        this.add("gui." + this.id + "." + key, name);
    }

    public void addMenuTitle(String key, String name) {
        this.add(this.id + ".menu_title." + key, name);
    }

    public void addConfig(String prefix, String name) {
        this.add(this.id + ".configuration." + prefix, name);
    }

    public void addClientConfig(String prefix, String key, String name) {
        this.add("config." + this.id + ".client." + prefix + "." + key, name);
        this.add("config." + this.id + ".client." + prefix + "." + key + ".tooltip", name);
    }

    public void addPackDescription(String packName, String description) {
        this.add("pack." + this.id + "." + packName + ".description", description);
    }
}
