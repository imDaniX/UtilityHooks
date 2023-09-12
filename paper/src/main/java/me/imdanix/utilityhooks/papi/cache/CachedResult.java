package me.imdanix.utilityhooks.papi.cache;

import org.jetbrains.annotations.Nullable;

import static java.lang.System.currentTimeMillis;

public final class CachedResult {
    private final @Nullable String value;
    private final long start;

    public CachedResult(@Nullable String value, long start) {
        this.value = value;
        this.start = start;
    }

    public boolean isOutdated(long offset) {
        return currentTimeMillis() >= start + offset;
    }

    public @Nullable String value() {
        return value;
    }
}
