package top.rongxiaoli.plugins.helldivers.backend.apifetch;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import top.rongxiaoli.plugins.helldivers.HelldiversHelper;
import top.rongxiaoli.plugins.helldivers.backend.Constants;
import top.rongxiaoli.plugins.helldivers.backend.datatype.Language;
import top.rongxiaoli.plugins.helldivers.backend.datatype.NewsFeedItem;
import top.rongxiaoli.plugins.helldivers.backend.datatype.War;

import java.util.HashMap;
import java.util.Map;

public class WarInfoHelper {
    private War war;
    public WarInfoHelper() {
        getWarInfo(Language.ZHS);
    }
    public WarInfoHelper(Language language) {
        getWarInfo(language);
    }
    public void getWarInfo(Language language) {
        String apiUrl = Constants.HD2API.API_ROOT + Constants.HD2API.API_V1_ROOT + Constants.HD2API.WAR_STATE_API;
        HttpRequest req = HttpRequest.get(apiUrl);
        Map<String, String> headerMap = new HashMap<>(language.toHeaderMap());
        headerMap.putAll(HelldiversHelper.CONFIG.getXSuperClientMap());
        headerMap.putAll(HelldiversHelper.CONFIG.getXSuperContactMap());
        req.addHeaders(headerMap);
        try(HttpResponse response = req.execute()) {
            String jsonStr = response.body();
            war = JSONUtil.toBean(jsonStr, War.class);
        }
    }
    public double getImpactMultiplier() {
        return war.getImpactMultiplier();
    }
    public long getDeathCount() {
        return war.getStatistics().getDeaths();
    }
    public long getPresentPlayerCount() {
        return war.getStatistics().getPlayerCount();
    }
    public long getMissionsWon() {
        return war.getStatistics().getMissionsWon();
    }
    public long getMissionsLost() {
        return war.getStatistics().getMissionsLost();
    }
    public long getTerminidKills() {
        return war.getStatistics().getTerminidKills();
    }
    public long getAutomatonKills() {
        return war.getStatistics().getAutomatonKills();
    }
    public long getIlluminateKills() {
        return war.getStatistics().getIlluminateKills();
    }
    public long getMissionSuccessRate() {
        return war.getStatistics().getMissionSuccessRate();
    }
}
