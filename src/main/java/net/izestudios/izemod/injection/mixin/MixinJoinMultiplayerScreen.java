/*
 * This file is part of iZeMod - https://github.com/iZeStudios/iZeMod
 * Copyright (C) 2025 iZeStudios and GitHub contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.izestudios.izemod.injection.mixin;

import net.izestudios.izemod.util.Constants;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Set;

@Mixin(JoinMultiplayerScreen.class)
public abstract class MixinJoinMultiplayerScreen extends Screen {

    protected MixinJoinMultiplayerScreen(final Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void moveVanillaButtons(CallbackInfo ci) {
        final String select = I18n.get(Constants.TEXT_SELECT_SERVER);
        final String direct = I18n.get(Constants.TEXT_DIRECT_CONNECT);
        final String add = I18n.get(Constants.TEXT_ADD_SERVER);
        final String edit = I18n.get(Constants.TEXT_EDIT_SERVER);
        final String delete = I18n.get(Constants.TEXT_DELETE_SERVER);
        final String refresh = I18n.get(Constants.TEXT_REFRESH);
        final String back = CommonComponents.GUI_BACK.getString();
        final Set<String> texts = Set.of(select, direct, add, edit, delete, refresh, back);

        for (final GuiEventListener element : children()) {
            if (element instanceof final Button button) {
                final String message = button.getMessage().getString();
                if (!texts.contains(message)) {
                    continue;
                }

                if (message.equals(select) || message.equals(edit)) {
                    button.setX(this.width / 2 - 206);
                } else if (message.equals(direct) || message.equals(delete)) {
                    button.setX(this.width / 2 - 102);
                } else if (message.equals(add) || message.equals(refresh)) {
                    button.setX(this.width / 2 + 2);
                } else if (message.equals(back)) {
                    button.setX(this.width / 2 + 4 + 102);
                }
                if (message.equals(select) || message.equals(direct) || message.equals(add)) {
                    button.setY(this.height - 52);
                } else {
                    button.setY(this.height - 28);
                }
                button.setWidth(100);
                button.setHeight(20);
            }
        }

        this.addRenderableWidget(Button.builder(Component.translatable("screens.multiplayer.manage"), button -> {
            // TODO
        }).bounds(this.width / 2 + 4 + 102, this.height - 52, 100, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("screens.multiplayer.last"), button -> {
            // TODO
        }).bounds(this.width - 207 , 5, 100, 20).build());
    }

}
