package com.aetherteam.cumulus.client.event.hooks;

import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.client.WorldDisplayHelper;
import com.aetherteam.cumulus.mixin.mixins.client.accessor.EntityRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class WorldPreviewHooks {
    private static final MutableBoolean setupLockout = new MutableBoolean();

    /**
     * When a {@link TitleScreen} is opened, if the {@link CumulusConfig.Client#enable_world_preview} config is enabled
     * then the world preview is set up, but otherwise it is ensured to be inactive.
     */
    public static void setupWorldPreview(Screen screen) {
        if (screen instanceof TitleScreen && CumulusConfig.CLIENT.enable_world_preview.get()) {
            if (!setupLockout.getValue()) {
                setupLockout.setValue(true);
                WorldDisplayHelper.enableWorldPreview();
                setupLockout.setValue(false);
            }
        } else if (screen instanceof TitleScreen && !CumulusConfig.CLIENT.enable_world_preview.get()) {
            WorldDisplayHelper.resetActive();
        }
    }

    /**
     * Checks if the {@link TitleScreen} should be hidden during loading. This is used to make sure it
     * doesn't show up briefly during the loading screen process when the world preview is being set up.
     *
     * @param screen The currently rendered {@link Screen}.
     * @return Whether to hide the screen, as a {@link Boolean}.
     */
    public static boolean hideScreen(Screen screen) {
        return screen instanceof TitleScreen && CumulusConfig.CLIENT.enable_world_preview.get() && Minecraft.getInstance().level == null;
    }

    /**
     * After the level is loaded for the world preview by other events, when it gets rendered then
     * the panorama-style setup with the displayed menu is handled by {@link WorldDisplayHelper#setupLevelForDisplay()}.
     */
    public static void renderMenuWithWorld() {
        Minecraft minecraft = Minecraft.getInstance();
        if (WorldDisplayHelper.isActive()) {
            if (minecraft.screen == null || minecraft.screen instanceof PauseScreen) { // The menu can only be rendered if there is no screen or a PauseScreen when the level loads.
                WorldDisplayHelper.setupLevelForDisplay();
            }
        }
    }

    /**
     * Handles how the world should be displayed for the world preview. Rendering, sounds, and music are allowed to tick, but nothing else is.
     * This makes the world static and paused but also still animated.
     */
    public static void tickMenuWhenPaused() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null && minecraft.player != null) {
            if (WorldDisplayHelper.isActive() && minecraft.isPaused()) {
                minecraft.gameRenderer.tick();
                minecraft.levelRenderer.tick();
                minecraft.getMusicManager().tick();
                minecraft.getSoundManager().tick(false);
                minecraft.level.animateTick(minecraft.player.getBlockX(), minecraft.player.getBlockY(), minecraft.player.getBlockZ());
                Minecraft.getInstance().particleEngine.tick();
            }
        }
    }

    /**
     * Angles and rotates the camera for the world preview display.
     */
    public static void angleCamera(double partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (WorldDisplayHelper.isActive() && player != null) {
            float f = (float) (partialTick * minecraft.options.panoramaSpeed().get()); // Ensures the rotation speed isn't tied to game tick speed
            float spin = wrapDegrees(player.getViewYRot((float) partialTick) + f * 0.2F);
            player.setYRot(spin);
            player.setXRot(0);
        }
    }

    /**
     * Modified to have a static max at 360 degrees.
     */
    private static float wrapDegrees(float value) {
        return value > 360.0F ? value - 360.0F : value;
    }

    /**
     * @return Whether to hide player screen overlays in the world preview, as a {@link Boolean}.
     */
    public static boolean hideOverlays() {
        return WorldDisplayHelper.isActive();
    }

    /**
     * @return Whether to hide the player in the world preview, as a {@link Boolean}.
     */
    public static boolean shouldHidePlayer() {
        return WorldDisplayHelper.isActive();
    }

    /**
     * Checks whether to hide an entity in the world preview.
     *
     * @param entity The {@link Entity}.
     * @return The {@link Boolean} result.
     */
    public static boolean shouldHideEntity(Entity entity) {
        return WorldDisplayHelper.isActive() && Minecraft.getInstance().player != null && Minecraft.getInstance().player.getVehicle() != null && Minecraft.getInstance().player.getVehicle().is(entity);
    }

    /**
     * Removes or enables an entity's shadow for world preview rendering.
     *
     * @param renderer The {@link EntityRenderer}.
     * @param flag     Whether the entity that the shadow belongs to is hidden.
     */
    public static void adjustShadow(EntityRenderer<?> renderer, boolean flag) {
        EntityRendererAccessor entityRendererAccessor = (EntityRendererAccessor) renderer;
        if (flag) {
            entityRendererAccessor.cumulus$setShadowRadius(0.0F);
        } else {
            if (entityRendererAccessor.cumulus$getShadowRadius() == 0.0F) {
                entityRendererAccessor.cumulus$setShadowRadius(0.5F);
            }
        }
    }
}
