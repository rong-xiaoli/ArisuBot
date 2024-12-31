package top.rongxiaoli.plugins.EmergencyStop;

import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractSimpleCommand;

public class EmergencyStop extends ArisuBotAbstractSimpleCommand {
    private boolean isStopped = false;
    private final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(EmergencyStop.class, "ArisuBot.EmergencyStop");
    public static final EmergencyStop INSTANCE = new EmergencyStop();
    public EmergencyStop() {
        super("EmergencyStop", "停止", "estop");
    }
    @Handler
    public void onCommand(CommandContext context) {
        context.getSender().sendMessage("正在紧急停止");
        if (isStopped) {
            ArisuBot.LOADER.reload();
            return;
        }
        ArisuBot.LOADER.shutdown();
    }

    @Override
    public void load() {
        LOGGER.debug("EmergencyStop loaded. ");
    }

    /**
     * Shutdown method.
     */
    @Override
    public void shutdown() {
        LOGGER.debug("EmergencyStop shut down. ");
    }

    /**
     * Disables this plugin.
     */
    @Override
    public void disablePlugin() {
    }

    /**
     * Enables this plugin.
     */
    @Override
    public void enablePlugin() {
    }

    /**
     * Get the plugin's status, true if on, false if off.
     */
    @Override
    public boolean pluginStatus() {
        return true;
    }
}
