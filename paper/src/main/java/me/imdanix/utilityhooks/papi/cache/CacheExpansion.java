package me.imdanix.utilityhooks.papi.cache;

import me.clip.placeholderapi.expansion.Cacheable;
import me.clip.placeholderapi.expansion.Cleanable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.imdanix.utilityhooks.papi.ExpansionBase;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.System.currentTimeMillis;

public class CacheExpansion extends ExpansionBase implements Cacheable, Cleanable {
    private static final UUID ZERO_UUID = new UUID(0, 0);
    private static final int OFFSET_INDEX = 0;
    private static final int PLACEHOLDER_INDEX = 1;
    private static final int PARAMETERS_INDEX = 2;

    private final Map<UUID, Map<String, CachedResult>> cache = new ConcurrentHashMap<>();

    @Override
    public @NotNull String getIdentifier() {
        return "cache";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.3";
    }

    @Override
    public @Nullable String onRequest(@Nullable OfflinePlayer player, @NotNull String params) {
        String[] split = params.split("_", 3); // offset_phname_params
        if (split.length < 3) {
            return null;
        }
        try {
            return findResult(
                    player,
                    Long.parseLong(split[OFFSET_INDEX]) * 1000,
                    split[PLACEHOLDER_INDEX],
                    split.length == 3 ? split[PARAMETERS_INDEX] : ""
            );
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private @Nullable String findResult(@Nullable OfflinePlayer player, long offset, @NotNull String phName, @NotNull String params) {
        UUID id = player == null ? ZERO_UUID : player.getUniqueId();
        var playerCache = cache.computeIfAbsent(id, (v) -> new ConcurrentHashMap<>());
        CachedResult result = playerCache.compute(params, (k, existing) -> {
            if (existing == null || (offset != 0 && existing.isOutdated(offset))) {
                PlaceholderExpansion expansion = getExpansion(phName);
                return new CachedResult(
                        expansion == null ? null : expansion.onRequest(player, params),
                        currentTimeMillis()
                );
            }
            return existing;
        });
        return result.value();
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public void cleanup(Player player) {
        cache.remove(player.getUniqueId());
    }
}
