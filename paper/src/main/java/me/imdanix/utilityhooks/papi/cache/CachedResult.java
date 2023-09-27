package me.imdanix.utilityhooks.papi.cache;

import org.jetbrains.annotations.Nullable;

import static java.lang.System.currentTimeMillis;

public record CachedResult(@Nullable String value, long start) {
    public boolean isOutdated(long offset) {
        return currentTimeMillis() >= start + offset;
    }
}
