package top.rongxiaoli.plugins.OsuBot.utils;

import top.rongxiaoli.plugins.OsuBot.backend.UserBaseData;
import top.rongxiaoli.plugins.OsuBot.backend.osusig.dataStruct.OsuSigBaseData;
import top.rongxiaoli.plugins.OsuBot.backend.osusig.dataStruct.OsuSigSettings;
import top.rongxiaoli.plugins.OsuBot.data.OsuData;

public class OsuUtils {
    private OsuUtils() {
        throw new UnsupportedOperationException("工具类不应该被实例化");
    }
    /**
     * Query username. Null if not found.
     * @param userID QQ number of the user.
     * @return The username.
     */
    public static String getName(long userID) {
        UserBaseData bData = OsuData.INSTANCE.getUserData(userID);
        if (bData == null) return null;
        return bData.getUserName();
    }
    /**
     * Set the username.
     * @param userID User's QQ number.
     * @param username osu! username.
     */
    public static void setName(long userID, String username) {
        UserBaseData bData = OsuData.INSTANCE.getUserData(userID);
        UserBaseData newData;
        if (bData != null) newData = new UserBaseData(username, bData.getOsuSigBaseData());
        else newData = new UserBaseData(username, new OsuSigBaseData(new OsuSigSettings()));
        OsuData.INSTANCE.setUserData(userID, newData);
    }
}
