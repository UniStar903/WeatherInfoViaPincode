package com.freight.fox.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "forecast")
@Data
public class Forecast {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonProperty("dt")
	private Long dt;

	@ManyToOne
	@JoinColumn(name = "weather_response_id")
	private WeatherResponse weatherResponse;

	@OneToMany(mappedBy = "forecast", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonProperty("weather")
	private List<Weather> weather;
}
