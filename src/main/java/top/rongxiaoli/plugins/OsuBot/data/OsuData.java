package top.rongxiaoli.plugins.OsuBot.data;

import net.mamoe.mirai.console.data.Value;
import net.mamoe.mirai.console.data.java.JavaAutoSavePluginData;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;
import top.rongxiaoli.ArisuBot;
import top.rongxiaoli.backend.interfaces.PluginBase.PluginDataBase;
import top.rongxiaoli.plugins.OsuBot.backend.UserBaseData;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class OsuData extends JavaAutoSavePluginData implements PluginDataBase {
    public static final OsuData INSTANCE = new OsuData();
    public static final MiraiLogger LOGGER = MiraiLogger.Factory.INSTANCE.create(OsuData.class, "ArisuBot.OsuBot.OsuData");

    private final Value<Map<Long, UserBaseData>> userDataList = typedValue("UserDataList",
            createKType(Map.class, createKType(Long.class), createKType(UserBaseData.class)),
            new HashMap<>()
    );

    public OsuData() {
        super("osu!data");
    }

    @Override
    public void load() {
        LOGGER.verbose("Loading data. ");
        ArisuBot.INSTANCE.reloadPluginData(INSTANCE);
        LOGGER.debug("Load complete. ");
    }

    @Override
    public void reload() {
        LOGGER.debug("Start reloading data. ");
        ArisuBot.INSTANCE.reloadPluginData(INSTANCE);
        LOGGER.debug("Data reloading complete. ");
    }

    @Override
    public void shutdown() {
        LOGGER.debug("Start shutdown process. ");
        LOGGER.verbose(String.valueOf(INSTANCE.userDataList.get().size())); //Fixme: data is not getting recorded.
        ArisuBot.INSTANCE.savePluginData(INSTANCE);
        LOGGER.debug("Shutdown process complete. ");
    }

    @Override
    public void saveData() {
        LOGGER.debug("Saving data. ");
        ArisuBot.INSTANCE.savePluginData(INSTANCE);
        LOGGER.debug("Data saved. ");
    }

    /**
     * Get the user's osu!bot data.
     * @param userID User's ID.
     * @return Return the user data. Null if no data found.
     */
    public synchronized UserBaseData getUserData(long userID) {
        Map<Long, UserBaseData> temp = OsuData.INSTANCE.userDataList.get();
        if (temp.get(userID) == null) return null;
        return temp.get(userID);
    }
    /**
     * Set the user's osu!bot data.
     * @param userID User's ID.
     * @param userBaseData User's new data. Should not be null.
     */
    public synchronized void setUserData(long userID, UserBaseData userBaseData) {
        Map<Long, UserBaseData> temp = OsuData.INSTANCE.userDataList.get();
        if (temp.containsKey(userID)) temp.replace(userID, userBaseData);
        else temp.put(userID, userBaseData);
        OsuData.INSTANCE.userDataList.set(temp);
    }

    /**
     * Delete user from data. Throw {@code NoSuchElementException} if user is not found in data.
     * @param userID User's QQ number.
     * @throws NoSuchElementException Throws when user's QQ number is not found in data.
     */
    public synchronized void deleteUserData(long userID)  throws NoSuchElementException {
        Map<Long, UserBaseData> t = OsuData.INSTANCE.userDataList.get();
        if (t.containsKey(userID)) t.remove(userID);
        else throw new NoSuchElementException("ID " + userID + " user not found. ");
    }
}
