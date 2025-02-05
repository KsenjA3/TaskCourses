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
    private  static final String apiKey="331de9ad471a43fbb1c193748252801";
    private  static final  String URL =  "jdbc:postgresql://localhost:5432/forecast_db";
    private  static final  String USER =  "myuser";
    private  static final  String PASSWORD =  "11111";
    private  static final  String COMMAND = """
                    INSERT INTO weather(id, city, description, temperature, time) VALUES
                    (?, ?, ?, ?, ?) ; 
                     """;

        public static void main(String[] args) {
            String urlString;
            Date date;
            String city;
            int temperature;
            String description;
            int id;

            ForecastDb forecast= new ForecastDb();

            try(Scanner sc = new Scanner(System.in) ;
                Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = con.prepareStatement(COMMAND)) {
//выбрать max id
                id =5;
                System.out.print("Введите название города.   ");

                while(sc.hasNextLine()) {
                    city = sc.nextLine();
                    urlString = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + city;
                    String response;

                    try {
                        response = forecast.getResponse(urlString).get();
                        JSONObject jObject = new JSONObject(response);

                        double temp=forecast.getTemperature(jObject);
                        temperature= (int) Math.round(temp);
                        description=forecast.getWeatherDescription(jObject);
                        date = Date.valueOf(LocalDate.now());
                        id++;

                        preparedStatement.setInt(1,id);
                        preparedStatement.setString(2,city);
                        preparedStatement.setString(3,description);
                        preparedStatement.setInt(4,temperature);
                        preparedStatement.setDate(5,date);

                        preparedStatement.executeUpdate();

                        System.out.printf("Температура в %s %d°C. ",city,temperature);
                        System.out.printf("%s.\n",description );

                    } catch (NoSuchElementException ex) {
                        System.err.println("Город не найден.");
                    }
                    System.out.print("Введите название города.   ");
                }

            } catch (SQLException e) {
                System.err.println("Ошибка работы с PostgreSQL.");
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

        private Optional<String> getResponse(String urlString) throws MalformedURLException, IOException {
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

        private Double getTemperature(JSONObject jObject) {
            Double tempC=jObject.getJSONObject("current").getDouble("temp_c");
            return tempC;
        }

        private String getWeatherDescription(JSONObject jObject) {
            return  jObject.getJSONObject("current").getJSONObject("condition").getString("text");
        }

}



