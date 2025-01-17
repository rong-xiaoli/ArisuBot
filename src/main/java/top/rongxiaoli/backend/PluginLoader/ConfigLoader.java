package top.rongxiaoli.backend.PluginLoader;

import top.rongxiaoli.backend.interfaces.PluginBase.PluginConfigBase;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The loader of the plugin's data.
 * Detailed definitions in PluginConfigBase.java.
 */
public class ConfigLoader {
    protected CopyOnWriteArrayList<PluginConfigBase> ConfigList;
    public static ConfigLoader INSTANCE = new ConfigLoader();
    // Todo: Add config loader.
    public ConfigLoader() {
        this.ConfigList = new CopyOnWriteArrayList<>();
    }
    public void load() {
        for (PluginConfigBase e :
                ConfigList) {
            e.load();
        }
    }
    public void reload() {
        for (PluginConfigBase e :
                ConfigList) {
            e.reload();
        }
    }
    public void shutdown() {
        for (PluginConfigBase e :
                ConfigList) {
            e.shutdown();
        }
    }
    public void saveData() {
        for (PluginConfigBase e :
                ConfigList) {
            e.saveData();

        }
    }
}
