package top.rongxiaoli.plugins.OsuBot.backend.osusig;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import org.jetbrains.annotations.NotNull;
import top.rongxiaoli.plugins.OsuBot.backend.UserBaseData;
import top.rongxiaoli.plugins.OsuBot.backend.osusig.dataStruct.OsuSigBaseData;
import top.rongxiaoli.plugins.OsuBot.backend.osusig.dataStruct.OsuSigSettings;
import top.rongxiaoli.plugins.OsuBot.data.OsuData;

import java.util.NoSuchElementException;

public class OsuSigUtils {
    public static final String API_Base = "https://osusig.lolicon.app/sig.php?";
    public static String getURL(@NotNull String userName, @NotNull OsuSigSettings settings) throws IllegalArgumentException {
        StringBuilder builder = new StringBuilder();
        builder.append(API_Base);
        if (userName.isEmpty()) throw new IllegalArgumentException("Argument \"username\" is empty. ");
        builder.append("uname=").append(userName);
        builder.append("&mode=").append(settings.component1().getMode());           // Play mode.
        if (settings.component2().getShowMethod() != -1)
            builder.append("&pp=").append(settings.component2().getShowMethod());   // PP show mode.
        builder.append("&colour=").append(settings.component3().getColorString());  // Color.
        if (settings.component4()) builder.append("&countryrank");                  // Show country rank.
        if (settings.component5()) builder.append("&rankedscore");                  // True to replace play count with ranked score
        if (settings.component6()) builder.append("&xpbar");                        // True to add a xp bar.
        if (settings.component7()) builder.append("&xpbarhex");                     // True to use the same color as background color.
        return builder.toString();
    }
    private static byte[] downloadPictureInBytes(String targetURL) throws HttpException {
        return HttpUtil.createGet(targetURL).execute().bodyBytes();

    }

    public static byte[] getPicture(long userID) throws HttpException {
        UserBaseData data = OsuData.INSTANCE.getUserData(userID);
        if (data.getUserName().isEmpty()) return null;
        String username = data.getUserName();
        OsuSigBaseData osuSigBaseData = data.getOsuSigBaseData();
        return OsuSigUtils.downloadPictureInBytes(
                OsuSigUtils.getURL(username, osuSigBaseData.getOsuSigSettings()));
    }
    /**
     * Set the osu!sig base data. Throw an exception if we can not find the username.
     * @param userID User's QQ number.
     * @param data new {@code OsuSigBaseData}.
     */
    public static void setBaseData(long userID, OsuSigBaseData data) {
        UserBaseData baseData = OsuData.INSTANCE.getUserData(userID);
        UserBaseData newData;
        if (baseData != null) newData = new UserBaseData(baseData.getUserName(), data);
        else throw new NoSuchElementException("Found not existing user: id " + userID);
        OsuData.INSTANCE.setUserData(userID, newData);
    }

    /**
     * Get the osu!sig base data. Null if not found.
     * @param userID User's QQ number.
     * @return Osu!sig base data.
     */
    public static OsuSigBaseData getBaseData(long userID) {
        UserBaseData baseData = OsuData.INSTANCE.getUserData(userID);
        if (baseData == null) return null;
        return baseData.getOsuSigBaseData();
    }
}
