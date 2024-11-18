package top.rongxiaoli.backend;

import net.mamoe.mirai.console.command.CommandManager;
import top.rongxiaoli.plugins.DailyFortune.DailyFortune;
import top.rongxiaoli.plugins.PicturesPlugin.PicturesPlugin;
import top.rongxiaoli.plugins.Ping.Ping;

import java.util.concurrent.CopyOnWriteArrayList;

public class PluginLoader {
    /**
     * Plugin list.
     */
    protected CopyOnWriteArrayList<PluginBase> PluginList;
    private final CommandManager INSTANCE = CommandManager.INSTANCE;
    public PluginLoader() {
        this.PluginList = new CopyOnWriteArrayList<>();
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
    private void addPlugins() {
        PluginList.add(PicturesPlugin.INSTANCE);
        INSTANCE.registerCommand(PicturesPlugin.INSTANCE, false);
        PluginList.add(Ping.INSTANCE);
        INSTANCE.registerCommand(Ping.INSTANCE, false);
        PluginList.add(DailyFortune.INSTANCE);
        INSTANCE.registerCommand(DailyFortune.INSTANCE, false);
    }
}
