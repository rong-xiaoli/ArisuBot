package top.rongxiaoli.backend;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import top.rongxiaoli.plugins.AutoAccept.AutoAccept;
import top.rongxiaoli.plugins.PokeReact.PokeReact;

/**
 * Handles any event here.
 */
public class EventListener extends SimpleListenerHost {
    @EventHandler
    public void onPoke(NudgeEvent e) {
        PokeReact.INSTANCE.onNudgeEvent(e);
    }
    @EventHandler
    public void onBotInvitedJoinGroupRequestEvent(BotInvitedJoinGroupRequestEvent e) {
        AutoAccept.INSTANCE.onBotInvitedJoinGroupRequestEvent(e);
    }
    @EventHandler
    public void onFriendAddEvent(FriendAddEvent e) {
        AutoAccept.INSTANCE.onFriendAddEvent(e);
    }
    @EventHandler
    public void onBotJoinGroupEvent(BotJoinGroupEvent e) {
        AutoAccept.INSTANCE.onBotJoinGroupEvent(e);
    }
    @EventHandler
    public void onNewFriendRequestEvent(NewFriendRequestEvent e) {
        AutoAccept.INSTANCE.onNewFriendRequestEvent(e);
    }
}
