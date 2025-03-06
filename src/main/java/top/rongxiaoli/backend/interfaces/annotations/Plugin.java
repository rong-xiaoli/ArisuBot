package top.rongxiaoli.backend.interfaces.annotations;

import top.rongxiaoli.backend.PluginLoader.PluginLoader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for automatic plugin loading in ArisuBot.<br/>
 * ArisuBot自动加载注解。
 * <p/>
 * If the plugin is not in the default scan path, add its package to the scan list in
 * {@link PluginLoader#PluginLoader()}
 * <br/>
 * 如果插件不在默认扫描路径下，需要在PluginLoader类的{@link PluginLoader#PluginLoader()}方法中添加扫描包。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface Plugin {
    String name();
}
