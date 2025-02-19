package top.rongxiaoli.plugins.DailySign;

import net.mamoe.mirai.utils.MiraiLogger;

import java.util.TimerTask;

public class DailySignTimer {
    public static class SignCountTimer extends TimerTask {
        private final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(SignCountTimer.class, "ArisuBot.DailySign.SignCountTimer");
        /**
         * When an object implementing interface {@code Runnable} is used
         * to create a thread, starting the thread causes the object's
         * {@code run} method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method {@code run} is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            DailySign.clearSignCount();
            LOGGER.verbose("Sign counter cleared. ");
        }
    }
    public static class DataSaveTimer extends TimerTask {

        private final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(DataSaveTimer.class, "ArisuBot.DailySign.DataSaveTimer");
        /**
         * When an object implementing interface {@code Runnable} is used
         * to create a thread, starting the thread causes the object's
         * {@code run} method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method {@code run} is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            DailySign.INSTANCE.saveData();
            LOGGER.verbose("Data saved. ");
        }
    }
}
