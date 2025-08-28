package com.freight.fox.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.freight.fox.entity.WeatherResponse;

public interface WeatherResponseRepo extends JpaRepository<WeatherResponse, Long>{
	@Query(nativeQuery = true,value = "SELECT * FROM weather_response w WHERE w.zip = :pincode AND w.for_date = :forDate")
	WeatherResponse findByPincodeAndForDate(@Param("pincode") String pincode,@Param("forDate") Date forDate);
}
