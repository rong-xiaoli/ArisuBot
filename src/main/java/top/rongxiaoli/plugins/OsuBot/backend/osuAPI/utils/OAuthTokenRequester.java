package top.rongxiaoli.plugins.OsuBot.backend.osuAPI.utils;

import top.rongxiaoli.plugins.OsuBot.backend.osuAPI.struct.OAuthRequest;

public class OAuthTokenRequester {
    public OAuthRequest request;
    public OAuthTokenRequester(OAuthRequest r) {
        request = r;
    }
    public OAuthTokenRequester(int clientID, String apiCode, String redirectUri) {
        request = new OAuthRequest();
        request.setClientId(clientID);
        request.setCode(apiCode);
        request.setRedirectUri(redirectUri);
        request.setGrantType("authorization_code");
    }
}