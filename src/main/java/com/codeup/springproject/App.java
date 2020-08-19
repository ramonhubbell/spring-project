package com.codeup.springproject;

import com.codeup.springproject.models.Combination;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class App {

    // function to sort hashmap by values
    public static HashMap<List<String>, Integer> sortByValue(HashMap<List<String>, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<List<String>, Integer> > list = new LinkedList<Map.Entry<List<String>, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<List<String>, Integer>>() {
            public int compare(Map.Entry<List<String>, Integer> o1,
                               Map.Entry<List<String>, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<List<String>, Integer> temp = new LinkedHashMap<List<String>, Integer>();
        for (Map.Entry<List<String>, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
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
//        System.out.println(response.body());
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
//        System.out.println(combinationAndCount);
//        System.out.println(sortByValue(combinationAndCount));

//        for ( int i = 0; i < 20; i +=1 ){
//            System.out.println(sortByValue(combinationAndCount).entrySet() + "rank: " + i);
//        }
//        LinkedHashMap<List<String>, Integer> reverseSortedMap = new LinkedHashMap<>();
//        Set<List<String>> keys = reverseSortedMap.keySet();
//        List<List<String>> listKeys = new LinkedList<List<String>>(keys);
//        System.out.println(listKeys.indexOf(0));
        int i = 0;
        for (Map.Entry<List<String>, Integer> toppings : sortByValue(combinationAndCount).entrySet()) {
            System.out.println(toppings.getKey().toString().substring(1, )  + " is #" + (i + 1)
                    + ", with " + toppings.getValue() + " orders.");
            i++;
        }
//        for (int i = 0 ; i < sortByValue(combinationAndCount).size(); i += 1){
//            System.out.println("Rank: " + (i + 1) + " Toppings: " + sortByValue(combinationAndCount).keySet());
//        }
//        LinkedHashMap<List<String>, Integer> reverseSortedMap = new LinkedHashMap<>();
//        combinationAndCount.entrySet()
//                            .stream()
//                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                            .limit(20)
//                            .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
//        for(int i =0; i < reverseSortedMap.size(); i += 1){
//            System.out.println("Rank: " + (i + 1) + "  Topping Combinations: " + reverseSortedMap.values());

//        }
//        System.out.println(reverseSortedMap);
//        String stringReverseSortedMap = reverseSortedMap.toString();
//        System.out.println(stringReverseSortedMap.substring(1, stringReverseSortedMap.length() -1));
    }
}




