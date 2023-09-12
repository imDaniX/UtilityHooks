package me.imdanix.utilityhooks.papi.format;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class LegacyFormatExpansionTest {
    private final Player mockPlayer = Mockito.mock(Player.class);
    private final PlaceholderExpansion mockExpansion = mock(PlaceholderExpansion.class);
    private final LegacyFormatExpansion expansion = new LegacyFormatExpansion() {
        @Override
        public @Nullable PlaceholderExpansion getExpansion(@NotNull String name) {
            return mockExpansion;
        }
    };

    @BeforeClass
    public void setup() {
        when(mockPlayer.getUniqueId()).thenReturn(UUID.randomUUID());
    }

    @DataProvider
    public Object[][] onRequestData() {
        return new Object[][] {
                {"&aHello", "§aHello"},
                {"&aHello&x&1&2&3&4&5&6", "§aHello§x§1§2§3§4§5§6"},
                {"&aHello&#123456", "§aHello§x§1§2§3§4§5§6"},
                {"Test", "Test"},
                {null, null}
        };
    }

    @Test(dataProvider = "onRequestData")
    public void onRequestTest(String phResult, String expected) {
        when(mockExpansion.onRequest(any(), anyString())).thenReturn(phResult);
        assertEquals(
                expansion.onRequest(mockPlayer, "placeholder"),
                expected
        );
    }
}
