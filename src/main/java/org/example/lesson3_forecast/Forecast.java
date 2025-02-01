package org.example.lesson3_forecast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

class Forecast {
    static final String apiKey="331de9ad471a43fbb1c193748252801";

    public static void main(String[] args) {
        Forecast forecast= new Forecast();
        String city;
        String urlString;

        try(Scanner sc = new Scanner(System.in)) {
            System.out.print("Введите название города.   ");

            while(sc.hasNextLine()) {
                city = sc.nextLine();
                urlString = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + city;
                String response;

                try {
                    response = forecast.getResponse(urlString).get();

                    JSONObject jObject = new JSONObject(response);

                    double temp=forecast.getTemperature(jObject);
                    System.out.printf("Температура в %s %d°C. ",city,Math.round(temp));
                    System.out.printf("%s.\n", forecast.getWeatherDescription(jObject));

                } catch (NoSuchElementException ex) {
                    System.err.println("Город не найден.");
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
//                jObject.getJSONArray("condition").getJSONObject(0).getString("description");
    }

}


