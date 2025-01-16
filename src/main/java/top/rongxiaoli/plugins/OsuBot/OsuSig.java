package top.rongxiaoli.plugins.OsuBot;

import cn.hutool.http.HttpException;
import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractCompositeCommand;
import top.rongxiaoli.backend.Utils.UserJudgeUtils;
import top.rongxiaoli.backend.interfaces.Plugin;
import top.rongxiaoli.plugins.OsuBot.backend.osusig.OsuSigUtils;
import top.rongxiaoli.plugins.OsuBot.backend.osusig.dataStruct.OsuSigBaseData;
import top.rongxiaoli.plugins.OsuBot.backend.osusig.dataStruct.OsuSigEnum;
import top.rongxiaoli.plugins.OsuBot.backend.osusig.dataStruct.OsuSigSettings;
import top.rongxiaoli.plugins.OsuBot.data.OsuData;
import top.rongxiaoli.plugins.OsuBot.utils.OsuUtils;

import java.util.Locale;
import java.util.Objects;

@Plugin(name = "OsuBot")
public class OsuSig extends ArisuBotAbstractCompositeCommand {
    private volatile boolean pluginStatus = false;
    public static OsuSig INSTANCE = new OsuSig();
    public static final OsuData DATA = new OsuData();
    public MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(OsuBot.class, "ArisuBot.OsuSig");
    public OsuSig() {
        super("osusig", "sig");
    }
    @SubCommand("help")
    @Description("获取osu!sig的使用帮助")
    public void getHelp(CommandContext context) {
        MessageChainBuilder mcb = new MessageChainBuilder();
        mcb.add("你可以使用osu!sig获取一张简要的osu!成绩图：osusig get\n");
        mcb.add("你可以设置背景颜色: osusig setBackground\n");
        mcb.add("设置游戏模式：osusig setGameMode\n");
        mcb.add("设置是否显示国家排名：osusig setCountryRank\n");
        mcb.add("设置是否替换PP为Rank分osusig setReplacePPWithRankScore\n");
        mcb.add("设置PP显示模式：osusig setPPShowMode\n");
        mcb.add("设置是否显示经验条：osusig setExpVisible\n");
        mcb.add("设置经验条是否与背景同色osusig setSameBackgroundColor\n");
        mcb.add("子命令有简写，具体可以访问ArisuBot项目的Wiki来看。\n");
        mcb.add("具体可以使用osusig help 加子命令名来获取详细帮助\n");
        mcb.add("感谢由 神代绮凛 提供的API，他提供了很有帮助的功能\n");
        mcb.add("项目地址：https://osusig.lolicon.app");
        context.getSender().sendMessage(mcb.build());
    }
    @SubCommand("help")
    @Description("获取osu!sig子命令的使用帮助")
    public void getHelp(CommandContext context, String subcommand) {
        MessageChainBuilder mcb = new MessageChainBuilder();
        switch (subcommand.toLowerCase()) {
            default:
                mcb.add("请选择正确的子命令。");
                break;
            case "setbackground":
            case "sbg":
            case "背景":
                mcb.add("设置名片背景颜色\n");
                mcb.add("参数有：\n");
                mcb.add(OsuSigEnum.BackgroundColor.BLACK + "表示黑色；\n");
                mcb.add(OsuSigEnum.BackgroundColor.BLUE + "表示蓝色；\n");
                mcb.add(OsuSigEnum.BackgroundColor.CYAN + "表示淡蓝色；\n");
                mcb.add(OsuSigEnum.BackgroundColor.GRASS_GREEN + "表示草绿色；\n");
                mcb.add(OsuSigEnum.BackgroundColor.ORANGE + "表示橙色；\n");
                mcb.add(OsuSigEnum.BackgroundColor.PINK + "表示粉色；\n");
                mcb.add(OsuSigEnum.BackgroundColor.PURPLE + "表示紫色；\n");
                mcb.add(OsuSigEnum.BackgroundColor.PURPLE_RED + "表示紫红色；\n");
                mcb.add(OsuSigEnum.BackgroundColor.RED + "表示红色；\n");
                mcb.add(OsuSigEnum.BackgroundColor.YELLOW + "表示黄色；\n");
                break;
            case "setgamemode":
            case "sgm":
            case "模式":
                mcb.add("设置名片游戏模式\n");
                mcb.add("参数有：\n");
                mcb.add(OsuSigEnum.GameMode.CATCH + "表示接果；\n");
                mcb.add(OsuSigEnum.GameMode.MANIA + "表示弹琴；\n");
                mcb.add(OsuSigEnum.GameMode.STANDARD + "表示标准；\n");
                mcb.add(OsuSigEnum.GameMode.TAIKO + "表示太鼓；\n");
                break;
            case "setppshowmode":
            case "sppsm":
            case "pp":
            case "pp显示模式":
                mcb.add("设置PP显示模式\n");
                mcb.add("参数有：\n");
                mcb.add(OsuSigEnum.PPShowMode.NO_DISPLAY + "表示不显示\n");
                mcb.add(OsuSigEnum.PPShowMode.REPLACE_LEVEL + "表示替换等级\n");
                mcb.add(OsuSigEnum.PPShowMode.AFTER_ACC + "表示在Acc之后显示\n");
                mcb.add(OsuSigEnum.PPShowMode.AFTER_RANK + "表示在Rank之后显示\n");
                mcb.add("不区分大小写，加了一些关键词针对解析");
                break;
            case "setcountryrank":
            case "scr":
            case "国家排名":
            case "排名":
                mcb.add("设置是否显示国家排名");
                mcb.add("这是一个布尔值，只有是和否；\n");
                mcb.add("你也可以使用true, false或显示，不显示来设置");
                break;
            case "srppwrs":
            case "setreplaceppwithrankscore":
            case "替换pp为rank分":
            case "替换":
                mcb.add("设置是否替换PP为Rank分");
                mcb.add("这是一个布尔值，只有是和否；\n");
                mcb.add("你也可以使用true, false或显示，不显示来设置");
                break;
            case "setexpvisible":
            case "sev":
            case "exp":
            case "经验":
            case "经验条":
            case "显示经验":
                mcb.add("设置是否显示经验条");
                mcb.add("这是一个布尔值，只有是和否；\n");
                mcb.add("你也可以使用true, false或显示，不显示来设置");
                break;
            case "setsamebackgroundcolor":
            case "ssbc":
            case "经验条背景同色":
            case "同色":
            case "经验条同色":
                mcb.add("设置是否统一背景和经验条颜色");
                mcb.add("这是一个布尔值，只有是和否；\n");
                mcb.add("你也可以使用true, false或显示，不显示来设置");
                break;
            case "get":
                mcb.add("获取osu!sig成绩图");
                break;
        }
        context.getSender().sendMessage(mcb.build());
    }

    @SubCommand({"setBackground", "sbg", "背景"})
    @Description("设置名片背景颜色")
    public void setOsuSigBackgroundColor(CommandContext context, OsuSigEnum.BackgroundColor color) {
        if (!pluginStatus) return;
        if (UserJudgeUtils.isConsoleCalling(context)) {
            context.getSender().sendMessage("this command cannot be invoked from console! ");
            return;
        }
        User user = context.getSender().getUser();
        Contact contact = context.getSender().getSubject();
        if (user == null || contact == null) {
            context.getSender().sendMessage("用户为空！");
            return;
        }
        long userID = user.getId();
        if (userID == 0) {
            LOGGER.warning("This command cannot be invoked in terminal! ");
            return;
        }
        if (OsuUtils.getName(userID) == null) {
            context.getSender().sendMessage("请先绑定osu!用户名");
            return;
        }
        OsuSigBaseData data = OsuSigUtils.getBaseData(userID);
        if (data == null) {
            data = new OsuSigBaseData(new OsuSigSettings());
        }
        data.getOsuSigSettings().setBackgroundColor(color);
        OsuSigUtils.setBaseData(userID, data);
        context.getSender().sendMessage("已将颜色设置为：" + color);
    }

    @SubCommand({"setGameMode", "sgm", "模式"})
    @Description("设置游戏模式")
    public void setOsuSigGameMode(CommandContext context, String gameModeStr) {
        if (!pluginStatus) return;
        if (UserJudgeUtils.isConsoleCalling(context)) {
            context.getSender().sendMessage("this command cannot be invoked from console! ");
            return;
        }
        User user = context.getSender().getUser();
        Contact contact = context.getSender().getSubject();
        if (user == null || contact == null) {
            context.getSender().sendMessage("用户为空！");
            return;
        }
        long userID = user.getId();
        if (userID == 0) {
            LOGGER.warning("This command cannot be invoked in terminal! ");
            return;
        }
        OsuSigBaseData oldData = OsuSigUtils.getBaseData(userID);
        if (oldData == null) {
            context.getSender().sendMessage("您尚未绑定用户！");
            return;
        }
        OsuSigSettings settings = oldData.getOsuSigSettings();
        switch (gameModeStr.toLowerCase()) {
            default:
                context.getSender().sendMessage("没有这个模式");
                return;
            case "接果":
            case "catch":
                settings.setGameMode(OsuSigEnum.GameMode.CATCH);
                break;
            case "弹琴":
            case "mania":
                settings.setGameMode(OsuSigEnum.GameMode.MANIA);
                break;
            case "太鼓":
            case "taiko":
                settings.setGameMode(OsuSigEnum.GameMode.TAIKO);
                break;
            case "标准":
            case "standard":
            case "osu":
                settings.setGameMode(OsuSigEnum.GameMode.STANDARD);
                break;
        }
        OsuSigBaseData newData = new OsuSigBaseData(settings);
        OsuSigUtils.setBaseData(userID, newData);
        context.getSender().sendMessage("已设置: " + gameModeStr);
    }
    @SubCommand({"setCountryRank", "scr", "国家排名", "排名"})
    @Description("设置是否显示国家排名")
    public void setCountryRankVisible(CommandContext context, String visibleStr) {
        if (!pluginStatus) return;
        if (UserJudgeUtils.isConsoleCalling(context)) {
            context.getSender().sendMessage("this command cannot be invoked from console! ");
            return;
        }
        User user = context.getSender().getUser();
        Contact contact = context.getSender().getSubject();
        if (user == null || contact == null) {
            context.getSender().sendMessage("用户为空！");
            return;
        }
        long userID = user.getId();
        if (userID == 0) {
            LOGGER.warning("This command cannot be invoked in terminal! ");
            return;
        }
        OsuSigBaseData oldData = OsuSigUtils.getBaseData(userID);
        if (oldData == null) {
            context.getSender().sendMessage("您尚未绑定用户！");
            return;
        }
        OsuSigSettings settings = oldData.getOsuSigSettings();
        switch (visibleStr.toLowerCase()) {
            default:
                context.getSender().sendMessage("无法解析这个参数");
                return;
            case "是":
            case "true":
            case "显示":
                settings.setShowCountryRank(true);
                break;
            case "否":
            case "false":
            case "不显示":
                settings.setShowCountryRank(false);
                break;
        }
        OsuSigBaseData newData = new OsuSigBaseData(settings);
        OsuSigUtils.setBaseData(userID, newData);
        context.getSender().sendMessage("已设置: " + visibleStr);
    }
    @SubCommand({"setReplacePPWithRankScore", "srPPwrs", "替换PP为Rank分", "替换"})
    @Description("设置是否替换PP为Rank分")
    public void setReplacePPWithRankScore(CommandContext context, String replaceStr) {

        if (!pluginStatus) return;
        if (UserJudgeUtils.isConsoleCalling(context)) {
            context.getSender().sendMessage("this command cannot be invoked from console! ");
            return;
        }
        User user = context.getSender().getUser();
        Contact contact = context.getSender().getSubject();
        if (user == null || contact == null) {
            context.getSender().sendMessage("用户为空！");
            return;
        }
        long userID = user.getId();
        if (userID == 0) {
            LOGGER.warning("This command cannot be invoked in terminal! ");
            return;
        }
        OsuSigBaseData oldData = OsuSigUtils.getBaseData(userID);
        if (oldData == null) {
            context.getSender().sendMessage("您尚未绑定用户！");
            return;
        }
        OsuSigSettings settings = oldData.getOsuSigSettings();
        switch (replaceStr.toLowerCase()) {
            default:
                context.getSender().sendMessage("无法解析这个参数");
                return;
            case "是":
            case "true":
            case "显示":
                settings.setReplacePPWithRankScore(true);
                break;
            case "否":
            case "false":
            case "不显示":
                settings.setReplacePPWithRankScore(false);
                break;
        }
        OsuSigBaseData newData = new OsuSigBaseData(settings);
        OsuSigUtils.setBaseData(userID, newData);
        context.getSender().sendMessage("已设置: " + replaceStr);
    }
    @SubCommand({"setPPShowMode","sPPsm", "PP", "PP显示模式"})
    @Description("设置PP显示模式")
    public void setPPShowMode(CommandContext context, String ppShowModeStr) {
        if (!pluginStatus) return;
        if (UserJudgeUtils.isConsoleCalling(context)) {
            context.getSender().sendMessage("this command cannot be invoked from console! ");
            return;
        }
        User user = context.getSender().getUser();
        Contact contact = context.getSender().getSubject();
        if (user == null || contact == null) {
            context.getSender().sendMessage("用户为空！");
            return;
        }
        long userID = user.getId();
        if (userID == 0) {
            LOGGER.warning("This command cannot be invoked in terminal! ");
            return;
        }
        OsuSigBaseData oldData = OsuSigUtils.getBaseData(userID);
        if (oldData == null) {
            context.getSender().sendMessage("您尚未绑定用户！");
            return;
        }
        OsuSigSettings settings = oldData.getOsuSigSettings();
        switch (ppShowModeStr.toLowerCase()) {
            default:
                context.getSender().sendMessage("无法解析这个参数");
                return;
            case "-1":
            case "不显示":
            case "无":
            case "no":
            case "false":
            case "nodisplay":
            case "no display":
                settings.setPpShowMode(OsuSigEnum.PPShowMode.NO_DISPLAY);
                break;
            case "0":
            case "替换等级":
            case "替换":
            case "replacelevel":
            case "replace level":
            case "replace":
            case "level":
                settings.setPpShowMode(OsuSigEnum.PPShowMode.REPLACE_LEVEL);
                break;
            case "1":
            case "acc之后":
            case "acc":
            case "afteracc":
            case "after acc":
                settings.setPpShowMode(OsuSigEnum.PPShowMode.AFTER_ACC);
                break;
            case "2":
            case "rank之后":
            case "rank":
            case "afterrank":
            case "after rank":
                settings.setPpShowMode(OsuSigEnum.PPShowMode.AFTER_RANK);
                break;
        }
        OsuSigBaseData newData = new OsuSigBaseData(settings);
        OsuSigUtils.setBaseData(userID, newData);
        context.getSender().sendMessage("已设置: " + ppShowModeStr);
    }
    @SubCommand({"setExpVisible", "sev", "exp", "经验", "经验条", "显示经验"})
    @Description("设置是否显示经验条")
    public void setExpVisible(CommandContext context, String visibleStr) {

        if (!pluginStatus) return;
        if (UserJudgeUtils.isConsoleCalling(context)) {
            context.getSender().sendMessage("this command cannot be invoked from console! ");
            return;
        }
        User user = context.getSender().getUser();
        Contact contact = context.getSender().getSubject();
        if (user == null || contact == null) {
            context.getSender().sendMessage("用户为空！");
            return;
        }
        long userID = user.getId();
        if (userID == 0) {
            LOGGER.warning("This command cannot be invoked in terminal! ");
            return;
        }
        OsuSigBaseData oldData = OsuSigUtils.getBaseData(userID);
        if (oldData == null) {
            context.getSender().sendMessage("您尚未绑定用户！");
            return;
        }
        OsuSigSettings settings = oldData.getOsuSigSettings();
        switch (visibleStr.toLowerCase()) {
            default:
                context.getSender().sendMessage("无法解析这个参数");
                return;
            case "是":
            case "true":
            case "显示":
                settings.setShowExpBar(true);
                break;
            case "否":
            case "false":
            case "不显示":
                settings.setShowExpBar(false);
                break;
        }
        OsuSigBaseData newData = new OsuSigBaseData(settings);
        OsuSigUtils.setBaseData(userID, newData);
        context.getSender().sendMessage("已设置: " + visibleStr);
    }

    @SubCommand({"setSameBackgroundColor", "ssbc", "经验条背景同色", "同色", "经验条同色"})
    @Description("设置经验条是否与背景同色")
    public void setSameBackgroundColor(CommandContext context, String visibleStr) {
        if (!pluginStatus) return;
        if (UserJudgeUtils.isConsoleCalling(context)) {
            context.getSender().sendMessage("this command cannot be invoked from console! ");
            return;
        }
        User user = context.getSender().getUser();
        Contact contact = context.getSender().getSubject();
        if (user == null || contact == null) {
            context.getSender().sendMessage("用户为空！");
            return;
        }
        long userID = user.getId();
        if (userID == 0) {
            LOGGER.warning("This command cannot be invoked in terminal! ");
            return;
        }
        OsuSigBaseData oldData = OsuSigUtils.getBaseData(userID);
        if (oldData == null) {
            context.getSender().sendMessage("您尚未绑定用户！");
            return;
        }
        OsuSigSettings settings = oldData.getOsuSigSettings();
        switch (visibleStr.toLowerCase()) {
            default:
                context.getSender().sendMessage("无法解析这个参数");
                return;
            case "是":
            case "true":
            case "显示":
                settings.setExpBarUseSameColor(true);
                break;
            case "否":
            case "false":
            case "不显示":
                settings.setExpBarUseSameColor(false);
                break;
        }
        OsuSigBaseData newData = new OsuSigBaseData(settings);
        OsuSigUtils.setBaseData(userID, newData);
        context.getSender().sendMessage("已设置: " + visibleStr);
    }
    @SubCommand({"get","pic"})
    @Description("获取osu!sig名片")
    public void getPict(CommandContext context) {
        if (!pluginStatus) return;
        if (UserJudgeUtils.isConsoleCalling(context)) {
            context.getSender().sendMessage("this command cannot be invoked from console! ");
            return;
        }
        User user = context.getSender().getUser();
        Contact contact = context.getSender().getSubject();
        if (user == null || contact == null) {
            context.getSender().sendMessage("用户为空！");
            return;
        }
        long userID = user.getId();
        if (userID == 0) {
            LOGGER.warning("This command cannot be invoked in terminal! ");
            return;
        }
        MessageChainBuilder mcb;
        String username = OsuUtils.getName(userID);
        if (username == null) {
            context.getSender().sendMessage("您尚未绑定osu!用户名");
            return;
        }
        OsuSigBaseData base = OsuSigUtils.getBaseData(userID);
        byte[] pictContent;
        try {
            pictContent = OsuSigUtils.getPicture(userID);
        } catch (HttpException e) {
            LOGGER.warning("Cannot fetch target image: " + OsuSigUtils.getURL(username, base.getOsuSigSettings()));
            context.getSender().sendMessage("出现意外，获取图片失败。");
            return;
        }
        if (pictContent == null) {
            context.getSender().sendMessage("出现意外，图片返回值为空。");
            LOGGER.warning("Got a null response: " + OsuSigUtils.getURL(username, base.getOsuSigSettings()));
            return;
        }
        ExternalResource resource = ExternalResource.create(Objects.requireNonNull(pictContent));
        try {
            Image image = contact.uploadImage(resource);
            mcb = new MessageChainBuilder();
            mcb.add("用户" + username + "：");
            mcb.add(image);
            contact.sendMessage(mcb.build());
        } catch (IllegalArgumentException e) {
            context.getSender().sendMessage("获取osu!sig失败，可能是最近没有成绩或用户名有误");
        }
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
    public synchronized void disablePlugin() throws IllegalStateException {
        this.pluginStatus = false;
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
    public synchronized void enablePlugin() throws IllegalStateException {
        this.pluginStatus = true;
    }

    @Override
    public boolean pluginStatus() {
        return pluginStatus;
    }

    /**
     * Load method. First time loading.
     */
    @Override
    public void load() {
        LOGGER.debug("OsuSig loading. ");
        LOGGER.verbose("Loading data. ");
        enablePlugin();
        LOGGER.debug("OsuSig loaded. ");
    }
    /**
     * Shutdown method.
     */
    @Override
    public void shutdown() {
        LOGGER.debug("OsuSig shutting down. ");
        disablePlugin();
        LOGGER.debug("OsuSig shut down. ");
    }
}
