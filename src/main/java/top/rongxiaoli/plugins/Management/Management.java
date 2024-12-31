package top.rongxiaoli.plugins.Management;

import org.jetbrains.annotations.NotNull;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractCompositeCommand;

public class Management extends ArisuBotAbstractCompositeCommand {
    public Management(@NotNull String primaryName, @NotNull String... secondaryNames) {
        super(primaryName, secondaryNames);
    }

}
