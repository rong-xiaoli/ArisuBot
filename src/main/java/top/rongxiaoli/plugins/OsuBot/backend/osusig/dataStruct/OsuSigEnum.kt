package top.rongxiaoli.plugins.OsuBot.backend.osusig.dataStruct

import kotlinx.serialization.Serializable
import lombok.AllArgsConstructor
import lombok.Data

class OsuSigEnum {
    /**
     * Osu gamemode. 
     */
    @Data
    @Serializable
    @AllArgsConstructor
    enum class GameMode(var mode: Int) {
        STANDARD(0),
        TAIKO(1),
        CATCH(2),
        MANIA(3);
        private var gameMode = 0
    }

    /**
     * OsuSig tool background color.
     */
    @Data
    @Serializable
    @AllArgsConstructor
    enum class BackgroundColor(val colorString: String) {
        YELLOW("yellow"),
        ORANGE("hexee8833"),
        RED("hexee3333"),
        GRASS_GREEN("hexaadd00"),
        CYAN("hex66ccff"),
        BLUE("hex2255ee"),
        PURPLE("hex8866ee"),
        PINK("hexff66aa"),
        PURPLE_RED("hexbb1177"),
        BLACK("hex000000");
    }

    /**
     * PP Show mode.
     */
    @Data
    @Serializable
    @AllArgsConstructor
    enum class PPShowMode(val showMethod: Int) {
        NO_DISPLAY(-1),
        REPLACE_LEVEL(0),
        AFTER_ACC(1),
        AFTER_RANK(2);
    }
}