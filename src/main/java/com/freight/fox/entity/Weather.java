package com.freight.fox.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "weather")
@Data
public class Weather {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonProperty("main")
	private String main;

	@JsonProperty("description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "forecast_id")
	private Forecast forecast;
}
