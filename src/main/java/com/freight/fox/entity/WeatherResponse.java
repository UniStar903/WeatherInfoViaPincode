package com.freight.fox.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "weather_response")
@Data
public class WeatherResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonProperty("zip")
	private String zip;

	@JsonProperty("lat")
	private Double lat;

	@JsonProperty("lon")
	private Double lon;

	@JsonProperty("forDate")
	private String forDate;

	@JsonProperty("cnt")
	private int cnt;

	@JsonProperty("list")
	@OneToMany(mappedBy = "weatherResponse", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Forecast> list;
}
