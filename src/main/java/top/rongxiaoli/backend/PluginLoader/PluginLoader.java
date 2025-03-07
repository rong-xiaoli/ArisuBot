package top.rongxiaoli.backend.PluginLoader;

import net.mamoe.mirai.console.command.Command;
import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.Utils.ClassUtil;
import top.rongxiaoli.backend.interfaces.annotations.Plugin;
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
    private final List<Package> targetPackages = new ArrayList<>();
    private final CommandManager INSTANCE = CommandManager.INSTANCE;
    public PluginLoader() {
        this.PluginList = new CopyOnWriteArrayList<>();

        // Package scan list start.
        targetPackages.add(ArisuBot.class.getPackage());
        // Package scan list end.
    }
    public boolean reload(String target) {
        for (PluginBase item :
                PluginList) {
            if (item.getClass().getName().toLowerCase().contains(target.toLowerCase())) {
                item.reload();
                return true;
            }
        }
        return false;
    }
    public boolean shutdown(String target) {
        for (PluginBase item :
                PluginList) {
            if (item.getClass().getName().toLowerCase().contains(target.toLowerCase())) {
                item.shutdown();
                return true;
            }
        }
        return false;
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
        LOGGER.debug("Loading classes from packages below: ");
        List<Class<?>> reflectClasses = new ArrayList<>();
        for (Package pack :
                targetPackages) {
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
            Plugin plugin = clazz.getDeclaredAnnotation(Plugin.class);
            if (plugin.name().isEmpty()) {
                LOGGER.debug("Class " + clazz.getName() + "have an empty plugin name. ");
            }
            try {
                Field f = clazz.getDeclaredField("INSTANCE");
                PluginList.add((PluginBase) f.get(null));
                INSTANCE.registerCommand((Command) f.get(null), false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                LOGGER.error("Cannot load class " + clazz.getName() + " because field \"INSTANCE\" was not found or is inaccessible.");
                LOGGER.error("Exception details: ", e);
            } catch (ClassCastException e) {
                LOGGER.warning("Cannot cast " + clazz.getName() + " to " + Command.class.getName());
            }
        }
        for (PluginBase e :
                PluginList) {
            LOGGER.verbose("Found plugin: " + e.getClass().getName());
        }
        ArisuBot.INSTANCE.getLogger().verbose("Found " + PluginList.size() + " plugins. ");
    }

    /**
     * Get all plugins loaded.
     * @return A list with all plugins loaded.
     */
    public List<PluginBase> getPlugins() {
        return this.PluginList;
    }
}
