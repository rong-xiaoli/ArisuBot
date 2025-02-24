package top.rongxiaoli.plugins.petpet;

import cn.hutool.http.HttpUtil;
import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractRawCommand;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractSimpleCommand;
import top.rongxiaoli.backend.interfaces.Plugin;
import top.rongxiaoli.plugins.petpet.backend.TargetResolver;

import java.io.*;
import java.nio.file.Files;

@Plugin(name = "Petpet")
public class Petpet extends ArisuBotAbstractRawCommand {
    public static Petpet INSTANCE = new Petpet();
    private static volatile boolean pluginStatus = false;
    private static MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(Petpet.class, "ArisuBot.Petpet");

    public Petpet() {
        super("pet", "摸", "petpet");
        setDescription("摸一摸某人");
    }

    @Override
    public void onCommand(@NotNull CommandContext context, @NotNull MessageChain args) {
        SingleMessage arg = args.get(0);
        if (arg instanceof At) {
            try {
                TargetResolver.handleAt(context, (At) arg);
            } catch (IOException e) {
                context.getSender().sendMessage("发生错误");
            }
            return;
        }
        try {
            TargetResolver.handleString(context, arg.contentToString());
        } catch (IOException e) {
            context.getSender().sendMessage("发生错误");
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
