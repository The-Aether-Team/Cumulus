package com.aetherteam.cumulus.data.providers;

import com.aetherteam.cumulus.client.LanguageProviderBase;
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

public class CumulusLanguageProvider extends FabricLanguageProvider implements LanguageProviderBase {
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

        addTranslations();
    }

    @Override
    public String id() {
        return this.id;
    }

    //--

    @Override
    public void add(String key, String value) {
        if (this.builderInstance == null) throw new IllegalStateException("TranslationBuilder was null!");

        this.builderInstance.add(key, value);
    }

    @Override
    public void addBlock(Supplier<? extends Block> key, String name) {
        builderInstance.add(key.get(), name);
    }

    @Override
    public void add(Block key, String name) {
        builderInstance.add(key, name);
    }

    @Override
    public void addItem(Supplier<? extends Item> key, String name) {
        builderInstance.add(key.get(), name);
    }

    @Override
    public void add(Item key, String name) {
        builderInstance.add(key, name);
    }

    @Override
    public void addItemStack(Supplier<ItemStack> key, String name) {
        builderInstance.add(key.get().getItem(), name);
    }

    @Override
    public void add(ItemStack key, String name) {
        builderInstance.add(key.getDescriptionId(), name);
    }

    @Override
    public void addEffect(Supplier<? extends MobEffect> key, String name) {
        builderInstance.add(key.get(), name);
    }

    @Override
    public void add(MobEffect key, String name) {
        builderInstance.add(key, name);
    }

    @Override
    public void addEntityType(Supplier<? extends EntityType<?>> key, String name) {
        builderInstance.add(key.get(), name);
    }

    @Override
    public void add(EntityType<?> key, String name) {
        builderInstance.add(key, name);
    }

    @Override
    public void addTag(Supplier<? extends TagKey<?>> key, String name) {
        builderInstance.add(key.get(), name);
    }

    @Override
    public void add(TagKey<?> tagKey, String name) {
        builderInstance.add(tagKey, name);
    }
}
