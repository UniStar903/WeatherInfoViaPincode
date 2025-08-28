package com.freight.fox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freight.fox.entity.Weather;

public interface WeatherWeatherRepo extends JpaRepository<Weather, Long> {

}
