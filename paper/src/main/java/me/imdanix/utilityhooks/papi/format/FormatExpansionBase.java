package me.imdanix.utilityhooks.papi.format;

import me.clip.placeholderapi.expansion.Relational;
import me.imdanix.utilityhooks.papi.ExpansionBase;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class FormatExpansionBase extends ExpansionBase implements Relational {
    private static final int PLACEHOLDER_INDEX = 0;
    private static final int PARAMETERS_INDEX = 1;

    @Override
    public String onPlaceholderRequest(Player player1, Player player2, String params) {
        return commonRequestLogic(
                params,
                (name) -> getExpansion(name) instanceof Relational relational ? relational : null,
                (rel, str) -> rel.onPlaceholderRequest(player1, player2, params)
        );
    }

    @Override
    public String onRequest(@Nullable OfflinePlayer player, @NotNull String params) {
        return commonRequestLogic(params, this::getExpansion, (e, str) -> e.onRequest(player, str));
    }

    private <E> @Nullable String commonRequestLogic(
            @NotNull String params,
            @NotNull Function<String, E> expansionFunct,
            @NotNull BiFunction<E, String, String> parserFunct
    ) {
        String[] split = params.split("_", 2);
        E expansion = expansionFunct.apply(split[PLACEHOLDER_INDEX]);
        if (expansion == null) {
            return null;
        }
        String parsed = parserFunct.apply(expansion, split.length == 2 ? split[PARAMETERS_INDEX] : "");
        return parsed == null ? null : reformat(parsed);
    }

    protected abstract @NotNull String reformat(@NotNull String str);

}
