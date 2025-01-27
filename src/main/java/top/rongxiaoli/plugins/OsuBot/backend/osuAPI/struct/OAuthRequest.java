package top.rongxiaoli.plugins.OsuBot.backend.osuAPI.struct;

import lombok.Data;

@Data
public class OAuthRequest {
    /**
     * The client ID of your application.
     */
    private long clientId;
    /**
     * The client secret of your application.
     */
    private String clientSecret;
    /**
     * This must always be authorization_code
     */
    private String grantType;
    /**
     * This must be the same as the one used on authorization request.
     */
    private String redirectUri;
}