package top.rongxiaoli.plugins.AutoAccept;

import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.contact.Stranger;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import net.mamoe.mirai.event.events.BotJoinGroupEvent;
import net.mamoe.mirai.event.events.FriendAddEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractCompositeCommand;
import top.rongxiaoli.backend.interfaces.Plugin;

@Plugin(name = "AutoAccept")
public class AutoAccept extends ArisuBotAbstractCompositeCommand {
    private volatile boolean pluginStatus = false;
    private volatile boolean isAutoAcceptEnabled = false;
    public static final AutoAccept INSTANCE = new AutoAccept();
    public static final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(AutoAccept.class, "ArisuBot.AutoAccept");

    public AutoAccept() {
        super("AutoAccept");
        setDescription("自动接受好友请求");
    }
    @SubCommand
    public void status(CommandContext context) {
        if (!pluginStatus) return;
        if (isAutoAcceptEnabled) context.getSender().sendMessage("已启用自动添加。");
        else context.getSender().sendMessage("已禁用自动添加。");
    }

    @SubCommand("switch")
    public void switchStatus(CommandContext context) {
        if (!pluginStatus) return;
        synchronized (this) {
            isAutoAcceptEnabled =! isAutoAcceptEnabled;
        }
        if (isAutoAcceptEnabled) context.getSender().sendMessage("已启用自动添加。");
        else context.getSender().sendMessage("已禁用自动添加。");
    }

    public void onNewFriendRequestEvent(NewFriendRequestEvent e) {
        if (isAutoAcceptEnabled) {
            e.accept();
        } else {
            Stranger stranger = e.getBot().getStranger(e.getFromId());
            if (stranger != null) stranger.sendMessage("暂不接受新的好友请求");
            e.reject(false);
        }
    }
    public void onFriendAddEvent(FriendAddEvent e) {
        MessageChainBuilder mcb = new MessageChainBuilder();
        mcb.add("邦邦卡邦！你好，欢迎使用ArisuBot！\n");
        mcb.add("本项目为开源Bot，项目在GitHub开源。\n");
        mcb.add("项目地址：https://github.com/rong-xiaoli/ArisuBot，欢迎贡献！\n");
        mcb.add("可用命令可以在Wiki上找到，如果有bug也可以在GitHub和Gitee镜像反馈！\n");
        mcb.add("镜像：https://gitee.com/rong_xiaoli/ArisuBot");
        e.getFriend().sendMessage(mcb.build());
    }
    public void onBotInvitedJoinGroupRequestEvent(BotInvitedJoinGroupRequestEvent e) {
        if (isAutoAcceptEnabled) {
            e.accept();
        } else {
            e.ignore();
            if (e.getInvitorId() == 0) {
                LOGGER.warning("Console attempting to invite bot to a group. ");
                return;
            }
            Stranger stranger = e.getBot().getStranger(e.getInvitorId());
            if (stranger != null) stranger.sendMessage("暂不支持邀请进群");
        }
    }
    public void onBotJoinGroupEvent(BotJoinGroupEvent e) {
        MessageChainBuilder mcb = new MessageChainBuilder();
        mcb.add("邦邦卡邦！你好，欢迎使用ArisuBot！\n");
        mcb.add("本项目为开源Bot，项目在GitHub开源。\n");
        mcb.add("项目地址：https://github.com/rong-xiaoli/ArisuBot，欢迎贡献！\n");
        mcb.add("可用命令可以在Wiki上找到，如果有bug也可以在GitHub和Gitee镜像反馈！\n");
        mcb.add("镜像：https://gitee.com/rong_xiaoli/ArisuBot");
        e.getGroup().sendMessage(mcb.build());
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
        LOGGER.debug("Plugin loading. ");
        enablePlugin();
        LOGGER.debug("Plugin loaded. ");
    }

    /**
     * Reload method. Usually for resetting state.
     */
    @Override
    public void reload() {
        LOGGER.debug("Plugin reloading. ");
        enablePlugin();
        LOGGER.debug("Plugin reloaded. ");
    }

    /**
     * Shutdown method.
     */
    @Override
    public void shutdown() {
        LOGGER.debug("Plugin shutting down. ");
        disablePlugin();
        LOGGER.debug("Plugin shut down. ");
    }
}
