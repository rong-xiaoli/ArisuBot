package top.rongxiaoli.plugins.OsuBot.backend.osusig

import kotlinx.serialization.Serializable

@Serializable
enum class BackgroundColor(val hex: String) {
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
//    fun getHex(): String {
//        return this.hex;
//    }
}