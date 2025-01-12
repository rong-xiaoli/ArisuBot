package top.rongxiaoli.plugins.OsuBot.backend;

import net.mamoe.mirai.console.data.Value;
import net.mamoe.mirai.console.data.java.JavaAutoSavePluginData;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.backend.interfaces.PluginBase.PluginDataBase;

import java.util.List;

public class OsuData extends JavaAutoSavePluginData implements PluginDataBase {
    public static final OsuData DATA = new OsuData();
    public static final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(OsuData.class, "ArisuBot.OsuBot.OsuData");

//    private final Value<List<UserData>> userDataList = typedValue("UserDataList",
//
//    );

    public OsuData() {
        super("osu!data");
    }

    @Override
    public void load() {

    }

    @Override
    public void reload() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public void saveData() {

    }
}
