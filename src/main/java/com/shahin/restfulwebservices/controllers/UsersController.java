package com.shahin.restfulwebservices.controllers;

import com.shahin.restfulwebservices.dao.UserDaoService;
import com.shahin.restfulwebservices.exception.UserNotFoundException;
import com.shahin.restfulwebservices.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import javax.validation.*;
import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class UsersController {

    @Autowired
    private UserDaoService userDaoService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() throws ParseException {
        return userDaoService.findAll();
    }

    @GetMapping("users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable Integer id) throws ParseException {
        User user = userDaoService.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("id - "+id);
        }
        EntityModel<User> userEntityModel = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        userEntityModel.add(linkTo.withRel("all-users"));
        return userEntityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = userDaoService.save(user);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> validate = validator.validate(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id){
        User user = userDaoService.deleteUser(id);
        if (user == null){
            throw new UserNotFoundException("{id} = "+ id);
        }
    }

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized(){
        return messageSource.getMessage("good.morning.message",null, LocaleContextHolder.getLocale());
    }



}
