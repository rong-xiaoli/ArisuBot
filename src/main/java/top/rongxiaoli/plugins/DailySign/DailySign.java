package top.rongxiaoli.plugins.DailySign;

import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractSimpleCommand;
import top.rongxiaoli.backend.interfaces.annotations.Plugin;
import top.rongxiaoli.backend.interfaces.PluginBase.PluginBase;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;

@Plugin(name = "DailySign")
public class DailySign extends ArisuBotAbstractSimpleCommand implements PluginBase {
    private boolean pluginStatus = false;
    private static final Timer dataSaveExecuteTimer = new Timer();
    private static final Timer signCountResetter = new Timer();
    private volatile static AtomicInteger signCount;
    private static final DailySignData DATA = new DailySignData();
    private static final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(DailySign.class, "ArisuBot.DailySign");
    public static final DailySign INSTANCE = new DailySign();

    public static void clearSignCount() {
        signCount.set(0);
    }
    public DailySign() {
        super("sign", "qd");
        setDescription("每日签到");
    }
    @Handler
    public void onCommand(CommandContext context) {
        if (!pluginStatus) return;
        // Is console calling?
        if (isConsoleCalling(context)) {
            context.getSender().sendMessage("你是0吗？");
            return;
        }
        long userID = Objects.requireNonNull(context.getSender().getUser()).getId();
        MessageChainBuilder mainBuilder = new MessageChainBuilder();
        GregorianCalendar lastSign = (GregorianCalendar) Calendar.getInstance();
        long lastSignMillis = DATA.queryLastSignDate(userID);
        lastSign.setTimeInMillis(lastSignMillis);
        int signCombo = DATA.querySignCombo(userID);
        GregorianCalendar gCalendar = ((GregorianCalendar) Calendar.getInstance());
        if (gCalendar.get(Calendar.DAY_OF_YEAR) == lastSign.get(Calendar.DAY_OF_YEAR)) {
            mainBuilder.append("你已经签过到了哦~\n");
            mainBuilder.append(DailySignString.GetRandomString());
            context.getSender().sendMessage(mainBuilder.build());
            return;
        }
        GregorianCalendar newSign = ((GregorianCalendar) Calendar.getInstance());
        int newCombo;
        LocalDateTime oldDate = lastSign.toZonedDateTime().toLocalDateTime(), newDate = newSign.toZonedDateTime().toLocalDateTime();
        LocalDateTime nextExpectedDate = oldDate.plusDays(1).truncatedTo(ChronoUnit.DAYS);
        LocalDateTime currentDate = newDate.truncatedTo(ChronoUnit.DAYS);
        newCombo = nextExpectedDate.isEqual(currentDate) ? signCombo + 1 : 1;
        signCount.addAndGet(1);
        synchronized (DATA) {
            DATA.setLastSignDate(userID, newSign.getTimeInMillis());
            DATA.setSignCombo(userID, newCombo);
        }
        mainBuilder.append("签到咯~\n");
        mainBuilder.append(DailySignString.GetRandomString()).append("\n")
                .append("你已连续签到").append(String.valueOf(newCombo)).append("天\n")
                .append("今天你是第").append(String.valueOf(signCount.get())).append("个签到的");
        context.getSender().sendMessage(mainBuilder.build());
    }
    /**
     * Load method. First time loading.
     */
    @Override
    public void load() {
        LOGGER.debug("DailySign loading. ");
        DATA.load();
        LOGGER.verbose("Data load complete. ");
        signCount = new AtomicInteger(0);
        LOGGER.verbose("No config load needed. ");
        final long PERIOD_DAY = 24 * 60 * 60 * 1000;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        signCountResetter.scheduleAtFixedRate(
                new DailySignTimer.SignCountTimer(),
                calendar.getTime(),
                PERIOD_DAY
        );
        dataSaveExecuteTimer.scheduleAtFixedRate(
                new DailySignTimer.DataSaveTimer(),
                Calendar.getInstance().getTime(),
                5 * 60 * 1000
        );
        LOGGER.verbose("The two scheduler started. ");
        enablePlugin();
        LOGGER.debug("DailySign loaded. ");
    }

    /**
     * Reload method. Usually for resetting state.
     */
    @Override
    public void reload() {
        LOGGER.debug("DailySign reloading. ");
        DATA.reload();
        LOGGER.verbose("Data load complete. ");
        LOGGER.verbose("No config reload needed. ");
        LOGGER.debug("DailySign reloaded. ");
    }

    /**
     * Shutdown method.
     */
    @Override
    public void shutdown() {
        LOGGER.debug("DailySign shutting down. ");
        DATA.shutdown();
        LOGGER.verbose("Data shutdown complete. ");
        LOGGER.verbose("No config shutdown needed. ");
        dataSaveExecuteTimer.cancel();
        disablePlugin();
        LOGGER.debug("DailySign shut down. ");
    }

    /**
     * Manually save the data.
     */
    @Override
    public void saveData() {
        LOGGER.debug("Saving data. ");
        DATA.saveData();
        LOGGER.verbose("Data saved. ");
    }

    /**
     * Manually reload the data. Discard the changes in memory.
     */
    @Override
    public void reloadData() {
        LOGGER.debug("DailySign data reloading. ");
        DATA.reload();
        LOGGER.verbose("Data reload complete. ");
        LOGGER.verbose("No config needed. ");
        enablePlugin();
        LOGGER.debug("DailySign reloaded. ");
    }

    /**
     * Disables this plugin.
     */
    @Override
    public void disablePlugin() {
        pluginStatus = false;
    }

    /**
     * Enables this plugin.
     */
    @Override
    public void enablePlugin() {
        pluginStatus = true;
    }

    /**
     * Get the plugin's status, true if on, false if off.
     */
    @Override
    public boolean pluginStatus() {
        return pluginStatus;
    }

    private boolean isConsoleCalling(CommandContext context) {
        long userID = 0, subjectID = 0;
        // From console, return:
        try {
            userID = Objects.requireNonNull(context.getSender().getUser()).getId();
            subjectID = Objects.requireNonNull(context.getSender().getSubject()).getId();
        } catch (NullPointerException e) {
            LOGGER.warning("This command cannot be invoked from console! ");
            return true;
        }
        if (userID == 0 || subjectID == 0) {
            LOGGER.warning("This command cannot be invoked from console! ");
            return true;
        }
        return false;
    }
    @Deprecated
    private long getMilliSecondsToNextDay12AM () {
        Calendar target = Calendar.getInstance();
        target.add(Calendar.DAY_OF_YEAR, 1);
        target.set(Calendar.HOUR_OF_DAY, 0);
        target.set(Calendar.MINUTE, 0);
        target.set(Calendar.SECOND, 0);
        target.set(Calendar.MILLISECOND, 0);
        return target.getTimeInMillis() - System.currentTimeMillis();
    }

}
