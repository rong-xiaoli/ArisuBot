package top.rongxiaoli.plugins.Ping;

import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.console.command.java.JSimpleCommand;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractSimpleCommand;
import top.rongxiaoli.backend.interfaces.PluginBase.PluginBase;

public class Ping extends ArisuBotAbstractSimpleCommand implements PluginBase {
    private boolean pluginStatus = false;
    private static final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(Ping.class, "ArisuBot.Ping");
    public static final Ping INSTANCE = new Ping();
    public Ping() {
        super("ping");
        setPrefixOptional(true);
    }

    @Handler
    public void run(CommandContext context) {
        if (!pluginStatus) return;
        if (!ArisuBot.PluginRunning) {
            return;
        }
        context.getSender().sendMessage("Pong! ");
    }

    /**
     * Load method. First time loading.
     */
    @Override
    public void load() {
        enablePlugin();
        LOGGER.debug("Command loaded. ");
    }

    /**
     * Reload method. Usually for resetting state.
     */
    @Override
    public void reload() {
        LOGGER.debug("Reload complete. ");
    }

    /**
     * Shutdown method.
     */
    @Override
    public void shutdown() {
        disablePlugin();
        LOGGER.debug("shutdown() invoked. Nothing special, pass. ");
    }

    /**
     * Manually save the data.
     */
    @Override
    public void saveData() {
        LOGGER.debug("Nothing to store. ");
    }

    /**
     * Manually reload the data. Discard the changes in memory.
     */
    @Override
    public void reloadData() {
        LOGGER.debug("Nothing to load. ");
    }

    /**
     * Disables this plugin.
     */
    @Override
    public void disablePlugin() {
        pluginStatus = false;
    }

    /**
     * Enables this plugin.
     */
    @Override
    public void enablePlugin() {
        pluginStatus = true;
    }

    /**
     * Get the plugin's status, true if on, false if off.
     */
    @Override
    public boolean pluginStatus() {
        return pluginStatus;
    }
}
