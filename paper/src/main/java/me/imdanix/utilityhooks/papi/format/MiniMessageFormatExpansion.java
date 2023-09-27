package me.imdanix.utilityhooks.papi.format;

import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;
import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacyAmpersand;

public class MiniMessageFormatExpansion extends FormatExpansionBase {
    @Override
    public @NotNull String getIdentifier() {
        return "mmformat";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    protected @NotNull String reformat(@NotNull String str) {
        return miniMessage().serialize(
                legacyAmpersand().deserialize(str.replace('§', '&'))
        );
    }
}
