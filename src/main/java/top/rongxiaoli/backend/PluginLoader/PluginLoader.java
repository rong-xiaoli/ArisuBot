package top.rongxiaoli.backend.PluginLoader;

import net.mamoe.mirai.console.command.CommandManager;
import top.rongxiaoli.backend.interfaces.PluginBase.PluginBase;
import top.rongxiaoli.plugins.Broadcast.Broadcast;
import top.rongxiaoli.plugins.DailySign.DailySign;
import top.rongxiaoli.plugins.DailyFortune.DailyFortune;
import top.rongxiaoli.plugins.EmergencyStop.EmergencyStop;
import top.rongxiaoli.plugins.Management.Management;
import top.rongxiaoli.plugins.PicturesPlugin.PicturesPlugin;
import top.rongxiaoli.plugins.Ping.Ping;
import top.rongxiaoli.plugins.PokeReact.PokeReact;

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
    private void addPlugins() {
        PluginList.add(PicturesPlugin.INSTANCE);
        INSTANCE.registerCommand(PicturesPlugin.INSTANCE, false);

        PluginList.add(Ping.INSTANCE);
        INSTANCE.registerCommand(Ping.INSTANCE, false);
      
        PluginList.add(DailySign.INSTANCE);
        INSTANCE.registerCommand(DailySign.INSTANCE, false);
      
        PluginList.add(DailyFortune.INSTANCE);
        INSTANCE.registerCommand(DailyFortune.INSTANCE, false);

        PluginList.add(PokeReact.INSTANCE);
        INSTANCE.registerCommand(PokeReact.INSTANCE, false);

        PluginList.add(Broadcast.INSTANCE);
        INSTANCE.registerCommand(Broadcast.INSTANCE, false);

        PluginList.add(Management.INSTANCE);
        INSTANCE.registerCommand(Management.INSTANCE, false);

        PluginList.add(EmergencyStop.INSTANCE);
        INSTANCE.registerCommand(EmergencyStop.INSTANCE, false);
    }
}
