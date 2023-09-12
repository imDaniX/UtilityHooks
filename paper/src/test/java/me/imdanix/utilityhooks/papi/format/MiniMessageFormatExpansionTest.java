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

public class MiniMessageFormatExpansionTest {
    private final Player mockPlayer = Mockito.mock(Player.class);
    private final PlaceholderExpansion mockExpansion = mock(PlaceholderExpansion.class);
    private final MiniMessageFormatExpansion expansion = new MiniMessageFormatExpansion() {
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
                {"&aHello", "<green>Hello"},
                {"&aHello&x&1&2&3&4&5&6 World", "<green>Hello</green><#123456> World"},
                {"&aHello&#123456 World", "<green>Hello</green><#123456> World"},
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
