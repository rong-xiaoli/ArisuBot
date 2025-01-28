package top.rongxiaoli.plugins.helldivers.backend.apifetch;

import cn.hutool.Hutool;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import top.rongxiaoli.plugins.helldivers.HelldiversHelper;
import top.rongxiaoli.plugins.helldivers.backend.Constants;
import top.rongxiaoli.plugins.helldivers.backend.datatype.Language;
import top.rongxiaoli.plugins.helldivers.backend.datatype.NewsFeedItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsFeedHelper {
    public static String getLatestNews(Language language) {
        String apiURL = Constants.HD2API.API_ROOT + Constants.HD2API.API_RAW_ROOT + Constants.HD2API.SE_NEWS_API;
        HttpRequest req = HttpRequest.get(apiURL);
        Map<String, String> headerMap = new HashMap<>(language.toHeaderMap());
        headerMap.putAll(HelldiversHelper.CONFIG.getXSuperClientMap());
        headerMap.putAll(HelldiversHelper.CONFIG.getXSuperContactMap());
        try(HttpResponse response = req.execute()) {
            String jsonStr = response.body();
        }
    }
}
