package org.example.lesson4_forecast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

class Forecast {
    public static String token;
        String apiKey="331de9ad471a43fbb1c193748252801";

    public static void main(String[] args) {
        Forecast forecast= new Forecast();
        String city;

        try(Scanner sc = new Scanner(System.in)) {
            System.out.print("Введите название города.   ");
            while(sc.hasNextLine()) {
                city = sc.nextLine();

                try {
                    String response = forecast.getWeather(city);

                    JSONObject jObject = new JSONObject(response);

                    double temp=forecast.getTemperature(jObject);
                    System.out.printf("Температура в %s %d°C. ",city,Math.round(temp));
                    System.out.printf("%s.\n", forecast.getWeatherDescription(jObject));

                } catch (IOException e) {
                    System.err.println("Ошибка ввода-вывода данных: " + e.getMessage());
                } catch (InterruptedException e) {
                    System.err.println("Ошибка прерывания: " + e.getMessage());
                } catch (ResponseException e) {
                    System.err.println("Город не найден. "+e.getMessage());
                } catch (Exception e) {
                    System.err.println("Ошибка выполнения программы: " + e.getMessage());
                }

                System.out.print("Введите название города.   ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     String getWeather(String city) throws IOException, InterruptedException, ResponseException {
        String urlString = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + city;

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(urlString))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) return response.body();
        else throw new ResponseException("Ошибка HTTP: " + response.statusCode() + ": " + response.body());
    }

    private Double getTemperature(JSONObject jObject) {
        Double tempC=jObject.getJSONObject("current").getDouble("temp_c");
        return tempC;
    }

    private String getWeatherDescription(JSONObject jObject) {
        return  jObject.getJSONObject("current").getJSONObject("condition").getString("text");
    }
}