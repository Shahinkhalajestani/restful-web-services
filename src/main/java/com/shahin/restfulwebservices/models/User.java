package com.shahin.restfulwebservices.models;


import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE )
    private Integer id ;
    
    @Column(name="name")
    @Size(min = 2, max=20,message = "must be between 2 and 20")
    private String name ;
    
    @Column(name="birthDate")
    @Past(message = "must be before the current date")
    private Date birthDate ;

    @OneToMany(mappedBy="user")
    private List<Post> posts;


    public User() {
    }

    public User(Integer id, String name, Date birthDate) {
        this.id = id;
        this.name = name;
        birthDate = birthDate;
    }

    public void addPost(Post post){
        if(CollectionUtils.isEmpty(this.posts)){
            this.posts = new ArrayList<>();
            posts.add(post);
        }else{
            posts.add(post);
        }
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
