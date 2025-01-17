package top.rongxiaoli.plugins.OsuBot.backend

import kotlinx.serialization.Serializable
import lombok.AllArgsConstructor
import lombok.Data
import top.rongxiaoli.plugins.OsuBot.backend.osusig.dataStruct.OsuSigBaseData

/**
 * OsuBot user data.
 */
@Data
@Serializable
@AllArgsConstructor
data class UserBaseData(val userName: String, val osuSigBaseData: OsuSigBaseData)
