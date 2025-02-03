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
    public static War getWarInfo(Language language) throws HttpException {
        String apiUrl = Constants.HD2API.API_DOMAIN + Constants.HD2API.API_V1_ROOT + Constants.HD2API.V1_WAR_STATE_API;
        HttpRequest req = HttpRequest.get(apiUrl);
        Map<String, String> headerMap = new HashMap<>(language.toHeaderMap());
        headerMap.putAll(HelldiversHelper.CONFIG.getXSuperClientMap());
        headerMap.putAll(HelldiversHelper.CONFIG.getXSuperContactMap());
        req.addHeaders(headerMap);
        try(HttpResponse response = req.execute()) {
            String jsonStr = response.body();
            return JSONUtil.toBean(jsonStr, War.class);
        }
    }
    public static ZonedDateTime getWarNowDateTime() throws HttpException {
        String apiUrl = Constants.HD2API.API_DOMAIN + Constants.HD2API.API_V1_ROOT + Constants.HD2API.V1_WAR_STATE_API;
        HttpRequest req = HttpRequest.get(apiUrl);
        Map<String, String> headerMap = new HashMap<>(Language.ZHS.toHeaderMap());
        headerMap.putAll(HelldiversHelper.CONFIG.getXSuperClientMap());
        headerMap.putAll(HelldiversHelper.CONFIG.getXSuperContactMap());
        req.addHeaders(headerMap);
        try(HttpResponse response = req.execute()) {
            String jsonStr = response.body();
            War war = JSONUtil.toBean(jsonStr, War.class);
            return HD2DateConverter.convert(war.getStarted(), war.getNow());
        }
    }
}
