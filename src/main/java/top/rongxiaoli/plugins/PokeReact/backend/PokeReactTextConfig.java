package top.rongxiaoli.plugins.PokeReact.backend;

import net.mamoe.mirai.console.data.Value;
import net.mamoe.mirai.console.data.java.JavaAutoSavePluginConfig;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.interfaces.PluginBase.PluginConfigBase;

import java.util.HashSet;
import java.util.Set;

public class PokeReactTextConfig extends JavaAutoSavePluginConfig implements PluginConfigBase {
    public static final PokeReactTextConfig INSTANCE = new PokeReactTextConfig();
    MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(PokeReactTextConfig.class, "ArisuBot.PokeReact.PokeReactTextConfig");
    public PokeReactTextConfig() {
        super("PokeReactText");
    }
    public Value<Set<String>> ValuePokeBackText = typedValue("PokeBackText",
            createKType(Set.class,
                    createKType(String.class)
            ),
            new HashSet<String>() {
                {
                    add("干什么！");
                    add("痛痛痛，别戳了");
                    add("就你会戳吗？");
                    add("戳你！");
                }
            }
    );
    public Value<Set<String>> ValueSayRandomText = typedValue("SayRandomText",
            createKType(Set.class,
                    createKType(String.class)
            ),
            new HashSet<String>() {
                {
                    add("QwQ");
                    add("awa");
                    add("-ω-");
                    add("oxO");
                    add("OvO");
                    add("你把我戳疼了(*。>Д<)o゜");
                    add("再戳我要哭了o(TヘTo)");
                    add("别戳了别戳了`(*>﹏<*)′");
                }
            }
    );


    @Override
    public void load() {
        LOGGER.debug("Loading config. ");
        ArisuBot.INSTANCE.reloadPluginConfig(INSTANCE);
        LOGGER.debug("Load complete. ");
    }

    @Override
    public void reload() {
        LOGGER.debug("Start reloading config. ");
        ArisuBot.INSTANCE.reloadPluginConfig(INSTANCE);
        LOGGER.debug("Config reloading complete. ");
    }

    @Override
    public void shutdown() {
        LOGGER.debug("Start shutdown process. ");
        ArisuBot.INSTANCE.savePluginConfig(INSTANCE);
        LOGGER.debug("Shutdown process complete. ");
    }

    @Override
    public void saveData() {
        LOGGER.debug("Saving config. ");
        ArisuBot.INSTANCE.savePluginConfig(INSTANCE);
        LOGGER.debug("Config saved. ");
    }
}
