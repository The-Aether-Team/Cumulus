package com.aetherteam.cumulus.mixin.mixins.client;

import com.aetherteam.cumulus.client.OpeningScreenEvents;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export = true)
@Mixin(value = Minecraft.class, priority = 1100)
public abstract class MinecraftMixin {

    @Shadow
    @Nullable
    public Screen screen;

    @Definition(id = "screen", field = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;")
    @Definition(id = "guiScreen", local = @Local(argsOnly = true, type = Screen.class))
    @Expression("this.screen = guiScreen")
    @WrapOperation(method = "setScreen", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private void beforeScreenSetCall(Minecraft instance, Screen value, Operation<Void> original, @Local(argsOnly = true) LocalRef<Screen> guiScreenRef) {
        if (value != null) {
            var replacementScreen = OpeningScreenEvents.POST.invoker().onOpening(this.screen, value);

            if (replacementScreen != null) {
                value = replacementScreen;

                guiScreenRef.set(replacementScreen);
            }
        }

        original.call(instance, value);
    }
}
