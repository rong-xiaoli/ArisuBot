package top.rongxiaoli.plugins.DailySign;

import kotlinx.serialization.Serializable;
import net.mamoe.mirai.console.data.Value;
import net.mamoe.mirai.console.data.java.JavaAutoSavePluginData;
import net.mamoe.mirai.utils.MiraiLogger;
import top.rongxiaoli.Elysia;
import top.rongxiaoli.backend.PluginDataBase;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
@Serializable
public class DailySignData extends JavaAutoSavePluginData implements PluginDataBase {
    public static final DailySignData INSTANCE = new DailySignData();
    private static final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(DailySignData.class);
    public static class DailySignPersonData {
        public GregorianCalendar lastLoginDate;
        public int ContinuousSignCombo;
    }
    public DailySignData() {
        super("DailySignData");
    }
    private final Value<Map<Long, DailySignPersonData>> DailySignDataSet = typedValue(
            "DailySignDataSet",
            createKType(Map.class, createKType(Long.class), createKType(DailySignPersonData.class)),
            new HashMap<Long, DailySignPersonData>() {{
                DailySignPersonData data = new DailySignPersonData();
                data.lastLoginDate = new GregorianCalendar(1999, Calendar.JANUARY,1);
                data.ContinuousSignCombo = 0;
                put(1L, data);
                }
            }
    );
    @Override
    public void load() {
        LOGGER.verbose("Loading data. ");
        Elysia.INSTANCE.reloadPluginData(INSTANCE);
        LOGGER.debug("Load complete. ");
    }

    @Override
    public void reload() {
        LOGGER.debug("Start reloading data. ");
        Elysia.INSTANCE.reloadPluginData(INSTANCE);
        LOGGER.debug("Data reloading complete. ");
    }

    @Override
    public void shutdown() {
        LOGGER.debug("Start shutdown process. ");
        Elysia.INSTANCE.savePluginData(INSTANCE);
        LOGGER.debug("Shutdown process complete. ");
    }

    @Override
    public void saveData() {
        LOGGER.debug("Saving data. ");
        Elysia.INSTANCE.savePluginData(INSTANCE);
        LOGGER.debug("Data saved. ");
    }
    public DailySignPersonData query(long userID) {
        return DailySignDataSet.get().get(userID);
    }
}
