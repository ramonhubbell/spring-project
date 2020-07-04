package com.codeup.springproject.Controllers;

import com.codeup.springproject.daos.UsersRepository;
import com.codeup.springproject.models.Post;
import com.codeup.springproject.daos.PostsRepository;
import com.codeup.springproject.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.codeup.springproject.services.EmailService;

import java.util.List;

@Controller
public class PostController {

    private PostsRepository postsDao;
    private UsersRepository usersDao;
    private final EmailService emailService;

    public PostController(PostsRepository postsRepository, UsersRepository usersRepository, EmailService emailService) {
        this.postsDao = postsRepository;
        this.usersDao = usersRepository;
        this.emailService = emailService;
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
        return "/posts/show";
    }

    @GetMapping("/posts/create")
    public String viewPost(Model viewModel){
        viewModel.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post postToBeSaved) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postToBeSaved.setUser(currentUser);
        Post savedPost = postsDao.save(postToBeSaved);
        emailService.prepareAndSend(savedPost, "A new post has been created.", "A post has been created with post id " + savedPost.getId());
        return "redirect:/posts/" + savedPost.getId();
    }

    @GetMapping("/posts/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model){
//        Find the post
        Post postToEdit = postsDao.getOne(id);
        model.addAttribute("post", postToEdit);
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String update(@ModelAttribute Post postToEdit){
//    Save the changes
        User currentUser = usersDao.getOne(1L);
        postToEdit.setUser(currentUser);
        postsDao.save(postToEdit);
    return "redirect:/posts/" + postToEdit.getId();
    }

    @PostMapping("/posts/{id}/delete")
    public String destroy(@PathVariable long id){
        postsDao.deleteById(id);
        return "redirect:/posts";
    }

}
