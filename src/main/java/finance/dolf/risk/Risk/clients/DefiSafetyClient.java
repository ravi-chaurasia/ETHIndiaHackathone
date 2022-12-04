package finance.dolf.risk.Risk.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import finance.dolf.risk.Risk.utils.HttpRequestUtils;

@Component
public class DefiSafetyClient {
    private static final String defiSafetyEndpoint = "https://api.defisafety.com";

    private final HttpRequestUtils httpRequestUtils;

    private final String apiKey;

    public DefiSafetyClient(@Value("${defisafety.api.key}") String apiKey,
            HttpRequestUtils httpRequestUtils) {
        this.apiKey = apiKey;
        this.httpRequestUtils = httpRequestUtils;
    }

    public <T> T getAllChainScores(
            Class<T> wClass) throws Exception {
        return httpRequestUtils.getHttpClassResponseWithApiKey(
                defiSafetyEndpoint + "/chain-scores?offset=0&limit=10", apiKey, wClass);
    }

    public <T> T getChainScoreForChain(String chainId,
            Class<T> wClass) throws Exception {
        return httpRequestUtils.getHttpClassResponseWithApiKey(
                defiSafetyEndpoint + "/chain-scores/" + chainId, apiKey, wClass);
    }

}
