package top.rongxiaoli.backend.interfaces.PluginBase;

public interface PluginDataBase {
    PluginDataBase INSTANCE = null;
    void load();
    void reload();
    void shutdown();
    void saveData();
}
