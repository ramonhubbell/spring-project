package com.codeup.springproject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class App {

    public static final String POSTS_API_URL = "https://www.olo.com/pizzas.json";

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(POSTS_API_URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        List<Combination> combinations = mapper.readValue(response.body(), new TypeReference<List<Combination>>() {});
        Map<List<String>, Integer> combinationAndCount = new HashMap<>();
        for (int i = 0; i < combinations.size(); i += 1) {
//            System.out.println(combinations.get(i).getToppings());
            Integer count = combinationAndCount.get(combinations.get(i).getToppings());
            if (count == null) {
                combinationAndCount.put(combinations.get(i).getToppings(), 1);
            } else { combinationAndCount.put(combinations.get(i).getToppings(), ++count);
            }
        }

        System.out.println("combinationAndCount: " + combinationAndCount);

        LinkedHashMap<List<String>, Integer> reverseSortedMap = new LinkedHashMap<>();
        combinationAndCount.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(20)
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        System.out.printf("Reverse Sorted Map: %s \n", reverseSortedMap);

    }
}



