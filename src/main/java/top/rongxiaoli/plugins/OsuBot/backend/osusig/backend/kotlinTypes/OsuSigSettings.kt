package top.rongxiaoli.plugins.OsuBot.backend.osusig.backend.kotlinTypes

import kotlinx.serialization.Serializable
import lombok.AllArgsConstructor
import lombok.Data

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
