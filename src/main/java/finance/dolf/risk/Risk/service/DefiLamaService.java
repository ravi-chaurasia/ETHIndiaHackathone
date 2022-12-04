package finance.dolf.risk.Risk.service;

public interface DefiLamaService {

    void populateAllProtocols();

    Object getAllProtocols();

    Object getAllCoinGeckoMarketChartData();

    void storeCsvToJson();

    Object getJsonData();

}
