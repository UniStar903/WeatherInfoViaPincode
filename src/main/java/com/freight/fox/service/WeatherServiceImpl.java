package com.freight.fox.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.freight.fox.entity.Forecast;
import com.freight.fox.entity.Weather;
import com.freight.fox.entity.WeatherResponse;
import com.freight.fox.repository.WeatherForecastRepo;
import com.freight.fox.repository.WeatherResponseRepo;
import com.freight.fox.repository.WeatherWeatherRepo;

@Service
public class WeatherServiceImpl implements WeatherService {

	@Value("${open.weather.completion.base.information.url}")
	private String InfoURL;

	@Value("${open.weather.completion.base.geocoding.url}")
	private String geoCURL;

	@Value("${open.weather.api.key}")
	private String key;

	@Autowired
	WeatherResponseRepo weatherRepo;

	@Autowired
	WeatherWeatherRepo wRepo;

	@Autowired
	WeatherForecastRepo forcastRepo;

	@Autowired
	RestTemplate restTemp;

	@Override
	public WeatherResponse postalCodeAndDateToSetLatLongAndSave(String code, String date) {
		WeatherResponse existing = weatherRepo.findByPincodeAndForDate(code, Date.valueOf(date));
		if (existing != null) {
			return existing;
		}

		// Get lat/lon from geocoding API
		String geoUrl = geoCURL + "zip=" + code + ",IN&appid=" + key;
		System.out.println(geoUrl);
		ResponseEntity<WeatherResponse> geoResp = restTemp.exchange(geoUrl, HttpMethod.GET, null,
				WeatherResponse.class);
		WeatherResponse geoBody = geoResp.getBody();

		// Save basic WeatherResponse info
		WeatherResponse entity = new WeatherResponse();
		entity.setZip(code);
		entity.setForDate(date);
		entity.setLat(geoBody.getLat());
		entity.setLon(geoBody.getLon());
		weatherRepo.save(entity);

		// Call weather API to get full forecast data
		WeatherResponse fullData = latLongToWeatherResponsermation(geoBody.getLat(), geoBody.getLon(), date);

		if (fullData.getList() != null) {
			for (Forecast f : fullData.getList()) {

				// Save Forecast
				Forecast forecast = new Forecast();
				forecast.setDt(f.getDt());
				forecast.setWeatherResponse(entity);
				forcastRepo.save(forecast);

				// Save Weather list
				if (f.getWeather() != null && !f.getWeather().isEmpty()) {
					for (Weather w : f.getWeather()) {
						Weather weatherEntity = new Weather();
						weatherEntity.setMain(w.getMain());
						weatherEntity.setDescription(w.getDescription());
						weatherEntity.setForecast(forecast);
						wRepo.save(weatherEntity);
					}
				}
			}
		}

		entity.setCnt(fullData.getCnt());
		weatherRepo.save(entity);

		return fullData;

	}

	@Override
	public WeatherResponse latLongToWeatherResponsermation(Double lat, Double lon, String date) {
		LocalDate lDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
		String Url = InfoURL + "lat=" + lat + "&lon=" + lon + "&type=hour" +
				"&start="+
				lDate.atStartOfDay(ZoneOffset.UTC).toInstant().getEpochSecond()+"&end="+
				lDate.atTime(LocalTime.MAX).atZone(ZoneOffset.UTC).toInstant().getEpochSecond()+
				"&appid=" + key;
		System.out.println(Url);
		ResponseEntity<WeatherResponse> response = restTemp.exchange(
				// lat={lat}&lon={lon}&type=hour&start={start}&end={end}&appid={API key}
				Url, HttpMethod.GET, null, WeatherResponse.class);

		return response.getBody();
	}

}
