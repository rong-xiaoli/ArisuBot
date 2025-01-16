package top.rongxiaoli.plugins.OsuBot.backend.osuAPI.utils;

import top.rongxiaoli.plugins.OsuBot.backend.osuAPI.struct.OAuthRequest;

public class OAuthTokenRequester {
    public OAuthRequest request;
/**
 * 用于请求 OAuth 令牌的工具类。
 * 支持通过 OAuthRequest 对象或独立参数构建请求。
 */
public class OAuthTokenRequester {
    private final OAuthRequest request;

    public OAuthTokenRequester(OAuthRequest r) {
        if (r == null) {
            throw new IllegalArgumentException("OAuthRequest cannot be null");
        }
        request = r;
    }

    public OAuthTokenRequester(int clientID, String apiCode, String redirectUri) {
        if (apiCode == null || redirectUri == null) {
            throw new IllegalArgumentException("API code and redirect URI cannot be null");
        }
        request = new OAuthRequest();
        request.setClientId(clientID);
        request.setCode(apiCode);
        request.setRedirectUri(redirectUri);
        request.setGrantType("authorization_code");
    }
}
