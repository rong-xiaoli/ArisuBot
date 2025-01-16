package top.rongxiaoli.plugins.OsuBot.backend.osuAPI;

import kotlinx.serialization.Serializable;

public class Enums {
    @Serializable
    public enum Ruleset {
        CATCH("fruits"),
        MANIA("mania"),
        TAIKO("taiko"),
        STANDARD("osu");
        Ruleset(String gameMode){}
        // TODO: 2025/1/16 Finish osu api.
    }
}
