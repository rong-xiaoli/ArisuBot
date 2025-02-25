package top.rongxiaoli.plugins.petpet;

import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractRawCommand;
import top.rongxiaoli.backend.interfaces.Plugin;
import top.rongxiaoli.plugins.petpet.backend.TargetResolver;

import java.io.IOException;

@Plugin(name = "Petpet")
public class Petpet extends ArisuBotAbstractRawCommand {
    public static final Petpet INSTANCE = new Petpet();
    private volatile boolean pluginStatus = false;
    private static MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(Petpet.class, "ArisuBot.Petpet");

    public Petpet() {
        super("pet", "摸", "petpet");
        setDescription("摸一摸某人，不带参数摸自己，可以@某人，也可以输入QQ号摸对应用户");
        setUsage("/pet {[@someone]|[QQID]}");
        setPrefixOptional(true);
    }

    @Override
    public void onCommand(@NotNull CommandContext context, @NotNull MessageChain args) {
        if (args.isEmpty()) {
            try {
                TargetResolver.handle(context, String.valueOf(context.getSender().getUser().getId()));
                return;
            } catch (IOException e) {
                MessageChainBuilder builder = new MessageChainBuilder();
                builder.add(new QuoteReply(context.getOriginalMessage()));
                builder.add("发生错误");
                context.getSender().sendMessage(builder.build());
                LOGGER.error(e);
                return;
            }
        }
        SingleMessage arg = args.get(0);
        if (arg instanceof At) {
            try {
                TargetResolver.handleAt(context, (At) arg);
            } catch (IOException e) {
                MessageChainBuilder builder = new MessageChainBuilder();
                builder.add(new QuoteReply(context.getOriginalMessage()));
                builder.add("发生错误");
                context.getSender().sendMessage(builder.build());
                LOGGER.error(e);
                return;
            }
            return;
        }
        try {
            TargetResolver.handleString(context, arg.contentToString());
        } catch (IOException e) {
            MessageChainBuilder builder = new MessageChainBuilder();
            builder.add(new QuoteReply(context.getOriginalMessage()));
            builder.add("发生错误");
            context.getSender().sendMessage(builder.build());
            LOGGER.error(e);
        }
    }
    @Override
    public void load() {
        LOGGER.debug("Petpet loading. ");
        enablePlugin();
        LOGGER.debug("Petpet loaded. ");
    }

    @Override
    public void reload() {
        LOGGER.debug("Petpet reloading. ");
        enablePlugin();
        LOGGER.debug("Petpet reloaded. ");
    }

    @Override
    public void shutdown() {
        LOGGER.debug("Petpet shutting down. ");
        disablePlugin();
        LOGGER.debug("Petpet shut down. ");
    }

    @Override
    public synchronized void disablePlugin() throws IllegalStateException {
        pluginStatus = false;
    }

    @Override
    public synchronized void enablePlugin() throws IllegalStateException {
        pluginStatus = true;
    }

    @Override
    public boolean pluginStatus() {
        return pluginStatus;
    }
}
