package top.rongxiaoli.plugins.petpet.backend;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.jetbrains.annotations.NotNull;
import top.rongxiaoli.ArisuBot;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TargetResolver {

    private static final String API = "https://api.andeer.top/API/gif_mo.php?qq=";
    public static void handleAt(@NotNull CommandContext context, At target) throws IOException{
        String targetID = String.valueOf(target.getTarget());
        sendTargetPict(context, targetID);
    }
    public static void handleString(@NotNull CommandContext context, String target) throws IOException {
        try {
            long targetID = Long.parseLong(ReUtil.get("\\d+", target, 0));
            sendTargetPict(context, String.valueOf(targetID));
        } catch (NumberFormatException e) {
            context.getSender().sendMessage("请输入ID或直接@摸头对象");
        }
    }
    private static void sendTargetPict(@NotNull CommandContext context, String target) throws IOException {
        File file = null;
        File path = new File(ArisuBot.GetDataPath().toFile(), "petpet");
        path.mkdirs();
        file = File.createTempFile("Cache", ".gif", path);
        byte[] content = HttpUtil.downloadBytes(API + target);
        BufferedOutputStream stream = null;
        stream = new BufferedOutputStream(Files.newOutputStream(file.toPath()));
        stream.write(content);
        stream.flush();
        if (context.getSender().getSubject() == null) {
            return;
        }
        Image image = ExternalResource.uploadAsImage(file, context.getSender().getSubject());
        context.getSender().sendMessage(image);
        file.deleteOnExit();
    }
}
