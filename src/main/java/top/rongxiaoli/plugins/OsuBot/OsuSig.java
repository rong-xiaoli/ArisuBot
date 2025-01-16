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
        mcb.add("你可以使用osu!sig获取一张简要的osu!成绩图。\n");
        mcb.add("你可以设置背景颜色");
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
            case "sigsetbackground":
            case "ssbg":
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
                mcb.add("请注意，区分大小写。");
                break;
            case "ssgm":
            case "sigsetgamemode":
                mcb.add("设置名片游戏模式\n");
                mcb.add("参数有：\n");
                mcb.add(OsuSigEnum.GameMode.CATCH + "表示接果；\n");
                mcb.add(OsuSigEnum.GameMode.MANIA + "表示弹琴；\n");
                mcb.add(OsuSigEnum.GameMode.STANDARD + "表示标准；\n");
                mcb.add(OsuSigEnum.GameMode.TAIKO + "表示太鼓；\n");
                //mcb.add("请注意，区分大小写。");
                break;
        }
        context.getSender().sendMessage(mcb.build());
    }

    @SubCommand({"sigSetBackground", "ssbg", "背景"})
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

    @SubCommand({"sigSetGameMode", "ssgm", "模式"})
    @Description("设置游戏模式")
    public void setOsuSigGameMode(CommandContext context, OsuSigEnum.GameMode gameMode) {
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
        settings.setGameMode(gameMode);
        OsuSigBaseData newData = new OsuSigBaseData(settings);
        OsuSigUtils.setBaseData(userID, newData);
        context.getSender().sendMessage("已设置: " + gameMode);
    }
    @SubCommand({"sigSetGameMode", "ssgm", "模式"})
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
            case "接果":
            case "catch":
                settings.setGameMode(OsuSigEnum.GameMode.CATCH);
            case "弹琴":
            case "mania":
                settings.setGameMode(OsuSigEnum.GameMode.CATCH);
            case "太鼓":
            case "taiko":
                settings.setGameMode(OsuSigEnum.GameMode.CATCH);
            case "标准":
            case "standard":
            case "osu":
                settings.setGameMode(OsuSigEnum.GameMode.CATCH);
        }
        OsuSigBaseData newData = new OsuSigBaseData(settings);
        OsuSigUtils.setBaseData(userID, newData);
        context.getSender().sendMessage("已设置: " + gameModeStr);
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
        MessageChainBuilder mcb = new MessageChainBuilder();
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
        Image image = contact.uploadImage(resource);
        mcb.add("您的用户名：" + username);
        mcb.add(image);
        contact.sendMessage(mcb.build());
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
