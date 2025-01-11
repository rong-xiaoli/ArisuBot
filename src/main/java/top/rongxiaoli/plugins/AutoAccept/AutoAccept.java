package top.rongxiaoli.plugins.AutoAccept;

import org.jetbrains.annotations.NotNull;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractSimpleCommand;

public class AutoAccept extends ArisuBotAbstractSimpleCommand {
    public AutoAccept() {
        super("AutoAccept");
        setDescription("自动接受");
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

    }

    @Override
    public boolean pluginStatus() {
        return false;
    }
}
