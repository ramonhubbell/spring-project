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

    public static class Combination {

        private List<String> toppings;

        public List<String> getToppings() {
            return toppings;
        }

        public void setToppings(List<String> toppings) {
            this.toppings = toppings;
        }

    }

    // function to sort hashmap by values
    public static HashMap<List<String>, Integer> sortByValue(HashMap<List<String>, Integer> hashMap)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<List<String>, Integer> > list = new LinkedList<Map.Entry<List<String>, Integer> >(hashMap.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<List<String>, Integer>>() {
            public int compare(Map.Entry<List<String>, Integer> o1,
                               Map.Entry<List<String>, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<List<String>, Integer> sortedMap = new LinkedHashMap<List<String>, Integer>();
        for (Map.Entry<List<String>, Integer> item : list) {
            sortedMap.put(item.getKey(), item.getValue());
        }
        return sortedMap;
    }

    public static final String POSTS_API_URL = "https://www.olo.com/pizzas.json";

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(POSTS_API_URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//        Parsed JSON.
        ObjectMapper mapper = new ObjectMapper();
        List<Combination> combinations = mapper.readValue(response.body(), new TypeReference<List<Combination>>() {});

        HashMap<List<String>, Integer> combinationAndCount = new HashMap<>();
        for (int i = 0; i < combinations.size(); i += 1) {
            Integer count = combinationAndCount.get(combinations.get(i).getToppings());
            if (count == null) {
                combinationAndCount.put(combinations.get(i).getToppings(), 1);
            } else { combinationAndCount.put(combinations.get(i).getToppings(), ++count);
            }
        }

        int i = 0;
        for (Map.Entry<List<String>, Integer> toppings : sortByValue(combinationAndCount).entrySet()) {
            if ( i < 20) {
                System.out.println(toppings.getKey().toString().substring(1, toppings.getKey().toString().length() - 1)  + " is #" + (i + 1)
                        + ", with " + toppings.getValue() + " orders.");
            }
            i++;
        }
    }
}




