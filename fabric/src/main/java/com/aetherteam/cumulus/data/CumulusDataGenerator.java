package com.aetherteam.cumulus.data;

import com.aetherteam.cumulus.data.providers.CumulusLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.SharedConstants;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;

import java.util.Optional;

public class CumulusDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();

        // Client Data
        pack.addProvider(CumulusLanguageProvider::new);

        // pack.mcmeta
        pack.addProvider((FabricPackOutput output) -> {
            return new PackMetadataGenerator(output)
                    .add(PackMetadataSection.CLIENT_TYPE, new PackMetadataSection(Component.translatable("pack.cumulus_menus.mod.description"), new InclusiveRange<>(SharedConstants.getCurrentVersion().packVersion(PackType.CLIENT_RESOURCES))));
        });
    }
}
