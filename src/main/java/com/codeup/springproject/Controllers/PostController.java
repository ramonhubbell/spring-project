package com.codeup.springproject.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    @GetMapping ("/posts")
    @ResponseBody
    public String index() {
        return "Posts index page";
    }

    @GetMapping("/posts/{id}")
    @ResponseBody
    public String postId(@PathVariable long id) {
        return "View post number " + id;
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String viewPost(){
        return "View the form for creating a post.";
    }

//    @RequestMapping(path = "/posts/create", method = RequestMethod.POST)
    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost() {
        return "Create a new post.";
    }

}
