package com.shahin.restfulwebservices.service;

import com.shahin.restfulwebservices.controllers.PostModel;
import com.shahin.restfulwebservices.exception.PostNotFoundException;
import com.shahin.restfulwebservices.models.Post;
import com.shahin.restfulwebservices.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(Integer id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()){
            throw new PostNotFoundException("{id} : "+id);
        }else{
            return post.get();
        }
    }

    @Override
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post save(PostModel postModel) {
        Post post = mapPostModelToPost(postModel);
        return this.save(post);
    }

    private Post mapPostModelToPost(PostModel postModel) {
        Post post = new Post();
        post.setDescription(postModel.getDescription());
        post.setUser(postModel.getUser());
        return post;
    }
}
