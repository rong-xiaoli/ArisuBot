package top.rongxiaoli.backend.interfaces.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ArisuBot New friend request event handler.<br/>
 * ArisuBot 新好友邀请事件响应注解。
 * <p/>
 * This annotation is used for handling new friend request.
 * It is estimated to be used in {@link top.rongxiaoli.backend.EventListener}.<br/>
 * 这个注解用于新好友请求响应方法。
 * 预计用于{@link top.rongxiaoli.backend.EventListener}
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface NewFriendRequestEvent {
}
