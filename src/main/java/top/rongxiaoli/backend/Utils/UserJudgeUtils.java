package top.rongxiaoli.backend.Utils;

import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.utils.MiraiLogger;

import java.util.Objects;

public class UserJudgeUtils {
    public UserJudgeUtils(MiraiLogger l) {
        LOGGER = l;
    }
    MiraiLogger LOGGER;
    public boolean isConsoleCalling(CommandContext context) {
        long userID = 0, subjectID = 0;
        // From console, return:
        try {
            userID = Objects.requireNonNull(context.getSender().getUser()).getId();
            subjectID = Objects.requireNonNull(context.getSender().getSubject()).getId();
        } catch (NullPointerException e) {
            LOGGER.warning("This command cannot be invoked from console! ");
            return true;
        }
        if (userID == 0 || subjectID == 0) {
            LOGGER.warning("This command cannot be invoked from console! ");
            return true;
        }
        return false;
    }
}