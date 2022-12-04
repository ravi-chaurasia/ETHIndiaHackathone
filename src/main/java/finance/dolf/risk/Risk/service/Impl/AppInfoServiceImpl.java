package finance.dolf.risk.Risk.service.Impl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import finance.dolf.risk.Risk.service.AppInfoService;


@Service
public class AppInfoServiceImpl implements AppInfoService {

	public static final String PROTOCOL_MARKET_FILE_PATH = "src/main/resources/marketdata/btc.json";

	@Autowired
    ObjectMapper objectMapper;

	@Override
	public String getAppInfo() {
		// TODO Auto-generated method stub
		return "Flow Complete";
	}

	@Override
	public String getLiquidityInfo() {
		File from = new File(PROTOCOL_MARKET_FILE_PATH);
        JsonNode masterJSON;
        try {
            masterJSON = objectMapper.readTree(from);
			JsonNode data = masterJSON.get("data");
			JsonNode marketPairs = data.get("marketPairs");
			double allVolume = 0;
			double multiplyVolume = 0;
			for(int i = 0; i< marketPairs.size();i++){
				JsonNode jsonNode = marketPairs.get(i);
				JsonNode volumeUsd = jsonNode.get("volumeUsd");
				double volume = volumeUsd.asDouble();
				//System.out.println(volumeUsd.asText());
				JsonNode effectiveLiquidity = jsonNode.get("effectiveLiquidity");
				double effectiL = effectiveLiquidity.asDouble();
				 allVolume = allVolume + volume;
				 multiplyVolume = multiplyVolume + (volume*effectiL);

			}

			double results = multiplyVolume/allVolume;
			System.out.println(results);

            return ""+results;

        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
	}



}
