package top.rongxiaoli.plugins.helldivers;

import net.mamoe.mirai.console.command.CommandContext;
import org.jetbrains.annotations.NotNull;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractCompositeCommand;
import top.rongxiaoli.backend.Utils.UserJudgeUtils;
import top.rongxiaoli.backend.interfaces.Plugin;

@Plugin(name = "HelldiversHelper")
public class HelldiversHelper extends ArisuBotAbstractCompositeCommand {
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
        super.load();
    }

    /**
     * Reload method. Usually for resetting state.
     */
    @Override
    public void reload() {
        super.reload();
    }

    /**
     * Shutdown method.
     */
    @Override
    public void shutdown() {
        super.shutdown();
    }

    /**
     * Manually save the data.
     */
    @Override
    public void saveData() {
        super.saveData();
    }

    /**
     * Manually reload the data. Discard the changes in memory.
     */
    @Override
    public void reloadData() {
        super.reloadData();
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
