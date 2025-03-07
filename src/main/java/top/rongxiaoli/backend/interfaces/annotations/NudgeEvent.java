package top.rongxiaoli.backend.interfaces.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ArisuBot Nudge event handler.<br/>
 * ArisuBot 戳一戳响应注解。
 * <p/>
 * This annotation is used for handling nudge event method.
 * It is estimated to be used in {@link top.rongxiaoli.backend.EventListener}.<br/>
 * 这个注解用于戳一戳响应方法。
 * 预计用于{@link top.rongxiaoli.backend.EventListener}
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface NudgeEvent {
}
