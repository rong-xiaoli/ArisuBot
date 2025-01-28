package top.rongxiaoli.plugins.helldivers;

import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractCompositeCommand;
import top.rongxiaoli.backend.interfaces.Plugin;
import top.rongxiaoli.plugins.helldivers.backend.Constants;

@Plugin(name = "HelldiversHelper")
public class HelldiversHelper extends ArisuBotAbstractCompositeCommand {
    private final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(HelldiversHelper.class, "ArisuBot.HelldiversHelper");
    private volatile boolean pluginStatus = false;
    public HelldiversHelper() {
        super("helldivers", "Helldivers", "HD2", "hd2");
    }
    @SubCommand({"notice", "通知"})
    public void getLatestNotice(CommandContext context) {
    }

    /**
     * Load method. First time loading.
     */
    @Override
    public void load() {
        LOGGER.debug("Helldiver helper loading. ");
        enablePlugin();
        LOGGER.debug("Helldiver helper loaded. ");
    }

    /**
     * Reload method. Usually for resetting state.
     */
    @Override
    public void reload() {
        LOGGER.debug("Helldiver helper reloading. ");
        enablePlugin();
        LOGGER.debug("Helldiver helper reloaded. ");
    }

    /**
     * Shutdown method.
     */
    @Override
    public void shutdown() {
        LOGGER.debug("Helldiver helper shutting down. ");
        disablePlugin();
        LOGGER.debug("Helldiver helper shut down. ");
    }

    /**
     * Manually save the data.
     */
    @Override
    public void saveData() {
        LOGGER.debug("Helldiver Helper data saving. ");
        LOGGER.debug("Helldiver Helper data saved. ");
    }

    /**
     * Manually reload the data. Discard the changes in memory.
     */
    @Override
    public void reloadData() {
        LOGGER.debug("Helldiver Helper data reloading. ");
        LOGGER.debug("Helldiver Helper data reloaded. ");
    }

    /**
     * Manages the plugin's operational state.
     * Thread-safe: State transitions are atomic and visible to all threads.
     * State transition rules:
     * - A disabled plugin cannot be disabled again
     * - An enabled plugin cannot be enabled again
     * - load() must be called before any enable/disable operations
     * - shutdown() invalidates the plugin regardless of current state
     */
    @Override
    public void disablePlugin() throws IllegalStateException {
        pluginStatus = false;
    }

    /**
     * Manages the plugin's operational state.
     * Thread-safe: State transitions are atomic and visible to all threads.
     * State transition rules:
     * - A disabled plugin cannot be disabled again
     * - An enabled plugin cannot be enabled again
     * - load() must be called before any enable/disable operations
     * - shutdown() invalidates the plugin regardless of current state
     */
    @Override
    public void enablePlugin() throws IllegalStateException {
        pluginStatus = true;
    }

    @Override
    public boolean pluginStatus() {
        return pluginStatus;
    }
}
