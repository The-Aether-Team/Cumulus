package com.aetherteam.cumulus;

import com.aetherteam.cumulus.api.Menus;
import com.aetherteam.cumulus.data.generators.CumulusLanguageData;
import com.mojang.logging.LogUtils;
import net.minecraft.SharedConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

@Mod(Cumulus.MODID)
public class Cumulus {
    public static final String MODID = "cumulus_menus";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Cumulus(ModContainer mod, IEventBus bus, Dist dist) {
        if (dist == Dist.CLIENT) {
            Menus.init();
            bus.addListener(this::dataSetup);

            mod.registerConfig(ModConfig.Type.CLIENT, CumulusConfig.CLIENT_SPEC);

            mod.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }

    public void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        // Client Data
        generator.addProvider(event.includeClient(), new CumulusLanguageData(packOutput));

        // pack.mcmeta
        PackMetadataGenerator packMeta = new PackMetadataGenerator(packOutput);
        packMeta.add(PackMetadataSection.TYPE, new PackMetadataSection(Component.translatable("pack.cumulus_menus.mod.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES)));
        generator.addProvider(true, packMeta);
    }
}
