package com.shahin.restfulwebservices.controllers;

import com.shahin.restfulwebservices.dao.UserDao;
import com.shahin.restfulwebservices.exception.UserNotFoundException;
import com.shahin.restfulwebservices.models.User;
import com.shahin.restfulwebservices.service.UserServiceImpl;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.*;
import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class UsersController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userServiceImpl.getUsers();
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
    public EntityModel<User> retrieveUser(@PathVariable Integer id) throws ParseException {
        Optional<User> user = userServiceImpl.getUserById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("id - " + id);
        }
        EntityModel<User> userEntityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        userEntityModel.add(linkTo.withRel("all-users"));
        return userEntityModel;
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
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userDao.save(user);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> validate = validator.validate(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        User user = userDao.deleteUser(id);
        if (user == null) {
            throw new UserNotFoundException("{id} = " + id);
        }
    }

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized() {
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }


}
