package top.rongxiaoli.plugins.helldivers.backend.apifetch;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import top.rongxiaoli.plugins.helldivers.HelldiversHelper;
import top.rongxiaoli.plugins.helldivers.backend.Constants;
import top.rongxiaoli.plugins.helldivers.backend.datatype.hd2.Language;
import top.rongxiaoli.plugins.helldivers.backend.datatype.hd2.War;
import top.rongxiaoli.plugins.helldivers.backend.utils.HD2DateConverter;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class WarInfoHelper {
    private static HttpResponse executeWarRequest(Language language) {
        String apiUrl = Constants.HD2API.API_DOMAIN + Constants.HD2API.API_V1_ROOT + Constants.HD2API.V1_WAR_STATE_API;
        Map<String, String> headerMap = new HashMap<>(language.toHeaderMap());
        HttpRequest req = HttpRequest.get(apiUrl);
        headerMap.putAll(HelldiversHelper.CONFIG.getXSuperClientMap());
        headerMap.putAll(HelldiversHelper.CONFIG.getXSuperContactMap());
        req.addHeaders(headerMap);
        HttpResponse response = req.execute();
        if (response.getStatus() != 200) {
            throw new HttpException("API request failed with status: " + response.getStatus());
        }
        return response;
    }

    public static War getWarInfo(Language language) throws HttpException {
        try(HttpResponse response = executeWarRequest(language)) {
            String jsonStr = response.body();
            return JSONUtil.toBean(jsonStr, War.class);
        }
    }
    public static ZonedDateTime getWarNowDateTime() throws HttpException {
        try(HttpResponse response = executeWarRequest(Language.ZHS)) {
            String jsonStr = response.body();
            War war = JSONUtil.toBean(jsonStr, War.class);
            return HD2DateConverter.convert(war.getStarted(), war.getNow());
        }
    }
}
