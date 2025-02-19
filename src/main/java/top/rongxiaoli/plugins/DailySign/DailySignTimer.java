package top.rongxiaoli.plugins.DailySign;

import net.mamoe.mirai.utils.MiraiLogger;

import java.util.TimerTask;

public class DailySignTimer {
    public static class SignCountTimer extends TimerTask {
        private final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(SignCountTimer.class, "ArisuBot.DailySign.SignCountTimer");

        @Override
        public void run() {
            DailySign.clearSignCount();
            LOGGER.verbose("Sign counter cleared. ");
        }
    }
    public static class DataSaveTimer extends TimerTask {

        private final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(DataSaveTimer.class, "ArisuBot.DailySign.DataSaveTimer");
        @Override
        public void run() {
            DailySign.INSTANCE.saveData();
            LOGGER.verbose("Data saved. ");
        }
    }
}
