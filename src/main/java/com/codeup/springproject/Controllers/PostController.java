package com.codeup.springproject.Controllers;

import com.codeup.springproject.models.Post;
import com.codeup.springproject.daos.PostsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    private PostsRepository postsDao;
    public PostController(PostsRepository postsRepository) {
        postsDao = postsRepository;
    }

    @GetMapping ("/posts")
    public String index(Model model) {
        List<Post> postsList = postsDao.findAll();
        model.addAttribute("noPostsFound", postsList.size() == 0);
        model.addAttribute("posts", postsList);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String postId(@PathVariable long id, Model model) {
        Post testPost = new Post("new Title","New body.");
//        Post testPost = null;
        boolean postPresent = testPost != null;
        model.addAttribute("postPresent", postPresent);
        model.addAttribute("postId", id);
        model.addAttribute("post", testPost);
        System.out.println(id + "this should be the id.");
//        System.out.println(testPost.getTitle());
        return "posts/show";
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
