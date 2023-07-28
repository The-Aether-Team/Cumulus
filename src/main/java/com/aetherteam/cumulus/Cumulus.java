package com.aetherteam.cumulus;

import com.aetherteam.cumulus.api.Menu;
import com.aetherteam.cumulus.api.Menus;
import com.aetherteam.cumulus.data.generators.CumulusLanguageData;
import com.mojang.logging.LogUtils;
import net.minecraft.SharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import org.slf4j.Logger;

import java.util.Map;
import java.util.function.Supplier;

@Mod(Cumulus.MODID)
public class Cumulus {
    public static final String MODID = "cumulus_menus";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final ResourceKey<Registry<Menu>> MENU_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(Cumulus.MODID, "menu"));
    public static final DeferredRegister<Menu> DEFERRED_MENUS = DeferredRegister.create(Cumulus.MENU_REGISTRY_KEY, Cumulus.MENU_REGISTRY_KEY.location().getNamespace());
    public static final Supplier<IForgeRegistry<Menu>> MENU_REGISTRY = DEFERRED_MENUS.makeRegistry(RegistryBuilder::new);

    public Cumulus() {
        DistExecutor.unsafeRunForDist(() -> () -> {
            IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
            modEventBus.addListener(this::dataSetup);

            DeferredRegister<?>[] registers = {
                    Menus.MENUS,
                    Cumulus.DEFERRED_MENUS
            };

            for (DeferredRegister<?> register : registers) {
                register.register(modEventBus);
            }

            ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CumulusConfig.CLIENT_SPEC);

            return true;
        }, () -> () -> {
            Cumulus.LOGGER.info("Disabling Cumulus as it is on server.");

            return false;
        });
    }

    public void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        // Client Data
        generator.addProvider(event.includeClient(), new CumulusLanguageData(packOutput));

        // pack.mcmeta
        PackMetadataGenerator packMeta = new PackMetadataGenerator(packOutput);
        Map<PackType, Integer> packTypes = Map.of(PackType.SERVER_DATA, SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
        packMeta.add(PackMetadataSection.TYPE, new PackMetadataSection(Component.translatable("pack.cumulus_menus.mod.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), packTypes));
        generator.addProvider(true, packMeta);
    }
}
