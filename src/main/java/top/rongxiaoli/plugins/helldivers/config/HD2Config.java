package top.rongxiaoli.plugins.helldivers.config;

import net.mamoe.mirai.console.data.Value;
import net.mamoe.mirai.console.data.java.JavaAutoSavePluginConfig;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.interfaces.PluginBase.PluginConfigBase;

import java.util.HashMap;

public class HD2Config extends JavaAutoSavePluginConfig implements PluginConfigBase {
    private final Value<String> XSuperClient = typedValue("X-Super-Client", createKType(String.class));
    private final Value<String> XSuperContact = typedValue("X-Super-Contact", createKType(String.class));
    public static final HD2Config INSTANCE = new HD2Config();
    public static final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(HD2Config.class, "ArisuBot.HelldiversHelper.HD2Config");
    public HD2Config() {
        super("HD2Config");
    }
    public String getXSuperClient() {
        return XSuperClient.get();
    }
    public String getXSuperContact() {
        return XSuperContact.get();
    }
    public HashMap<String, String> getXSuperClientMap() {
        HashMap<String,String> ret = new HashMap<>();
        ret.put("X-Super-Client", getXSuperClient());
        return ret;
    }
    public HashMap<String, String> getXSuperContactMap() {
        HashMap<String,String> ret = new HashMap<>();
        ret.put("X-Super-Contact", getXSuperContact());
        return ret;
    }
    @Override
    public void load() {
        LOGGER.debug("HD2Config loading. ");
        ArisuBot.INSTANCE.reloadPluginConfig(INSTANCE);
        LOGGER.debug("HD2Config loaded. ");
    }

    @Override
    public void reload() {
        LOGGER.debug("HD2Config loading. ");
        ArisuBot.INSTANCE.reloadPluginConfig(INSTANCE);
        LOGGER.debug("HD2Config loaded. ");
    }

    @Override
    public void shutdown() {
        LOGGER.debug("HD2Config loading. ");
        ArisuBot.INSTANCE.savePluginConfig(INSTANCE);
        LOGGER.debug("HD2Config loaded. ");
    }

    @Override
    public void saveData() {
        LOGGER.debug("HD2Config loading. ");
        ArisuBot.INSTANCE.savePluginData(INSTANCE);
        LOGGER.debug("HD2Config loaded. ");
    }
}
