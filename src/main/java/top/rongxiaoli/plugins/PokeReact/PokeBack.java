package top.rongxiaoli.plugins.PokeReact;

import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.event.events.NudgeEvent;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.plugins.PokeReact.backend.PokeReactTextConfig;

import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class PokeBack {
    private static final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(PokeBack.class, "ArisuBot.PokeReact.PokeBack");
    private static String getRandomString() {
        String out;
        Set<String> TextSet = PokeReactTextConfig.INSTANCE.ValuePokeBackText.get();
        String[] preset = TextSet.toArray(new String[0]);
        if (preset.length == 0) return "别戳啦";
        Random random = new Random(System.currentTimeMillis());
        out = preset[random.nextInt(preset.length)];
        return out;
    }
    public void React(CommandContext context) {
        String messageString = getRandomString();
        context.getSender().sendMessage(messageString);
        Objects.requireNonNull(context.getSender().getUser()).nudge().sendTo(Objects.requireNonNull(context.getSender().getSubject()));
    }
    public void React(NudgeEvent event) {
        String messageString = getRandomString();
        event.getSubject().sendMessage(messageString);
        Objects.requireNonNull(event.getFrom()).nudge().sendTo(event.getSubject());
    }
}
