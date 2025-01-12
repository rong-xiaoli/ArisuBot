package top.rongxiaoli.plugins.OsuBot.backend.osusig

import kotlinx.serialization.Serializable

@Serializable
enum class GameMode(var mode: Int) {
    STANDARD(0),
    TAIKO(1),
    CATCH(2),
    MANIA(3);
    private var gameMode = 0
}