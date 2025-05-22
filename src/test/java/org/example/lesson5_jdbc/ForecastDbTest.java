package org.example.lesson5_jdbc;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.sql.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@WireMockTest(httpPort = 8080)
public class ForecastDbTest {

    private ForecastDb forecastDb;
    private Connection connection;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        forecastDb = new ForecastDb();
        ForecastDb.apiUrl = "http://localhost:8080"; // Переопределяем URL для использования WireMock
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
        
        // Создаем таблицу weather для тестов
        try (PreparedStatement stmt = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS weather (id INT PRIMARY KEY, city VARCHAR(255), description VARCHAR(255), temperature INT, time DATE)")) {
            stmt.execute();
        }
        
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        System.setOut(originalOut);
    }


    @Test
    void testWeatherRecordAndRetrieval() throws Exception {
        int expectedTemperature = 25;

        String city = "London";
        String description = "Sunny";
        double temperature = 25.3;

        JSONObject jsonBody = new JSONObject();
        JSONObject location = new JSONObject();
        location.put("name", city);
        location.put("region", "City of London, Greater London");
        location.put("country", "United Kingdom");
        location.put("lat", 51.5171);
        location.put("lon", -0.1062);
        location.put("tz_id", "Europe/London");
        location.put("localtime_epoch", 1747809715);
        location.put("localtime", "2025-05-21 07:41");
        jsonBody.put("location", location);

        JSONObject current = new JSONObject();
        current.put("last_updated_epoch", 1747809000);
        current.put("last_updated", "2025-05-21 07:30");
        current.put("temp_c", temperature);
        current.put("temp_f", 53.8);
        current.put("is_day", 1);

        JSONObject condition = new JSONObject();
        condition.put("text", description);
        condition.put("icon", "//cdn.weatherapi.com/weather/64x64/day/296.png");
        condition.put("code", 1183);
        current.put("condition", condition);

        current.put("wind_mph", 3.1);
        current.put("wind_kph", 5.0);
        current.put("wind_degree", 40);
        current.put("wind_dir", "NE");
        current.put("pressure_mb", 1017.0);
        current.put("pressure_in", 30.03);
        current.put("precip_mm", 0.0);
        current.put("precip_in", 0.0);
        current.put("humidity", 94);
        current.put("cloud", 75);
        current.put("feelslike_c", 12.1);
        current.put("feelslike_f", 53.8);
        current.put("windchill_c", 13.5);
        current.put("windchill_f", 56.3);
        current.put("heatindex_c", 13.3);
        current.put("heatindex_f", 56.0);
        current.put("dewpoint_c", 5.9);
        current.put("dewpoint_f", 42.6);
        current.put("vis_km", 10.0);
        current.put("vis_miles", 6.0);
        current.put("uv", 0.1);
        current.put("gust_mph", 4.1);
        current.put("gust_kph", 6.6);

        jsonBody.put("current", current);

        String body = jsonBody.toString();

        // Настройка мок-ответа от API
        stubFor(get(urlPathEqualTo("/v1/current.json"))
                .withQueryParam("key", equalTo(ForecastDb.apiKey))
                .withQueryParam("q", equalTo(city))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(body)
                ));


        String urlString = ForecastDb.apiUrl + "/v1/current.json?key=" + ForecastDb.apiKey + "&q=" + city;
        String response = forecastDb.getResponse(urlString).get();
        JSONObject jObject = new JSONObject(response);

            double temp = forecastDb.getTemperature(jObject);
            int temperatureGet = (int) Math.round(temp);
            String descriptionGet = forecastDb.getWeatherDescription(jObject);
            Date dateGet = Date.valueOf(LocalDate.now());
            int id=1;

            forecastDb.saveWeatherToDatabase(connection, id, city, descriptionGet, temperatureGet, dateGet);
            forecastDb.printWeatherByCity(connection, city);

        assertEquals(temperatureGet, expectedTemperature);
        assertEquals(descriptionGet, description);

        // Проверка, что данные были корректно записаны и получены
        int retrievedTemperature = forecastDb.printWeatherByCity(connection, city);

        assertEquals(expectedTemperature, retrievedTemperature);
        assertTrue(outContent.toString().contains("Температура в " + city + " " + expectedTemperature + "°C. Sunny."));
    }
}