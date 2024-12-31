package top.rongxiaoli.backend.interfaces.PluginBase;

import net.mamoe.mirai.console.command.Command;
import net.mamoe.mirai.utils.MiraiLogger;

public interface PluginBase extends Command {
    PluginBase INSTANCE = null;
    PluginConfigBase CONFIG_BASE = null;
    PluginDataBase DATA_BASE = null;
    MiraiLogger LOGGER = null;

    /**
     * Load method. First time loading.
     */
    void load();

    /**
     * Reload method. Usually for resetting state.
     */
    void reload();

    /**
     * Shutdown method.
     */
    void shutdown();

    /**
     * Manually save the data.
     */
    void saveData();

    /**
     * Manually reload the data. Discard the changes in memory.
     */
    void reloadData();
    /**
     * Disables this plugin.
     */
    void disablePlugin();
    /**
     * Enables this plugin.
     */
    void enablePlugin();

    /**
     * Get the plugin's status, true if on, false if off.
     */
    boolean pluginStatus();
}
