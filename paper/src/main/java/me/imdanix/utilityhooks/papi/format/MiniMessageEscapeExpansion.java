package me.imdanix.utilityhooks.papi.format;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

public class MiniMessageEscapeExpansion extends FormatExpansionBase {
    @Override
    public @NotNull String getIdentifier() {
        return "mmescape";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    protected @NotNull String reformat(@NotNull String str) {
        return MiniMessage.miniMessage().escapeTags(str.replace('§', '&'));
    }
}
