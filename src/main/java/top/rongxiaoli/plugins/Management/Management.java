package top.rongxiaoli.plugins.Management;

import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractCompositeCommand;
import top.rongxiaoli.backend.interfaces.Plugin;

@Plugin(name = "Management")
public class Management extends ArisuBotAbstractCompositeCommand {
    public static Management INSTANCE = new Management();
    private final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(Management.class, "ArisuBot.Management");
    public Management() {
        super("manage", "管理");
        setDescription("管理某项功能开关");
    }
    @SubCommand
    public void enable(CommandContext context, String targetPlugin) {
        ArisuBot.LOADER.reload(targetPlugin);
    }
    @SubCommand
    public void disable(CommandContext context, String targetPlugin) {
        ArisuBot.LOADER.shutdown(targetPlugin);
    }
    @Override
    public void load() {
        LOGGER.debug("Management loading. ");
        LOGGER.debug("Management loaded. ");
    }

    @Override
    public void shutdown() {
        LOGGER.debug("Management shutting down. ");
        LOGGER.debug("Management shut down. ");
    }

    /**
     * Disables this plugin.
     */
    @Override
    public void disablePlugin() {
        return;
    }

    /**
     * Enables this plugin.
     */
    @Override
    public void enablePlugin() {
        return;
    }

    /**
     * Get the plugin's status, true if on, false if off.
     */
    @Override
    public boolean pluginStatus() {
        return true;
    }
}
