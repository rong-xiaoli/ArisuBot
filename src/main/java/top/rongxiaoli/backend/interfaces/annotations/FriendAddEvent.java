package top.rongxiaoli.backend.interfaces.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ArisuBot Friend add event handler.<br/>
 * ArisuBot 新增好友响应注解。
 * <p/>
 * This annotation is used for handling friend add event.
 * It is estimated to be used in {@link top.rongxiaoli.backend.EventListener}.<br/>
 * 这个注解用于响应新增好友。
 * 预计用于{@link top.rongxiaoli.backend.EventListener}
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface FriendAddEvent {
}
