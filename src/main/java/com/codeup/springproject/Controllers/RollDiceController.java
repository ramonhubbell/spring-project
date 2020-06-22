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

//@PostMapping("/roll-dice/${number}")
//    public String rollDice(@RequestParam int number, Model model) {
//    model.addAttribute("number", number);
//    return "diceRoll";
//
//}

}
