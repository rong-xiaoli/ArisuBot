package top.rongxiaoli.plugins.OsuBot.backend.osuAPI.struct;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Successful requests will be issued an access token.
 */
@Data
@AllArgsConstructor
public class OAuthSuccessResponse {
    /**
     * The type of token, this should always be Bearer.
     */
    public String token_type;
    /**
     * The number of seconds the token will be valid for.
     */
    public int expires_in;
    /**
     * 	The access token.
     */
    public String access_token;
    /**
     * The refresh token.
     */
    public String refresh_token;
}
