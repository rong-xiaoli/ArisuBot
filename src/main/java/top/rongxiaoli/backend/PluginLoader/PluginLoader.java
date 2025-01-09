package top.rongxiaoli.backend.PluginLoader;

import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.Utils.ClassUtil;
import top.rongxiaoli.backend.interfaces.Plugin;
import top.rongxiaoli.backend.interfaces.PluginBase.PluginBase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PluginLoader {
    private final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(PluginLoader.class, "ArisuBot.PluginLoader");
    /**
     * Plugin list.
     */
    protected CopyOnWriteArrayList<PluginBase> PluginList;
    private final CommandManager INSTANCE = CommandManager.INSTANCE;
    public PluginLoader() {
        this.PluginList = new CopyOnWriteArrayList<>();
    }
    public void reload(String target) {
        for (PluginBase item :
                PluginList) {
            String pluginName = item.getPrimaryName();
            pluginName = pluginName.toLowerCase();
            if (target.toLowerCase().contains(pluginName)) {
                item.reload();
                break;
            }
        }
    }
    public void shutdown(String target) {
        for (PluginBase item :
                PluginList) {
            if (item.getClass().getName().contains(target)) {
                item.shutdown();
                break;
            }
        }
    }

    /**
     * Load method. First time loading. Register all plugins.
     */
    public void load() {
        addPlugins();
        for (PluginBase e :
                PluginList) {
            e.load();
        }
    }
    /**
     * Load method. Not first time loading.
     */
    public void reload() {
        for (PluginBase e :
                PluginList) {
            e.reload();
        }
    }
    /**
     * Unload method.
     */
    public void shutdown() {
        for (PluginBase e :
                PluginList) {
            e.shutdown();
        }
    }

    /**
     * This plugin is to add all plugins marked with Annotation {@code Plugin} into plugin list.
     */
    private void addPlugins() {
        List<Package> checklist = new ArrayList<>();

        // Package scan list start.
        checklist.add(ArisuBot.class.getPackage());
        // Package scan list end.

        LOGGER.debug("Loading classes from packages below: ");
        List<Class<?>> reflectClasses = new ArrayList<>();
        for (Package pack :
                checklist) {
            LOGGER.debug(pack.toString());
            reflectClasses.addAll(ClassUtil.scan(pack, ArisuBot.GetPluginClassLoader()));
        }
        LOGGER.verbose("Scanning " + reflectClasses.size() + " classes for plugins. ");
        // Process and add plugins to PluginList.
        for (Class<?> clazz :
                reflectClasses) {
            if (!clazz.isAnnotationPresent(Plugin.class)) {
                continue;
            }
            try {
                Field f = clazz.getDeclaredField("INSTANCE");
                f.setAccessible(true);
                PluginList.add((PluginBase) f.get(null));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                LOGGER.error("Cannot load class because field \"INSTANCE\" not found. Exception below: ");
                LOGGER.error(e);
                LOGGER.warning("Detail below: ");
                throw new RuntimeException(e);
            }
        }
        for (PluginBase e :
                PluginList) {
            LOGGER.verbose("Found plugin: " + e.getClass().getName());
        }
        ArisuBot.INSTANCE.getLogger().verbose("Found " + PluginList.size() + " plugins. ");
    }
}
