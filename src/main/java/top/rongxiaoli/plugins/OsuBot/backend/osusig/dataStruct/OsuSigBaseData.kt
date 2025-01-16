package top.rongxiaoli.plugins.OsuBot.backend.osusig.dataStruct

import kotlinx.serialization.Serializable
import lombok.AllArgsConstructor
import lombok.Data

@Data
@Serializable
@AllArgsConstructor
data class OsuSigBaseData(val osuSigSettings: OsuSigSettings)
