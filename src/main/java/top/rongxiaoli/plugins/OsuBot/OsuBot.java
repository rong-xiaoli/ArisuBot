package top.rongxiaoli.plugins.OsuBot;

import net.mamoe.mirai.console.data.Value;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractCompositeCommand;
import top.rongxiaoli.backend.interfaces.Plugin;
import top.rongxiaoli.plugins.OsuBot.backend.UserData;

import java.util.List;

@Plugin(name = "OsuBot")
public class OsuBot extends ArisuBotAbstractCompositeCommand {
    public static final OsuBot INSTANCE = new OsuBot();
    public OsuBot() {
        super("osu");
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
