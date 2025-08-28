package com.freight.fox.service;

import com.freight.fox.entity.WeatherResponse;

public interface WeatherService {
	public WeatherResponse postalCodeAndDateToSetLatLongAndSave(String code, String date);

	public WeatherResponse latLongToWeatherResponsermation(Double lat, Double lon, String date);
}
