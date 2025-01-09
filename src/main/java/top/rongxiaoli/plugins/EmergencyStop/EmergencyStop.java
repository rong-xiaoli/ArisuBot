package top.rongxiaoli.plugins.EmergencyStop;

import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractCompositeCommand;
import top.rongxiaoli.backend.interfaces.Plugin;

@Plugin(name = "EmergencyStop")
public class EmergencyStop extends ArisuBotAbstractCompositeCommand {
    private boolean isStopped = false;
    private final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(EmergencyStop.class, "ArisuBot.EmergencyStop");
    public static final EmergencyStop INSTANCE = new EmergencyStop();
    public EmergencyStop() {
        super("EmergencyStop", "停止", "estop");
        setDescription("紧急停止。后面加一个字符串可以查看状态。");
    }
    @SubCommand
    public void switchStatus(CommandContext context) {
        if (isStopped) {
            context.getSender().sendMessage("正在关闭紧急停止");
            ArisuBot.LOADER.reload();
            isStopped =! isStopped;
            return;
        }
        context.getSender().sendMessage("正在紧急停止");
        ArisuBot.LOADER.shutdown();
        isStopped =! isStopped;
    }
    @SubCommand
    public void status(CommandContext context) {
        MessageChainBuilder mcb = new MessageChainBuilder();
        mcb.add(new QuoteReply(context.getOriginalMessage()));
        mcb.add("当前状态：" + isStopped);
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
