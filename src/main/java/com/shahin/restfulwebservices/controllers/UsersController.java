package com.shahin.restfulwebservices.controllers;

import com.shahin.restfulwebservices.models.Post;
import com.shahin.restfulwebservices.models.User;
import com.shahin.restfulwebservices.service.PostService;
import com.shahin.restfulwebservices.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PostService postService;

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('student:read')")
    @ApiOperation(value = "retrieveAllUsers", authorizations = { @Authorization(value="jwtToken") })
    public List<User> retrieveAllUsers() {
        return userService.getUsers();
    }

    @Operation(summary = "Get a User By Its Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found The Book",
                    content = {@Content(mediaType = "application/xml",
                            schema = @Schema(implementation = User.class)),
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @GetMapping("users/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public EntityModel<User> retrieveUser(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        EntityModel<User> userEntityModel = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        userEntityModel.add(linkTo.withRel("all-users"));
        return userEntityModel;
    }
    @ApiResponses(value = @ApiResponse(responseCode = "201" , description = "Post Created",
    content = {@Content(mediaType="application/json",
    schema = @Schema(implementation = Post.class)),
    @Content(mediaType = "application/xml",
    schema = @Schema(implementation = Post.class))}))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Both Json And Xml Supported"
            ,content ={@Content(mediaType="application/json",
            schema = @Schema(implementation = User.class)),
            @Content(mediaType = "application/xml",
                    schema = @Schema(implementation = User.class))} )
    @PostMapping("/users/{id}/posts")
    @PreAuthorize("hasAnyAuthority('post:write')")
    public ResponseEntity<Object> createPost(@PathVariable Integer id,@Valid @RequestBody PostModel postModel) {
        User user = userService.getUserById(id);
        postModel.setUser(user);
        Post post = postService.save(postModel);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @ApiResponses(value = @ApiResponse(responseCode = "201" , description = "User Created",
            content = {@Content(mediaType="application/json",
                    schema = @Schema(implementation = User.class)),
                    @Content(mediaType = "application/xml",
                            schema = @Schema(implementation = User.class))}))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Both Json And Xml Supported"
            ,content ={@Content(mediaType="application/json",
            schema = @Schema(implementation = User.class)),
            @Content(mediaType = "application/xml",
                    schema = @Schema(implementation = User.class))} )
    @PostMapping("/users")
    @PreAuthorize("hasAnyAuthority('student:write')")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserModel userModel) {
        User savedUser = userService.save(userModel);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('student:write')")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized() {
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }

    @GetMapping(path="users/{id}/posts")
    @PreAuthorize("hasAnyAuthority('post:read')")
    public List<Post> getUserPosts(@PathVariable Integer id){
        User user = userService.getUserById(id);
        return user.getPosts();
    }

    @GetMapping("posts/{id}")
    @PreAuthorize("hasAnyAuthority('post:read')")
    public EntityModel<Post> retrievePost(@PathVariable("id") Integer id){
        Post post = postService.getPostById(id);
        EntityModel<Post> postEntityModel = EntityModel.of(post);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllPosts());
        postEntityModel.add(linkTo.withRel("get-all-posts"));
        return postEntityModel;
    }
    @GetMapping("posts")
    @PreAuthorize("hasAnyAuthority('post:read')")
    public List<Post> retrieveAllPosts(){
        return postService.getPosts();
    }


}
