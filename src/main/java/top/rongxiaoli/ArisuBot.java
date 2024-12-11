package top.rongxiaoli;

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
    public static boolean PluginRunning = false;

    private ArisuBot() {
        super(new JvmPluginDescriptionBuilder("top.rongxiaoli.ArisuBot", "0.0.0")
                .name("ArisuBot")
                .info("REBORN, even better. ")
                .author("rong-xiaoli")
                .build());
    }

    /**
     * @param $this$onLoad This parameter is not used. 
     */
    @Override
    public void onLoad(@NotNull PluginComponentStorage $this$onLoad) {
        getLogger().debug("Loading ArisuBot plugin config...");
        CONFIG.load();
        getLogger().debug("Loading ArisuBot plugin data...");
        DATA.load();
        getLogger().debug("Load complete. Waiting for enabling. ");
    }

    @Override
    public void onEnable() {
        getLogger().debug("Plugin loading.");
        LOADER.load();
        getLogger().verbose("Plugin load complete. ");
        getLogger().verbose("Registering listener host. ");
        GlobalEventChannel.INSTANCE.registerListenerHost(new EventListener());
        getLogger().debug("Initialization complete. ");
        ArisuBot.PluginRunning = true;
    }

    @Override
    public void onDisable() {
        getLogger().debug("Start disabling process. ");
        DATA.shutdown();
        getLogger().debug("Data saved. ");
        CONFIG.shutdown();
        getLogger().debug("Config saved. ");
        LOADER.shutdown();
        getLogger().debug("Shutdown complete. ");
    }

    public static Path GetConfigPath() {
      return INSTANCE.getConfigFolderPath();
    }
    public static Path GetDataPath() {
      return INSTANCE.getDataFolderPath();
    }
}