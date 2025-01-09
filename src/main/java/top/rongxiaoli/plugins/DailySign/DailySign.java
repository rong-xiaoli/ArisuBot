package top.rongxiaoli.plugins.DailySign;

import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractSimpleCommand;
import top.rongxiaoli.backend.interfaces.Plugin;
import top.rongxiaoli.backend.interfaces.PluginBase.PluginBase;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
@Plugin(name = "DailySign")
public class DailySign extends ArisuBotAbstractSimpleCommand implements PluginBase {
    private boolean pluginStatus = false;
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
    private static int signCount = 0;
    private static final DailySignData DATA = new DailySignData();
    private static final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(DailySign.class, "ArisuBot.DailySign");
    public static final DailySign INSTANCE = new DailySign();
    public static void clearSignCount() {
        signCount = 0;
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
        LOGGER.verbose(String.valueOf(gCalendar.get(Calendar.DAY_OF_YEAR)));
        LOGGER.verbose(String.valueOf(lastSign.get(Calendar.DAY_OF_YEAR)));
        if (gCalendar.get(Calendar.DAY_OF_YEAR) == lastSign.get(Calendar.DAY_OF_YEAR)) {
            mainBuilder.append("你已经签过到了哦~\n");
            mainBuilder.append(DailySignString.GetRandomString());
            context.getSender().sendMessage(mainBuilder.build());
            return;
        }
        GregorianCalendar newSign = ((GregorianCalendar) Calendar.getInstance());
        int newCombo;
        if (newSign.getTimeInMillis() - lastSign.getTimeInMillis() >= 86400) {
            newCombo = 1;
        } else newCombo = signCombo + 1;
        signCount += 1;
        DATA.setLastSignDate(userID, newSign.getTimeInMillis());
        DATA.setSignCombo(userID, newCombo);
        mainBuilder.append("签到咯~\n");
        mainBuilder.append(DailySignString.GetRandomString()).append("\n")
                .append("你已连续签到").append(String.valueOf(newCombo)).append("天\n")
                .append("今天你是第").append(String.valueOf(signCount)).append("个签到的");
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
        LOGGER.verbose("No config load needed. ");
        executorService.scheduleAtFixedRate(
                new DailySignTimer.SignCountTimer(),
                getMilliSecondsToNextDay12AM(),
                86400000000L,
                TimeUnit.MILLISECONDS
        );
        executorService.scheduleAtFixedRate(
                new DailySignTimer.DataSaveTimer(),
                0,
                5,
                TimeUnit.MINUTES
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
        executorService.shutdown();
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
