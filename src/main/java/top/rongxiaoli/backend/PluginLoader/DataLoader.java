package top.rongxiaoli.backend.PluginLoader;

import top.rongxiaoli.backend.interfaces.PluginBase.PluginDataBase;

import java.util.concurrent.CopyOnWriteArrayList;

public class DataLoader {
    protected CopyOnWriteArrayList<PluginDataBase> DataList;
    public static DataLoader INSTANCE = new DataLoader();
    public DataLoader() {
        this.DataList = new CopyOnWriteArrayList<>();
    }
    public void load() {
        for (PluginDataBase e :
                DataList) {
            e.load();
        }
    }
    public void reload() {
        for (PluginDataBase e :
                DataList) {
            e.reload();
        }
    }
    public void shutdown() {
        for (PluginDataBase e :
                DataList) {
            e.shutdown();
        }
    }
    public void saveData() {
        for (PluginDataBase e :
                DataList) {
            e.saveData();

        }
    }
}
