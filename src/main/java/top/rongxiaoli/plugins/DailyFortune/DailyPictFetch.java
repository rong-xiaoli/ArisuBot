package top.rongxiaoli.plugins.DailyFortune;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.interfaces.PluginBase.PluginBase;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;
import java.util.UUID;

public class DailyPictFetch {
    public static class DailyPictureFetch extends TimerTask {
        private static final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(DailyPictFetch.class, "ArisuBot.DailyFortune.DailyPictureFetch");

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            LOGGER.verbose("Start daily pict fetch for DailyFortune. ");
            List<PluginBase> pluginBases = ArisuBot.LOADER.getPlugins();
            for (PluginBase base :
                    pluginBases) {
                if (base.getClass().getName().equals(DailyFortune.class.getName()) && !base.pluginStatus())
                    return;
            }
            File directoryPathFile = new File(ArisuBot.GetDataPath().toFile(), "DailyFortunePicture");
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
            File pictureFile = new File(directoryPathFile, formatter.format(LocalDateTime.now()) + UUID.randomUUID().toString().substring(0, 8) + ".webp");
            try {
                HttpUtil.downloadFile("https://t.alcy.cc/moemp", pictureFile);
            } catch (HttpException e) {
                LOGGER.warning("Fail to download file from \"https://t.alcy.cc/moemp\". ");
                LOGGER.warning(e);
            } catch (Exception e) {
                LOGGER.error("Unknown exception caught while downloading file from \"https://t.alcy.cc/moemp\". ", e);
            }
            File pictureFileFormatted;

        }
    }
}
