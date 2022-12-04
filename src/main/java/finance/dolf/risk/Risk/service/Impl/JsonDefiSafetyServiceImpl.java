package finance.dolf.risk.Risk.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Service;

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
import finance.dolf.risk.Risk.clients.DefiSafetyClient;
import finance.dolf.risk.Risk.service.DefiSafetyService;

@Service
public class JsonDefiSafetyServiceImpl implements DefiSafetyService {

    public static final String CHAIN_SCORES_CSV_FILE_PATH = "src/main/resources/DefiSafetyChainScores.json";

    @Autowired
    private DefiSafetyClient defiSafetyClient;

    private final ObjectMapper objectMapper;

    public JsonDefiSafetyServiceImpl() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public Object getAllChainScores() {
        File from = new File(CHAIN_SCORES_CSV_FILE_PATH);
        JsonNode masterJSON;
        try {
            masterJSON = objectMapper.readTree(from);
            return masterJSON;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void populateAllChainScores() {
        try {
            Object chainScores = defiSafetyClient.getAllChainScores(Object.class);
            JsonNode jsonTree = objectMapper.valueToTree(chainScores);

            ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(new File(CHAIN_SCORES_CSV_FILE_PATH), jsonTree);

        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}