package finance.dolf.risk.Risk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import finance.dolf.risk.Risk.pojo.GenericResponse;
import finance.dolf.risk.Risk.service.AppInfoService;
import finance.dolf.risk.Risk.service.DefiLamaService;
import finance.dolf.risk.Risk.service.DefiSafetyService;
import io.swagger.client.ApiException;
import io.swagger.client.api.TvlApi;

@RestController()
@CrossOrigin(origins = "*")
public class AppInfoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppInfoController.class);

	@Autowired
	private AppInfoService appInfoService;

	@Autowired
	private DefiLamaService defiLamaService;

	@Autowired
	private DefiSafetyService defiSafetyService;

	@GetMapping("/appInfo")
	@ResponseBody
	public GenericResponse<String> getAppInfo() {
		LOGGER.info("App Info method Called");
		// this is used as health check in oracle lb
		GenericResponse<String> response = GenericResponse.<String>builder()
				.data(appInfoService.getAppInfo())
				.build();

		return response;

	}

	@GetMapping("/processLiquidity")
	@ResponseBody
	public GenericResponse<String> getLiquidityInfo() {
		LOGGER.info("App Info method Called");
		// this is used as health check in oracle lb
		GenericResponse<String> response = GenericResponse.<String>builder()
				.data(appInfoService.getLiquidityInfo())
				.build();

		return response;

	}

	@GetMapping("/populateProtocolDefilama")
	@ResponseBody
	public GenericResponse<String> populateProtocolDefilama() {
		LOGGER.info("App Info method Called");
		// this is used as health check in oracle lb
		GenericResponse<String> response = GenericResponse.<String>builder()
				.data(appInfoService.getAppInfo())
				.build();
		defiLamaService.populateAllProtocols();
		return response;

	}

	@GetMapping("/getAllProtocolDefilama")
	@ResponseBody
	public Object getAllProtocolDefilama() {
		LOGGER.info("App Info method Called");

		Object allProtocolInfo = defiLamaService.getAllProtocols();
		return allProtocolInfo;

	}

	@GetMapping("/populateChainScoresDefiSafety")
	@ResponseBody
	public GenericResponse<String> populateChainScoresDefiSafety() {
		LOGGER.info("App Info method Called");
		// this is used as health check in oracle lb
		GenericResponse<String> response = GenericResponse.<String>builder()
				.data(appInfoService.getAppInfo())
				.build();
		defiSafetyService.populateAllChainScores();

		return response;

	}

	@GetMapping("/getAllChainScoresDefiSafety")
	@ResponseBody
	public Object getAllChainScoresDefiSafety() {
		LOGGER.info("App Info method Called");

		Object allChainScores = defiSafetyService.getAllChainScores();
		return allChainScores;

	}

	@GetMapping("/getAllCoinGeckoMarketChartData")
	@ResponseBody
	public Object getAllCoinGeckoMarketChartData() {
		LOGGER.info("getAllCoinGeckoMarketChartData method Called");

		return defiLamaService.getAllCoinGeckoMarketChartData();
	}

	@GetMapping("/convertCsvToJson")
	@ResponseBody
	public void csvToJson() {
		
		defiLamaService.storeCsvToJson();

	}

	@GetMapping("/getFromJson")
	@ResponseBody
	public Object getFromJson() {
		LOGGER.info("getFromJson method Called");

		return defiLamaService.getJsonData();
	}
}