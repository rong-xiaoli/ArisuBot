package top.rongxiaoli.plugins.DailyFortune;

import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.Commands.ArisuBotAbstractSimpleCommand;
import top.rongxiaoli.backend.interfaces.PluginBase.PluginBase;

import java.io.File;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Random;

public class DailyFortune extends ArisuBotAbstractSimpleCommand implements PluginBase {
    public static final DailyFortune INSTANCE = new DailyFortune();
    private boolean pluginStatus = false;
    private final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(DailyFortune.class, "ArisuBot.DailyFortune");

    public DailyFortune() {
        super("fortune", "yunshi", "今日运势", "jrys");
        setDescription("今日运势，今天幸运吗？");
        setPrefixOptional(true);
    }
    @Handler
    public void onCommand(CommandSender sender) {
        if (!pluginStatus) return;
        long senderID;
        try {
            senderID = Objects.requireNonNull(sender.getUser()).getId();
        } catch (NullPointerException e) {
            LOGGER.warning("sender.getUser.getID is null. Maybe console is calling this command. Exiting. ");
            sender.sendMessage("你是0吗？");
            return;
        }
        int luck = getRandom(senderID);
        String wishString = getWishString(luck);
        MessageChainBuilder mcb = new MessageChainBuilder();
        mcb.append("今日你的运势是: \n")
                .append(String.valueOf(luck)).append(",").append(getWishLevel(luck)).append("\n")
                .append(wishString);
        try {
            SecureRandom random = new SecureRandom();
            File directoryPathFile = new File(ArisuBot.GetDataPath().toFile(), "DailyFortunePicture");
            if (!directoryPathFile.exists() && !directoryPathFile.mkdirs()) {
                LOGGER.warning("Failed to create directory: " + directoryPathFile);
            }
            File[] targetFiles = directoryPathFile.listFiles(new UploadFileFilter("jpg", "png", "jpeg", "bmp"));
            if ((targetFiles != null ? targetFiles.length : 0) == 0) {
                LOGGER.info("No pictures for random, pass. ");
            }
            else {
                File targetFile = targetFiles[random.nextInt(0,targetFiles.length)];
                Image image = ExternalResource.uploadAsImage(targetFile, Objects.requireNonNull(sender.getSubject()));
                mcb.append(image);
            }
        } catch (Exception e) {
            LOGGER.warning("Error processing fortune command", e);
        }
        sender.sendMessage(mcb.build());

    }

    private int getRandom(long ID) {
        Calendar cal = new GregorianCalendar();
        int year, month, day;
        year = cal.get(Calendar.YEAR) * 10000;
        month = (cal.get(Calendar.MONTH) + 1) * 100;
        day = cal.get(Calendar.DAY_OF_YEAR);
        Random rand;
        if (cal.get(Calendar.MONTH) == Calendar.APRIL && cal.get(Calendar.DAY_OF_MONTH) == 1) {
            rand = new Random();
            return rand.nextInt(-100, 201);
        }
        long seedLong = ID + year + month + day;
        rand = new Random(seedLong);
        return rand.nextInt(0, 101);
    }
    private String getWishString(int fortuneRandom) {
        // Fixme: Use switch - case instead of if - else if - else.
        if (fortuneRandom == 0) {
            return "哦不，你也太非了吧？";
        } else if (0 < fortuneRandom && fortuneRandom <= 10) {
            return "今天运气不太好哦，但是也要加油呀～";
        } else if (10 < fortuneRandom && fortuneRandom <= 25) {
            return "今天有点非哦，但也是美好的一天～";
        } else if (25 < fortuneRandom && fortuneRandom <= 40) {
            return "今天有点小非呢";
        } else if (40 < fortuneRandom && fortuneRandom <= 55) {
            return "今天运气平平，也是平常的一天～";
        } else if (55 < fortuneRandom && fortuneRandom <= 70) {
            return "今天有点小幸运呢";
        } else if (70 < fortuneRandom && fortuneRandom <= 85) {
            return "幸运的一天～";
        } else if (85 < fortuneRandom && fortuneRandom <= 99) {
            return "Lucky~今天好幸运啊!";
        } else if (fortuneRandom == 100) {
            return "哇！欧皇！";
        } else return "这啥？";
    }
    private String getWishLevel(int fortuneRandom) {
        // Fixme: Use switch - case instead of if - else if - else.
        if (fortuneRandom == 0) {
            return "大凶";
        } else if (0 < fortuneRandom && fortuneRandom <= 10) {
            return "凶";
        } else if (10 < fortuneRandom && fortuneRandom <= 40) {
            return "末吉";
        } else if (40 < fortuneRandom && fortuneRandom <= 70) {
            return "小吉";
        } else if (70 < fortuneRandom && fortuneRandom <= 99) {
            return "中吉";
        } else if (fortuneRandom == 100) {
            return "大吉";
        } else return " ";
    }
    /**
     * Load method. First time loading.
     */
    @Override
    public void load() {
        LOGGER.debug("Command loading. ");
        Calendar cal = new GregorianCalendar();
        if ((cal.get(Calendar.MONTH) == Calendar.OCTOBER && cal.get(Calendar.DAY_OF_MONTH) == 24) ||
                (cal.get(Calendar.DAY_OF_YEAR) == 256)) {
            LOGGER.verbose("Happy programmer's day! ");
        }
        enablePlugin();
        LOGGER.debug("Command loaded. ");
    }

    /**
     * Reload method. Usually for resetting state.
     */
    @Override
    public void reload() {
        LOGGER.debug("Nothing to reload, pass. ");
    }

    /**
     * Shutdown method.
     */
    @Override
    public void shutdown() {
        disablePlugin();
        LOGGER.debug("DailyFortune shut down. ");
    }

    /**
     * Manually save the data.
     */
    @Override
    public void saveData() {
        LOGGER.debug("Nothing to do, pass. ");
    }

    /**
     * Manually reload the data. Discard the changes in memory.
     */
    @Override
    public void reloadData() {
        LOGGER.debug("Nothing to do, pass. ");
    }

    /**
     * Disables this plugin.
     */
    @Override
    public void disablePlugin() {
        pluginStatus = false;
    }

    /**
     * Enables this plugin.
     */
    @Override
    public void enablePlugin() {
        pluginStatus = true;
    }

    /**
     * Get the plugin's status, true if on, false if off.
     */
    @Override
    public boolean pluginStatus() {
        return pluginStatus;
    }
}
