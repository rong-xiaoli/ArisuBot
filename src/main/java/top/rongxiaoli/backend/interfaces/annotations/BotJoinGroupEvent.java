package top.rongxiaoli.backend.interfaces.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ArisuBot Bot join group event handler.<br/>
 * ArisuBot 机器人加群响应注解。
 * <p/>
 * This annotation is used for handling bot join group event.
 * It is estimated to be used in {@link top.rongxiaoli.backend.EventListener}.<br/>
 * 这个注解用响应于机器人加群事件。
 * 预计用于{@link top.rongxiaoli.backend.EventListener}
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface BotJoinGroupEvent {
}
