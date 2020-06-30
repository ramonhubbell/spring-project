package com.codeup.springproject.Controllers;

import com.codeup.springproject.daos.UsersRepository;
import com.codeup.springproject.models.Post;
import com.codeup.springproject.daos.PostsRepository;
import com.codeup.springproject.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    private PostsRepository postsDao;
    private UsersRepository usersDao;
    public PostController(PostsRepository postsRepository, UsersRepository usersRepository) {
        postsDao = postsRepository;
        usersDao = usersRepository;
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
        Post post = postsDao.getOne(id);
        boolean postPresent = post != null;
        model.addAttribute("postPresent", postPresent);
        model.addAttribute("postId", id);
        model.addAttribute("post", post);
        System.out.println(id + " this should be the id.");
        return "/posts/show";
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String viewPost(){
        return "View the form for creating a post.";
    }

    @PostMapping("/posts/create")
    public String createPost() {
        User currentUser = usersDao.getOne(1L);
        Post newPost = new Post("Newer Title", "newer blog.", null);
        postsDao.save(newPost);
        return "redirect:/posts/" + newPost.getId();
    }

    @GetMapping("/posts/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model){
//        Find the post
        Post postToEdit = postsDao.getOne(id);
        model.addAttribute("post", postToEdit);
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String update(@PathVariable long id,
                         @RequestParam(name = "title") String title,
                         @RequestParam(name = "body") String body){
//    find a post
    Post foundPost = postsDao.getOne(id);
//    edit the post
    foundPost.setTitle(title);
    foundPost.setBody(body);
//    Save the changes
    postsDao.save(foundPost);
        System.out.println(foundPost.getTitle());
    return "redirect:/posts/" + foundPost.getId();
    }

    @PostMapping("/posts/{id}/delete")
    public String destroy(@PathVariable long id){
        postsDao.deleteById(id);
        return "redirect:/posts";
    }

}
