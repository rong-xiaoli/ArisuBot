package top.rongxiaoli.backend;

import net.mamoe.mirai.event.AbstractEvent;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.interfaces.PluginBase.PluginBase;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles any event here.
 */
public class EventListener extends SimpleListenerHost {
    private static final MiraiLogger LOGGER  = MiraiLogger.Factory.INSTANCE.create(EventListener.class, "ArisuBot.EventListener");

    @EventHandler
    public void onPoke(NudgeEvent e) {
        for (PluginBase plugin :
                ArisuBot.LOADER.getPlugins()) {
            List<Method> targets = getAnnotatedMethods(plugin, top.rongxiaoli.backend.interfaces.annotations.NudgeEvent.class);
            if (!targets.isEmpty()) invokeEachTargetMethod(plugin, targets, e);
        }
    }
    @EventHandler
    public void onFriendMessage(FriendMessageEvent e) {
        for (PluginBase plugin :
                ArisuBot.LOADER.getPlugins()) {
            List<Method> targets = getAnnotatedMethods(plugin, top.rongxiaoli.backend.interfaces.annotations.FriendMessageEvent.class);
            if (!targets.isEmpty()) invokeEachTargetMethod(plugin, targets, e);
        }
    }
    @EventHandler
    public void onGroupMessage(GroupMessageEvent e) {
        for (PluginBase plugin :
                ArisuBot.LOADER.getPlugins()) {
            List<Method> targets = getAnnotatedMethods(plugin, top.rongxiaoli.backend.interfaces.annotations.GroupMessageEvent.class);
            if (!targets.isEmpty()) invokeEachTargetMethod(plugin, targets, e);
        }
    }
    @EventHandler
    public void onBotInvitedJoinGroupRequestEvent(BotInvitedJoinGroupRequestEvent e) {
        for (PluginBase plugin :
                ArisuBot.LOADER.getPlugins()) {
            List<Method> targets = getAnnotatedMethods(plugin, top.rongxiaoli.backend.interfaces.annotations.BotInvitedJoinGroupRequestEvent.class);
            if (!targets.isEmpty()) invokeEachTargetMethod(plugin, targets, e);
        }
    }
    @EventHandler
    public void onFriendAddEvent(FriendAddEvent e) {
        for (PluginBase plugin :
                ArisuBot.LOADER.getPlugins()) {
            List<Method> targets = getAnnotatedMethods(plugin, top.rongxiaoli.backend.interfaces.annotations.FriendAddEvent.class);
            if (!targets.isEmpty()) invokeEachTargetMethod(plugin, targets, e);
        }
    }
    @EventHandler
    public void onBotJoinGroupEvent(BotJoinGroupEvent e) {
        for (PluginBase plugin :
                ArisuBot.LOADER.getPlugins()) {
            List<Method> targets = getAnnotatedMethods(plugin, top.rongxiaoli.backend.interfaces.annotations.BotJoinGroupEvent.class);
            if (!targets.isEmpty()) invokeEachTargetMethod(plugin, targets, e);
        }
    }
    @EventHandler
    public void onNewFriendRequestEvent(NewFriendRequestEvent e) {
        for (PluginBase plugin :
                ArisuBot.LOADER.getPlugins()) {
            List<Method> targets = getAnnotatedMethods(plugin, top.rongxiaoli.backend.interfaces.annotations.NewFriendRequestEvent.class);
            if (!targets.isEmpty()) invokeEachTargetMethod(plugin, targets, e);
        }
    }
    @EventHandler
    public void onMemberLeaveEvent(MemberLeaveEvent e) {
        for (PluginBase base :
                ArisuBot.LOADER.getPlugins()) {
            List<Method> targets = getAnnotatedMethods(base, top.rongxiaoli.backend.interfaces.annotations.MemberLeaveEvent.class);
            if (!targets.isEmpty()) invokeEachTargetMethod(base, targets, e);
        }
    }

    private List<Method> getAnnotatedMethods(PluginBase plugin, Class<? extends Annotation> annotation) {
        Method[] methods = plugin.getClass().getMethods();
        List<Method> out = new ArrayList<>();
        for (Method method :
                methods) {
            if (method.isAnnotationPresent(annotation)) {
                out.add(method);
            }
        }
        return out;
    }
    private void invokeEachTargetMethod(PluginBase base, List<Method> methods, AbstractEvent e) {
        for (Method method :
                methods) {
            try {
                method.invoke(base, e);
            } catch (InvocationTargetException ex) {
                LOGGER.error("Fail to invoke method, target error: " + method.getName(), ex);
            } catch (IllegalAccessException ex) {
                LOGGER.error("Fail to invoke method, can not access: " + method.getName(), ex);
            }
        }
    }
}
