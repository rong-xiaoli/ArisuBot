package top.rongxiaoli.plugins.OsuBot;

import cn.hutool.http.HttpException;
import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractCompositeCommand;
import top.rongxiaoli.backend.Utils.UserJudgeUtils;
import top.rongxiaoli.backend.interfaces.Plugin;
import top.rongxiaoli.plugins.OsuBot.backend.osusig.OsuSigUtils;
import top.rongxiaoli.plugins.OsuBot.backend.osusig.dataStruct.OsuSigBaseData;
import top.rongxiaoli.plugins.OsuBot.backend.osusig.dataStruct.OsuSigSettings;
import top.rongxiaoli.plugins.OsuBot.data.OsuData;
import top.rongxiaoli.plugins.OsuBot.utils.OsuUtils;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Plugin(name = "OsuBot")
public class OsuBot extends ArisuBotAbstractCompositeCommand {
    private volatile boolean pluginStatus = false;
    public static final OsuData DATA = OsuData.INSTANCE;
    public static final OsuBot INSTANCE = new OsuBot();
    public MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(OsuBot.class, "ArisuBot.OsuBot");
    // private final AtomicInteger requestCounter = new AtomicInteger(0);
    public OsuBot() {
        super("osu");
    }
    @SubCommand("bind")
    @Description("将osu!账号绑定至你的QQ号。")
    public void bindUserName(CommandContext context, String... unameArray) {
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
        String username = String.join(" ", unameArray).trim();
        MessageChainBuilder mcb = new MessageChainBuilder();
        mcb.add(new QuoteReply(context.getOriginalMessage()));
        mcb.add("你绑定了：" + username);
        OsuSigBaseData osuSigBaseData;
        if (OsuUtils.getName(userID) != null) {
            mcb.add("，你的设置将被继承。");
            osuSigBaseData = OsuSigUtils.getBaseData(userID);
        } else osuSigBaseData = new OsuSigBaseData(new OsuSigSettings());
        context.getSender().sendMessage(mcb.build());
        OsuUtils.setName(userID, username);
        LOGGER.verbose("User " + userID + "'s username changed to: " + username);
        byte[] pictContent;
        try {
            pictContent = OsuSigUtils.getPicture(userID);
        } catch (HttpException e) {
            LOGGER.warning("Cannot fetch target image: " + OsuSigUtils.getURL(username, osuSigBaseData.getOsuSigSettings()));
            context.getSender().sendMessage("出现意外，获取图片失败。");
            return;
        }
        if (pictContent == null) {
            context.getSender().sendMessage("出现意外，图片返回值为空。");
            LOGGER.warning("Got a null response: " + OsuSigUtils.getURL(username, osuSigBaseData.getOsuSigSettings()));
            return;
        }
        try (ExternalResource resource = ExternalResource.create(Objects.requireNonNull(pictContent))){
            Image image = contact.uploadImage(resource);
            mcb = new MessageChainBuilder();
            mcb.add("osu!sig预览：");
            mcb.add(image);
            contact.sendMessage(mcb.build());
        } catch (IllegalArgumentException e) {
            context.getSender().sendMessage("获取osu!sig失败，可能是最近没有成绩或用户名有误");
        } catch (IOException e) {
            context.getSender().sendMessage("获取osu!sig失败，IO异常");
        }
    }
    @SubCommand("name")
    @Description("查询你目前绑定的用户名")
    public void queryUserName(CommandContext context) {
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
        String name = OsuUtils.getName(userID);
        if (name != null) context.getSender().sendMessage("您的用户名为：" + name);
        else context.getSender().sendMessage("您尚未绑定用户名！");
    }
    @SubCommand("del")
    @Description("从数据中移除你的信息，包括设置。")
    public void deleteUser(CommandContext context) {
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
        try {
            DATA.deleteUserData(userID);
        } catch (NoSuchElementException e) {
            context.getSender().sendMessage("未找到该用户。");
            return;
        }
        context.getSender().sendMessage("用户已移除。设置已清空。");
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

    /**
     * Load method. First time loading.
     */
    @Override
    public void load() {
        LOGGER.debug("OsuBot loading. ");
        LOGGER.verbose("Loading data. ");
        DATA.load();
        enablePlugin();
        LOGGER.debug("OsuBot loaded. ");
    }

    /**
     * Reload method. Usually for resetting state.
     */
    @Override
    public void reload() {
        LOGGER.debug("OsuBot reloading. ");
        LOGGER.verbose("Saving data. ");
        DATA.saveData();
        LOGGER.verbose("Reloading data. ");
        DATA.reload();
        LOGGER.debug("OsuBot reloaded. ");
    }

    /**
     * Shutdown method.
     */
    @Override
    public void shutdown() {
        LOGGER.debug("OsuBot shutting down. ");
        DATA.shutdown();
        disablePlugin();
        LOGGER.debug("OsuBot shut down. ");
    }

    /**
     * Manually save the data.
     */
    @Override
    public void saveData() {
        LOGGER.debug("Saving data. ");
        DATA.saveData();
        LOGGER.debug("Data saved. ");
    }

    /**
     * Manually reload the data. Discard the changes in memory.
     */
    @Override
    public void reloadData() {
        LOGGER.debug("Reloading data. ");
        DATA.reload();
        LOGGER.debug("Data reloaded. ");
    }
}
