package top.rongxiaoli;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;
import org.jetbrains.annotations.NotNull;
import top.rongxiaoli.backend.EventListener;
import top.rongxiaoli.backend.PluginLoader.ConfigLoader;
import top.rongxiaoli.backend.PluginLoader.DataLoader;
import top.rongxiaoli.backend.PluginLoader.PluginLoader;

import java.nio.file.Path;


public final class ArisuBot extends JavaPlugin {
    public static final ArisuBot INSTANCE = new ArisuBot();
    public static final PluginLoader LOADER = new PluginLoader();
    public static final ConfigLoader CONFIG = new ConfigLoader();
    public static final DataLoader DATA = new DataLoader();
    public static volatile boolean PluginRunning = false;
    private ArisuBot() {
        super(new JvmPluginDescriptionBuilder("top.rongxiaoli.ArisuBot", "0.0.1")
                .name("ArisuBot")
                .info("REBORN, even better. ")
                .author("容小狸")
                .build());
    }
    private static TimeInterval interval;

    /**
     * @param $this$onLoad This parameter is not used. 
     */
    @Override
    public void onLoad(@NotNull PluginComponentStorage $this$onLoad) {
        interval = DateUtil.timer();
        getLogger().debug("Loading ArisuBot plugin config...");
        try {
            CONFIG.load();
        } catch (Exception e) {
            getLogger().error("Failed to load plugin config: " + e.getMessage(), e);
            throw e;
        }
        getLogger().debug("Loading ArisuBot plugin data...");
        try {
            DATA.load();
        } catch (Exception e) {
            getLogger().error("Failed to load plugin data: " + e.getMessage(), e);
            throw e;
        }
        getLogger().debug("Load complete. Waiting for enabling. ");
    }

    @Override
    public void onEnable() {
        getLogger().debug("Plugin loading.");
        LOADER.load();
        getLogger().verbose("Plugin load complete. ");
        getLogger().verbose("Registering listener host. ");
        try {
            GlobalEventChannel.INSTANCE.registerListenerHost(new EventListener());
        } catch (Exception e) {
            getLogger().error("Failed to register event listener", e);
            throw e;
        }
        getLogger().debug("Initialization complete. ");
        ArisuBot.PluginRunning = true;
        getLogger().debug("Loading and enabling cost " + interval.intervalMs() + "ms. ");
    }

    @Override
    public void onDisable() {
        getLogger().debug("Start disabling process. ");
        try {
            DATA.shutdown();
            getLogger().debug("Data saved. ");
        } catch (Exception e) {
            getLogger().error("Failed to shutdown data: " + e.getMessage(), e);
        }
        try {
            CONFIG.shutdown();
            getLogger().debug("Config saved. ");
        } catch (Exception e) {
            getLogger().error("Failed to shutdown config: " + e.getMessage(), e);
        }
        LOADER.shutdown();
        getLogger().debug("Shutdown complete. ");
    }

    public static Path GetConfigPath() {
      return INSTANCE.getConfigFolderPath();
    }
    public static Path GetDataPath() {
      return INSTANCE.getDataFolderPath();
    }
    public static ClassLoader GetPluginClassLoader() {
        return INSTANCE.getJvmPluginClasspath().getPluginClassLoader();
    }
}