package com.codeup.springproject.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MathController {

    @GetMapping(path = "/add/{x}/and/{y}")
    @ResponseBody
    public String add(@PathVariable long x, @PathVariable long y) {
        long sum = x + y;
        return "The answer is " + sum;
    }

    @GetMapping ("/subtract/{y}/from/{x}")
    @ResponseBody
    public String subtract(@PathVariable long x, @PathVariable long y) {
        long difference = x - y;
        return "The difference is " + difference;
    }

    @GetMapping ("/multiply/{x}/and/{y}")
    @ResponseBody
    public String multiply(@PathVariable long x, @PathVariable long y) {
        long product = x * y;
        return "The product is " + product;
    }

    @GetMapping ("/divide/{x}/by/{y}")
    @ResponseBody
    public String divide(@PathVariable long x, @PathVariable long y) {
        long quotient = x/y;
        return "The quotient is " + quotient;
    }


    @GetMapping("/ads/{id}")
    @ResponseBody
    public String show(@PathVariable long id){
        return "show ad id: " + id;
    }

}
