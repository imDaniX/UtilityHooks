package me.imdanix.utilityhooks.papi.format;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

@SuppressWarnings("deprecation")
public class LegacyFormatExpansion extends FormatExpansionBase {
    private static final Pattern HEX_COLOR = Pattern.compile("[&§]#" + "([0-9a-fA-F])".repeat(6));

    @Override
    public @NotNull String getIdentifier() {
        return "lformat";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.1";
    }

    @Override
    protected @NotNull String reformat(@NotNull String str) {
        return ChatColor.translateAlternateColorCodes(
                '&',
                HEX_COLOR.matcher(str).replaceAll("§x§$1§$2§$3§$4§$5§$6")
        );
    }
}
