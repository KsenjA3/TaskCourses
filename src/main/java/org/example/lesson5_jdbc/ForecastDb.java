package org.example.lesson5_jdbc;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.sql.*;

public class ForecastDb {
    public static String apiKey = "331de9ad471a43fbb1c193748252801";
    public static String apiUrl = "http://api.weatherapi.com";
    public static String URL = "jdbc:postgresql://localhost:5432/forecast_db";
    public static String USER = "myuser";
    public static String PASSWORD = "11111";
    private static final String COMMAND = """
                                            INSERT INTO weather(id, city, description, temperature, time) 
                                            VALUES (?, ?, ?, ?, ?) ; 
                                             """;
    private static final String COMMAND_FIND_MAX_ID = "SELECT MAX(id) as max FROM weather;";

    public static void main(String[] args) {
        String urlString;
        Date date;
        String city;
        int temperature;
        String description;
        int id = 0;

        ForecastDb forecast = new ForecastDb();

        try (Scanner sc = new Scanner(System.in);
             Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = con.createStatement()) {

            //find max id  to update DB
            ResultSet rs = statement.executeQuery(COMMAND_FIND_MAX_ID);
            if (rs.next()) id = rs.getInt("max");

            System.out.print("Введите название города.   ");

            while (sc.hasNextLine()) {
                city = sc.nextLine();
                urlString = apiUrl + "/v1/current.json?key=" + apiKey + "&q=" + city;
                String response;

                try {
                    response = forecast.getResponse(urlString).get();
                    JSONObject jObject = new JSONObject(response);

                    double temp = forecast.getTemperature(jObject);
                    temperature = (int) Math.round(temp);
                    description = forecast.getWeatherDescription(jObject);
                    date = Date.valueOf(LocalDate.now());
                    id++;

                    forecast.saveWeatherToDatabase(con, id, city, description, temperature, date);
                    forecast.printWeatherByCity(con, city);

                } catch (NoSuchElementException ex) {
                    System.err.println("Город не найден.");
                    Thread.sleep(30);
                } finally {
                    System.out.print("Введите название города.   ");
                }
            }

        } catch (SQLException e) {
            System.err.println("Ошибка работы с PostgreSQL.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected Optional<String> getResponse(String urlString) throws MalformedURLException, IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), Charset.forName("utf-8")))) {
                return Optional.of(reader.lines().collect(Collectors.joining(System.lineSeparator())));
            }
        }
        return Optional.empty();
    }

    protected Double getTemperature(JSONObject jObject) {
        Double tempC = jObject.getJSONObject("current").getDouble("temp_c");
        return tempC;
    }

    protected String getWeatherDescription(JSONObject jObject) {
        return jObject.getJSONObject("current").getJSONObject("condition").getString("text");
    }

    protected int printWeatherByCity(Connection connection, String city) {
        String query = "SELECT temperature, description FROM weather WHERE city = ? ORDER BY time DESC LIMIT 1";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, city);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int temperature = rs.getInt("temperature");
                    String description = rs.getString("description");
                    System.out.printf("Температура в %s %d°C. %s.\n", city, temperature, description);
                    return temperature;
                } else {
                    System.out.println("Записей для города " + city + " не найдено.");
                }

            }

        } catch (SQLException e) {
            System.err.println("Ошибка при получении данных из базы:");
            e.printStackTrace();
        }
        return -999999999;
    }

    protected void saveWeatherToDatabase(Connection connection, int id, String city, String description, int temperature, Date date) throws SQLException {
        String sql = "INSERT INTO weather(id, city, description, temperature, time) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, city);
            preparedStatement.setString(3, description);
            preparedStatement.setInt(4, temperature);
            preparedStatement.setDate(5, date);

            preparedStatement.executeUpdate();
        }
    }

}
