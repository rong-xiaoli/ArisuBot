package top.rongxiaoli.plugins.Broadcast;

import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractCompositeCommand;
import top.rongxiaoli.backend.interfaces.annotations.Plugin;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Plugin(name = "Broadcast")
public class Broadcast extends ArisuBotAbstractCompositeCommand {
    private boolean pluginStatus = false;
    private static final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(Broadcast.class, "ArisuBot.Broadcast");
    public static Broadcast INSTANCE = new Broadcast();
    private MessageChainBuilder builder = new MessageChainBuilder();
    private volatile boolean isBroadcasting = false;
    public Broadcast() {
        super("broadcast", "广播");
        setDescription("向所有人广播消息，请注意一定要配合LuckPerms进行权限管理，该功能可能会造成滥用！");
        setPrefixOptional(false);
    }
    @SubCommand
    public void message(CommandContext context, String... args) {
        if (!pluginStatus) return;
        if (isBroadcasting) {
            context.getSender().sendMessage("正在广播，请稍后再试");
            return;
        }
        if (args.length == 0) {
            context.getSender().sendMessage("广播内容为空，请重新发送内容");
            return;
        }
        if (context.getSender().getSubject() == null) {
            LOGGER.warning("Command sender is null. ");
            context.getSender().sendMessage("出现错误，请上报开发者。详情请见控制台。");
            return;
        }
        MessageChainBuilder mcb = new MessageChainBuilder();
        for (String arg :
                args) {
            mcb.append(arg);
            mcb.append("\n");
        }
        builder = mcb;
        context.getSender().sendMessage("信息已保存。请使用broadcast confirm以进行广播。信息预览：");
        context.getSender().sendMessage(mcb.build());
    }
    @SubCommand
    public void confirm(CommandContext context) {
        if (!pluginStatus) return;
        try {
            LOGGER.warning("Start broadcasting. Broadcast bot: " + Objects.requireNonNull(context.getSender().getBot()).getId());
        } catch (NullPointerException e) {
            LOGGER.error("Cannot broadcast because broadcast bot is null. ");
            context.getSender().sendMessage("出现错误，请上报开发者。详情请见控制台。");
            return;
        }
        ContactList<Friend> friendsList = new ContactList<>();
        ContactList<Group> groupList = new ContactList<>();
        try {
            friendsList = context.getSender().getBot().getFriends();
            groupList = context.getSender().getBot().getGroups();
        } catch (NullPointerException e) {
            LOGGER.error("Either friend list or group list is null. Please check again. ");
            context.getSender().sendMessage("出现错误，请上报开发者。详情请见控制台。");
            return;
        }
        isBroadcasting = true;
        context.getSender().sendMessage("即将开始广播。");
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        int delay = 0;
        Random ran = new Random();
        for (Friend singleFriend :
                friendsList) {
            delay += ran.nextInt(100,3000);
            executor.schedule(() -> {
                singleFriend.sendMessage(builder.build());
            }, delay, TimeUnit.MILLISECONDS);
            LOGGER.verbose("Send to Friend: " + singleFriend.getId());
        }
        executor.shutdown();
        delay = 0;
        executor = Executors.newSingleThreadScheduledExecutor();
        for (Group singleGroup :
                groupList) {
            delay += ran.nextInt(100, 3000);
            executor.schedule(() -> {
                singleGroup.sendMessage(builder.build());
            }, delay, TimeUnit.MILLISECONDS);
            LOGGER.verbose("Send to Group: " + singleGroup.getId());
        }
        executor.schedule(() -> {
            context.getSender().sendMessage("广播结束。");
            isBroadcasting = false;
        }, delay + 100, TimeUnit.MILLISECONDS);
        context.getSender().sendMessage("广播共发送了" + friendsList.size() + "个好友，共" + groupList.size() + "个群。");

    }
    @SubCommand
    public void cancel(CommandContext context) {
        if (!pluginStatus) return;
        builder = new MessageChainBuilder();
        context.getSender().sendMessage("已清空消息缓存。");
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

    /**
     * Load method. First time loading.
     */
    @Override
    public void load() {
        enablePlugin();
        LOGGER.debug("Broadcast started. ");
    }

    /**
     * Shutdown method.
     */
    @Override
    public void shutdown() {
        disablePlugin();
        LOGGER.debug("Broadcast shut down. ");
    }
}
