package top.rongxiaoli.backend.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ArisuBot自动加载注解。如果插件不在{@code top/rongxiaoli/backend/PluginLoader/PluginLoader.java}下，
 * 则需要在
 * <p>
 * {@code top.rongxiaoli.backend.PluginLoader.PluginLoader#addPlugins()}的
 * </p>
 * {@code checklist.add(ArisuBot.class.getPackage());}<p/>
 * (大约87行)处加上扫描的包。
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface Plugin {
    String name();
}
