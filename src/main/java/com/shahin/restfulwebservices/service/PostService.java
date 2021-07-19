package com.shahin.restfulwebservices.service;

import com.shahin.restfulwebservices.models.Post;
import com.shahin.restfulwebservices.models.User;

import java.util.List;

public interface PostService {
    void deletePost(Integer id);
    Post save(Post post);
    Post getPostById(Integer id);
    List<Post> getPosts();
}
