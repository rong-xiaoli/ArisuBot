package top.rongxiaoli.backend.interfaces.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ArisuBot Bot invited join group event handler.<br/>
 * ArisuBot 机器人邀请入群响应注解。
 * <p/>
 * This annotation is used for handling event indicating bot being invited to group.
 * It is estimated to be used in {@link top.rongxiaoli.backend.EventListener}.<br/>
 * 这个注解用于响应机器人被邀请入群的方法。
 * 预计用于{@link top.rongxiaoli.backend.EventListener}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BotInvitedJoinGroupRequestEvent {
}
