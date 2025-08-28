package com.freight.fox.controller;

import java.time.Instant;
import java.time.ZoneId;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freight.fox.entity.WeatherResponse;
import com.freight.fox.service.WeatherServiceImpl;

@RestController
@RequestMapping("/api")
public class WeatherController {

	@Autowired
	WeatherServiceImpl weatherService;

	@GetMapping("/weather")
	public String getWetherInformation(@RequestParam String pincode, @RequestParam String for_date) {
		WeatherResponse wr = weatherService.postalCodeAndDateToSetLatLongAndSave(pincode, for_date);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < wr.getCnt(); i++) {
			sb.append(Instant.ofEpochSecond(wr.getList().get(i).getDt()).atZone(ZoneId.of("UTC")) + "\t");
			sb.append(wr.getList().get(i).getWeather().stream().map(f -> f.getMain()).collect(Collectors.joining(", "))
					+ "\n");
		}
		return sb.toString();
	}
}
