# Weather Info Backend Assignment

## Project Overview
This project provides a **REST API** to fetch weather information for a specific **Pincode** on a given **date**. The API is optimized to minimize external API calls by storing previously retrieved data in a relational database (RDBMS).

The workflow includes:
1. Convert **Pincode → Latitude/Longitude** using Google Maps or OpenWeather Geocoding API.
2. Retrieve **Weather Information → Latitude/Longitude** using OpenWeather API.
3. Store the retrieved data in the database.
4. Optimize subsequent API calls by fetching data from the database if already available.

---

## Features
- **Single REST API** for fetching weather information.
- **Database Storage** for:
  - Pincode with latitude and longitude.
  - Weather information for the pincode on a particular date.
- **Optimized API Calls** to reduce redundant calls.
- Fully **testable via Postman or Swagger**.
- **Code structured properly** following best practices.
- **Test-Driven Development (TDD)** with unit tests.

---

## API Details

### Endpoint
```

POST /api/weather

````

### Request through Postman

http://localhost:8080/api/weather?pincode=411026&for_date=2020-10-15

### Response Example

```String
date hour  weather info
```

---

## External APIs Used

* [OpenWeatherMap API](https://openweathermap.org/current) – for weather data.
* [OpenWeather Geocoding API](https://openweathermap.org/api/geocoding-api) – for converting pincode to latitude/longitude.

---

## How It Works

1. When a request is received, the system checks if the **pincode and date** exist in the database.
2. If not found:

   * Converts the **pincode → latitude/longitude**.
   * Fetches **weather information** for that location.
   * Saves both **pincode coordinates** and **weather info** in the database.
3. If found:

   * Returns **stored weather information** directly, reducing API calls.

---

## Technologies Used

* Java 17
* Spring Boot
* Maven
* MySQL (RDBMS)
* Postman for API testing

---

## Running the Project

1. Clone the repository:

```bash
git clone <repo-url>
cd weather-backend
```

2. Configure `application.properties` with your database credentials and API keys:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/weatherdb
spring.datasource.username=root
spring.datasource.password=root
openweathermap.api.key=<YOUR_API_KEY>
```

3. Build and run the application:

```bash
mvn clean install
mvn spring-boot:run
```

4. Access the API at:

```
http://localhost:8080/api/weather
```


## Notes

* No UI is implemented; this is a backend-only REST API project.
* API calls are optimized to reduce unnecessary requests to external services.
* Code is structured and modular for easy maintenance.

---

## Author

Shardul Lote
