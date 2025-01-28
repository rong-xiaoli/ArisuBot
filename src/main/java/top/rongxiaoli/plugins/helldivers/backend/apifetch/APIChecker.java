package top.rongxiaoli.plugins.helldivers.backend.apifetch;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import top.rongxiaoli.plugins.helldivers.HelldiversHelper;
import top.rongxiaoli.plugins.helldivers.backend.Constants;

public class APIChecker {
    /**
     * Return state in boolean.
     * @return True if alive, false otherwise.
     */
    public static boolean stateInBoolean() {
        try {
            getRawResponse();
            return true;
        } catch (HttpException e) {
            return false;
        }
    }
    private static String getRawResponse() throws HttpException {
        HttpRequest req = HttpRequest.get(Constants.HD2API.API_ROOT);
        req.addHeaders(HelldiversHelper.CONFIG.getXSuperClientMap());
        req.addHeaders(HelldiversHelper.CONFIG.getXSuperContactMap());
        try(HttpResponse response = req.execute()){
            return response.body();
        } catch (HttpException e) {
            throw e;
        }
    }
}
