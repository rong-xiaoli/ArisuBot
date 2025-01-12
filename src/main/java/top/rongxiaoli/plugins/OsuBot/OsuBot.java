package top.rongxiaoli.plugins.OsuBot;

import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractCompositeCommand;
import top.rongxiaoli.backend.interfaces.Plugin;
import top.rongxiaoli.plugins.OsuBot.backend.UserData;
import top.rongxiaoli.plugins.OsuBot.backend.osusig.OsuSigUtils;
import top.rongxiaoli.plugins.OsuBot.data.OsuData;
import top.rongxiaoli.plugins.OsuBot.backend.UserBaseData;
import top.rongxiaoli.plugins.OsuBot.backend.osusig.backend.kotlinTypes.OsuSigSettings;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Plugin(name = "OsuBot")
public class OsuBot extends ArisuBotAbstractCompositeCommand {
    private volatile boolean pluginStatus = false;
    public static final OsuBot INSTANCE = new OsuBot();
    public MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(OsuBot.class, "ArisuBot.OsuBot");
    private AtomicInteger requestCounter = new AtomicInteger(0);
    public OsuBot() {
        super("osu");
    }
    @SubCommand("bind")
    @Description("将osu!账号绑定至你的QQ号。")
    public void bindUserName(CommandContext context, String username) {
        if (!pluginStatus) return;
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
        mcb.add(new QuoteReply(context.getOriginalMessage()));
        mcb.add("你绑定了：" + username);
        UserBaseData data = OsuData.INSTANCE.getUserData(userID);
        UserData userData;
        if (OsuData.INSTANCE.getUserData(userID) == null) {
            userData = new UserData(new OsuSigSettings());
        } else {
            userData = data.getUserData();
            mcb.add("，你的设置将被继承。");
        }
        context.getSender().sendMessage(mcb.build());
        UserBaseData newData = new UserBaseData(username, userData);
        OsuData.INSTANCE.setUserData(userID, newData);
        LOGGER.verbose("User " + userID + "'s username changed to: " + username);
        byte[] pictContent = null;
        try {
            pictContent = OsuSigUtils.downloadPictureInBytes(
                    OsuSigUtils.getURL(username, userData.getOsuSigSettings()), userID);
        } catch (Exception e) {
            LOGGER.warning("Cannot fetch target image: " + OsuSigUtils.getURL(username, userData.getOsuSigSettings()));
            context.getSender().sendMessage("出现意外，获取图片失败。");
        }
        ExternalResource resource = null;
        if (pictContent == null) {
            context.getSender().sendMessage("出现意外，图片返回值为空。");
            LOGGER.warning("Got a null response: " + OsuSigUtils.getURL(username, userData.getOsuSigSettings()));
        }
        resource = ExternalResource.create(pictContent);
        Image image = contact.uploadImage(resource);
        mcb = new MessageChainBuilder();
        mcb.add("预览：");
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
        OsuData.INSTANCE.load();
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
        OsuData.INSTANCE.saveData();
        LOGGER.verbose("Reloading data. ");
        OsuData.INSTANCE.reload();
        LOGGER.debug("OsuBot reloaded. ");
    }

    /**
     * Shutdown method.
     */
    @Override
    public void shutdown() {
        LOGGER.debug("OsuBot shutting down. ");
        OsuData.INSTANCE.shutdown();
        disablePlugin();
        LOGGER.debug("OsuBot shut down. ");
    }

    /**
     * Manually save the data.
     */
    @Override
    public void saveData() {
        LOGGER.debug("Saving data. ");
        OsuData.INSTANCE.saveData();
        LOGGER.debug("Data saved. ");
    }

    /**
     * Manually reload the data. Discard the changes in memory.
     */
    @Override
    public void reloadData() {
        LOGGER.debug("Reloading data. ");
        OsuData.INSTANCE.reload();
        LOGGER.debug("Data reloaded. ");
    }
}
