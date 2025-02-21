package top.rongxiaoli.plugins.petpet;

import cn.hutool.http.HttpUtil;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractRawCommand;
import top.rongxiaoli.backend.interfaces.Plugin;

import java.io.*;

@Plugin(name = "Petpet")
public class Petpet extends ArisuBotAbstractRawCommand {
    public static Petpet INSTANCE = new Petpet();
    private static final String API = "https://api.andeer.top/API/gif_mo.php?qq=";
    private static volatile boolean pluginStatus = false;
    private static MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(Petpet.class, "ArisuBot.Petpet");

    public Petpet() {
        super("pet", "摸", "petpet");
        setDescription("摸一摸某人");
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull MessageChain args) {

        try {
            At source = (At) args.get(0);
            byte[] file = HttpUtil.downloadFile(API + source.getTarget(), );
            File.createTempFile("Temp",".gif", );
            ExternalResource.uploadAsImage()
            sender.sendMessage();

        } catch (ClassCastException e) {
            SingleMessage source = args.get(0);
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
