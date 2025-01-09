package top.rongxiaoli.backend.Utils;

import top.rongxiaoli.backend.interfaces.Plugin;

import java.util.ArrayList;
import java.util.List;

public class AnnotationUtil {
    public static List<String> ValidAnnotation(List<Class<?>> list) {
        List<String> target = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return null;
        }
        for (Class<?> clazz : list) {
            Plugin plugin = (Plugin) clazz.getAnnotation(Plugin.class);
            if (plugin != null) {
                target.add(plugin.name());
            }
        }
        return target;
    }
}
