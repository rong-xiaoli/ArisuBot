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
     * Manages the plugin's operational state.
     * Thread-safe: State transitions are atomic and visible to all threads.
     * State transition rules:
     * - A disabled plugin cannot be disabled again
     * - An enabled plugin cannot be enabled again
     * - load() must be called before any enable/disable operations
     * - shutdown() invalidates the plugin regardless of current state
     */
    void disablePlugin() throws IllegalStateException;
    /**
     * Manages the plugin's operational state.
     * Thread-safe: State transitions are atomic and visible to all threads.
     * State transition rules:
     * - A disabled plugin cannot be disabled again
     * - An enabled plugin cannot be enabled again
     * - load() must be called before any enable/disable operations
     * - shutdown() invalidates the plugin regardless of current state
     */
    void enablePlugin() throws IllegalStateException;
    boolean pluginStatus();
}
