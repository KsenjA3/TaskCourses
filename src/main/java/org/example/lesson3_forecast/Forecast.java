package org.example.lesson3_forecast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Forecast {
    public static void main(String[] args) {
        Forecast forecast= new Forecast();
        String apiKey="331de9ad471a43fbb1c193748252801";
        String city;
        String urlString;
        try(Scanner sc = new Scanner(System.in)) {
            System.out.print("Введите название города.   ");
            while(sc.hasNextLine()) {
                city = sc.nextLine();
                urlString = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + city;

                if (forecast.getResponse(urlString).isEmpty())
                    System.out.println("Город не найден.");
                else  {
                    double temp=forecast.parsingResponse(forecast.getResponse(urlString).get());
                    System.out.printf("Температура в %s %d°C.\n",city,Math.round(temp));
                }
                System.out.print("Введите название города.   ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Optional<String> getResponse(String urlString) throws MalformedURLException, IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
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

    private Double parsingResponse(String response) {
        JSONObject jObject = new JSONObject(response);
        Double tempC=jObject.getJSONObject("current").getDouble("temp_c");
        return tempC;
    }

}


