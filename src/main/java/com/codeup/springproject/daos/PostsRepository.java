package com.codeup.springproject.daos;

import com.codeup.springproject.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Post, Long> {

}
