package top.rongxiaoli.plugins.OsuBot.backend.osusig.dataStruct

import kotlinx.serialization.Serializable
import lombok.AllArgsConstructor
import lombok.Data

/**
 * OsuSig settings.
 * Var 1: game mode.
 * Var 2: PP show mode.
 * Var 3: background color.
 * Var 4: show country rank.
 * Var 5: replace PP with rank score.
 * Var 6: show exp bar.
 * Var 7: exp bar use the same color as the background color.
 * @param gameMode game mode.
 */
@Data
@Serializable
@AllArgsConstructor
data class OsuSigSettings(
    var gameMode: OsuSigEnum.GameMode = OsuSigEnum.GameMode.STANDARD,
    var ppShowMode: OsuSigEnum.PPShowMode = OsuSigEnum.PPShowMode.NO_DISPLAY,
    var backgroundColor: OsuSigEnum.BackgroundColor = OsuSigEnum.BackgroundColor.YELLOW,
    var showCountryRank: Boolean = true,
    var replacePPWithRankScore: Boolean = false,
    var showExpBar: Boolean = true,
    var expBarUseSameColor: Boolean = false
    )
