package top.rongxiaoli.plugins.helldivers;

import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractCompositeCommand;
import top.rongxiaoli.backend.interfaces.Plugin;
import top.rongxiaoli.plugins.helldivers.backend.apifetch.APIChecker;
import top.rongxiaoli.plugins.helldivers.backend.apifetch.DSSHelper;
import top.rongxiaoli.plugins.helldivers.backend.apifetch.NewsFeedHelper;
import top.rongxiaoli.plugins.helldivers.backend.apifetch.WarInfoHelper;
import top.rongxiaoli.plugins.helldivers.backend.datatype.diveharder.Cost;
import top.rongxiaoli.plugins.helldivers.backend.datatype.diveharder.DSSInfo;
import top.rongxiaoli.plugins.helldivers.backend.datatype.diveharder.TacticalAction;
import top.rongxiaoli.plugins.helldivers.backend.datatype.hd2.Language;
import top.rongxiaoli.plugins.helldivers.backend.datatype.hd2.SpaceStation2;
import top.rongxiaoli.plugins.helldivers.backend.datatype.hd2.War;
import top.rongxiaoli.plugins.helldivers.config.HD2Config;

import java.util.HashMap;
import java.util.List;

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
        War war = WarInfoHelper.getWarInfo(Language.ZHS);
        MessageChainBuilder mcb = new MessageChainBuilder();
        mcb.add("目前绝地潜兵们的胜率为" + war.getStatistics().getMissionSuccessRate() + "%\n");
        mcb.add("赢下了" + war.getStatistics().getMissionsWon() + "个任务\n");
        mcb.add("输掉了" + war.getStatistics().getMissionsLost() + "个任务\n");
        mcb.add("共击杀终结族" + war.getStatistics().getTerminidKills() + "只\n");
        mcb.add("共击杀光能者" + war.getStatistics().getIlluminateKills() + "个\n");
        mcb.add("共击杀机器人" + war.getStatistics().getAutomatonKills() + "个\n");
        mcb.add("共损失潜兵" + war.getStatistics().getDeaths() + "人\n");
        context.getSender().sendMessage(mcb.build());
    }
    @SubCommand({"info", "信息"})
    public void getCurrentInfo(CommandContext context) {
        War war = WarInfoHelper.getWarInfo(Language.ZHS);
        MessageChainBuilder mcb = new MessageChainBuilder();
        mcb.add("当前影响因数" + war.getImpactMultiplier() + "\n");
        mcb.add("当前活跃潜兵" + war.getStatistics().getPlayerCount() + "人\n");
        context.getSender().sendMessage(mcb.build());
    }
    @SubCommand({"dss", "空间站"})
    public void getDSSInfo(CommandContext context) {
        List<SpaceStation2> dssList = DSSHelper.getDSSListHD2API(Language.ZHS);
        HashMap<Long, DSSInfo> dhDssList = DSSHelper.getDSSListDiveHarderAPI();
        if (dssList.isEmpty()) {
            context.getSender().sendMessage("目前没有DSS，或者DSS处于异常情况");
            return;
        }
        MessageChainBuilder mcb = new MessageChainBuilder();
        mcb.add("目前共有" + dssList.size() + "个空间站在线：\n");
        for (SpaceStation2 station :
                dssList) {
            if (station.getPlanet() == null) {
                mcb.add("ID " + station.getId32() + "目前异常（行星名为空）\n\n");
            } else {
                DSSInfo info = dhDssList.get(station.getId32());
                mcb.add("ID" + station.getId32() + "空间站目前位于" + station.getPlanet().getName() + "\n");
                mcb.add("目前星球持有方为：" + station.getPlanet().getCurrentOwner() + "\n");
                if (info == null) mcb.add("DiveHarder API 返回值无效，无法检查战备\n");
                else mcb.add(getTacticalActionsSummary(info));
                mcb.add("FTL目标投票截止日期为" + station.getElectionEnd() + "\n\n");
            }
        }
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

    private String getTacticalActionsSummary(DSSInfo info) {
        List<TacticalAction> actions = info.getTacticalActions();
        StringBuilder builder = new StringBuilder().append("目前进度：\n");
        for (TacticalAction action :
                actions) {
            if (action == null) continue;
            builder.append("战术行动：").append(action.getName()).append("\n");
            for (Cost cost : action.getCost())
                builder.append(cost.getId()).append("进度").append(cost.getCurrentValue() / cost.getTargetValue() * 100).append("%\n");
        }
        return builder.toString();
    }
}
