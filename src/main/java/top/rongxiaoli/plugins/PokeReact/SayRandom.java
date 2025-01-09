package top.rongxiaoli.plugins.PokeReact;

import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.event.events.NudgeEvent;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.plugins.PokeReact.backend.PokeReactTextConfig;

import java.util.Random;
import java.util.Set;

public class SayRandom {
    public static final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(SayRandom.class, "ArisuBot.PokeReact.SayRandom");
    private static String getRandomString() {
        String out;
        Set<String> TextSet = PokeReactTextConfig.INSTANCE.ValueSayRandomText.get();
        String[] preset = TextSet.toArray(new String[0]);
        if (preset.length == 0) return "别戳啦";
        out = preset[new Random().nextInt(preset.length)];
        return out;
    }
    public void React(CommandContext context) {
        String messageString = getRandomString();
        context.getSender().sendMessage(messageString);
    }
    public void React(NudgeEvent event) {
        String messageString = getRandomString();
        event.getSubject().sendMessage(messageString);
    }
}
