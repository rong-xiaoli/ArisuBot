package top.rongxiaoli.plugins.helldivers;

import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractCompositeCommand;
import top.rongxiaoli.backend.interfaces.Plugin;
import top.rongxiaoli.plugins.helldivers.backend.Constants;
import top.rongxiaoli.plugins.helldivers.backend.apifetch.APIChecker;
import top.rongxiaoli.plugins.helldivers.backend.apifetch.NewsFeedHelper;
import top.rongxiaoli.plugins.helldivers.backend.apifetch.WarInfoHelper;
import top.rongxiaoli.plugins.helldivers.backend.datatype.Language;
import top.rongxiaoli.plugins.helldivers.config.HD2Config;

@Plugin(name = "HelldiversHelper")
public class HelldiversHelper extends ArisuBotAbstractCompositeCommand {
    private final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(HelldiversHelper.class, "ArisuBot.HelldiversHelper");
    private volatile boolean pluginStatus = false;

    public static final HelldiversHelper INSTANCE = new HelldiversHelper();
    public static final HD2Config CONFIG = new HD2Config();
    public HelldiversHelper() {
        super("helldivers", "Helldivers", "HD2", "hd2");
    }
    @SubCommand({"notice", "通知"})
    public void getLatestNotice(CommandContext context) {
        if (!APIChecker.stateInBoolean()) {
            context.getSender().sendMessage("目前HellDivers 2 API暂不可用，请稍后再试");
            return;
        }
        context.getSender().sendMessage(NewsFeedHelper.getLatestNews());
    }
    @SubCommand({"stats","统计"})
    public void getCurrentStats(CommandContext context) {
        WarInfoHelper helper = new WarInfoHelper();
        MessageChainBuilder mcb = new MessageChainBuilder();
        mcb.add("目前绝地潜兵们的胜率为" + helper.getMissionSuccessRate() + "%\n");
        mcb.add("赢下了" + helper.getMissionsWon() + "个任务\n");
        mcb.add("输掉了" + helper.getMissionsLost() + "个任务\n");
        mcb.add("共击杀终结族" + helper.getTerminidKills() + "只\n");
        mcb.add("共击杀光能者" + helper.getIlluminateKills() + "个\n");
        mcb.add("共击杀机器人" + helper.getAutomatonKills() + "个\n");
        mcb.add("共损失潜兵" + helper.getDeathCount() + "人\n");
        context.getSender().sendMessage(mcb.build());
    }
    @SubCommand({"info", "信息"})
    public void getCurrentInfo(CommandContext context) {
        WarInfoHelper helper = new WarInfoHelper();
        MessageChainBuilder mcb = new MessageChainBuilder();
        mcb.add("当前影响因数" + helper.getImpactMultiplier() + "\n");
        mcb.add("当前活跃潜兵" + helper.getPresentPlayerCount() + "人\n");
        context.getSender().sendMessage(mcb.build());
    }
    /**
     * Load method. First time loading.
     */
    @Override
    public void load() {
        LOGGER.debug("Helldiver helper loading. ");
        CONFIG.load();
        enablePlugin();
        LOGGER.debug("Helldiver helper loaded. ");
    }

    /**
     * Reload method. Usually for resetting state.
     */
    @Override
    public void reload() {
        LOGGER.debug("Helldiver helper reloading. ");
        CONFIG.reload();
        enablePlugin();
        LOGGER.debug("Helldiver helper reloaded. ");
    }

    /**
     * Shutdown method.
     */
    @Override
    public void shutdown() {
        LOGGER.debug("Helldiver helper shutting down. ");
        CONFIG.shutdown();
        disablePlugin();
        LOGGER.debug("Helldiver helper shut down. ");
    }

    /**
     * Manually save the data.
     */
    @Override
    public void saveData() {
        LOGGER.debug("Helldiver Helper data saving. ");
        CONFIG.saveData();
        LOGGER.debug("Helldiver Helper data saved. ");
    }

    /**
     * Manually reload the data. Discard the changes in memory.
     */
    @Override
    public void reloadData() {
        LOGGER.debug("Helldiver Helper data reloading. ");
        CONFIG.reload();
        LOGGER.debug("Helldiver Helper data reloaded. ");
    }

    /**
     * Manages the plugin's operational state.
     * Thread-safe: State transitions are atomic and visible to all threads.
     * State transition rules:
     * - A disabled plugin cannot be disabled again
     * - An enabled plugin cannot be enabled again
     * - load() must be called before any enable/disable operations
     * - shutdown() invalidates the plugin regardless of current state
     */
    @Override
    public void disablePlugin() throws IllegalStateException {
        pluginStatus = false;
    }

    /**
     * Manages the plugin's operational state.
     * Thread-safe: State transitions are atomic and visible to all threads.
     * State transition rules:
     * - A disabled plugin cannot be disabled again
     * - An enabled plugin cannot be enabled again
     * - load() must be called before any enable/disable operations
     * - shutdown() invalidates the plugin regardless of current state
     */
    @Override
    public void enablePlugin() throws IllegalStateException {
        pluginStatus = true;
    }

    @Override
    public boolean pluginStatus() {
        return pluginStatus;
    }
}
