package top.rongxiaoli.plugins.petpet;

import cn.hutool.http.HttpUtil;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractRawCommand;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractSimpleCommand;
import top.rongxiaoli.backend.interfaces.Plugin;

import java.io.*;
import java.nio.file.Files;

@Plugin(name = "Petpet")
public class Petpet extends ArisuBotAbstractSimpleCommand {
    public static Petpet INSTANCE = new Petpet();
    private static final String API = "https://api.andeer.top/API/gif_mo.php?qq=";
    private static volatile boolean pluginStatus = false;
    private static MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(Petpet.class, "ArisuBot.Petpet");

    public Petpet() {
        super("pet", "摸", "petpet");
        setDescription("摸一摸某人");
    }

    //@Override
    @Handler
    public void onCommand(@NotNull CommandSender sender, At target) {
        if (sender.getSubject() == null) {
            return;
        }
        File file = null;
        try {
            file = File.createTempFile("Cache", ".gif");
        } catch (IOException e) {
            sender.getSubject().sendMessage("无法创建临时文件");
        }
        byte[] content = HttpUtil.downloadBytes(API + target.getTarget());
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(Files.newOutputStream(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            stream.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            stream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image image = ExternalResource.uploadAsImage(file, sender.getSubject());
        sender.sendMessage(image);
        file.delete();
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
