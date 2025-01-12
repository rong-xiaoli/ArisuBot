package top.rongxiaoli.plugins.OsuBot.backend.osusig;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import org.jetbrains.annotations.NotNull;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.plugins.OsuBot.backend.osusig.backend.kotlinTypes.OsuSigSettings;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class OsuSigUtils {
    public static final String API_Base = "https://osusig.lolicon.app/sig.php";
    public static String getURL(@NotNull String userName, @NotNull OsuSigSettings settings) throws IllegalArgumentException {
        StringBuilder builder = new StringBuilder();
        builder.append(API_Base);
        if (userName.isEmpty()) throw new IllegalArgumentException("Argument \"username\" is empty. ");
        builder.append("?uname=").append(userName);
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
    public static byte[] downloadPictureInBytes(String targetURL, long userID) throws HttpException {
        return HttpUtil.createGet(targetURL).execute().bodyBytes();

    }
}
