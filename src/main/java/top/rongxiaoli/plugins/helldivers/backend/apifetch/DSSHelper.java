package top.rongxiaoli.plugins.helldivers.backend.apifetch;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import top.rongxiaoli.plugins.helldivers.HelldiversHelper;
import top.rongxiaoli.plugins.helldivers.backend.Constants;
import top.rongxiaoli.plugins.helldivers.backend.datatype.diveharder.DSSInfo;
import top.rongxiaoli.plugins.helldivers.backend.datatype.hd2.Language;
import top.rongxiaoli.plugins.helldivers.backend.datatype.hd2.SpaceStation2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DSSHelper {
    public static HashMap<Long, String> getDSSCurrentPlanet(Language language) throws HttpException {
        return getSpaceStationOrbitPlanetMap(getDSSListHD2API(language));
    }
    public static List<SpaceStation2> getDSSListHD2API(Language language) throws HttpException {
        String apiUrl = Constants.HD2API.API_DOMAIN + Constants.HD2API.API_V1_ROOT + Constants.HD2API.V1_DSS_LIST_API;
        HttpRequest req = HttpUtil.createGet(apiUrl);
        Map<String, String> headerMap = new HashMap<>(language.toHeaderMap());
        headerMap.putAll(HelldiversHelper.CONFIG.getXSuperClientMap());
        headerMap.putAll(HelldiversHelper.CONFIG.getXSuperContactMap());
        req.addHeaders(headerMap);
        try (HttpResponse response = req.execute()) {
            String jsonStr = response.body();
            return JSONUtil.toList(jsonStr, SpaceStation2.class);
        }
    }
    public static HashMap<Long, String> getSpaceStationOrbitPlanetMap(List<SpaceStation2> spaceStations) {
        HashMap<Long, String> out = new HashMap<>();
        if (spaceStations == null) return out;
        for (SpaceStation2 station :
                spaceStations) {
            if (station.getPlanet() == null) {
                out.put(station.getId32(), null);
                continue;
            }
            out.put(station.getId32(), station.getPlanet().getName());
        }
        return out;
    }
    public static HashMap<Long, DSSInfo> getDSSListDiveHarderAPI() throws HttpException {
        String apiUrl = Constants.DiveHarderAPI.API_DOMAIN + Constants.DiveHarderAPI.API_RAW_ROOT + Constants.DiveHarderAPI.RAW_GET_DSS_API;
        HttpRequest req = HttpUtil.createGet(apiUrl);
        try (HttpResponse response = req.execute()) {
            String jsonStr = response.body();
            HashMap<Long, DSSInfo> out = new HashMap<>();
            List<DSSInfo> returnList = JSONUtil.toList(jsonStr, DSSInfo.class);
            for (DSSInfo info :
                    returnList) {
                out.put(info.getId32(), info);
            }
            return out;
        }
    }
}
