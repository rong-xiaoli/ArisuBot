package top.rongxiaoli.plugins.Broadcast;

import net.mamoe.mirai.console.command.CommandContext;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractSimpleCommand;

public class Broadcast extends ArisuBotAbstractSimpleCommand {
    public Broadcast() {
        super(ArisuBot.INSTANCE, "broadcast", "广播");
        setDescription("向所有人广播消息");
        setPrefixOptional(false);
    }
    @Override
    public void onCommand(CommandContext context) {
    }
}
