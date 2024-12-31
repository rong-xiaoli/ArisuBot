package top.rongxiaoli.plugins.Broadcast;

import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.SingleMessage;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractCompositeCommand;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractSimpleCommand;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Broadcast extends ArisuBotAbstractCompositeCommand {
    private static final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(Broadcast.class, "ArisuBot.Broadcast");
    public static Broadcast INSTANCE = new Broadcast();
    public static MessageChainBuilder builder = new MessageChainBuilder();
    public static boolean isBroadcasting = false;
    public Broadcast() {
        super("broadcast", "广播");
        setDescription("向所有人广播消息，请注意一定要配合LuckPerms进行权限管理，该功能可能会造成滥用！");
        setPrefixOptional(false);
    }
    @SubCommand
    public void message(CommandContext context, String... args) {
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
        Random ran = new Random();
        for (Friend SingleFriend :
                friendsList) {
            SingleFriend.sendMessage(builder.build());
            LOGGER.verbose("Send to Friend: " + SingleFriend.getId());
            try {
                Thread.sleep(ran.nextInt(100, 3000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (Group SingleGroup :
                groupList) {
            SingleGroup.sendMessage(builder.build());
            LOGGER.verbose("Send to Group: " + SingleGroup.getId());
            try {
                Thread.sleep(ran.nextInt(100, 3000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        isBroadcasting = false;
        context.getSender().sendMessage("广播结束，共发送了" + friendsList.size() + "个好友，共" + groupList.size() + "个群。");
    }
    @SubCommand
    public void cancel(CommandContext context) {
        builder = new MessageChainBuilder();
        context.getSender().sendMessage("已清空消息缓存。");
    }
}
