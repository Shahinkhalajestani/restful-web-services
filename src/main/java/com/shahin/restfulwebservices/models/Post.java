package com.shahin.restfulwebservices.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name ="Post")
public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Size(min=5 , max=100 , message = "Must be At least 2 Characters")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY , cascade = {CascadeType.DETACH,CascadeType.REFRESH,
    CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Post() {
    }

    public Post(Integer id, String description, User user) {
        this.id = id;
        this.description = description;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
