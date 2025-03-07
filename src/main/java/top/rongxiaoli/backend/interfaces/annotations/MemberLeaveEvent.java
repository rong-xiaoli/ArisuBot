package top.rongxiaoli.backend.interfaces.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ArisuBot Member leave event handler.<br/>
 * ArisuBot 成员退群响应注解。
 * <p/>
 * This annotation is used for handling member leaving group.
 * It is estimated to be used in {@link top.rongxiaoli.backend.EventListener}.<br/>
 * 这个注解用于响应成员退群。
 * 预计用于{@link top.rongxiaoli.backend.EventListener}
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface MemberLeaveEvent {
}
