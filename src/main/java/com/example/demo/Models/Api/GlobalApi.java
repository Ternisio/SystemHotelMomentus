package com.example.demo.Models.Api;

import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GlobalApi {


    public HttpResponse<String> Api(HttpClient client, String json){
        Dotenv dotenv = Dotenv.load();
        String Url_Api = dotenv.get("API_URL");
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(Url_Api))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpResponse<String> ApiS(HttpClient client, String json){
        Dotenv dotenv = Dotenv.load();
        String Url_Api = dotenv.get("API_URL");
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(Url_Api))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
