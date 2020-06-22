package com.codeup.springproject.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RollDiceController {

@GetMapping("/roll-dice")
public String showNumbers() {
    return "diceRoll";
}

@GetMapping("/roll-dice/{n}")
    public String rollDice(@PathVariable String n, Model model) {
    model.addAttribute("n", n);
    return "diceRoll";
}

@PostMapping("/roll-dice/{n}")
    public String randomNumber(@PathVariable String n, Model model) {
    int randomNumber = (int) Math.floor(Math.random() * 7);
    model.addAttribute("randomNumber", randomNumber);
    return "diceRoll";
}

}
