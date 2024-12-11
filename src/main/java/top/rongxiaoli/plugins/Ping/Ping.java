package top.rongxiaoli.plugins.Ping;

import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.console.command.java.JSimpleCommand;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.PluginBase.PluginBase;

public class Ping extends JSimpleCommand implements PluginBase {
    private static final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(Ping.class, "ArisuBot.Ping");
    public static final Ping INSTANCE = new Ping();
    public Ping() {
        super(ArisuBot.INSTANCE, "ping");
        setPrefixOptional(true);
    }

    @Handler
    public void run(CommandContext context) {
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
}
