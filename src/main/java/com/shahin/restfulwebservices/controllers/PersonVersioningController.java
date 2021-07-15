package com.shahin.restfulwebservices.controllers;

import com.shahin.restfulwebservices.models.Name;
import com.shahin.restfulwebservices.models.PersonV1;
import com.shahin.restfulwebservices.models.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

    @GetMapping("v1/person")
    public PersonV1 personV1(){
        return new PersonV1("Ramtin The Cuzz");
    }

    @GetMapping("v2/person")
    public PersonV2 personV2(){
        return new PersonV2(new Name("Ram","Cuzz"));
    }

}
