package me.imdanix.utilityhooks.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.imdanix.utilityhooks.Hookable;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public abstract class ExpansionBase extends PlaceholderExpansion implements Hookable {
    public @Nullable PlaceholderExpansion getExpansion(@NotNull String name) {
        return getPlaceholderAPI().getLocalExpansionManager().getExpansion(name.toLowerCase(Locale.ROOT));
    }

    @Override
    public final String onPlaceholderRequest(@Nullable Player player, @NotNull String params) {
        return onRequest(player, params);
    }

    @Override
    public abstract @Nullable String onRequest(@Nullable OfflinePlayer player, @NotNull String params);

    @Override
    public @NotNull String getAuthor() {
        return "imDaniX";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean hook() {
        return register();
    }

    @Override
    public void unhook() {
        unregister();
    }
}
