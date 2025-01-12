package top.rongxiaoli.plugins.OsuBot.backend

import kotlinx.serialization.Serializable

@Serializable
data class UserData(val userID: Long, var userData: UserData ) {

}
