package com.codeup.springproject.Controllers;

import com.codeup.springproject.models.Combination;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Controller
public class PizzaCombinationController {

    @GetMapping("/combinations")
    public String viewCombinations(Model model) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create("https://www.olo.com/pizzas.json"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//        Parse JSON
        ObjectMapper mapper = new ObjectMapper();
        List<Combination> combinations = mapper.readValue(response.body(), new TypeReference<List<Combination>>() {});
        Map<List<String>, Integer> combinationAndCount = new HashMap<>();
        for (int i = 0; i < combinations.size(); i += 1) {
            Integer count = combinationAndCount.get(combinations.get(i).getToppings());
            if (count == null) {
                combinationAndCount.put(combinations.get(i).getToppings(), 1);
            } else { combinationAndCount.put(combinations.get(i).getToppings(), ++count);
            }
        }
        LinkedHashMap<List<String>, Integer> reverseSortedMap = new LinkedHashMap<>();
        combinationAndCount.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(20)
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        model.addAttribute("combinations", reverseSortedMap);
        return "combinations";
    }
}
