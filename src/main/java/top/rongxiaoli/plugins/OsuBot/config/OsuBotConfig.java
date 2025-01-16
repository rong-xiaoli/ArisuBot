package top.rongxiaoli.plugins.OsuBot.config;

import net.mamoe.mirai.console.data.Value;
import net.mamoe.mirai.console.data.java.JavaAutoSavePluginConfig;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.interfaces.PluginBase.PluginConfigBase;

public class OsuBotConfig extends JavaAutoSavePluginConfig implements PluginConfigBase {
    public static final OsuBotConfig INSTANCE = new OsuBotConfig();
    public static final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(OsuBotConfig.class, "ArisuBot.OsuBot.OsuBotConfig");
    public static final Value<String> osuAPIKey = INSTANCE.value("osuAPIKey", "");
    public OsuBotConfig() {
        super("osu!api");
    }

    @Override
    public void load() {
        LOGGER.debug("OsuBot config loading. ");
        ArisuBot.INSTANCE.reloadPluginConfig(INSTANCE);
        LOGGER.debug("OsuBot config loaded. ");
    }

    @Override
    public void reload() {
        LOGGER.debug("OsuBot config reloading. ");
        ArisuBot.INSTANCE.savePluginConfig(INSTANCE);
        ArisuBot.INSTANCE.reloadPluginConfig(INSTANCE);
        LOGGER.debug("OsuBot config reloaded. ");
    }

    @Override
    public void shutdown() {
        LOGGER.debug("Config shutting down. ");
        ArisuBot.INSTANCE.savePluginConfig(OsuBotConfig.INSTANCE);
        LOGGER.debug("Config shut down. ");
    }

    @Override
    public void saveData() {
        LOGGER.debug("Saving data. ");
        ArisuBot.INSTANCE.savePluginConfig(INSTANCE);
        LOGGER.debug("Data saved. ");
    }
}
