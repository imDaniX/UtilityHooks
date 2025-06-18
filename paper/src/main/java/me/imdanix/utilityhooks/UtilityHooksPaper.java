package me.imdanix.utilityhooks;

import me.imdanix.utilityhooks.papi.cache.CacheExpansion;
import me.imdanix.utilityhooks.papi.format.LegacyFormatExpansion;
import me.imdanix.utilityhooks.papi.format.MiniMessageEscapeExpansion;
import me.imdanix.utilityhooks.papi.format.MiniMessageFormatExpansion;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class UtilityHooksPaper extends JavaPlugin {
    private final List<Hookable> hookables = new ArrayList<>();

    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();
        List<Hookable> toHook = new ArrayList<>();
        if (pluginManager.isPluginEnabled("PlaceholderAPI")) {
            getLogger().fine("PlaceholderAPI found. Registering expansions.");
            toHook.addAll(Arrays.asList(
                    new CacheExpansion(),
                    new LegacyFormatExpansion(),
                    new MiniMessageFormatExpansion(),
                    new MiniMessageEscapeExpansion()
            ));
        }

        for (var hookable : toHook) {
            if (hookable.hook()) {
                hookables.add(hookable);
            }
        }
    }

    @Override
    public void onDisable() {
        var iterator = hookables.iterator();
        while (iterator.hasNext()) {
            iterator.next().unhook();
            iterator.remove();
        }
    }
}
