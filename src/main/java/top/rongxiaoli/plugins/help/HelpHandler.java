package top.rongxiaoli.plugins.help;

import org.jetbrains.annotations.NotNull;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractRawCommand;
import top.rongxiaoli.backend.interfaces.annotations.Plugin;

@Plugin(name = "HelpHandler")
public class HelpHandler extends ArisuBotAbstractRawCommand {
    public HelpHandler() {
        super("help", "帮助");
        setDescription("获取帮助");
    }
    public static final HelpHandler INSTANCE = new HelpHandler();

    @Override
    public void disablePlugin() throws IllegalStateException {

    }

    @Override
    public void enablePlugin() throws IllegalStateException {

    }

    @Override
    public boolean pluginStatus() {
        return false;
    }
}
