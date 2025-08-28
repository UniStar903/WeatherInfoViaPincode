package com.freight.fox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freight.fox.entity.Forecast;

public interface WeatherForecastRepo extends JpaRepository<Forecast, Long> {

}
