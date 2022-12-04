package finance.dolf.risk.Risk.service.Impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import finance.dolf.risk.Risk.utils.JsonBodyHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;

import finance.dolf.risk.Risk.service.DefiLamaService;
import io.swagger.client.ApiException;
import io.swagger.client.api.CoinsApi;
import io.swagger.client.api.StablecoinsApi;
import io.swagger.client.api.TvlApi;
import io.swagger.client.api.YieldsApi;

@Service
public class JsonDefiLamaServiceImpl implements DefiLamaService {

    public static final String PROTOCOL_CSV_FILE_PATH = "src/main/resources/DefilamaProtocols.json";
    public static final String COIN_GECKO_MARKET_CHART_DATA = "src/main/resources/coin_gecko_market_data.json";
    public static final String CSV_JSON_FILE = "src/main/resources/csv_json_file.json";
    public static final String CSV_FILE = "src/main/resources/csv_file.csv";

    private final TvlApi tvlApi;
    private final CoinsApi coinsApi;
    private final StablecoinsApi stablecoinsApi;
    private final YieldsApi yieldsApi;
    private final ObjectMapper objectMapper;

    public JsonDefiLamaServiceImpl() {
        tvlApi = new TvlApi();
        coinsApi = new CoinsApi();
        stablecoinsApi = new StablecoinsApi();
        yieldsApi = new YieldsApi();
        objectMapper = new ObjectMapper();
    }

    @Override
    public void populateAllProtocols() {

        try {
            Object protocolInfo = tvlApi.protocolsGet();
            JsonNode jsonTree = objectMapper.valueToTree(protocolInfo);

            ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(new File(PROTOCOL_CSV_FILE_PATH), jsonTree);

        } catch (ApiException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private JsonNode flattenJsonNode(JsonNode jsonNode) {
        Map<String, String> map = new HashMap<String, String>();
        addKeys("", jsonNode, map);
        return objectMapper.valueToTree(map);
    }

    private void addKeys(String currentPath, JsonNode jsonNode, Map<String, String> map) {
        if (jsonNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Iterator<Map.Entry<String, JsonNode>> iter = objectNode.fields();
            String pathPrefix = currentPath.isEmpty() ? "" : currentPath + ".";

            while (iter.hasNext()) {
                Map.Entry<String, JsonNode> entry = iter.next();
                addKeys(pathPrefix + entry.getKey(), entry.getValue(), map);
            }
        } else if (jsonNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonNode;
            for (int i = 0; i < arrayNode.size(); i++) {
                addKeys(currentPath + "[" + i + "]", arrayNode.get(i), map);
            }
        } else if (jsonNode.isValueNode()) {
            ValueNode valueNode = (ValueNode) jsonNode;
            map.put(currentPath, valueNode.asText());
        }
    }

    @Override
    public Object getAllProtocols() {
        File from = new File(PROTOCOL_CSV_FILE_PATH);
        return JsonBodyHandler.getJsonFromFile(from);
    }

    @Override
    public Object getAllCoinGeckoMarketChartData() {
        File from = new File(COIN_GECKO_MARKET_CHART_DATA);
        return JsonBodyHandler.getJsonFromFile(from);
    }

    @Override
    public void storeCsvToJson() {
        try {
            List<Map<?, ?>> data = JsonBodyHandler.readObjectsFromCsv(new File(CSV_FILE));
            JsonBodyHandler.writeAsJson(data, new File(CSV_JSON_FILE));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Object getJsonData() {
        File from = new File(CSV_JSON_FILE);
        return JsonBodyHandler.getJsonFromFile(from);
    }
}
